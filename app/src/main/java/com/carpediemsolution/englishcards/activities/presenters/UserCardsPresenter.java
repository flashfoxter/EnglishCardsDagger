package com.carpediemsolution.englishcards.activities.presenters;

import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.carpediemsolution.englishcards.app.CardsApp;
import com.carpediemsolution.englishcards.dao.DatabaseHelper;
import com.carpediemsolution.englishcards.model.Card;
import com.carpediemsolution.englishcards.utils.CardUtils;
import com.carpediemsolution.englishcards.utils.PrefUtils;
import com.carpediemsolution.englishcards.utils.Preferences;
import com.carpediemsolution.englishcards.activities.views.UserCardsView;
import com.carpediemsolution.englishcards.api.WebService;
import java.io.IOException;
import java.sql.SQLException;
import javax.inject.Inject;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
        deleteCardDB(card);
        deleteCardApi(card);
    }

    private void deleteCardDB(Card card) {
        if (CardUtils.isEmptyToken(PrefUtils.getUserToken())) {
            try {
                databaseHelper.getCardDAO().delete(card);
                getViewState().showSuccess(card);

            } catch (SQLException e) {
                e.printStackTrace();
                getViewState().showError();
            }
        }
    }

    private void deleteCardApi(Card card) {
        Call<ResponseBody> callPost = cardsService.deleteCard(PrefUtils.getUserToken(), card);
        Log.d(LOG_TAG, "token " + PrefUtils.getUserToken() + card.getWord() + callPost.toString());
        callPost.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(LOG_TAG, "---onResponse " + response);
                if (response.isSuccessful()) {
                    try {
                        String s = response.body().string();
                        Log.d(LOG_TAG, "---onResponse " + s);
                        if (s.equals(Preferences.CARD_DELETED) || s.equals(Preferences.NO_CARDS__EXIST)) {
                            databaseHelper.getCardDAO().delete(card);
                        }
                    } catch (SQLException e) {
                        getViewState().showError();

                    } catch (IOException e) {
                        e.printStackTrace();
                        getViewState().showError();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(LOG_TAG, "---onFailure ");
                if (CardUtils.isEmptyToken(PrefUtils.getUserToken())) {
                    try {
                        databaseHelper.getCardDAO().delete(card);
                        getViewState().showSuccess(card);

                    } catch (SQLException e) {
                        e.printStackTrace();
                        getViewState().showError();
                    }
                }
            }
        });
    }
}