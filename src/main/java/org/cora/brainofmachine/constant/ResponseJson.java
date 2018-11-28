package org.cora.brainofmachine.constant;

import com.alibaba.fastjson.JSONObject;

/**
 * @author Colin
 */

public enum ResponseJson {
    /**
     * invalid
     */
    INVALID("9501", "invalid"),
    /**
     * success
     */
    SUCCESS("1000", "success"),
    /**
     * fail
     */
    FAIL("1001", "fail"),
    /**
     * empty
     */
    EMPTY("2000", "empty");

    private String code;
    private String message;

    ResponseJson(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Constants.CODE, this.code);
        jsonObject.put(Constants.MESSAGE, this.message);
        return jsonObject;
    }

    public JSONObject toJSONObject(String message) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Constants.CODE, this.code);
        jsonObject.put(Constants.MESSAGE, message);
        return jsonObject;
    }

    public String toJSONString() {
        return this.toJSONObject().toString();
    }

    public String toJSONString(String message) {
        return this.toJSONObject(message).toString();
    }
}
