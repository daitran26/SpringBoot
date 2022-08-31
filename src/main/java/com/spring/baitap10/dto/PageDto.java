package com.spring.baitap10.dto;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDto {

//    @NotBlank(message = "Số trang tìm kiếm không được bỏ trống")
    @Schema(description = "Số trang tìm kiếm", defaultValue = "0", example = "0")
//    @Pattern(regexp = "[0-9]*", message = "Số trang tìm kiếm không đúng định dạng")
    private int pageNumber = 0;

//    @NotBlank(message = "Số bản ghi/1 trang không được bỏ trống")
    @Schema(description = "Số bản ghi/1 trang", defaultValue = "5", example = "5")
//    @Pattern(regexp = "^[0-9]*", message = "Số bản ghi/1 trang không đúng định dạng")
    private int pageSize = 5;

    @Hidden
    public Pageable getPageable() {
        return PageRequest.of(getPageNumber(), getPageSize());
    }

    @Hidden
    public Pageable getPageable(Sort sort) {
        return PageRequest.of(getPageNumber(), getPageSize(), sort);
    }

    @Hidden
    public Pageable getPageable(Sort.Direction direction, String... properties) {
        return PageRequest.of(getPageNumber(), getPageSize(), direction, properties);
    }
}
