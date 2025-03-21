package com.solovev.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Card with question for category
 */
@Data
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table(name = "cards",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"question", "answer", "category_id"})})
@NamedQueries({
        @NamedQuery(name = "Card_getAll", query = "from Card"),
        @NamedQuery(name = "Card_getById", query = "from Card where id = :id"),
        @NamedQuery(name = "Card_getByCategory", query = "from Card where category= :category"),
        @NamedQuery(name = "Card_getByUser", query = "SELECT c FROM Card c JOIN c.category cat WHERE cat.user = :user"),
        @NamedQuery(
                name = "Card_update",
                query = """
                        UPDATE Card
                        SET question = :question, answer = :answer, category = :category, creationDate= :creationDate
                        WHERE id = :id
                        """),
        @NamedQuery(
                name = "Card_delete",
                query = "DELETE FROM Card WHERE id = :id"
        )
})
@NamedNativeQuery(
        name = "Card_insert",
        query = "INSERT INTO cards (question, answer, category_id, creation_date) VALUES (:question, :answer, :category_id, :creation_date)"
)
public class Card implements DaoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @Column(name = "question", nullable = false)
    private String question;

    @NonNull
    @Column(name = "answer", nullable = false)
    private String answer;

    @NonNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;

    @NonNull
    @Column(name = "creation_date", nullable = false)
    private final LocalDate creationDate = LocalDate.now();


}
