package com.ershou.ershou.exception;

import com.ershou.ershou.constant.HttpStatus;
import com.ershou.ershou.utils.AjaxResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GeneralBusinessException.class)
    public AjaxResult handleGeneralBusinessException(GeneralBusinessException ex){
        return AjaxResult.error(ex.getMessage());
    }

    // 你也可以捕获其他常见异常，例如 NullPointerException 等
    @ExceptionHandler(Exception.class)
    public AjaxResult handleException(Exception ex) {
        return AjaxResult.error("系统内部错误: " + ex.getMessage());
    }
}
