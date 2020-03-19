package com.onuryahsi.roomwordsample.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "word_table")
public class Word {

    @PrimaryKey
    private Integer id;

    @NonNull
    @ColumnInfo(name = "word")
    private String mWord;

    @NonNull
    public String getMWord() {
        return mWord;
    }

    public void setmWord(@NonNull String mWord) {
        this.mWord = mWord;
    }

    public Word(@NonNull String mWord) {
        this.mWord = mWord;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
