package ca.campbell.roomwordsample.Data;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface PizzaDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Pizza pizzaname);

    @Query("delete from pizza_table")
    void deleteAll();

    @Query("select * from pizza_table order by pizza asc")
    LiveData<List<Pizza>>   getAllPizzas();
    
    @Query("select * from pizza_table where pizza like :pizzaname limit 1")
    Pizza  getByName(String pizzaname);
}
