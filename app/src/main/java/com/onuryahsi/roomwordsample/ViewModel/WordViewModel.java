package com.onuryahsi.roomwordsample.ViewModel;

import android.app.Application;

import com.onuryahsi.roomwordsample.Model.Word;
import com.onuryahsi.roomwordsample.Repository.WordRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class WordViewModel extends AndroidViewModel {

    private WordRepository wordRepository;
    private LiveData<List<Word>> allWords;

    public WordViewModel(@NonNull Application application) {
        super(application);
        wordRepository = new WordRepository(application);
        allWords = wordRepository.getWordList();
    }

    public void insert(Word word) {
        wordRepository.insert(word);
    }

    public void update(Word word) {
        wordRepository.update(word);
    }

    public void delete(Word word) {
        wordRepository.delete(word);
    }

    public void deleteAll() {
        wordRepository.deleteAll();
    }

    public LiveData<List<Word>> getAllWords() {
        return allWords;
    }
}