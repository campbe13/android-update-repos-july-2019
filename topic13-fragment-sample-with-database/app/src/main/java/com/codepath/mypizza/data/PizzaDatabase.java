package com.codepath.mypizza.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * This is the Database class for the pizza db
 * see https://developer.android.com/training/data-storage/room/accessing-data
 * @author pmcampbell
 * @version today
 */

// db can consist of multiple tables which would be multiple entities
// ex @Database(entities = {Pizza.class, Calzone.class}, version = 1)
@Database(entities = {Pizza.class}, version = 1)
public abstract class PizzaDatabase extends RoomDatabase {
    public abstract PizzaDao pizzaDao();

    // make it a singleton
    private static PizzaDatabase SINGLEDB;

    static PizzaDatabase getDatabase(final Context context) {
        if (SINGLEDB == null) {
            synchronized (PizzaDatabase.class) {
                if (SINGLEDB == null) {
                    // Create database here
                    SINGLEDB = Room.databaseBuilder(context.getApplicationContext(),
                            PizzaDatabase.class, "word_database")
                            .build();
                }
            }
        }
        return SINGLEDB;
    }

    private static RoomDatabase.Callback roomDBCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(SINGLEDB).execute(PizzaStrings.pizzaMenu, PizzaStrings.pizzaDetails);
                }
            };

    private static class PopulateDbAsync extends AsyncTask<String[], Void, Void> {

        private final PizzaDao dao;

        PopulateDbAsync(PizzaDatabase db) {
            dao = db.pizzaDao();
        }

        @Override
        protected Void doInBackground(final String[]... params) {
            String[] pizzaNames = params[0];
            String[] pizzaText = params[1];

            dao.deleteAll();

            for (int i = 0; i < pizzaNames.length; i++) {
                Pizza pizza = new Pizza(pizzaNames[i], pizzaText[i]);
                dao.insert(pizza);
            }
            return null;
        }
    }
}