package ca.campbell.roomwordsample;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ca.campbell.roomwordsample.Data.PizzaRepository;
import ca.campbell.roomwordsample.Data.Pizza;
import ca.campbell.roomwordsample.Data.PizzaRepository;

public class PizzaViewModel extends AndroidViewModel {
    // Add a private member variable to hold a reference to the repository.
    private PizzaRepository mRepository;
    // Add a private LiveData member variable to cache the list of words.
    private LiveData<List<Pizza>> mAllPizzas;
    // Add a constructor that gets a reference to the repository and gets the list of words from the repository.
    public PizzaViewModel (Application application) {
        super(application);
        mRepository = new PizzaRepository(application);
        mAllPizzas = mRepository.getAllPizzas();
    }
    // Add a "getter" method for all the words. This completely hides the implementation from the UI.
    LiveData<List<Pizza>> getAllPizzas() { return mAllPizzas; }
    // Create a wrapper insert() method that calls the Repository's insert() method. In this way, the implementation of insert() is completely hidden from the UI.
    public void insert(Pizza word) { mRepository.insert(word); }

}
