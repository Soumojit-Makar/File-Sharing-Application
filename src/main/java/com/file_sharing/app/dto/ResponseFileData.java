package com.file_sharing.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseFileData {
    private String fileName;
    private String fileType;
    private String url;
    private long fileSize;
    private String fileId;
}
