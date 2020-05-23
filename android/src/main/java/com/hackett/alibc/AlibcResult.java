package com.hackett.alibc;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class AlibcResult implements Serializable {

    private static final long serialVersionUID = 1L;
    private int code;
    private String msg;
    private Object data;

    public static Map<String, Object> success(Object data) {
        return new AlibcResult(0, "成功", data).toMap();
    }

    public static Map<String, Object> error(int code, String msg, Object data) {
        return new AlibcResult(code, msg, data).toMap();
    }

    private AlibcResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private Map<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("msg", msg);
        map.put("data", data);
        return map;
    }
}
