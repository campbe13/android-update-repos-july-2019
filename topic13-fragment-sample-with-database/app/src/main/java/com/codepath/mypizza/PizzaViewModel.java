package com.codepath.mypizza;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.codepath.mypizza.data.Pizza;
import com.codepath.mypizza.data.PizzaRepository;

import java.util.List;

public class PizzaViewModel extends AndroidViewModel{
    private PizzaRepository pizzaRepository;
    private LiveData<List<Pizza>> allPizzas;
    public PizzaViewModel(@NonNull Application application) {
        super(application);
        pizzaRepository = new PizzaRepository(application);
        allPizzas = pizzaRepository.getAllPizzas();
    }
    LiveData<List<Pizza>> getAllPizzas() { return allPizzas; }
    public void insert (Pizza pizza ) { pizzaRepository.insert(pizza); }
}
