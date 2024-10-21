package com.king.exception;

import com.king.model.enums.CommonError;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * @author King
 * @version 1.0
 * @description 全局异常处理器
 * @date 2024/10/21 15:04
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public RestErrorResponse exception(Exception e) {

        log.error("【系统异常】{}", e.getMessage(), e);
        e.printStackTrace();

        if (e.getMessage().equals("不允许访问")) {
            return new RestErrorResponse("没有操作此功能的权限");
        }

        //唯一索引异常
        if (e.getMessage().contains("java.sql.SQLIntegrityConstraintViolationException")) {

            Throwable cause = e.getCause();
            String message = cause.getMessage();
            if (message.contains("phone")) {
                return new RestErrorResponse("该手机号已存在");
            }
            if (message.contains("account_number")) {
                return new RestErrorResponse("该账号已存在");
            }
        }


        return new RestErrorResponse(CommonError.UNKNOWN_ERROR.getErrMessage());
    }

    @ResponseBody
    @ExceptionHandler(NettyChatException.class)
    @ResponseStatus(HttpStatus.OK)
    public RestErrorResponse graduationDesignException(NettyChatException e) {
        log.error("【自定义异常】：{}", e.getMsg(), e);
        //解析异常信息
        String errMessage = e.getMsg();
        return new RestErrorResponse(errMessage);

    }

    @ResponseBody
    @ExceptionHandler(MessagingException.class)
    @ResponseStatus(HttpStatus.OK)
    public RestErrorResponse messagingException(MessagingException e) {

        log.error("【邮件发送异常】{}", e.getMessage(), e);
        e.printStackTrace();
        return new RestErrorResponse(CommonError.EMAIL_SEND_ERROR.getErrMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public RestErrorResponse doValidException(MethodArgumentNotValidException argumentNotValidException) {

        BindingResult bindingResult = argumentNotValidException.getBindingResult();
        StringBuffer errMsg = new StringBuffer();

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        fieldErrors.forEach(error -> {
            errMsg.append(error.getDefaultMessage()).append(",");
        });
        log.error(errMsg.toString());
        return new RestErrorResponse(errMsg.toString());
    }

}
