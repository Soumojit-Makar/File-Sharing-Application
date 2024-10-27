package com.file_sharing.app.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserEntity {
    @Id
    private String userId;
//    @Getter(AccessLevel.NONE)
    private String password;
    private String name;
    @Column(unique = true,nullable = false)
    private String email;
    private String phone;
    private String address;
    private String gender;
    @Column(length = 1000)
    private String profilePic;
    @Column(length = 1000)
    private String about;
    private boolean enabled=true;
    private Providers providers;
    @OneToMany(mappedBy = "uploadBy",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<FileEntity> file;
//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    private List<Role> roles=new ArrayList<>();



}
