package com.zichen.managersystem.service;

import com.zichen.managersystem.common.exception.ParamInvalidException;
import com.zichen.managersystem.model.param.PermissionParam;

import java.util.Map;

public interface RoleService {

    Boolean addPermission(PermissionParam permissionParam) throws ParamInvalidException;

    Map<Integer, Boolean> getUsersByResource(String resource);
}
