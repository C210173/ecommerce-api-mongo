package com.sky.ecommerce.request;

import com.sky.ecommerce.model.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeRoleRequest {
    private String userId;
    private Role role;
}
