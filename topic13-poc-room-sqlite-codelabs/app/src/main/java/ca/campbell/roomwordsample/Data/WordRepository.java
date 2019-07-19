package ca.campbell.roomwordsample.Data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

// create a public class called WordRepository.
public class WordRepository {
    //Add member variables for the DAO and the list of words.
    private WordDAO mWordDao;
    private LiveData<List<Word>> mAllWords;
    // Add a constructor that gets a handle to the database and initializes the member variables.

    public WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAllWords();
    }
    // Add a wrapper for getAllWords(). Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }
    public void insert (Word word) {
        new insertAsyncTask(mWordDao).execute(word);
    }

    private static class insertAsyncTask extends AsyncTask<Word, Void, Void> {

        private WordDAO mAsyncTaskDao;

        insertAsyncTask(WordDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

}
