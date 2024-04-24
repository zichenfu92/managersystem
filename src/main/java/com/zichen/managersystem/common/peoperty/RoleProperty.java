package com.zichen.managersystem.common.peoperty;

import com.zichen.managersystem.model.entity.Role;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "roles")
public class RoleProperty {
    private Map<String, Role> maps;
}