package com.zichen.managersystem;

import com.zichen.managersystem.common.enumeration.BizCodeEnum;
import com.zichen.managersystem.common.enumeration.RoleEnum;
import com.zichen.managersystem.common.exception.ParamInvalidException;
import com.zichen.managersystem.common.utils.JsonData;
import com.zichen.managersystem.controller.RoleController;
import com.zichen.managersystem.interceptor.RoleInterceptor;
import com.zichen.managersystem.model.entity.Role;
import com.zichen.managersystem.model.param.PermissionParam;
import com.zichen.managersystem.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ManagerSystemControllerTests {

    @InjectMocks
    @Spy
    private RoleController roleController;

    @Mock
    RoleServiceImpl roleServiceImpl;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(roleController).build();
    }


    @Test
    void getRole_withRoleInfo_shouldReturnSuccess() {
        // Arrange
        Role mockRole = new Role(1L, "fuzichen", RoleEnum.USER.getMsg());
        RoleInterceptor.roleInfo.set(mockRole);
        // Act
        JsonData result = roleController.getRole();

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getCode());
        assertEquals("SUCCESS", result.getMsg());
        assertEquals(mockRole, result.getData());
    }

    @Test
    void getRole_withoutRoleInfo_shouldReturnError() {
        // Arrange
        RoleInterceptor.roleInfo.set(null);

        // Act
        JsonData result = roleController.getRole();

        // Assert
        assertNotNull(result);
        assertEquals(BizCodeEnum.MISSING_ROLE.getCode(), result.getCode());
        assertEquals(BizCodeEnum.MISSING_ROLE.getMsg(), result.getMsg());
    }



    @Test
    public void testAddPermissionSuccess() throws Exception {
        // Arrange
        PermissionParam permissionParam = new PermissionParam();
        permissionParam.setUserId(1);
        permissionParam.setEndpoint(List.of("endpoint1", "endpoint2"));
        when(roleServiceImpl.addPermission(permissionParam)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(post("/ms/admin/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1,\"endpoint\":[\"endpoint1\",\"endpoint2\"]}"))
                .andExpect(status().isOk())
                .andReturn();

        verify(roleServiceImpl, times(1)).addPermission(permissionParam);
    }


    @Test
    public void testAddPermissionWithParamInvalidException() throws Exception {
        // Arrange
        PermissionParam permissionParam = new PermissionParam();
        permissionParam.setUserId(null);
        permissionParam.setEndpoint(null);
        doThrow(new ParamInvalidException("Invalid parameter")).when(roleServiceImpl).addPermission(permissionParam);

        // Act & Assert
        mockMvc.perform(post("/ms/admin/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":null,\"endpoint\":null}"))
                .andExpect(status().isOk())
                .andReturn();

        verify(roleServiceImpl, times(1)).addPermission(permissionParam);
    }
}
