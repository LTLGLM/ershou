package com.ershou.ershou.utils;

import com.ershou.ershou.constant.HttpStatus;

import java.util.HashMap;

public class AjaxResult extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public static final String CODE_TAG = "code";

    public static final String MSG_TAG = "message";

    public static final String DATA_TAG = "data";

    /**
     * 空消息
     */
    public AjaxResult() {
    }

    /**
     * @param code
     * @param msg
     */
    public AjaxResult(int code, String msg) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }

    /**
     * @param code
     * @param msg
     * @param data
     */
    public AjaxResult(int code, String msg, Object data) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (data != null) {
            super.put(DATA_TAG, data);
        }
    }

    public static AjaxResult success() {
        return AjaxResult.success("操作成功");
    }

    public static AjaxResult success(String msg) {
        return AjaxResult.success(msg, null);
    }

    public static AjaxResult success(Object data) {
        return AjaxResult.success("操作成功", data);
    }

    public static AjaxResult success(String msg, Object data) {
        return new AjaxResult(HttpStatus.SUCCESS, msg, data);
    }

    public static AjaxResult warn(String msg) {
        return AjaxResult.warn(msg, null);
    }

    public static AjaxResult warn(String msg, Object data) {
        return new AjaxResult(HttpStatus.WARN, msg, data);
    }

    public static AjaxResult error() {
        return AjaxResult.error("操作失败");
    }

    public static AjaxResult error(String msg) {
        return AjaxResult.error(msg, null);
    }

    public static AjaxResult error(String msg, Object data) {
        return AjaxResult.error(HttpStatus.ERROR, msg, data);
    }

    public static AjaxResult error(Integer code,String msg, Object data){
        return new AjaxResult(code, msg, data);
    }

    /**
     * 方便链式调用
     *
     * @param key   键
     * @param value 值
     * @return 数据对象
     */
    @Override
    public Object put(String key, Object value) {
        return super.put(key, value);
    }
}
