package com.solovev.kiteshop.model.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.solovev.kiteshop.model.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ORDERS")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@RequiredArgsConstructor
public class Order extends AuditableEntity {

    @OneToMany(mappedBy = "order", orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private final List<OrderPosition> orderPositions = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "USER_ID")
    @NonNull
    private User user;
    @Column(nullable = false, name = "STATUS")
    private Status status = Status.REGISTERED;

    public boolean isRegistered() {
        return this.status.equals(Status.REGISTERED);
    }

    public enum Status {
        REGISTERED,
        PAID,
        CANCELED
    }
}
