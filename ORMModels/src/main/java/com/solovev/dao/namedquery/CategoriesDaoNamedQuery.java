package com.solovev.dao.namedquery;

import com.solovev.dao.CategoriesDao;
import com.solovev.model.Category;

import java.util.*;

import static java.util.Objects.isNull;

public class CategoriesDaoNamedQuery implements CategoriesDao {

    private final DaoFacade<Category> daoFacade = new DaoFacade<>(Category.class);
    private final UserDaoNamedQuery userDaoNamedQuery = new UserDaoNamedQuery();

    @Override
    public Collection<Category> getByUser(Long userId) {
        var foundUser = userDaoNamedQuery.get(userId);
        return foundUser.isPresent()
                ? daoFacade.getResults("Category_getByUser", "user", foundUser.get())
                : Collections.emptyList();
    }

    @Override
    public Optional<Category> get(long id) {
        return daoFacade.getResult("Category_getById", "id", id);
    }

    @Override
    public Collection<Category> get() {
        return daoFacade.getResults("Category_getAll");
    }

    @Override
    public boolean add(Category elem) throws IllegalArgumentException {
        var category = getCategoryMap(elem);
        var id = daoFacade.executeNativeUpdate("Category_insert", category);
        elem.setId(id);
        return true;
    }

    @Override
    public Optional<Category> delete(long id) {
        var found = get(id);
        found.ifPresent(__ -> daoFacade.executeNamedUpdate("Category_delete", Map.of("id", id)));
        return Optional.empty();
    }

    @Override
    public boolean update(Category elem) {
        var catMap = getCategoryMap(elem);
        catMap.put("id", elem.getId());
        return daoFacade.executeNamedUpdate("Category_update", catMap);
    }

    private Map<String, Object> getCategoryMap(Category elem) {
        var user = elem.getUser();
        if (isNull(user)) {
            throw new IllegalArgumentException("user is null");
        }
        Map<String, Object> categoriesMap = new HashMap<>();
        categoriesMap.put("name", elem.getName());
        categoriesMap.put("user_id", elem.getUser().getId());
        return categoriesMap;
    }
}
