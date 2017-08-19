package com.carpediemsolution.englishcards.model;

import com.carpediemsolution.englishcards.utils.DBSchema;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Юлия on 16.04.2017.
 */
@DatabaseTable(tableName = DBSchema.CardTable.USER_NAME)
public class User {

    @DatabaseField(canBeNull = false, dataType = DataType.STRING)
    private String username;
    @DatabaseField(canBeNull = false, dataType = DataType.STRING)
    private String password;
    @DatabaseField(dataType = DataType.INTEGER)
    private int personId;
    @DatabaseField(canBeNull = false, dataType = DataType.STRING)
    private String email;
    @DatabaseField(canBeNull = false, dataType = DataType.STRING)
    private String token;

    public String getPassword() {
        return password;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getToken() {return token;}

    public void setToken(String token) {this.token = token;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}
}
