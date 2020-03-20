package com.onuryahsi.roomwordsample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.onuryahsi.roomwordsample.Model.Word;

import java.util.ArrayList;
import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordHolder> {

    private List<Word> wordList = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public WordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);

        return new WordHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WordHolder holder, int position) {
        Word currentWord = wordList.get(position);
        holder.textView_id.setText("" + currentWord.getId());
        holder.textView_word.setText(currentWord.getMWord());
    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }

    public void setWordList(List<Word> words) // Bu kısmı manuel ekledik. Tüm listeyi almak için sanırım. LiveData ile alakalı
    {
        this.wordList = words;
        notifyDataSetChanged();
    }

    public Word getWordAt(Integer position) { // swipe left için
        return wordList.get(position);
    }

    class WordHolder extends RecyclerView.ViewHolder { // yukarıdaki WordListAdapter.WordHolder oluşturdu

        private TextView textView_id;
        private TextView textView_word;

        public WordHolder(@NonNull View itemView) { // WordHolder classı oluşunca super() geldi
            super(itemView);
            textView_id = itemView.findViewById(R.id.txtViewId);
            textView_word = itemView.findViewById(R.id.txtViewWord);

            itemView.setOnClickListener(new View.OnClickListener() { // click listener eklendi
                @Override
                public void onClick(View v) {
                    if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION)
                        listener.onItemClick(wordList.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface OnItemClickListener { // click listener interface eklendi
        void onItemClick(Word word);
    }

    public void setOnItemClickListener(OnItemClickListener listener) { // click listener item listener
        this.listener = listener;
    }
}
