package com.ershou.ershou.exception;

/**
 * 通用业务异常类
 */
public class GeneralBusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    // 错误代码（可以是 HTTP 状态码或者自定义的错误代码）
    private String errorCode;

    // 构造方法：仅包含异常信息
    public GeneralBusinessException(String message) {
        super(message);
    }

    // 构造方法：包含异常信息和错误代码
    public GeneralBusinessException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    // 获取错误代码
    public String getErrorCode() {
        return errorCode;
    }

    // 可以重写 toString 方法，输出更详细的异常信息
    @Override
    public String toString() {
        return "GeneralBusinessException{"
                + "message='" + getMessage() + '\''
                + ", errorCode='" + errorCode + '\''
                + '}';
    }
}
