package com.carpediemsolution.englishcards.cards.views;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;
import com.carpediemsolution.englishcards.model.Card;

import java.util.List;

/**
 * Created by Юлия on 20.08.2017.
 */

public interface CardsBaseView extends MvpView {

    void showCards(@NonNull List<Card> cards);

    void showDetails(@NonNull Card card);
}
