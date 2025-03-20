package com.solovev.dao.criteria;

import com.solovev.dao.CategoriesDao;
import com.solovev.dao.CategoriesDaoTest;

public class CategoriesDaoCriteriaTest extends CategoriesDaoTest {
    @Override
    protected CategoriesDao categoriesDao() {
        return new CategoriesDaoCriteria();
    }
}
