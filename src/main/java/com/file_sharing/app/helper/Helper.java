package com.file_sharing.app.helper;

import com.file_sharing.app.dto.PageableResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;

public class Helper {
    public static <U,V> PageableResponse<V> pageableResponse(Page<U> page, Class<V> type) {
        List<U> content = page.getContent();
        List<V> dtoList=content
                .stream()
                .map(object-> new ModelMapper().map(object, type))
                .toList();
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
