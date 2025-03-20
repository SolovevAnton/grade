package com.solovev.dao;

import com.solovev.model.Category;

import java.util.Collection;

public interface CategoriesDao extends DAO<Category> {
    /**
     * Gets category by users id
     *
     * @param userId id of the user
     * @return matching categories
     */
    public Collection<Category> getByUser(Long userId);
}
