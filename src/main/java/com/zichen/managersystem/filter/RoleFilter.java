package com.zichen.managersystem.filter;

import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson2.JSON;
import com.zichen.managersystem.common.constant.Constant;
import com.zichen.managersystem.common.peoperty.RoleProperty;
import com.zichen.managersystem.model.entity.Role;
import jakarta.annotation.Resource;
import jakarta.servlet.annotation.WebFilter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;

@WebFilter("/*")
public class RoleFilter implements Filter {
    @Resource
    RoleProperty roleProperty;

    private Map<String, Role> maps;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        maps = roleProperty.getMaps();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("doFilter...");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HeaderMapRequestWrapper requestWrapper = new HeaderMapRequestWrapper(request);
        //
        String accountName = request.getHeader(Constant.ACCOUNT_NAME);
        if (!maps.containsKey(accountName)) {
            requestWrapper.addHeader(Constant.ROLE_INFO, Constant.EMPTY);
            filterChain.doFilter(requestWrapper, servletResponse);
        }
        //
        Role role = maps.get(accountName);
        String jsonString = JSON.toJSONString(role);
        String base64Encode = new String(Base64.getEncoder().encode(jsonString.getBytes()));
        requestWrapper.addHeader(Constant.ROLE_INFO, base64Encode);
        filterChain.doFilter(requestWrapper, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
