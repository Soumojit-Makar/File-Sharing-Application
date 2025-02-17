package com.file_sharing.app.dto;

import com.file_sharing.app.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "DTO representing the role of a user within the application.")
public class RoleDTo {
    @Schema(description = "The unique identifier for the role", example = "admin")
    private String roleId;
    @Schema(description = "The name of the role", example = "Administrator")
    private String roleName;
}
