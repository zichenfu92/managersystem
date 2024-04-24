package com.zichen.managersystem.common.exception;

import com.zichen.managersystem.common.enumeration.BizCodeEnum;
import com.zichen.managersystem.common.utils.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.zichen.managersystem.controller")
public class ExceptionControllerAdvice {

    @ExceptionHandler(value = ParamInvalidException.class)
    public JsonData handleValidException(MethodArgumentNotValidException e){
        return JsonData.buildError(BizCodeEnum.VALID_EXCEPTION.getMsg(), BizCodeEnum.VALID_EXCEPTION.getCode());
    }

}
