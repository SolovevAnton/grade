package com.solovev.dao.namedquery;

import com.solovev.dao.UserDao;
import com.solovev.dao.UserDaoTest;

class UserDaoNamedQueryTest extends UserDaoTest {
    @Override
    protected UserDao getUserDao() {
        return new UserDaoNamedQuery();
    }
}