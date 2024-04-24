package com.zichen.managersystem.model.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PermissionParam {

    Integer userId;
    List<String> endpoint;
}
