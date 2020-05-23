package com.hackett.alibc;

import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.widget.Toast;
import android.content.Intent;
import com.ali.auth.third.core.model.Session;
import com.alibaba.baichuan.trade.biz.context.AlibcResultType;
import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcDetailPage;
import com.alibaba.baichuan.android.trade.page.AlibcMyCartsPage;
import com.alibaba.baichuan.android.trade.page.AlibcShopPage;
import com.alibaba.baichuan.trade.biz.applink.adapter.AlibcFailModeType;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.alibaba.baichuan.trade.biz.core.taoke.AlibcTaokeParams;
import com.alibaba.baichuan.trade.biz.login.AlibcLogin;
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.alibaba.baichuan.trade.common.utils.AlibcLogger;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.MethodCall;
import java.util.HashMap;
import android.app.AlertDialog;
import static com.hackett.alibc.AlibcUtil.*;
import com.hackett.alibc.web.WebViewActivity;
import java.util.Map;

public class AlibcHandle {

    private static AlibcHandle handle;
    private Registrar register;

    public AlibcHandle(Registrar register) {
        this.register = register;
    }

    // 初始化
    public boolean init(Result result) {
        AlibcTradeSDK.asyncInit(register.activity().getApplication(), new AlibcTradeInitCallback() {
            @Override
            public void onSuccess() {
                result.success(AlibcResult.success(null));
            }

            @Override
            public void onFailure(int code, String msg) {
                result.success(AlibcResult.error(code, msg, null));
            }
        });
    }

    // 登录淘宝
    public void login(Result result) {
        final AlibcLogin alibcLogin = AlibcLogin.getInstance();
        if (alibcLogin.isLogin()) {
            Session session = AlibcLogin.getInstance().getSession();
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("nick", session.nick);
            userInfo.put("avatarUrl", session.avatarUrl);
            userInfo.put("openId", session.openId);
            userInfo.put("openSid", session.openSid);
            userInfo.put("topAccessToken", session.topAccessToken);
            userInfo.put("topAuthCode", session.topAuthCode);
            result.success(AlibcResult.success(userInfo));
            return;
        }

        alibcLogin.showLogin(new AlibcLoginCallback() {
            @Override
            public void onSuccess(int loginResult, String openId, String userNick) {
                Map<String, Object> userInfo = new HashMap<>();
                Session session = AlibcLogin.getInstance().getSession();
                userInfo.put("nick", session.nick);
                userInfo.put("avatarUrl", session.avatarUrl);
                userInfo.put("openId", session.openId);
                userInfo.put("openSid", session.openSid);
                userInfo.put("topAccessToken", session.topAccessToken);
                userInfo.put("topAuthCode", session.topAuthCode);
                result.success(AlibcResult.success(userInfo));
            }

            @Override
            public void onFailure(int code, String msg) {
                result.success(AlibcResult.error(code, msg, null));
            }
        });
    }

    // 退出登录
    public void logout(Result result) {
        AlibcLogin alibcLogin = AlibcLogin.getInstance();
        alibcLogin.logout(new AlibcLoginCallback() {
            @Override
            public void onSuccess(int loginResult, String openId, String userNick) {
                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("openId", openId);
                userInfo.put("userNick", userNick);
                result.success(AlibcResult.success(userInfo));
            }

            @Override
            public void onFailure(int code, String msg) {
                result.success(AlibcResult.error(code, msg, null));
            }
        });
    }

    // 打开淘宝URL
    public void openURL(String backUrl, Result result) {
        AlibcShowParams showParams = new AlibcShowParams();
        AlibcTaokeParams taokeParams = new AlibcTaokeParams("", "", "");

        showParams.setBackUrl(backUrl);

        if (call.argument(AlibcConst.key_OpenType) != null) {
            System.out.println("openType" + call.argument(AlibcConst.key_OpenType));
            showParams.setOpenType(getOpenType("" + call.argument(AlibcConst.key_OpenType)));
        }
        if (call.argument(AlibcConst.key_ClientType) != null) {
            System.out.println("clientType " + call.argument(AlibcConst.key_ClientType));
            showParams.setClientType(getClientType("" + call.argument(AlibcConst.key_ClientType)));
        }
        if (call.argument("taokeParams") != null) {
            taokeParams = getTaokeParams(call.argument("taokeParams"));
        }
        if ("false".equals(call.argument("isNeedCustomNativeFailMode"))) {
            showParams.setNativeOpenFailedMode(AlibcFailModeType.AlibcNativeFailModeNONE);
        } else if (call.argument(AlibcConst.key_NativeFailMode) != null) {
            showParams.setNativeOpenFailedMode(getFailModeType("" + call.argument(AlibcConst.key_NativeFailMode)));
        }

        Map<String, String> trackParams = new HashMap<>();
        String url = call.argument("url");
        // 以显示传入url的方式打开页面（第二个参数是套件名称）
        AlibcTrade.openByUrl(register.activity(), "", url, null, new WebViewClient(), new WebChromeClient(), showParams,
                taokeParams, trackParams, new AlibcTradeCallback() {
                    @Override
                    public void onTradeSuccess(AlibcTradeResult tradeResult) {
                        Map<String, Object> results = new HashMap<>();
                        if (AlibcResultType.TYPECART == tradeResult.resultType) {
                            results.put("type", 1);
                        } else if (AlibcResultType.TYPEPAY == tradeResult.resultType) {
                            results.put("type", 0);
                            results.put("payFailedOrders", tradeResult.payResult.payFailedOrders);
                            results.put("paySuccessOrders", tradeResult.payResult.paySuccessOrders);
                        }
                        result.success(AlibcResult.success(results));
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        result.success(new AlibcResult(Integer.toString(code), msg, null));
                    }
                });
    }

