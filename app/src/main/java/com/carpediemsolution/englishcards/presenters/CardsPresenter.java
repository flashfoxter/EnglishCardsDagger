package com.carpediemsolution.englishcards.presenters;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.carpediemsolution.englishcards.app.CardsApp;
import com.carpediemsolution.englishcards.webApi.WebService;
import com.carpediemsolution.englishcards.model.Card;
import com.carpediemsolution.englishcards.views.CardsView;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


@InjectViewState
public class CardsPresenter extends MvpPresenter<CardsView> {

    private static final String LOG_TAG = "CardsPresenter";

    @Inject
    WebService cardsService;

    public CardsPresenter() {
        CardsApp.getAppComponent().inject(this);
        loadData();
    }


    public void onItemClick(@NonNull Card card) {
        getViewState().showDetails(card);
    }

    public void loadData() {

        cardsService.getCards()
                .doOnSubscribe(getViewState()::showLoading)
                .doOnTerminate(getViewState()::hideLoading)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getViewState()::showCards, throwable -> getViewState().showError());
    }
}
