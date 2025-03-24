package com.solovev.kiteshop.config;

import com.solovev.kiteshop.model.user.Authority;
import com.solovev.kiteshop.model.user.User;
import com.solovev.kiteshop.repository.AuthorityRepo;
import com.solovev.kiteshop.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@PropertySource("classpath:/sensitive-data/admin-credentials.properties")
@RequiredArgsConstructor
public class AuthoritiesAndInitialAdminConfig {
    @Value("${name}")
    private String adminUserName;
    @Value("${pass}")
    private String pass;
    @Value("${roles}")
    private String[] roles;

    private final AuthorityRepo authorityRepo;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void addAdminAuthoritiesAndUser() {
        var uniqueAuthorities = getAuthorities();

        addAdmin(uniqueAuthorities);
    }

    private void addAdmin(List<Authority> allAuthorities) {
        User admin = adminUser(adminUserName, pass);
        allAuthorities.forEach(admin::addAuthority);
        userService.save(admin);
    }

    private User adminUser(String username, String password) {
        User admin;
        try {
            admin = userService.loadUserByUsername(username);
            admin.setPassword(passwordEncoder.encode(password));
        } catch (UsernameNotFoundException e) {
            admin = new User(username, passwordEncoder.encode(pass));
        }
        return admin;
    }

    private List<Authority> getAuthorities() {
        var existingAuthoritiesNames =
                authorityRepo.findAll().stream().map(Authority::getAuthority).collect(Collectors.toSet());
        var uniqueAuthorities = Arrays.
                stream(roles)
                .map(Authority::new)
                .filter(a -> !existingAuthoritiesNames.contains(a.getAuthority()))
                .toList();
        authorityRepo.saveAll(uniqueAuthorities);
        return authorityRepo.findAll();
    }

}
