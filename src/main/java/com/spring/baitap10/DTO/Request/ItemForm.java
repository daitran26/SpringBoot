package com.spring.baitap10.DTO.Request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
@Data
public class ItemForm {
	@Min(value = 1)
    private Integer quantity;
    @NotEmpty
    private Long productId;
}
