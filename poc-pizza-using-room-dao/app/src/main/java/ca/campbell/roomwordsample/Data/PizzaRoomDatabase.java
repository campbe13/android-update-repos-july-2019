package ca.campbell.roomwordsample.Data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

/**
 * ref https://medium.com/@tonia.tkachuk/android-app-example-using-room-database-63f7091e69af
 * Database class
 * @author PMCampbell
 * @version today
 */
@Database(entities = {Pizza.class}, version = 1, exportSchema = false)
public abstract class PizzaRoomDatabase extends RoomDatabase {
    // getter abstract
    public abstract PizzaDAO pizzaDao();
    private static final String TAG = "DB";

    // make DB a singleton
    private static volatile PizzaRoomDatabase INSTANCE;

    static PizzaRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PizzaRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                PizzaRoomDatabase.class, "word_database")
                                .addCallback(sRoomDatabaseCallback)
                                .build();
                    }
            }
        }
        return INSTANCE;
    }
    // To delete all content and repopulate the database whenever the app is started,
    // you create a RoomDatabase.Callback and override onOpen(). Because you cannot do
    // Room database operations on the UI thread, onOpen() creates and executes an
    // AsyncTask to add content to the database.
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db){
                    super.onCreate(db);
                    Log.d(TAG, "populating new db");
                    new PopulateDbAsync(INSTANCE).execute();
                }

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    Log.d(TAG, "open db");
                }
            };
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final PizzaDAO mDao;

        PopulateDbAsync(PizzaRoomDatabase db) {
            mDao = db.pizzaDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            String pizzaNames[] = { "abc", "def", "ghi"} ;
            mDao.deleteAll();
            for (int i=0 ; i< pizzaNames.length; i++) {
                Pizza word = new Pizza(pizzaNames[i]);
                mDao.insert(word);
            }
            return null;
        }
    }
}