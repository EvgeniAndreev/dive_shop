package com.under_waves.dive_shop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Embeddable
public class ProductInfo {

    @Column(name = "info", length = 512)
    @Lob
    private String info;
}
