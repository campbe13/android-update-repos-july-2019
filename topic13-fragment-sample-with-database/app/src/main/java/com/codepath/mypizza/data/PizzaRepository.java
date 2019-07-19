package com.codepath.mypizza.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class PizzaRepository {
    private PizzaDao pizzaDao;
    private LiveData<List<Pizza>> allPizzas;
    private LiveData<List<Pizza.PizzaName>>  allPizzaNames;

    public PizzaRepository(Application app) {
        PizzaDatabase db = PizzaDatabase.getDatabase(app);
        pizzaDao = db.pizzaDao();
        allPizzas = pizzaDao.getAllPizzas();
        allPizzaNames = pizzaDao.getAllPizzaNames();
    }
    // observed LiveData will notify observer when it gets data
    public LiveData<List<Pizza>> getAllPizzas() {
        return allPizzas;
    }

    // observed LiveData will notify observer when it gets data
    public LiveData<List<Pizza.PizzaName>> getAllPizzaNames() {
        return allPizzaNames;
    }

    // insert wrapper we put insert on a bg thread
    public void insert (Pizza pizza) {
        new insertAsyncTask(pizzaDao).execute(pizza);
    }
    private static class insertAsyncTask extends AsyncTask<Pizza, Void, Void> {

        private PizzaDao asyncTaskDao;

        insertAsyncTask(PizzaDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Pizza... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }

}
