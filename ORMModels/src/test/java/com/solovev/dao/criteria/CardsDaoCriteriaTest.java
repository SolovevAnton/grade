package com.solovev.dao.criteria;

import com.solovev.dao.CardsDao;
import com.solovev.dao.CardsDaoTest;

public class CardsDaoCriteriaTest extends CardsDaoTest {
    @Override
    protected CardsDao getCardsDao() {
        return new CardsDaoCriteria();
    }
}
