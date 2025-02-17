package com.file_sharing.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.file_sharing.app.entity.Providers;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTo {
    private String userId;
    @NotBlank(message = "Password is required")
    @Size(min = 4,message = "Minimum 4 character")
    private String password;
    @NotBlank(message = "Name is required")
    @Size(min = 3,message = "Required minimum 3 character")
    private String name;
    @NotBlank(message = "Email is required")
    @Email
    private String email;
    @NotBlank(message = "Phone number is required")
    @Size(max = 10,min = 10,message = "Invalid")
    private String phone;
    @NotBlank(message = "Address is required")
    private String address;
    @NotBlank(message = "Gender is required")
    private String gender;
    private String profilePic;
    private String about;
    private boolean enabled;
    private Providers providers;
    private List<FileDTO> file;
    private List<RoleDTo> roles;
}
