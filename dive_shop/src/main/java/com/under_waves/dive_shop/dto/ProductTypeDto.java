package com.under_waves.dive_shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProductTypeDto {

    private Long id;

    @NotBlank(message = "Product type should not be empty.")
    private String type;
}
