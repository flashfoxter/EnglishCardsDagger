package com.carpediemsolution.englishcards.general;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.carpediemsolution.englishcards.R;
import com.carpediemsolution.englishcards.model.Card;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RepositoryHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.list_item__word_text_view)
    TextView word;

    public RepositoryHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(@NonNull Card card) {
        word.setText(card.getWord());
    }
}
