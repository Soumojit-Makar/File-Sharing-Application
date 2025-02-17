package com.file_sharing.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.file_sharing.app.entity.Providers;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "DTO representing the details of a user in the system.")
public class UserDTo {
    @Schema(description = "Unique identifier for the user", example = "1werer2345")
    private String userId;
    @NotBlank(message = "Password is required")
    @Schema(description = "Password for the user, must be at least 4 characters", example = "password123")
    @Size(min = 4,message = "Minimum 4 character")
    private String password;
    @Schema(description = "Name of the user, must be at least 3 characters", example = "Soumojit makar")
    @NotBlank(message = "Name is required")
    @Size(min = 3,message = "Required minimum 3 character")
    private String name;
    @Schema(description = "Email address of the user", example = "user@example.com")
    @NotBlank(message = "Email is required")
    @Email
    private String email;
    @Schema(description = "Phone number of the user, must be 10 digits", example = "9876543210")
    @NotBlank(message = "Phone number is required")
    @Size(max = 10,min = 10,message = "Invalid")
    private String phone;
    @Schema(description = "Physical address of the user", example = "123 Main St, Springfield, IL")
    @NotBlank(message = "Address is required")
    private String address;
    @Schema(description = "Gender of the user", example = "Male")
    @NotBlank(message = "Gender is required")
    private String gender;
    @Schema(description = "Profile picture of the user", example = "profilePic.jpg")
    private String profilePic;
    @Schema(description = "A short description about the user", example = "Software developer")
    private String about;
    @Schema(description = "Whether the user is enabled or not", example = "true")
    private boolean enabled;
    @Schema(description = "The provider associated with the user", implementation = Providers.class)
    private Providers providers;
    @Schema(description = "List of files associated with the user")
    private List<FileDTO> file;
    @Schema(description = "List of roles assigned to the user")
    private List<RoleDTo> roles;
}
