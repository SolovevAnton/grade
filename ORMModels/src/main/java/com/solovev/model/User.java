package com.solovev.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(force = true)
@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = "User_getAll", query = "from User"),
        @NamedQuery(name = "User_getById", query = "from User where id = :id"),
        @NamedQuery(name = "User_getByLogAndPass", query = "from User where login= :login and password= :password"),
        @NamedQuery(name = "User_getByHashAndId", query = "from User where cookieHash= :cookieHash and id= :id"),
        @NamedQuery(
                name = "User_update",
                query = """
                        UPDATE User u
                        SET u.login = :login, u.password = :password, u.name = :name, u.cookieHash = :cookieHash, u.registrationDate = :registrationDate
                        WHERE u.id = :id
                        """
        ),
        @NamedQuery(
                name = "User_delete",
                query = "DELETE FROM User u WHERE u.id = :id"
        )
})
@NamedNativeQuery(
        name = "User_insert",
        query = "INSERT INTO users (login, password, name, registration_date, cookie_hash) VALUES (:login, :password, :name, :registrationDate, :cookieHash)")
public class User implements DaoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @Column(unique = true, nullable = false)
    private String login;
    @NonNull
    @Column(nullable = false, columnDefinition = "TEXT")
    private String password;

    private String name;

    @Column(name = "registration_date", updatable = false)
    private final LocalDate registrationDate = LocalDate.now();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private final List<Category> categories = new ArrayList<>();

    @Column(name = "cookie_hash")
    private String cookieHash;

    public User(@NonNull String login, @NonNull String password) {
        this.login = login;
        setPassword(password);
    }

    public User(long id, @NonNull String login, @NonNull String password, String name) {
        this.id = id;
        this.login = login;
        setPassword(password);
        this.name = name;
    }

    public User(@NonNull String login, @NonNull String password, String name, String cookieHash) {
        this.login = login;
        setPassword(password);
        this.name = name;
        this.cookieHash = cookieHash;
    }

    public User(long id, @NonNull String login, @NonNull String password, String name, String cookieHash) {
        this.id = id;
        this.login = login;
        setPassword(password);
        this.name = name;
        this.cookieHash = cookieHash;
    }

}
