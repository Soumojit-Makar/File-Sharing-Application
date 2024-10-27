package com.file_sharing.app.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Role {
    @Id
    private String roleId;
    private String roleName;
//    @ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
//    private List<UserEntity> users=new ArrayList<UserEntity>();
}
