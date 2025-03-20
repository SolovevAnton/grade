package com.solovev.dao.model;

import com.solovev.dao.CardsDao;
import com.solovev.dao.CategoriesDao;
import com.solovev.dao.UserDao;
import com.solovev.dao.criteria.CardsDaoCriteria;
import com.solovev.dao.criteria.CategoriesDaoCriteria;
import com.solovev.dao.criteria.UserDaoCriteria;

public class CriteriaRelationshipsTest extends RelationshipsTest {
    @Override
    UserDao userDao() {
        return new UserDaoCriteria();
    }

    @Override
    CategoriesDao categoriesDao() {
        return new CategoriesDaoCriteria();
    }

    @Override
    CardsDao cardsDao() {
        return new CardsDaoCriteria();
    }
}
