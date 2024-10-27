package com.file_sharing.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileDTO {
    private String fileId;
    private String fileName;
    private String fileType;
    private byte[] fileData;
}
