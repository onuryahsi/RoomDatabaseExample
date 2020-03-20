package com.onuryahsi.roomwordsample;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.onuryahsi.roomwordsample.Model.Word;
import com.onuryahsi.roomwordsample.ViewModel.WordViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WordViewModel wordViewModel;

    public static final Integer ADD_WORD_REQUEST = 1;
    public static final Integer EDIT_WORD_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewWord);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        WordListAdapter wordListAdapter = new WordListAdapter();
        recyclerView.setAdapter(wordListAdapter);

        FloatingActionButton floatingActionButton = findViewById(R.id.floating_new_word);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddNewWordActivity.class);
                startActivityForResult(i, ADD_WORD_REQUEST);
            }
        });

        wordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        wordViewModel.getAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                // Update RecyclerView
                wordListAdapter.setWordList(words);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                wordViewModel.delete(wordListAdapter.getWordAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);

        wordListAdapter.setOnItemClickListener(new WordListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Word word) {
                Intent i = new Intent(MainActivity.this, AddEditWordActivity.class);
                i.putExtra(AddEditWordActivity.EXTRA_ID, word.getId());
                i.putExtra(AddEditWordActivity.EXTRA_WORD, word.getMWord());
                startActivityForResult(i, EDIT_WORD_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_WORD_REQUEST && resultCode == RESULT_OK) {
            String word = data.getStringExtra(AddNewWordActivity.EXTRA_WORD);
            Word w = new Word(word);
            wordViewModel.insert(w);
            Toast.makeText(getApplicationContext(), "Word Saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_WORD_REQUEST && resultCode == RESULT_OK) {
            Integer id = data.getIntExtra(AddEditWordActivity.EXTRA_ID, -1);
            if (id.equals(-1)) {
                Toast.makeText(getApplicationContext(), "Word Not Updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String word = data.getStringExtra(AddEditWordActivity.EXTRA_WORD);
            Word w = new Word(word);
            w.setId(id);
            w.setmWord(word);
            wordViewModel.update(w);
            Toast.makeText(getApplicationContext(), "" + id + " : Word Updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Word Not Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_delete_all, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_delete_all:
                wordViewModel.deleteAll();
                Toast.makeText(getApplicationContext(), "All Words Deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
// https://www.youtube.com/channel/UC_Fh8kvtkVPkeihBs42jGcA