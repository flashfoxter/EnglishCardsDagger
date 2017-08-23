package com.carpediemsolution.englishcards.presenters;

import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.carpediemsolution.englishcards.app.CardsApp;
import com.carpediemsolution.englishcards.dagger.AppComponent;
import com.carpediemsolution.englishcards.dao.DatabaseHelper;
import com.carpediemsolution.englishcards.model.Card;
import com.carpediemsolution.englishcards.utils.CardUtils;
import com.carpediemsolution.englishcards.utils.PrefUtils;
import com.carpediemsolution.englishcards.views.UserCardsView;
import com.carpediemsolution.englishcards.webApi.WebService;

import java.sql.SQLException;


import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by Юлия on 20.08.2017.
 */
@InjectViewState
public class UserCardsPresenter extends MvpPresenter<UserCardsView> {

    private static final String LOG_TAG = "UserCardsPresenter";

    @Inject
    AppComponent appComponent;
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

        Log.d(LOG_TAG, "token " + PrefUtils.getUserToken() + card.getWord());
        if (CardUtils.isEmptyToken(PrefUtils.getUserToken())) {
            try {
                databaseHelper.getCardDAO().delete(card);
            } catch (SQLException e) {
                e.printStackTrace();
                //
            }
        }

            Observer observer = new Observer<ResponseBody>() {


                @Override
                public void onCompleted() {
                    Log.d("onCompleted", "");

                }

                @Override
                public void onError(Throwable e) {
                    Log.d("onError ", e.toString());
                    onResponseFailure(card);
                }

                @Override
                public void onNext(ResponseBody hotels) {
                    Log.d("onNext ", hotels.toString());
                    //test progressDialog
                    isCompleted(card);
                }
            };

            cardsService.deleteCard(PrefUtils.getUserToken(), card)
                    .doOnSubscribe(getViewState()::showLoading)
                    .doOnTerminate(getViewState()::hideLoading)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    //.subscribe((s) -> isCompleted(card),
                    //     throwable -> onResponseFailure(card));
                    .subscribe(observer);
        }

    private void onResponseFailure(Card card) {
        getViewState().showError();
        Log.d(LOG_TAG,"onResponseFailure " + card);
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
        Log.d(LOG_TAG,"card error " +card.getWord());
        try {
            databaseHelper.getCardDAO().deleteCard(card);
            Log.d(LOG_TAG,"isCompleted " + card);
        } catch (SQLException e) {
           getViewState().showError();
            Log.d(LOG_TAG,"isCompleted error " + e.toString());
        }
    }
}