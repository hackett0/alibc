package com.hackett.alibc;

import com.ali.auth.third.core.model.Session;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcAddCartPage;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcDetailPage;
import com.alibaba.baichuan.android.trade.page.AlibcMyCartsPage;
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
        final Map data = call.argument("PageParams");
        if (data == null) {
            return null;
        }

        switch ((String) data.get("type")) {
            case "detail":
                return new AlibcDetailPage((String) data.get("itemId"));
            case "shop":
                return new AlibcShopPage((String) data.get("shopId"));
            case "cart":
                return new AlibcMyCartsPage();
            case "addCart":
                return new AlibcAddCartPage((String) data.get("itemId"));
            default:
                return null;
        }
    }

    public static AlibcShowParams callShowParams(MethodCall call) {
        final Map data = call.argument("ShowParams");
        if (data == null) {
            return null;
        }

        AlibcShowParams params = new AlibcShowParams();
        if (data.containsKey("backUrl"))
            params.setBackUrl((String) data.get("backUrl"));
        if (data.containsKey("degradeUrl"))
            params.setDegradeUrl((String) data.get("degradeUrl"));
        if (data.containsKey("openType"))
            params.setOpenType((String) data.get("openType") == "native" ? OpenType.Native : OpenType.Auto);
        if (data.containsKey("clientType"))
            params.setClientType((String) data.get("clientType"));
        if (data.containsKey("title"))
            params.setTitle((String) data.get("title"));
        if (data.containsKey("proxy"))
            params.setProxyWebview((String) data.get("proxy") == "true");
        if (data.containsKey("failedMode"))
            params.setNativeOpenFailedMode(fallModeType((String) data.get("failedMode")));
        if (data.containsKey("originalOpenType"))
            params.setOriginalOpenType(
                    (String) data.get("originalOpenType") == "native" ? OpenType.Native : OpenType.Auto);

        return params;
    }

    public static AlibcTaokeParams callTaokeParams(MethodCall call) {
        final Map data = call.argument("TaokeParams");
        if (data == null) {
            return null;
        }

        AlibcTaokeParams params = new AlibcTaokeParams((String) data.get("pid"), (String) data.get("subPid"),
                (String) data.get("unionId"));
        params.setAdzoneid((String) data.get("adzoneId"));
        params.setExtraParams((Map) call.argument("ExtraParams"));
        return params;
    }

    public static AlibcFailModeType fallModeType(String value) {
        switch (value) {
            case "brower":
                return AlibcFailModeType.AlibcNativeFailModeJumpBROWER;
            case "download":
                return AlibcFailModeType.AlibcNativeFailModeJumpDOWNLOAD;
            case "h5":
                return AlibcFailModeType.AlibcNativeFailModeJumpH5;
            default:
                return AlibcFailModeType.AlibcNativeFailModeNONE;
        }
    }
}
