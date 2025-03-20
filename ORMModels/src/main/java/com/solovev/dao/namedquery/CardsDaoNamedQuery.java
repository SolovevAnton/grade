package com.solovev.dao.namedquery;

import com.solovev.dao.CardsDao;
import com.solovev.model.Card;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class CardsDaoNamedQuery implements CardsDao {
    @Override
    public Collection<Card> getByUser(Long userId) {
        return List.of();
    }

    @Override
    public Collection<Card> getByCategory(Long categoryId) {
        return List.of();
    }

    @Override
    public Optional<Card> get(long id) {
        return Optional.empty();
    }

    @Override
    public Collection<Card> get() {
        return List.of();
    }

    @Override
    public boolean add(Card elem) throws IllegalArgumentException {
        return false;
    }

    @Override
    public Optional<Card> delete(long id) {
        return Optional.empty();
    }

    @Override
    public boolean update(Card elem) {
        return false;
    }
}
