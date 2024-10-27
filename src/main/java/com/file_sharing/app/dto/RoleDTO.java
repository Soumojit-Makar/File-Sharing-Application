package com.file_sharing.app.dto;

import com.file_sharing.app.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class RoleDTo {
    private String roleId;
    private String roleName;
//    private List<UserEntity> users;
}
