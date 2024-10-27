package com.file_sharing.app.controller;

import com.file_sharing.app.dto.ApiResponseMessage;
import com.file_sharing.app.dto.FileDTO;
import com.file_sharing.app.dto.PageableResponse;
import com.file_sharing.app.dto.ResponseFileData;
import com.file_sharing.app.helper.AppCon;
import com.file_sharing.app.service.FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/file")
public class FileController {
    FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }
    @PostMapping("/upload/userId/{userID}")
    public ResponseEntity<ResponseFileData> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("userID") String userID) {
        FileDTO fileDTO = fileService.saveFile(file, userID);
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/file/files/download/")
                .path(fileDTO.getFileId())
                .toUriString();

        return ResponseEntity.ok(
                ResponseFileData.builder()
                        .fileId(fileDTO.getFileId())
                        .fileName(fileDTO.getFileName())
                        .fileSize(fileDTO.getFileData().length)
                        .fileType(fileDTO.getFileType())
                        .url(fileDownloadUri)
                        .build()
        );
    }
    @GetMapping("/files/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileId") String fileId) {
        FileDTO fileDTO = fileService.downloadFile(fileId);
        if (fileDTO == null) {
            return ResponseEntity.notFound().build(); // Return 404 if the file is not found
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileDTO.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDTO.getFileName() + "\"")
                .body(new ByteArrayResource(fileDTO.getFileData()));
    }
    @PutMapping("/update/{fileId}/name/{name}")
    public ResponseEntity<ResponseFileData> updateFile(@PathVariable("fileId") String fileId,@PathVariable("name") String Name) {
        FileDTO fileDTO= fileService.updateFile(fileId, Name);
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/file/files/download/")
                .path(fileDTO.getFileId())
                .toUriString();
        return ResponseEntity.ok(
                ResponseFileData.builder()
                        .fileId(fileDTO.getFileId())
                        .fileName(fileDTO.getFileName())
                        .fileSize(fileDTO.getFileData().length)
                        .fileType(fileDTO.getFileType())
                        .url(fileDownloadUri)
                        .build()
        );
    }
    @DeleteMapping("/{fileId}")
    public ResponseEntity<ApiResponseMessage> deleteFile(@PathVariable("fileId") String fileId) {
        fileService.deleteFile(fileId);
        return new ResponseEntity<>(ApiResponseMessage.builder()
                .message("File deleted")
                .success(true)
                .httpStatus(HttpStatus.ACCEPTED)
                .build(), HttpStatus.ACCEPTED);
    }
    @GetMapping
    public ResponseEntity<PageableResponse<ResponseFileData>> getFiles(
            @RequestParam(value = "pageNumber",defaultValue = AppCon.Page_Number,required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppCon.Page_Size,required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue="fileName",required = false) String sortBy ,
            @RequestParam(value = "sortDir", defaultValue= AppCon.Sort_Dir,required = false) String sortDir
    ) {
        return new  ResponseEntity(
                fileService.getAllFiles(pageNumber,pageSize,sortBy,sortDir),
                HttpStatus.ACCEPTED
        );
    }
    @GetMapping("/{userId}")
    public ResponseEntity<PageableResponse<ResponseFileData>> getFilesByUserId(
            @PathVariable("userId") String userID,
            @RequestParam(value = "pageNumber",defaultValue = AppCon.Page_Number,required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppCon.Page_Size,required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue="fileName",required = false) String sortBy ,
            @RequestParam(value = "sortDir", defaultValue= AppCon.Sort_Dir,required = false) String sortDir
    ){
        return new ResponseEntity<>(fileService.getAllFilesByUser(pageNumber,pageSize,sortBy,sortDir,userID), HttpStatus.ACCEPTED);
    }
    @GetMapping("/search")
    public ResponseEntity<PageableResponse<ResponseFileData>> search(
            @RequestParam(value = "keyword") String keyword,
            @RequestParam(value = "pageNumber",defaultValue = AppCon.Page_Number,required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppCon.Page_Size,required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue="fileName",required = false) String sortBy ,
            @RequestParam(value = "sortDir", defaultValue= AppCon.Sort_Dir,required = false) String sortDir
    ){
        return new ResponseEntity<>(fileService.searchFiles(keyword,pageNumber,pageSize,sortBy,sortDir), HttpStatus.ACCEPTED);
    }
}
