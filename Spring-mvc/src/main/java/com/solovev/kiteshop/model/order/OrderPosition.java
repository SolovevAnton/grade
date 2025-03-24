package com.solovev.kiteshop.model.order;

import com.solovev.kiteshop.model.product.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ORDER_POSITIONS",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"ORDER_ID", "PRODUCT_ID"})})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class OrderPosition extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ORDER_ID")
    @NonNull
    private Order order;

    @ManyToOne(optional = false)
    @JoinColumn(name = "PRODUCT_ID")
    @NonNull
    private Product product;

    @Column(name = "QUANTITY")
    private int quantity;

    public OrderPosition(@NonNull Order order, @NonNull Product product, int quantity) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
    }

    public void addQuantity(int numberToAdd) {
        quantity += numberToAdd;
    }
}
