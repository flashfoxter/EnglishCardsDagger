package com.carpediemsolution.englishcards.presenters;

import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.carpediemsolution.englishcards.app.CardsApp;
import com.carpediemsolution.englishcards.dao.DatabaseHelper;
import com.carpediemsolution.englishcards.model.Card;
import com.carpediemsolution.englishcards.utils.CardUtils;
import com.carpediemsolution.englishcards.utils.PrefUtils;
import com.carpediemsolution.englishcards.views.UserCardsView;
import com.carpediemsolution.englishcards.webApi.WebService;

import java.sql.SQLException;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by Юлия on 20.08.2017.
 */
@InjectViewState
public class UserCardsPresenter extends MvpPresenter<UserCardsView> {

    private static final String LOG_TAG = "UserCardsPresenter";

    @Inject
    DatabaseHelper databaseHelper;
    @Inject
    WebService cardsService;

    public UserCardsPresenter() {
        CardsApp.getAppComponent().inject(this);
        loadData();
    }

    public void onItemClick(@NonNull Card card) {
        getViewState().showDetails(card);
    }

    private void loadData() {
        try {
            databaseHelper.getCardDAO().getDataClass();
            getViewState().showCards(databaseHelper.getCardDAO().getAllCards());

        } catch (SQLException e) {
            getViewState().showError();
        }
    }

    public void deleteCard(Card card) {

        if (CardUtils.isEmptyToken(PrefUtils.getUserToken())) {
            try {
                databaseHelper.getCardDAO().delete(card);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

            cardsService.deleteCard(PrefUtils.getUserToken(), card)
                    .doOnSubscribe(getViewState()::showLoading)
                    .doOnTerminate(getViewState()::hideLoading)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((s) -> getViewState().showLoading(),
                            throwable -> onResponseFailure(card),
                            () -> isCompleted(card));
        }


    private void onResponseFailure(Card card) {
        getViewState().showError();
        if (CardUtils.isEmptyToken(PrefUtils.getUserToken())) {
            try {
                databaseHelper.getCardDAO().delete(card);
                getViewState().showSuccess(card);
                Log.d(LOG_TAG,"onResponseFailure " + card);
            } catch (SQLException e) {
                getViewState().showError();
            }
        }
    }

    private void isCompleted(Card card) {
        getViewState().showSuccess(card);
        try {
            databaseHelper.getCardDAO().delete(card);
            Log.d(LOG_TAG,"isCompleted " + card);
        } catch (SQLException e) {
            getViewState().showError();
        }
    }
}