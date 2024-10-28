package com.file_sharing.app.dto;

import com.file_sharing.app.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDTo {
    private String roleId;
    private String roleName;
//    private List<UserEntity> users;
}
