package com.solovev.dao.criteria;

import com.solovev.dao.UserDao;
import com.solovev.dao.UserDaoTest;

public class UserDaoCriteriaTest extends UserDaoTest {
    @Override
    protected UserDao getUserDao() {
        return new UserDaoCriteria();
    }
}
