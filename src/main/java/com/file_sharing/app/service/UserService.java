package com.file_sharing.app.service;

import com.file_sharing.app.dto.PageableResponse;
import com.file_sharing.app.dto.UserDTo;

public interface UserService {
    //create
    UserDTo createUser(UserDTo userDTO);
    //update
    UserDTo updateUser(UserDTo userDTO, String userId);
    //delete
    void deleteUser(String userId);
    //get single user
    UserDTo getUser(String userId);
    //get user Email
    UserDTo getUserByEmail(String email);
    //get all users
    PageableResponse<UserDTo> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir);
    // search
    PageableResponse<UserDTo> search(String keyword, int pageNumber, int pageSize, String sortBy, String sortDir);

}
