package com.solovev.kiteshop.model;

import com.solovev.kiteshop.BaseDBRelatedConfig;
import com.solovev.kiteshop.model.user.Authority;
import com.solovev.kiteshop.model.user.User;
import com.solovev.kiteshop.repository.AuthorityRepo;
import com.solovev.kiteshop.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_CLASS;

@Sql(scripts = {"/authorities.sql", "/users.sql"}, executionPhase = BEFORE_TEST_CLASS)
class AuthorityUserRelationshipTest extends BaseDBRelatedConfig {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AuthorityRepo authorityRepo;

    @Test
    public void successfullyAddAuthorityToUser() {
        //given
        User userToGrantAuthority = userRepo.findById(1L).orElseThrow();
        Authority authorityToGive = authorityRepo.findById(1L).orElseThrow();
        assumeFalse(userToGrantAuthority.getAuthorities().contains(authorityToGive));
        //when
        userToGrantAuthority.addAuthority(authorityToGive);
        userRepo.saveAndFlush(userToGrantAuthority);
        //then
        User userToCheck = userRepo.findById(1L).orElseThrow();
        assertTrue(userToCheck.getAuthorities().contains(authorityToGive));
    }

    @Test
    public void notAddingNotExistentAuthorityToToUser() {
        //given
        User userToGrantAuthority = userRepo.findById(1L).orElseThrow();
        Authority nonExistentAuthority = new Authority("Non existent");
        assumeFalse(userToGrantAuthority.getAuthorities().contains(nonExistentAuthority));
        //then
        userToGrantAuthority.addAuthority(nonExistentAuthority);
        assertThrows(DataAccessException.class, () -> userRepo.saveAndFlush(userToGrantAuthority));
    }

    @Test
    @Sql(scripts = {"/user_authorities.sql"})
    public void deletingAuthorityWillDeleteItForUser() {
        //given
        long authorityId = 2L;
        User userWithExistingGrantAuthority = userRepo.findById(1L).orElseThrow();
        Authority authorityToDelete = authorityRepo.findById(authorityId).orElseThrow();
        assumeTrue(userWithExistingGrantAuthority.getAuthorities().remove(authorityToDelete));
        //when
        userRepo.saveAndFlush(userWithExistingGrantAuthority);
        authorityRepo.delete(authorityToDelete);
        authorityRepo.flush();
        //then
        User userToCheck = userRepo.findById(1L).orElseThrow();
        assertFalse(userToCheck.getAuthorities().contains(authorityToDelete));
    }
}