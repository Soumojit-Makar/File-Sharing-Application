package com.file_sharing.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO representing the response for file data after an upload or query.")
public class ResponseFileData {
    @Schema(description = "The name of the file", example = "document.pdf")
    private String fileName;
    @Schema(description = "The type of the file (MIME type)", example = "application/pdf")
    private String fileType;
    @Schema(description = "The URL where the file can be downloaded", example = "https://example.com/file/files/download/12345")
    private String url;
    @Schema(description = "The size of the file in bytes", example = "1024")
    private long fileSize;
    @Schema(description = "The unique ID of the file", example = "123eregrf45")
    private String fileId;
}
