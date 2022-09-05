package com.spring.baitap10.dto.request;

import com.spring.baitap10.dto.PageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchProductRequestDto extends PageDto {
    @Schema(description = "Tên sản phẩm", example = "Samsung")
    private String name;

    @Schema(description = "Mã danh mục", example = "1")
    private Long categoryId;
}
