package ca.campbell.roomwordsample.Data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

// create a public class called PizzaRepository.
public class PizzaRepository {
    //Add member variables for the DAO and the list of Pizzas.
    private PizzaDAO mPizzaDao;
    private LiveData<List<Pizza>> mAllPizzas;
    // Add a constructor that gets a handle to the database and initializes the member variables.

    public PizzaRepository(Application application) {
        PizzaRoomDatabase db = PizzaRoomDatabase.getDatabase(application);
        mPizzaDao = db.pizzaDao();
        mAllPizzas = mPizzaDao.getAllPizzas();
    }
    // Add a wrapper for getAllPizzas(). Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Pizza>> getAllPizzas() {
        return mAllPizzas;
    }
    public void insert (Pizza Pizza) {
        new insertAsyncTask(mPizzaDao).execute(Pizza);
    }

    private static class insertAsyncTask extends AsyncTask<Pizza, Void, Void> {

        private PizzaDAO mAsyncTaskDao;

        insertAsyncTask(PizzaDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Pizza... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

}
