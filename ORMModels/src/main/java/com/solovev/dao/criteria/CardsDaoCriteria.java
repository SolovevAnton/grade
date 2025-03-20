package com.solovev.dao.criteria;

import com.solovev.dao.CardsDao;
import com.solovev.model.Card;
import com.solovev.model.Category;

import java.util.ArrayList;
import java.util.Collection;

public class CardsDaoCriteria extends CriteriaAbstractDao<Card> implements
        CardsDao {
    public CardsDaoCriteria() {
        super(Card.class);
    }


    public Collection<Card> getByUser(Long userId) {
        Collection<Category> categoriesByUser = new CategoriesDaoCriteria().getByUser(userId);
        Collection<Card> result = new ArrayList<>();

        for (Category category : categoriesByUser) {
            long categoryId = category.getId();
            result.addAll(getByCategory(categoryId));
        }

        return result;
    }


    public Collection<Card> getByCategory(Long categoryId) {
        return getObjectsByParam("category", categoryId);
    }
}
