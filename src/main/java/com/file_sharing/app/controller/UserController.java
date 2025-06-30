package com.file_sharing.app.controller;

import com.file_sharing.app.dto.ApiResponseMessage;
import com.file_sharing.app.dto.PageableResponse;
import com.file_sharing.app.dto.UserDTo;
import com.file_sharing.app.entity.Providers;
import com.file_sharing.app.helper.AppCon;
import com.file_sharing.app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Tag(name = "User Controller", description = "This controller handles all operations related to user management such as creating, updating, deleting, and fetching users.")
public class UserController {
    UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @Enumerated(EnumType.STRING)
    private final Providers provider=Providers.SELF;
    //Creates a new user.
    @Operation(
            summary = "Create a new user",
            description = "Creates a new user with the provided details.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully created the user",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTo.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponseMessage.class)
                            )
                    )
            }
    )
    @PostMapping
    ResponseEntity<UserDTo> createUser(@Valid @RequestBody UserDTo userDTO) {
        userDTO.setEnabled(true);
        userDTO.setProviders(provider);
        return ResponseEntity.ok(userService.createUser(userDTO));
    }
    // Deletes a user by ID.
    @Operation(
            summary = "Delete a user by ID",
            description = "Deletes a user identified by their ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully deleted the user",
                            content = @Content(
                                    mediaType = "text/plain",
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponseMessage.class)
                            )
                    )
            }
    )
    @PreAuthorize("hasAnyRole('NORMAL', 'ADMIN')")
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
    //Updates a user's information.
    @PreAuthorize("hasAnyRole('NORMAL', 'ADMIN')")
    @Operation(
            summary = "Update a user's information",
            description = "Updates a user's details with the provided information.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully updated the user",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTo.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponseMessage.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponseMessage.class)
                            )
                    )
            }
    )

    @PutMapping("/{userId}")
    ResponseEntity<UserDTo> updateUser(@Valid @RequestBody UserDTo userDTO, @PathVariable("userId") String userId) {
        UserDTo updatedUser=userService.updateUser(userDTO, userId);
        return ResponseEntity.ok(updatedUser);
    }
    //  Retrieves all users with pagination and sorting.
    @Operation(
            summary = "Retrieve all users with pagination and sorting",
            description = "Retrieves all users with pagination and sorting options.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved users",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PageableResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponseMessage.class)
                            )
                    )
            }
    )
    @PreAuthorize("hasAnyRole('NORMAL', 'ADMIN')")
    @GetMapping
    ResponseEntity<PageableResponse<UserDTo>> getAllUsers(
            @RequestParam(value = "pageNumber",defaultValue = AppCon.Page_Number,required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppCon.Page_Size,required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue=AppCon.Sort_By,required = false) String sortBy ,
            @RequestParam(value = "sortDir", defaultValue= AppCon.Sort_Dir,required = false) String sortDir) {
           PageableResponse<UserDTo> pageableResponse= userService.getAllUsers(pageNumber,pageSize,sortBy,sortDir);
           return ResponseEntity.ok(pageableResponse);
    }
    // Retrieves a specific user by ID
    @Operation(
            summary = "Retrieve a specific user by ID",
            description = "Fetches the details of a user by their ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved the user",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTo.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponseMessage.class)
                            )
                    )
            }
    )
    @PreAuthorize("hasAnyRole('NORMAL', 'ADMIN')")
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
    // Searches for users by a keyword with pagination and sorting.
    @Operation(
            summary = "Search for users by keyword with pagination and sorting",
            description = "Searches for users based on a keyword and returns paginated results with sorting options.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully searched users",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PageableResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponseMessage.class)
                            )
                    )
            }
    )
    @PreAuthorize("hasAnyRole('NORMAL', 'ADMIN')")
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
