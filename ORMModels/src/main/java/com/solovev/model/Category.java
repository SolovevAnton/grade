package com.solovev.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents category for user, one user can have multiple categories
 */
@Data
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table(name = "categories",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "user_id"})})
@NamedQueries({
        @NamedQuery(name = "Category_getAll", query = "from Category"),
        @NamedQuery(name = "Category_getById", query = "from Category where id = :id"),
        @NamedQuery(name = "Category_getByUser", query = "from Category where user= :user"),
        @NamedQuery(
                name = "Category_update",
                query = """
                        UPDATE Category
                        SET name = :name, user = :user
                        WHERE id = :id
                        """),
        @NamedQuery(
                name = "Category_delete",
                query = "DELETE FROM Category WHERE id = :id"
        )
})
@NamedNativeQuery(
        name = "Category_insert",
        query = "INSERT INTO categories (name, user_id) VALUES (:name, :user_id)")
public class Category implements DaoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @Column(name = "name", nullable = false)
    private String name;

    @NonNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "category", orphanRemoval = true, cascade = CascadeType.ALL)
    private final List<Card> cards = new ArrayList<>();

}
