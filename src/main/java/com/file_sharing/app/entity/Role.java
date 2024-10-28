package com.file_sharing.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role {
    @Id
    private String roleId;
    private String roleName;
    @ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
    private List<UserEntity> users=new ArrayList<UserEntity>();
}
