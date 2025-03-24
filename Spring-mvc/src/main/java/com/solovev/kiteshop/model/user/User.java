package com.solovev.kiteshop.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.solovev.kiteshop.model.order.Order;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Entity
@Table(name = "USERS")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, name = "USERNAME")
    @NonNull
    private String username;

    @Column(nullable = false, name = "PASSWORD")
    @NonNull
    private String password;

    @Column(nullable = false, name = "ACCOUNT_NON_LOCKED")
    private boolean accountNonLocked = true;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private final List<Order> orders = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USER_AUTHORITIES",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "AUTHORITY_ID")
    )
    private final Collection<Authority> authorities = new HashSet<>();

    public boolean addAuthority(Authority grantedAuthority) {
        return authorities.add(grantedAuthority);
    }

    public boolean hasAuthority(String authorityName) {
        return authorities.stream().map(Authority::getAuthority).anyMatch(a -> a.equalsIgnoreCase(authorityName));
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }
}

