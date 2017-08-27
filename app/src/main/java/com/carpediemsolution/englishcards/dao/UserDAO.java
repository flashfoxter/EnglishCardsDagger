package com.carpediemsolution.englishcards.dao;

import com.carpediemsolution.englishcards.model.User;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

/**
 * Created by Юлия on 18.08.2017.
 */

public class UserDAO extends BaseDaoImpl<User, Integer> {
    //to do...
    public UserDAO(ConnectionSource connectionSource,
                   Class<User> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}
