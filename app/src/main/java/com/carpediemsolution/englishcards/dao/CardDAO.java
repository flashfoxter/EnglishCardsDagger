package com.carpediemsolution.englishcards.dao;

import com.carpediemsolution.englishcards.model.Card;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Юлия on 30.05.2017.
 */

public class CardDAO extends BaseDaoImpl<Card, Integer> {

    public CardDAO(ConnectionSource connectionSource,
                   Class<Card> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
    //to do...
    public Card getCardById(int id) throws SQLException {
        return this.queryForId(id);
    }

    public void deleteCard (Card card) throws SQLException {
        this.delete(card);
    }

    public List<Card>getAllCards()throws SQLException{
       //возвращает все карточки без сортировки по темам
       return this.queryForAll();
    }
    //to do...
    public List<Card> getCardsByTheme() throws SQLException{
        //возвращает карточки,отсортированные по темам
        return this.queryBuilder().orderBy("theme", false).query();
    }

}

