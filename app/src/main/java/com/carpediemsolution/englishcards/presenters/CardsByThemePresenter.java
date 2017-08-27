package com.carpediemsolution.englishcards.presenters;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpPresenter;
import com.carpediemsolution.englishcards.app.CardsApp;
import com.carpediemsolution.englishcards.api.WebService;
import com.carpediemsolution.englishcards.model.Card;
import com.carpediemsolution.englishcards.views.CardsView;

import javax.inject.Inject;

/**
 * Created by Юлия on 18.08.2017.
 */

public class CardsByThemePresenter extends MvpPresenter<CardsView> {

    @Inject
    WebService cardsService;

    public CardsByThemePresenter() {
        CardsApp.getAppComponent().inject(this);
        // loadData();
    }

    public void onItemClick(@NonNull Card card) {
        getViewState().showDetails(card);
    }
//to do...
  /*  public void loadData(String theme) {

        cardsService.getCardsByTheme(theme)
                .doOnSubscribe(getViewState()::showLoading)
                .doOnTerminate(getViewState()::hideLoading)
                //.compose(mLifecycleHandler.load(R.id.repositories_request))
               .subscribe(getViewState()::showCards, throwable -> getViewState().showError());

    }*/
}