package com.solovev.model;

import com.solovev.dto.DaoEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(force = true)
@Entity
@Table(name = "users")
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
