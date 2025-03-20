package com.solovev.dao.namedquery;

import com.solovev.dao.CategoriesDao;
import com.solovev.model.Category;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class CategoriesDaoNamedQuery implements CategoriesDao {
    @Override
    public Collection<Category> getByUser(Long userId) {
        return List.of();
    }

    @Override
    public Optional<Category> get(long id) {
        return Optional.empty();
    }

    @Override
    public Collection<Category> get() {
        return List.of();
    }

    @Override
    public boolean add(Category elem) throws IllegalArgumentException {
        return false;
    }

    @Override
    public Optional<Category> delete(long id) {
        return Optional.empty();
    }

    @Override
    public boolean update(Category elem) {
        return false;
    }
}
