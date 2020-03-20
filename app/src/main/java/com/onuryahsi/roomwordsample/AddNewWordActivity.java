package com.onuryahsi.roomwordsample;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddNewWordActivity extends AppCompatActivity {

    private EditText editTextNewWord;
    public static String EXTRA_WORD = "com.onuryahsi.roomwordsample.EXTRA_WORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_word);

        editTextNewWord = findViewById(R.id.EditText_newWord);

        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_keyboard_backspace));
        setTitle("Add A Word");
    }

    private void saveWord() {
        String mWord = editTextNewWord.getText().toString();
        if (editTextNewWord.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Type A Word..", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_WORD, mWord);
        setResult(RESULT_OK, data);
        finish(); // -> MainActivity'e gidiyoruz
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.word_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_word:
                saveWord();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
