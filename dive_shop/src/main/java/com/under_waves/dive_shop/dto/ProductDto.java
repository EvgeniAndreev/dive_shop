package com.under_waves.dive_shop.dto;

import com.under_waves.dive_shop.model.ProductInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProductDto {

    private Long id;

    @NotBlank
    @Size(min = 13, max = 13, message = "Barcode should be 13 digits.")
    private String barCode;

    @NotBlank
    private String name;

    @NotBlank
    private String size;

    private int quantity;

    private int price;

    private ProductInfo info;

    private ProductTypeDto productType;

    private String manufacturerName;

    private Set<ImageDto> images;
}
