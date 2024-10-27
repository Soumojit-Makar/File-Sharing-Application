package com.file_sharing.app.controller;

import com.file_sharing.app.dto.ApiResponseMessage;
import com.file_sharing.app.dto.PageableResponse;
import com.file_sharing.app.dto.UserDTo;
import com.file_sharing.app.entity.Providers;
import com.file_sharing.app.helper.AppCon;
import com.file_sharing.app.service.UserService;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @Enumerated(EnumType.STRING)
    private final Providers provider=Providers.SELF;
    @PostMapping
    ResponseEntity<UserDTo> createUser(@Valid @RequestBody UserDTo userDTO) {
        userDTO.setProviders(provider);
        UserDTo createdUser = userService.createUser(userDTO);

        return ResponseEntity.ok(createdUser);
    }
    @DeleteMapping("/{userId}")
    ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable("userId") String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(
                ApiResponseMessage.builder()
                        .message("User Successfully deleted")
                        .httpStatus(HttpStatus.ACCEPTED)
                        .success(true)
                        .build()
        );
    }
    @PutMapping("/{userId}")
    ResponseEntity<UserDTo> updateUser(@Valid @RequestBody UserDTo userDTO, @PathVariable("userId") String userId) {
        UserDTo updatedUser=userService.updateUser(userDTO, userId);
        return ResponseEntity.ok(updatedUser);
    }
    @GetMapping
    ResponseEntity<PageableResponse<UserDTo>> getAllUsers(
            @RequestParam(value = "pageNumber",defaultValue = AppCon.Page_Number,required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppCon.Page_Size,required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue=AppCon.Sort_By,required = false) String sortBy ,
            @RequestParam(value = "sortDir", defaultValue= AppCon.Sort_Dir,required = false) String sortDir) {
           PageableResponse<UserDTo> pageableResponse= userService.getAllUsers(pageNumber,pageSize,sortBy,sortDir);
           return ResponseEntity.ok(pageableResponse);
    }
    @GetMapping("/{userId}")
    ResponseEntity<UserDTo> getUser(
            @PathVariable("userId") String userId,
            @RequestParam(value = "pageNumber",defaultValue = AppCon.Page_Number,required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppCon.Page_Size,required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue=AppCon.Sort_By,required = false) String sortBy ,
            @RequestParam(value = "sortDir", defaultValue= AppCon.Sort_Dir,required = false) String sortDir
            )
    {
        UserDTo userDTO=userService.getUser(userId);
        return ResponseEntity.ok(userDTO);
    }
    @GetMapping("/search")
    ResponseEntity<PageableResponse<UserDTo>> searchUsers(
            @RequestParam(value = "searchKeyword") String keyword,
            @RequestParam(value = "pageNumber",defaultValue = AppCon.Page_Number,required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppCon.Page_Size,required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue=AppCon.Sort_By,required = false) String sortBy ,
            @RequestParam(value = "sortDir", defaultValue= AppCon.Sort_Dir,required = false) String sortDir

    )
    {
     PageableResponse<UserDTo> pageableResponse= userService.search(keyword,pageNumber,pageSize,sortBy,sortDir);
     return ResponseEntity.ok(pageableResponse);
    }
}
