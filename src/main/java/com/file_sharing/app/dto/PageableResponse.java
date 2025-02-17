package com.file_sharing.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Schema(description = "Paginated response containing a list of content with pagination details.")
@Data
@AllArgsConstructor
public class PageableResponse <T>{
    @Schema(description = "The current page number in the paginated response", example = "1")
    private int pageNumber;
    @Schema(description = "The size of each page (number of items per page)", example = "10")
    private int pageSize;
    @Schema(description = "Total number of elements across all pages", example = "100")
    private long totalElements;
    @Schema(description = "Total number of pages", example = "10")
    private long totalPages;
    @Schema(description = "List of content for the current page", implementation = List.class)
    private List<T> content;
    @Schema(description = "Indicates if this is the last page of the response", example = "true")
    private boolean lastPage;

}
