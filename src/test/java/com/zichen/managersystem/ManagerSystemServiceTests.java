package com.zichen.managersystem;

import com.zichen.managersystem.common.enumeration.RoleEnum;
import com.zichen.managersystem.common.exception.ParamInvalidException;
import com.zichen.managersystem.model.entity.Role;
import com.zichen.managersystem.model.param.PermissionParam;
import com.zichen.managersystem.interceptor.RoleInterceptor;
import com.zichen.managersystem.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ManagerSystemServiceTests {

    @InjectMocks
    @Spy
    private RoleServiceImpl roleServiceImpl;

    @Mock
    private Yaml yaml;

    @BeforeEach
    public void setUp() {
        Role role = new Role(1L, "fuzichen", RoleEnum.ADMIN.getMsg());
        RoleInterceptor.roleInfo.set(role);
    }

    @Test
    public void testAddPermissionWithValidParam() throws ParamInvalidException, IOException {
        PermissionParam permissionParam = new PermissionParam();
        permissionParam.setUserId(1);
        permissionParam.setEndpoint(new ArrayList<>());

        Boolean result = roleServiceImpl.addPermission(permissionParam);

        assertTrue(result);
    }

    @Test
    public void testAddPermissionWithInvalidParam() {
        PermissionParam permissionParam = new PermissionParam();
        permissionParam.setUserId(1);
        permissionParam.setEndpoint(null);

        // 执行测试方法并验证期望的异常被抛出
        assertThrows(ParamInvalidException.class, () -> {
            roleServiceImpl.addPermission(permissionParam);
        });
    }

    @Test
    public void testAddPermissionWithNonAdminRole() throws ParamInvalidException {
        Role role = new Role(2L, "linyixi", RoleEnum.USER.getMsg());
        RoleInterceptor.roleInfo.set(role);

        PermissionParam permissionParam = new PermissionParam();
        permissionParam.setUserId(1);
        permissionParam.setEndpoint(new ArrayList<>());

        Boolean result = roleServiceImpl.addPermission(permissionParam);

        assertFalse(result);
    }

}
