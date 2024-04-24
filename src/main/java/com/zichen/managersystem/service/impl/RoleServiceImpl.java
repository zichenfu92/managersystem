package com.zichen.managersystem.service.impl;

import com.zichen.managersystem.common.constant.Constant;
import com.zichen.managersystem.common.enumeration.BizCodeEnum;
import com.zichen.managersystem.common.enumeration.RoleEnum;
import com.zichen.managersystem.common.exception.ParamInvalidException;
import com.zichen.managersystem.interceptor.RoleInterceptor;
import com.zichen.managersystem.model.entity.Role;
import com.zichen.managersystem.model.param.PermissionParam;
import com.zichen.managersystem.service.RoleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Override
    public Boolean addPermission(PermissionParam permissionParam) throws ParamInvalidException {
        if (permissionParam.getUserId() == null || permissionParam.getEndpoint() == null) {
            throw new ParamInvalidException(BizCodeEnum.VALID_EXCEPTION.getMsg());
        }
        Role role = RoleInterceptor.roleInfo.get();
        if (role == null || !role.getRole().equals(RoleEnum.ADMIN.getMsg())) {
            return false;
        }
        //TODO: It is better to use distributed lock
        try {
            updateData(permissionParam, new Yaml());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public Map<Integer, Boolean> getUsersByResource(String resource)  {
        Map<Integer, Boolean> res = new HashMap<>();
        //
        try {
            FileInputStream input = new FileInputStream(Constant.FILE_PATH);
            Map<Integer, List<String>> readData = new Yaml().load(input);
            if (readData == null || readData.isEmpty()) {
                return null;
            }

            for (Map.Entry<Integer, List<String>> entry : readData.entrySet()){
                List<String> list = entry.getValue();
                for (String str : list) {
                    res.put(entry.getKey(), false);
                    if (str.equals(resource)) {
                        res.put(entry.getKey(), true);
                        break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    private void updateData(PermissionParam permissionParam, Yaml yaml) throws IOException {
        //读
        FileInputStream input = new FileInputStream(Constant.FILE_PATH);
        Map<Integer, List<String>> readData = yaml.load(input);
        // 写
        List<String> oldList = null;
        if (readData != null && readData.containsKey(permissionParam.getUserId())) {
             oldList = readData.get(permissionParam.getUserId());
        }
        List<String> newList = permissionParam.getEndpoint();
        // 并集（去重）
        if (oldList != null) {
            readData.remove(permissionParam.getUserId());
            newList.addAll(oldList);
        }
        newList = newList.stream().distinct().collect(Collectors.toList());
        Map<Integer, List<String>> writeData = new HashMap<>();
        if (readData != null && !readData.isEmpty()) {
            writeData.putAll(readData);
        }
        writeData.put(permissionParam.getUserId(), newList);
        FileWriter writer = new FileWriter(Constant.FILE_PATH);
        yaml.dump(writeData, writer);
        writer.close();
    }
}
