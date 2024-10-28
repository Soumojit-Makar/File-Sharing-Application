package com.file_sharing.app.helper;

import com.file_sharing.app.dto.PageableResponse;
import com.file_sharing.app.dto.ResponseFileData;
import com.file_sharing.app.entity.FileEntity;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

public class Helper {
//     * Converts a pageable entity response to a pageable DTO response.
    public static <U,V> PageableResponse<V> pageableResponse(Page<U> page, Class<V> type) {
        List<U> content = page.getContent(); // Get content from the page
        List<V> dtoList=content
                .stream()
                .map(object-> new ModelMapper().map(object, type)) // Map each entity to its corresponding DTO
                .toList();
        // Return a pageable response with the page details and mapped DTOs
        return new PageableResponse<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                dtoList,
                page.isLast()
        );
    }
//    Converts a pageable response of file entities to a pageable response of file data DTOs.
    public static  PageableResponse<ResponseFileData> pageableFileResponse(Page<FileEntity> page){
        List<FileEntity> content = page.getContent(); // Get content from the page
        List<ResponseFileData> dtoList=content
                .stream()
                .map(object->{
                    // Build ResponseFileData DTO for each FileEntity
                    return ResponseFileData.builder()
                            .fileName(object.getFileName())
                            .fileSize(object.getFileData().length)
                            .fileType(object.getFileType())
                            .fileId(object.getFileId())
                            .url(ServletUriComponentsBuilder
                                    .fromCurrentContextPath()
                                    .path("/file/files/download/")
                                    .path(object.getFileId())
                                    .toUriString())
                            .build();

                })
                .toList();
        // Return a pageable response with the page details and mapped file data DTOs
        return new PageableResponse<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                dtoList,
                page.isLast()
        );
    }
}
