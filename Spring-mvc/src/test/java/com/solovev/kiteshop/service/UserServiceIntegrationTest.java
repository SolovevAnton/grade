package com.solovev.kiteshop.service;

import com.solovev.kiteshop.BaseDBRelatedConfig;
import com.solovev.kiteshop.exception.UserAlreadyExistsException;
import com.solovev.kiteshop.model.user.User;
import com.solovev.kiteshop.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_CLASS;

@Sql(scripts = {
        "/users.sql"
}, executionPhase = BEFORE_TEST_CLASS)
class UserServiceIntegrationTest extends BaseDBRelatedConfig {

    @TestConfiguration
    public static class Configure {
        @Autowired
        private UserRepo userRepo;

        @Bean
        public UserService getService() {
            return new UserService(userRepo);
        }
    }

    @Autowired
    private UserService userService;

    @Test
    public void shouldThrowWhenRegisterUserWithExistingLogin() {
        //given
        String existingLogin = userService.findAll().stream().findAny().orElseThrow().getUsername();
        User toAdd = new User(existingLogin, "password");
        //then
        assertThrows(UserAlreadyExistsException.class, () -> userService.save(toAdd));
    }

    @Test
    public void shouldAddUserWithNonExistingLogin() {
        //given
        String nonExistingLogin = "nonExisting";
        User toAdd = new User(nonExistingLogin, "pass");
        //when
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(nonExistingLogin));
        userService.save(toAdd);
        //then
        assertEquals(toAdd.getUsername(), userService.loadUserByUsername(nonExistingLogin).getUsername());
    }

}