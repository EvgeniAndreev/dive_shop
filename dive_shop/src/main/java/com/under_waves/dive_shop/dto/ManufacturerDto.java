package com.under_waves.dive_shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ManufacturerDto {

    private Long id;

    @NotBlank
    private String name;

    private ManufacturerInfoDto manufacturerInfoDto;

    private Set<ProductDto> products;

}
