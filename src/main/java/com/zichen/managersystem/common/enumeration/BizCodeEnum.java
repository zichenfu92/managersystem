package com.zichen.managersystem.common.enumeration;


import lombok.Getter;


@Getter
public enum BizCodeEnum {

    MISSING_ROLE(10000, "This accountName doesn't belongs to any role, Please contact your admin!"),
    WRONG_ROLE(10001, "You don't have permission to add accesses for users"),
    VALID_EXCEPTION(10003, "Parameters are invalid");


    private final Integer code;
    private final String msg;

    BizCodeEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

}
