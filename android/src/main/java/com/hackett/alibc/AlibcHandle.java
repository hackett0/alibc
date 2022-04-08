package com.hackett.alibc;

import android.app.Activity;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;

import com.ali.auth.third.core.model.Session;
import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.trade.biz.AlibcTradeCallback;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.alibaba.baichuan.trade.biz.core.taoke.AlibcTaokeParams;
import com.alibaba.baichuan.trade.biz.login.AlibcLogin;
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback;

import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.MethodChannel.Result;

public class AlibcHandle {
    private Activity activity;
    private boolean isInit = false;

    public void setActivity(Activity activity) {
        this.activity = activity;

        if (!isInit) {
            init();
            isInit = true;
        }
    }

    // 初始化
    public void init() {
        AlibcTradeSDK.asyncInit(activity.getApplication(), new AlibcTradeInitCallback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onFailure(int code, String msg) {
            }
        });
    }

    // 登录
    public void login(final Result result) {
        AlibcLogin alibcLogin = AlibcLogin.getInstance();
        if (alibcLogin.isLogin()) {
            Session session = AlibcLogin.getInstance().getSession();
            result.success(AlibcResult.success(AlibcHelpers.sessionToMap(session)));
            return;
        }

        alibcLogin.showLogin(new AlibcLoginCallback() {
            @Override
            public void onSuccess(int code, String userId, String nick) {
                Session session = AlibcLogin.getInstance().getSession();
                result.success(AlibcResult.success(AlibcHelpers.sessionToMap(session)));
            }

            @Override
            public void onFailure(int code, String msg) {
                result.success(AlibcResult.error(code, msg, null));
            }
        });
    }

    // 是否登录
    public void islogin(final Result result) {
        AlibcLogin alibcLogin = AlibcLogin.getInstance();
        if (alibcLogin.isLogin()) {
            Session session = AlibcLogin.getInstance().getSession();
            result.success(AlibcResult.success(AlibcHelpers.sessionToMap(session)));
            return;
        }

        result.success(AlibcResult.success(null));
    }

    // 退出
    public void logout(final Result result) {
        AlibcLogin alibcLogin = AlibcLogin.getInstance();
        if (alibcLogin.isLogin()) {
            alibcLogin.logout(new AlibcLoginCallback() {
                @Override
                public void onSuccess(int code, String openId, String userNick) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("openId", openId);
                    map.put("userNick", userNick);
                    result.success(AlibcResult.success(map));
                }

                @Override
                public void onFailure(int code, String msg) {
                    result.success(AlibcResult.error(code, msg, null));
                }
            });
        }
    }

    // 打开URL
    public void openURL(String kit,
                        String url,
                        AlibcShowParams show,
                        AlibcTaokeParams taoke,
                        Map<String, String> track,
                        final Result result) {
        // 通过百川内部的webview打开页面
        AlibcTrade.openByUrl(activity, kit, url, null,
                new WebViewClient(), new WebChromeClient(),
                show, taoke, track, new AlibcTradeCallback() {
                    @Override
                    public void onTradeSuccess(AlibcTradeResult tradeResult) {
                        result.success(AlibcResult.success(tradeResult));
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        result.success(AlibcResult.error(code, msg, null));
                    }
                });
    }

    // 打开Page
    public void openPage(
            AlibcBasePage page,
            String type,
            AlibcShowParams show,
            AlibcTaokeParams taoke,
            Map<String, String> track,
            final Result result) {
        AlibcTrade.openByBizCode(activity, page, null, new WebViewClient(), new WebChromeClient(),
                type, show, taoke, track, new AlibcTradeCallback() {
                    @Override
                    public void onTradeSuccess(AlibcTradeResult tradeResult) {
                        result.success(AlibcResult.success(tradeResult));
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        result.success(AlibcResult.error(code, msg, null));
                    }
                });
    }


    // 全局设置
    // 设置淘客打点策略 是否异步
    public void setSyncForTaoke(boolean sync) {
        AlibcTradeSDK.setSyncForTaoke(sync);
    }

    // 淘宝客参数
    public void setTaokeParams(AlibcTaokeParams params) {
        AlibcTradeSDK.setTaokeParams(params);
    }

    // 渠道信息
    public void setChannel(String type, String channel) {
        AlibcTradeSDK.setChannel(type, channel);
    }

    // ISV的Code
    public void setISVCode(String code) {
        AlibcTradeSDK.setISVCode(code);
    }

    // ISV的版本，通常为本APP版本
    public void setISVVersion(String version) {
        AlibcTradeSDK.setISVVersion(version);
    }
}
