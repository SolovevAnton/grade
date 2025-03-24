package com.solovev.kiteshop.repository;

import com.solovev.kiteshop.model.user.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface AuthorityRepo extends JpaRepository<Authority, Long> {
    Collection<Authority> findAllByAuthorityIn(Collection<String> name);
}
