package com.onuryahsi.roomwordsample;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onuryahsi.roomwordsample.Model.Word;
import com.onuryahsi.roomwordsample.ViewModel.WordViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WordViewModel wordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewWord);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        WordListAdapter wordListAdapter = new WordListAdapter();
        recyclerView.setAdapter(wordListAdapter);


        wordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        wordViewModel.getAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                // Update RecyclerView
                wordListAdapter.setWordList(words);
            }
        });
    }
}
// https://www.youtube.com/channel/UC_Fh8kvtkVPkeihBs42jGcA