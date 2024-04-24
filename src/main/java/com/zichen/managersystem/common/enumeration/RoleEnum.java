package com.zichen.managersystem.common.enumeration;

public enum RoleEnum {

    ADMIN(1, "admin"),
    USER(2,"user");


    private final Integer code;
    private final String msg;

    RoleEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
