package com.codepath.mypizza.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Database Pizza table entity
 * annotated for Room persistence library
 */
@Entity(tableName = "pizza_table")
public class Pizza {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int primaryKey;
    @NonNull
    @ColumnInfo(name = "pizza")
    private String pizzaName;
    @NonNull
    @ColumnInfo(name = "text")
    private String pizzaText;

    public Pizza(@NonNull String pizzaName, @NonNull String pizzaText) {
        this.pizzaName = pizzaName;
        this.pizzaText = pizzaText;
    }

    public String getPizzaName() {
        return this.pizzaName;
    }

    public String getPizzaText() {
        return this.pizzaText;
    }

    public void deleteAll() {
    }

    public class PizzaName {
        @ColumnInfo(name = "pizza")
        private String pizzaName;
    }
}
