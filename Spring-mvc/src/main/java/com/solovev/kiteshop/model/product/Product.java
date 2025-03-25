package com.solovev.kiteshop.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.solovev.kiteshop.model.order.OrderPosition;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PRODUCTS",
        uniqueConstraints = @UniqueConstraint(columnNames = {"MODEL", "SIZE", "PRODUCTION_YEAR", "BRAND"})
)
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false, name = "MODEL")
    private String model;

    @NonNull
    @Column(nullable = false, name = "PRICE")
    private BigDecimal price;

    @Column(nullable = false, name = "SIZE", columnDefinition = "SMALLINT")
    private byte size;

    @NonNull
    @Column(nullable = false, name = "PRODUCTION_YEAR")
    private Year productionYear;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "BRAND")
    private Brand brand;

    public Product(@NonNull String model, @NonNull BigDecimal price, byte size, @NonNull Year productionYear,
                   @NonNull Brand brand) {
        this.model = model;
        this.price = price;
        this.size = size;
        this.productionYear = productionYear;
        this.brand = brand;
    }

    @OneToMany(mappedBy = "product", orphanRemoval = true)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private final List<OrderPosition> orderPositions = new ArrayList<>();
}
