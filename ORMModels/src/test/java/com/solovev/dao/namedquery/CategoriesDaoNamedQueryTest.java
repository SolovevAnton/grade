package com.solovev.dao.namedquery;

import com.solovev.dao.CategoriesDao;
import com.solovev.dao.CategoriesDaoTest;

public class CategoriesDaoNamedQueryTest extends CategoriesDaoTest {
    @Override
    protected CategoriesDao categoriesDao() {
        return new CategoriesDaoNamedQuery();
    }
}
