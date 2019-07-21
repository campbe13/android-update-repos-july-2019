package ca.campbell.roomwordsample.Data;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface PizzaTextDAO {
    static final String TABLE = "pizzatext_table";
    @Insert
    void insert(PizzaText pizzaText);

    // should not be needed ??
    @Query("delete from "+TABLE)
    void deleteAll();

    @Query("select * from " + TABLE + " where textId == :textId limit 1")
    PizzaText  getById(int textId);
}