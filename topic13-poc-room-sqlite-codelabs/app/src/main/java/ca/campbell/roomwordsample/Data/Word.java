package ca.campbell.roomwordsample.Data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "word_table")
public class Word {
    // autoGenerate optional
    // must be int, Integer, long or Long
    // @PrimaryKey(autoGenerate = true)
    @PrimaryKey
    @NonNull
    @ColumnInfo(name="word")
    private String mWord;

    public Word(@NonNull String word) {this.mWord = word;}

    public String getWord(){return this.mWord;}
}
