package com.hackett.alibc;

import com.ali.auth.third.core.model.Session;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcAddCartPage;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcDetailPage;
import com.alibaba.baichuan.android.trade.page.AlibcMyCartsPage;
import com.alibaba.baichuan.android.trade.page.AlibcMyOrdersPage;
import com.alibaba.baichuan.android.trade.page.AlibcShopPage;
import com.alibaba.baichuan.trade.biz.applink.adapter.AlibcFailModeType;
import com.alibaba.baichuan.trade.biz.core.taoke.AlibcTaokeParams;

import io.flutter.plugin.common.MethodCall;
import java.util.HashMap;
import java.util.Map;

public class AlibcHelpers {
    public static Map<String, String> sessionToMap(Session session) {
        Map<String, String> map = new HashMap<>();
        map.put("userid", session.userid);
        map.put("nick", session.nick);
        map.put("avatarUrl", session.avatarUrl);
        map.put("openId", session.openId);
        map.put("openSid", session.openSid);
        map.put("topAccessToken", session.topAccessToken);
        map.put("topAuthCode", session.topAuthCode);
        map.put("topExpireTime", session.topExpireTime);
        map.put("ssoToken", session.ssoToken);
        map.put("havanaSsoToken", session.havanaSsoToken);
        return map;
    }

    public static AlibcBasePage callPageParams(MethodCall call) {
        String type = (String) call.argument("type");
        switch (type)
        {
            case "detail": return new AlibcDetailPage(callParam(call, "itemId", ""));
            case "shop": return new AlibcShopPage(callParam(call, "shopId", ""));
            case "cart": return new AlibcMyCartsPage();
            case "addCart": return new AlibcAddCartPage(callParam(call, "itemId", ""));

            // status   默认跳转页面(0:全部, 1:待付款, 2:待发货, 3:待收货, 4:待评价)
            // allOrder 为 true 显示所有订单，为false只显示通过当前app下单的订单
            case "myOrder": return new AlibcMyOrdersPage(
                    Integer.parseInt(callParam(call, "status", "0")),
                    callParam(call, "allOrder", "true") == "true");
            default: return null;
        }
    }

    public static AlibcShowParams callShowParams(MethodCall call) {
        AlibcShowParams params = new AlibcShowParams();
        params.setBackUrl(callParam(call, "backUrl", ""));
        params.setDegradeUrl(callParam(call, "degradeUrl", ""));
        params.setOpenType(callParam(call, "openType", "auto") == "native" ? OpenType.Native : OpenType.Auto);
        params.setClientType(callParam(call, "clientType", "taobao"));
        params.setTitle(callParam(call, "title", ""));
        params.setProxyWebview(callParam(call, "proxy", "false") == "true");
        params.setShowTitleBar(callParam(call, "bar", "false") == "true");
        params.setNativeOpenFailedMode(fallModeType(callParam(call, "failedMode", "")));
        params.setOriginalOpenType(callParam(call, "originalOpenType", "auto") == "native" ? OpenType.Native : OpenType.Auto);
        return params;
    }

    public static AlibcTaokeParams callTaokeParams(MethodCall call) {
        AlibcTaokeParams params = new AlibcTaokeParams(
                callParam(call, "pid", ""),
                callParam(call, "subPid", ""),
                callParam(call, "unionId", ""));
        params.setAdzoneid(callParam(call, "adzoneId", ""));
        // params.setExtraParams(Map<String, String> var1);
        return params;
    }

    public static Map<String, String> callTrackParams(MethodCall call) {
        Map<String, String> params = new HashMap();
        return params;
    }

    public static String callParam(MethodCall call, String key, String value) {
        if (call.hasArgument(key)) {
            return (String)call.argument(key);
        }
        return value;
    }

    public static AlibcFailModeType fallModeType(String value) {
        switch (value) {
            case "brower": return AlibcFailModeType.AlibcNativeFailModeJumpBROWER;
            case "download": return AlibcFailModeType.AlibcNativeFailModeJumpDOWNLOAD;
            case "h5": return AlibcFailModeType.AlibcNativeFailModeJumpH5;
            default: return AlibcFailModeType.AlibcNativeFailModeNONE;
        }
    }
}
