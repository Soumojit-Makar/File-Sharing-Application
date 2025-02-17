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
    // Uploads a file for a specific user.
    @PostMapping("/upload/userId/{userID}")
    public ResponseEntity<ResponseFileData> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("userID") String userID) {
        FileDTO fileDTO = fileService.saveFile(file, userID); // Save the file using the service
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/file/files/download/")
                .path(fileDTO.getFileId())
                .toUriString();  // Construct the download URI
        // Build and return the response
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
    //  Downloads a file by its ID.
    @GetMapping("/files/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileId") String fileId) {
        FileDTO fileDTO = fileService.downloadFile(fileId); // Retrieve the file details
        if (fileDTO == null) {
            return ResponseEntity.notFound().build(); // Return 404 if the file is not found
        }
        // Return the file as a resource
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileDTO.getFileType())) // Set the content type
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDTO.getFileName() + "\"") // Set the attachment header
                .body(new ByteArrayResource(fileDTO.getFileData())); // Return the file data as a resource
    }
//    Updates the name of a file by its ID.
    @PutMapping("/update/{fileId}/name/{name}")
    public ResponseEntity<ResponseFileData> updateFile(@PathVariable("fileId") String fileId,@PathVariable("name") String Name) {
        FileDTO fileDTO= fileService.updateFile(fileId, Name); // Update the file using the service
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
    //Deletes a file by its ID.
    @DeleteMapping("/{fileId}")
    public ResponseEntity<ApiResponseMessage> deleteFile(@PathVariable("fileId") String fileId) {
        fileService.deleteFile(fileId);// Call the service to delete the file
        return new ResponseEntity<>(ApiResponseMessage.builder()
                .message("File deleted") // Build response message
                .success(true)
                .httpStatus(HttpStatus.ACCEPTED)
                .build(), HttpStatus.ACCEPTED);
    }
    //Retrieves all files with pagination and sorting.
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
    //Retrieves all files for a specific user with pagination and sorting.
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
    //   * Searches for files by keyword with pagination and sorting.
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
