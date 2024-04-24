package com.zichen.managersystem.controller;

import com.zichen.managersystem.common.enumeration.BizCodeEnum;
import com.zichen.managersystem.common.exception.ParamInvalidException;
import com.zichen.managersystem.common.utils.JsonData;
import com.zichen.managersystem.interceptor.RoleInterceptor;
import com.zichen.managersystem.model.entity.Role;
import com.zichen.managersystem.model.param.PermissionParam;
import com.zichen.managersystem.service.RoleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.JobName;
import java.util.Map;

/**
 * manager system controller
 */

@RestController
@RequestMapping("/ms")
public class RoleController {

    @Resource
    RoleService roleServiceImpl;

    /**
     * Please add the accountName in the header.
     * e.g accountName:fuzichen for admin or linyixi for user
     *
     * get role info
     * @return JsonData
     */

    @GetMapping("/role")
    public JsonData getRole() {
        Role role = RoleInterceptor.roleInfo.get();
        if (role == null) {
            return JsonData.buildError(BizCodeEnum.MISSING_ROLE.getMsg(), BizCodeEnum.MISSING_ROLE.getCode());
        }
        return JsonData.buildSuccess(role, "SUCCESS");
    }

    /**
     * add the accesses for users
     * @param permissionParam
     * @return
     */
    @PostMapping("/admin/addUser")
    public JsonData addPermission(@RequestBody PermissionParam permissionParam)  {
        try {
            Boolean res = roleServiceImpl.addPermission(permissionParam);
            if (!res) {
                return JsonData.buildError(BizCodeEnum.WRONG_ROLE.getMsg(), BizCodeEnum.WRONG_ROLE.getCode());
            } else {
                return JsonData.buildSuccess("SUCCESS", 0);
            }
        }  catch (ParamInvalidException pe) {
            return JsonData.buildError(BizCodeEnum.VALID_EXCEPTION.getMsg(), BizCodeEnum.VALID_EXCEPTION.getCode());
        }

    }

    /**
     * get who has specific resource
     * @param resource
     * @return
     */
    @GetMapping("/user/{resource}")
    public JsonData getUsersByResource(@PathVariable(value = "resource") String resource){
        Map<Integer, Boolean> usersByResource = roleServiceImpl.getUsersByResource(resource);
        return JsonData.buildSuccess(usersByResource,"SUCCESS");
    }
}
