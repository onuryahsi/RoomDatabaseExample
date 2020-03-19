package com.onuryahsi.roomwordsample.Database;

import android.content.Context;
import android.os.AsyncTask;

import com.onuryahsi.roomwordsample.Dao.WordDao;
import com.onuryahsi.roomwordsample.Model.Word;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Word.class, version = 1, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {

    public abstract WordDao wordDao(); // Abstract Class has no body. We will use it in Repository

    private static WordRoomDatabase INSTANCE;

    public static synchronized WordRoomDatabase getINSTANCE(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    WordRoomDatabase.class, "word_database")
                    .addCallback(roomCallBack) // initialize
                    .fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }

    private static WordRoomDatabase.Callback roomCallBack = new WordRoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private WordDao wordDao;

        private PopulateDbAsyncTask(WordRoomDatabase wordRoomDatabase) {
            wordDao = wordRoomDatabase.wordDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            wordDao.insert(new Word("Turkey"));
            wordDao.insert(new Word("Germany"));
            wordDao.insert(new Word("Sweden"));
            wordDao.insert(new Word("Great Britain"));
            return null;
        }
    }
}
