package com.solovev.dao.daoImplementations;

import com.solovev.dao.AbstractDAO;
import com.solovev.model.Category;

import java.util.Collection;

public class CategoriesDao extends AbstractDAO<Category> {
    public CategoriesDao() {
        super(Category.class);
    }

    /**
     * Gets category by users id
     *
     * @param userId id of the user
     * @return matching categories
     */
    public Collection<Category> getByUser(Long userId) {
        return getObjectsByParam("user", userId);
    }
}
