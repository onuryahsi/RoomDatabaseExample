package com.onuryahsi.roomwordsample.Repository;

import android.app.Application;
import android.os.AsyncTask;

import com.onuryahsi.roomwordsample.Dao.WordDao;
import com.onuryahsi.roomwordsample.Database.WordRoomDatabase;
import com.onuryahsi.roomwordsample.Model.Word;

import java.nio.channels.WritePendingException;
import java.util.List;

import androidx.lifecycle.LiveData;

public class WordRepository {

    private WordDao wordDao;
    private LiveData<List<Word>> wordList;

    public WordRepository(Application application) {
        WordRoomDatabase wordRoomDatabase = WordRoomDatabase.getINSTANCE(application);
        wordDao = wordRoomDatabase.wordDao();
        wordList = wordDao.getAllWords();
    }

    public void insert(Word word) {
        new InsertWordAsync(wordDao).execute(word);
    }

    public void update(Word word) {
        new UpdateWordAsync(wordDao).execute(word);
    }

    public void delete(Word word) {
        new DeleteWordAsync(wordDao).execute(word);
    }

    public void deleteAll() {
        new DeleteAllWordAsync(wordDao).execute();
    }

    public LiveData<List<Word>> getWordList() {
        return wordList;
    }

    private static class InsertWordAsync extends AsyncTask<Word, Void, Void> {
        private WordDao wordDao;

        private InsertWordAsync(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.insert(words[0]);
            return null;
        }
    }

    private static class UpdateWordAsync extends AsyncTask<Word, Void, Void> {
        private WordDao wordDao;

        private UpdateWordAsync(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.update(words[0]);
            return null;
        }
    }

    private static class DeleteWordAsync extends AsyncTask<Word, Void, Void> {
        private WordDao wordDao;

        private DeleteWordAsync(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.delete(words[0]);
            return null;
        }
    }

    private static class DeleteAllWordAsync extends AsyncTask<Word, Void, Void> {
        private WordDao wordDao;

        private DeleteAllWordAsync(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.deleteAll();
            return null;
        }
    }
}
