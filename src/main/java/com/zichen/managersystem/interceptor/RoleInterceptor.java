package com.zichen.managersystem.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zichen.managersystem.common.constant.Constant;
import com.zichen.managersystem.common.enumeration.BizCodeEnum;
import com.zichen.managersystem.common.utils.JsonData;
import com.zichen.managersystem.model.entity.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class RoleInterceptor implements HandlerInterceptor {

    public static ThreadLocal<Role> roleInfo = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String base64Encode = request.getHeader(Constant.ROLE_INFO);

        if (StringUtils.isEmpty(base64Encode)) {
            return false;
        } else {
            Role role  = JSON.parseObject(new String(Base64.getDecoder().decode(base64Encode)),
                    new TypeReference<Role>() {});
            roleInfo.set(role);
            return true;
        }
    }
}
