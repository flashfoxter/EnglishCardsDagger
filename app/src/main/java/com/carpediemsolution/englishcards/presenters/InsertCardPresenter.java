package com.carpediemsolution.englishcards.presenters;

import android.text.TextUtils;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.carpediemsolution.englishcards.app.CardsApp;
import com.carpediemsolution.englishcards.webApi.WebApi;
import com.carpediemsolution.englishcards.dagger.AppComponent;
import com.carpediemsolution.englishcards.dao.DatabaseHelper;
import com.carpediemsolution.englishcards.model.Card;
import com.carpediemsolution.englishcards.utils.PrefUtils;
import com.carpediemsolution.englishcards.views.InsertCardView;

import java.sql.SQLException;
import java.util.UUID;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Юлия on 18.08.2017.
 */
@InjectViewState
public class InsertCardPresenter extends MvpPresenter<InsertCardView> {

    private static final String LOG_TAG = "InsertCardPresenter";

    @Inject
    DatabaseHelper databaseHelper;
    @Inject
    WebApi webApi;

    public InsertCardPresenter() {
        CardsApp.getAppComponent().inject(this);
    }


    public void saveCard(String word, String translate, String description, String theme) {

        String token = PrefUtils.insertToken();
        if (TextUtils.isEmpty(word)) {
            getViewState().showError();
        } else if (TextUtils.isEmpty(translate)) {
            getViewState().showError();
        } else {
            Card card = new Card(word, translate, description, theme, String.valueOf(UUID.randomUUID()), 0);

            Call<Card> callPost = webApi.uploadCards(token, card);
            callPost.enqueue(new Callback<Card>() {
                @Override
                public void onResponse(Call<Card> call, Response<Card> response) {
                    Log.d(LOG_TAG, "---RESULT OK" + response.body());
                }

                @Override
                public void onFailure(Call<Card> call, Throwable t) {
                    Log.d(LOG_TAG, "---RESULT Failed");
                }
            });

            try {
                databaseHelper.getCardDAO().create(card);
                getViewState().showSuccess(card);
            } catch (SQLException e) {
                Log.d(LOG_TAG, e.toString());
                getViewState().showError();
            }
        }
    }
}
