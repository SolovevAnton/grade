package com.solovev.kiteshop.service;

import com.solovev.kiteshop.exception.UserAlreadyExistsException;
import com.solovev.kiteshop.model.user.User;
import com.solovev.kiteshop.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static com.solovev.kiteshop.common.ValidationErrorsMessages.USERNAME_NOT_FOUND_MSG_TEMPLATE;
import static com.solovev.kiteshop.common.ValidationErrorsMessages.USER_LOGIN_EXISTS_TEMPLATE;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;

    @Transactional(readOnly = true)
    public Collection<User> findAll() {
        return userRepo.findAll();
    }

    @Transactional
    public User save(User user) {
        try {
            return userRepo.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistsException(String.format(USER_LOGIN_EXISTS_TEMPLATE, user.getUsername()));
        }
    }

    @Transactional(readOnly = true)
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findUserByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format(USERNAME_NOT_FOUND_MSG_TEMPLATE, username)));
    }

}
