package com.zichen.managersystem.common.exception;


public class ParamInvalidException extends Exception {
    private String msg;
    public ParamInvalidException(String msg){
        super(msg);
    }
}
