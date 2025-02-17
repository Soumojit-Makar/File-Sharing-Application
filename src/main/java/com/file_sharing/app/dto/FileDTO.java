package com.file_sharing.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.file_sharing.app.entity.UserEntity;
import lombok.Data;
import java.time.Instant;
@Data
public class FileDTO {
    private String fileId;
    private String fileName;
    private String fileType;
    @JsonIgnore
    private UserEntity uploadBy;
    private Instant uploadDate;
    private byte[] fileData;
}
