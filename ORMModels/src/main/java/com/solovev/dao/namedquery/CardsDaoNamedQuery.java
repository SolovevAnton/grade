package com.solovev.dao.namedquery;

import com.solovev.dao.CardsDao;
import com.solovev.dao.CategoriesDao;
import com.solovev.dao.UserDao;
import com.solovev.model.Card;

import java.util.*;

import static java.util.Objects.isNull;

public class CardsDaoNamedQuery implements CardsDao {

    private final DaoFacade<Card> daoFacade = new DaoFacade<>(Card.class);
    private final CategoriesDao categoriesDao = new CategoriesDaoNamedQuery();
    private final UserDao userDaoNamedQuery = new UserDaoNamedQuery();

    @Override
    public Collection<Card> getByUser(Long userId) {
        var foundUser = userDaoNamedQuery.get(userId);
        return foundUser.isPresent()
                ? daoFacade.getResults("Card_getByUser", "user", foundUser.get())
                : Collections.emptyList();
    }

    @Override
    public Collection<Card> getByCategory(Long categoryId) {
        var category = categoriesDao.get(categoryId);
        return category.isPresent()
                ? daoFacade.getResults("Card_getByCategory", "category", category.get())
                : Collections.emptyList();
    }

    @Override
    public Optional<Card> get(long id) {
        return daoFacade.getResult("Card_getById", "id", id);
    }

    @Override
    public Collection<Card> get() {
        return daoFacade.getResults("Card_getAll");
    }

    @Override
    public boolean add(Card elem) throws IllegalArgumentException {
        var card = getCardMap(elem);
        var id = daoFacade.executeNativeUpdate("Card_insert", card);
        elem.setId(id);
        return true;
    }

    @Override
    public Optional<Card> delete(long id) {
        var found = get(id);
        found.ifPresent(__ -> daoFacade.executeNamedUpdate("Card_delete", Map.of("id", id)));
        return found;
    }

    @Override
    public boolean update(Card elem) {
        var cardMap = getCardMap(elem);
        cardMap.put("id", elem.getId());
        return daoFacade.executeNamedUpdate("Card_update", cardMap);
    }

    private Map<String, Object> getCardMap(Card elem) {
        var category = elem.getCategory();
        if (isNull(category)) {
            throw new IllegalArgumentException("category is null");
        }
        Map<String, Object> categoriesMap = new HashMap<>();
        categoriesMap.put("question", elem.getQuestion());
        categoriesMap.put("answer", elem.getAnswer());
        categoriesMap.put("category_id", elem.getCategory().getId());
        categoriesMap.put("creation_date", elem.getCreationDate());
        return categoriesMap;
    }
}
