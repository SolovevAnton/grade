package com.solovev.kiteshop.model.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Brand {
    FONE("F-ONE"),
    CORE("CORE"),
    DUOTONE("Duotone"),
    NAISH("Naish");

    private final String brandName;
}
