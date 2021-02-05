package com.under_waves.dive_shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ManufacturerInfoDto {

    private Long id;

    @NotBlank
    private String address;

    @NotBlank
    @Size(min = 13, max = 13, message = "Phone number should be between 10 and 13 digits.")
    private String phone;

    @NotBlank
    private String email;
}
