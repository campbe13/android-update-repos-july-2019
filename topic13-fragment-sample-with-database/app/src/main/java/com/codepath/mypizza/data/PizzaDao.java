package com.codepath.mypizza.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

/**
 * This is the DAO  interface for the pizza db
 * see https://developer.android.com/training/data-storage/room/accessing-data
 * @author pmcampbell
 * @version today
 */
import java.util.List;

@Dao
public interface PizzaDao {
    @Insert
    void insert(Pizza pizza);

    @Query("select * from pizza_table order by pizza asc ")
    LiveData<List<Pizza>> getAllPizzas();

    @Query("select * from pizza_table order by pizza asc ")
    LiveData<List<Pizza.PizzaName>> getAllPizzaNames();

    @Query("select *  from pizza_table where pizza like :pizzareq ")
    LiveData<List<Pizza>> getPizza(String pizzareq);

    @Query("DELETE FROM pizza_table")
    void deleteAll();
}
