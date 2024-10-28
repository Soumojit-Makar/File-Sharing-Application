package com.file_sharing.app.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserEntity implements UserDetails{
    @Id
    private String userId;
    @Getter(AccessLevel.NONE)
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
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Role> roles=new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
    }
    @Override
    public String getPassword() {
        return this.password;
    }
    @Override
    public String getUsername() {
        return this.email;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
