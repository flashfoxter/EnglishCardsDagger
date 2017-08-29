package com.carpediemsolution.englishcards.cards.views;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;
import com.carpediemsolution.englishcards.general.LoadingView;
import com.carpediemsolution.englishcards.model.Card;


/**
 * Created by Юлия on 20.08.2017.
 */

public interface UserCardsView extends MvpView, ErrorView, CardsBaseView,LoadingView, SuccessView{

    void showSuccess(@NonNull Card card);
}