    /**
     * 打开商店
     * 
     * @param call
     * @param result
     */
    public void openShop(MethodCall call, Result result) {
        AlibcBasePage page = new AlibcShopPage(call.argument("shopId"));
        openByBizCode(page, "shop", call, result);
    }

    /**
     * 打开购物车
     * 
     * @param result
     */
    public void openCart(MethodCall call, Result result) {
        AlibcBasePage page = new AlibcMyCartsPage();
        openByBizCode(page, "cart", call, result);
    }

    /**
     * 打开商品详情
     * 
     * @param call   call.argument["itemID"] 详情id
     * @param result
     */
    public void openItemDetail(MethodCall call, Result result) {
        AlibcBasePage page = new AlibcDetailPage(call.argument("itemID"));
        openByBizCode(page, "detail", call, result);
    }

    private void openByBizCode(AlibcBasePage page, String type, MethodCall call, Result result) {
        AlibcShowParams showParams = new AlibcShowParams();
        AlibcTaokeParams taokeParams = new AlibcTaokeParams("", "", "");

        showParams.setBackUrl(call.argument(AlibcConst.key_BackUrl));

        if (call.argument(AlibcConst.key_OpenType) != null) {
            showParams.setOpenType(getOpenType("" + call.argument(AlibcConst.key_OpenType)));
        }
        if (call.argument(AlibcConst.key_ClientType) != null) {
            showParams.setClientType(getClientType("" + call.argument(AlibcConst.key_ClientType)));
        }
        if (call.argument("taokeParams") != null) {
            taokeParams = getTaokeParams(call.argument("taokeParams"));
        }

        if ("false".equals(call.argument("isNeedCustomNativeFailMode"))) {
            showParams.setNativeOpenFailedMode(AlibcFailModeType.AlibcNativeFailModeNONE);
        } else if (call.argument(AlibcConst.key_NativeFailMode) != null) {
            showParams.setNativeOpenFailedMode(getFailModeType("" + call.argument(AlibcConst.key_NativeFailMode)));
        }

        Map<String, String> trackParams = new HashMap<>();
        AlibcTrade.openByBizCode(register.activity(), page, null, new WebViewClient(), new WebChromeClient(), type,
                showParams, taokeParams, trackParams, new AlibcTradeCallback() {
                    @Override
                    public void onTradeSuccess(AlibcTradeResult tradeResult) {
                        Map<String, Object> results = new HashMap<>();
                        if (AlibcResultType.TYPECART == tradeResult.resultType) {
                            results.put("type", 1);
                        } else if (AlibcResultType.TYPEPAY == tradeResult.resultType) {
                            results.put("type", 0);
                            results.put("payFailedOrders", tradeResult.payResult.payFailedOrders);
                            results.put("paySuccessOrders", tradeResult.payResult.paySuccessOrders);
                        }
                        result.success(AlibcResult.success(results));
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        // 失败回调信息
                        result.success(new AlibcResult(Integer.toString(code), msg, null));
                    }
                });
    }

    /**
     * 设置淘客打点策略 是否异步
     * 
     * @param call
     */
    public void syncForTaoke(MethodCall call) {
        AlibcTradeSDK.setSyncForTaoke(call.argument("isSync"));
    }

    /**
     * TODO
     * 
     * @param call
     */
    public void useAlipayNative(MethodCall call) {
        AlibcTradeSDK.setShouldUseAlipay(call.argument("isNeed"));
    }

}