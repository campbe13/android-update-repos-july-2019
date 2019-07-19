package ca.campbell.roomwordsample.Data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "pizza_table")
public class Pizza {
    // autoGenerate optional
    // must be int, Integer, long or Long
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="pid")
    public int id;

    @NonNull
    @ColumnInfo(name="pizza")
    private String pizza;

    public Pizza(@NonNull String pizza) {this.pizza = pizza;}

    public String getPizza(){return this.pizza;}
    public int getPId(){return this.id;}

}
