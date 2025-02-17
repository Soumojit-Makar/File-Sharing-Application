package com.file_sharing.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.file_sharing.app.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.Instant;
@Schema(description = "DTO representing a file with its metadata and content data.")
@Data
public class FileDTO {
    @Schema(description = "Unique identifier of the file", example = "erweg12der345678dff9")
    private String fileId;
    @Schema(description = "Name of the file", example = "example.jpg")
    private String fileName;
    @Schema(description = "Type of the file (e.g., image/jpeg, application/pdf)", example = "image/jpeg")
    private String fileType;
    @Schema(description = "User who uploaded the file", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonIgnore
    private UserEntity uploadBy;
    @Schema(description = "Date and time when the file was uploaded", example = "2025-02-17T14:30:00Z")
    private Instant uploadDate;
    @Schema(description = "File data in byte array format", accessMode = Schema.AccessMode.READ_ONLY)
    private byte[] fileData;
}
