package com.mygit.common.exception;

import com.mygit.common.ApiResult;
import org.apache.shiro.ShiroException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;



/**
 * 全局異常
 * @author
 * @date 2021/5/2
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * logger
     */
   private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

   @ResponseStatus(HttpStatus.BAD_REQUEST)
   @ExceptionHandler(RuntimeException.class)
   public ApiResult handlerRunTimeException(Exception e){
       logger.error("RunTimeException"+e.getMessage());
       return ApiResult.sendFail(400,e.getMessage(),null);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public ApiResult handlerShiroException(Exception e){
        logger.error("ShiroException"+e.getMessage());
        return ApiResult.sendFail(401,e.getMessage(),e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult handlerMethodArgumentNotValidException(MethodArgumentNotValidException e){
        logger.error("MethodArgumentNotValidException"+e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        for (FieldError error :
                bindingResult.getFieldErrors()) {
            logger.error("參數:{}校驗失敗,原因:{}",error.getField(),error.getDefaultMessage());
        }
        //取第一個異常原因返回
        ObjectError firstError = bindingResult.getAllErrors().stream().findFirst().get();
        return ApiResult.sendFail(firstError.getDefaultMessage());
    }

}
