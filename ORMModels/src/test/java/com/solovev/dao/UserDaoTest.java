package com.solovev.dao;

import com.solovev.DBSetUpAndTearDown;
import com.solovev.DataConstants;
import com.solovev.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.persistence.Table;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public abstract class UserDaoTest {

    @Test
    public void getByIdTest() {
        long maxUserId = USERS.size();
        long minUserId = 1;

        //found
        User firstUser = USERS.get(0);
        User lastUser = USERS.get(USERS.size() - 1);

        assertEquals(firstUser, userDAO.get(minUserId).orElse(null));
        assertEquals(lastUser, userDAO.get(maxUserId).orElse(null));

        //not found
        assertEquals(Optional.empty(), userDAO.get(-1));
        assertEquals(Optional.empty(), userDAO.get(minUserId - 1));
        assertEquals(Optional.empty(), userDAO.get(maxUserId + 1));
    }

    @Test
    public void getAll() throws SQLException {
        var all = userDAO.get();
        assertEquals(USERS, all);

        dbSetUpAndTearDown.clearTable(USERS_TABLE_NAME);

        assertEquals(List.of(), userDAO.get());
    }

    @Test
    public void getUserByLogAndPassSuccess() {
        User userToFind = USERS.get(1);
        String userLog = userToFind.getLogin();
        String userPass = userToFind.getPassword();

        assertEquals(userToFind, userDAO.getUserByLoginAndPass(userLog, userPass).get());
    }

    @Test
    public void getUserByLogAndPassNotFound() {

        User userToFind = USERS.get(1);
        String userLog = userToFind.getLogin();
        String userPass = userToFind.getPassword();
        String nonExistentLog = userLog + " corrupted";
        String nonExistentPass = userPass + " corrupted";

        assertEquals(Optional.empty(), userDAO.getUserByLoginAndPass(userLog, nonExistentPass));
        assertEquals(Optional.empty(), userDAO.getUserByLoginAndPass(nonExistentLog, userPass));
        assertEquals(Optional.empty(), userDAO.getUserByLoginAndPass(nonExistentLog, nonExistentPass));

    }

    @Nested
    public class cookieRelated {
        @Test
        public void getUserByIdAndHashSuccess() {
            User userToFind = USERS.get(0);
            String userId = String.valueOf(userToFind.getId());
            String userHash = userToFind.getCookieHash();

            assertEquals(userToFind, userDAO.getUserByHashAndId(userId, userHash).get());
        }

        @Test
        public void getUserByIdAndHashNullHashFail() {
            User userToFind = USERS.get(1);
            String userId = String.valueOf(userToFind.getId());
            String userHash = userToFind.getCookieHash();

            assertThrows(NullPointerException.class, () -> userDAO.getUserByHashAndId(userId, userHash));
        }

        @Test
        public void getUserByLogAndCookieHashNotFound() {
            User userToFind = USERS.get(0);
            String userId = String.valueOf(userToFind.getId());
            String userHash = userToFind.getCookieHash();
            String idWithOtherHash = "1";
            String nonExistentHash = userHash + " corrupted";

            assertEquals(Optional.empty(), userDAO.getUserByLoginAndPass(userId, nonExistentHash));
            assertEquals(Optional.empty(), userDAO.getUserByLoginAndPass(idWithOtherHash, userHash));
            assertEquals(Optional.empty(), userDAO.getUserByLoginAndPass(idWithOtherHash, nonExistentHash));
        }
    }

    @Test
    public void delete() throws SQLException {
        long idToDelete = 1;
        User userToDelete = USERS.get(0);

        assumeTrue(userDAO.get().contains(userToDelete));
        assertEquals(userToDelete, userDAO.delete(idToDelete).orElse(null));
        assertFalse(userDAO.get().contains(userToDelete));
        assertEquals(Optional.empty(), userDAO.delete(idToDelete));
    }

    @Test
    public void changeIdTest() {
        User userToAdd = new User(-1, "addedLog", "addedPass", "addedName");
        int possibleAddedId = USERS.size() + 1;

        assumeFalse(userDAO.get().contains(userToAdd));
        assertTrue(userDAO.add(userToAdd));
        assertEquals(possibleAddedId, userToAdd.getId());
    }

    @Test
    public void addSuccessful() throws SQLException {
        User userToAdd = new User(-1, "addedLog", "addedPass", "addedName");
        int possibleAddedId = USERS.size() + 1;

        assumeFalse(userDAO.get().contains(userToAdd));
        assertTrue(userDAO.add(userToAdd));
        assertEquals(userDAO.get(possibleAddedId).get(), userToAdd);
    }

    @Test
    public void addUnsuccessful() {
        User emptyUser = new User();
        User existingUser = USERS.get(0);

        assertThrows(IllegalArgumentException.class, () -> userDAO.add(emptyUser));
        assertThrows(IllegalArgumentException.class, () -> userDAO.add(existingUser));

        //asserts that original table is the same
        assertEquals(USERS, userDAO.get());
    }

    @Test
    public void updateSuccessful() throws SQLException {
        long idToUpdate = 1;
        User originalUser = userDAO.get(idToUpdate).orElse(null);
        User userUpdate = new User(idToUpdate, "updatedLog", "updatedPass", "updatedName");
        assumeTrue(userDAO.get().contains(originalUser));

        assertTrue(userDAO.update(userUpdate));
        assertEquals(userUpdate, userDAO.get(idToUpdate).get());
        assertFalse(userDAO.get().contains(originalUser));
    }

    @Test
    public void updateUnsuccessful() throws SQLException {
        long idToUpdate = 1;
        User originalUser = userDAO.get(idToUpdate).orElse(null);
        User emptyUser = new User();
        //will repeat login from some user
        User corruptedUser = new User(idToUpdate, USERS.get(2).getLogin(), "updatedPass", "updatedName");
        User corruptedIdUser = new User(idToUpdate - 1, USERS.get(2).getLogin(), "updatedPass", "updatedName");
        assumeTrue(userDAO.get().contains(originalUser));

        assertThrows(IllegalArgumentException.class, () -> userDAO.update(corruptedUser));
        assertThrows(IllegalArgumentException.class, () -> userDAO.update(corruptedIdUser));
        assertThrows(IllegalArgumentException.class, () -> userDAO.update(emptyUser));

        //asserts that original table is the same
        assertEquals(USERS, userDAO.get());
    }

    private final String USERS_TABLE_NAME = User.class.getAnnotation(Table.class).name();

    @BeforeEach
    public void setUp() throws SQLException, IOException, ClassNotFoundException {
        dbSetUpAndTearDown.dbFactoryAndTablesCreation();

        dbSetUpAndTearDown.setUpUsersTableValues(USERS);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        dbSetUpAndTearDown.dbFactoryAndTablesTearDown();
    }


    protected abstract UserDao getUserDao();

    private final UserDao userDAO = getUserDao();

    private static final DBSetUpAndTearDown dbSetUpAndTearDown = new DBSetUpAndTearDown();
    private final List<User> USERS = DataConstants.USERS;

}

