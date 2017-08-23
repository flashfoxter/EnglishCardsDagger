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
    private int person_id;

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

    public Card(String word, String translate, String description, String theme, String id, int person_id) {
        this.word = word;
        this.translate = translate;
        this.description = description;
        this.theme = theme;
        this.id = id;
        this.person_id = person_id;
    }

    public Card(int id) {
        this.person_id = id;
    }

    public String getId() {
        return id;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
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
