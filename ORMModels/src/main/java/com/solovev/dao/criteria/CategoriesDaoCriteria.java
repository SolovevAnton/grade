package com.solovev.dao.criteria;

import com.solovev.dao.CategoriesDao;
import com.solovev.model.Category;

import java.util.Collection;

public class CategoriesDaoCriteria extends CriteriaAbstractDao<Category> implements CategoriesDao {
    public CategoriesDaoCriteria() {
        super(Category.class);
    }

    public Collection<Category> getByUser(Long userId) {
        return getObjectsByParam("user", userId);
    }
}
