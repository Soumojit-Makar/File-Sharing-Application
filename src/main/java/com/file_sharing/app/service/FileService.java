package com.file_sharing.app.service;

import com.file_sharing.app.dto.FileDTO;
import com.file_sharing.app.dto.PageableResponse;
import com.file_sharing.app.dto.ResponseFileData;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {



    //create
    FileDTO saveFile(MultipartFile file, String userID);
    //delete
    void deleteFile(String fileID);
    //auto delete
    void deleteFilesAuto(String userID);
    //update
    FileDTO updateFile(String fileID,String Name);
    //download file
    FileDTO downloadFile(String fileId);
    //get a list of files
    PageableResponse<ResponseFileData> getAllFiles(int pageNumber, int pageSize, String sortBy, String sortDir);
    // get files by user
    PageableResponse<ResponseFileData> getAllFilesByUser(int pageNumber, int pageSize, String sortBy, String sortDir, String userID);
    //search
    PageableResponse<ResponseFileData> searchFiles(String fileName,int pageNumber, int pageSize, String sortBy, String sortDir);
}
