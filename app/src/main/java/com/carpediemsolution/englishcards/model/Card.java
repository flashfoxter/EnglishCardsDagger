package com.carpediemsolution.englishcards.model;

import com.carpediemsolution.englishcards.utils.DBSchema;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Юлия on 21.03.2017.
 */
@DatabaseTable(tableName = DBSchema.CardTable.NAME_ENRUS)
public class Card {

    @DatabaseField(generatedId = true)
    private int Id;

    @SerializedName("id")
    @Expose
    private String id;

    @DatabaseField(dataType = DataType.INTEGER)
    @SerializedName("person_id")
    @Expose
    private int personId;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING)
    @SerializedName("theme")
    @Expose
    private String theme;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING)
    @SerializedName("translate")
    @Expose
    private String translate;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING)
    @SerializedName("word")
    @Expose
    private String word;

    @DatabaseField(dataType = DataType.STRING)
    @SerializedName("description")
    @Expose
    private String description;

    public Card() {
    }

    public Card(String word, String translate, String description, String theme, String id, int personId) {
        this.word = word;
        this.translate = translate;
        this.description = description;
        this.theme = theme;
        this.id = id;
        this.personId = personId;
    }

    public Card(int id) {
        this.personId = id;
    }

    public String getId() {
        return id;
    }

    public int getPersonId() {
        return personId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPersonId(int person_id) {
        this.personId = person_id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String mTheme) {
        this.theme = mTheme;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String mTranslate) {
        this.translate = mTranslate;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String mWord) {
        this.word = mWord;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
