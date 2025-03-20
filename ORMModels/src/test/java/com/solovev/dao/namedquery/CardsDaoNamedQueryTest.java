package com.solovev.dao.namedquery;

import com.solovev.dao.CardsDao;
import com.solovev.dao.CardsDaoTest;

public class CardsDaoNamedQueryTest extends CardsDaoTest {
    @Override
    protected CardsDao getCardsDao() {
        return new CardsDaoNamedQuery();
    }
}
