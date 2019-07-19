package ca.campbell.roomwordsample;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ca.campbell.roomwordsample.Data.Word;
import ca.campbell.roomwordsample.Data.WordRepository;

public class WordViewModel extends AndroidViewModel {
    // Add a private member variable to hold a reference to the repository.
    private WordRepository mRepository;
    // Add a private LiveData member variable to cache the list of words.
    private LiveData<List<Word>> mAllWords;
    // Add a constructor that gets a reference to the repository and gets the list of words from the repository.
    public WordViewModel (Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
    }
    // Add a "getter" method for all the words. This completely hides the implementation from the UI.
    LiveData<List<Word>> getAllWords() { return mAllWords; }
    // Create a wrapper insert() method that calls the Repository's insert() method. In this way, the implementation of insert() is completely hidden from the UI.
    public void insert(Word word) { mRepository.insert(word); }

}
