package com.solovev.dao;

import com.solovev.model.Card;

import java.util.Collection;

public interface CardsDao extends DAO<Card> {
    /**
     * Gets cards by users id
     *
     * @param userId id of the user
     * @return matching cards
     */
    Collection<Card> getByUser(Long userId);

    /**
     * Gets cards by category id
     *
     * @param categoryId id of the user
     * @return matching cards
     */
    Collection<Card> getByCategory(Long categoryId);
}
