package com.carpediemsolution.englishcards.presenters;

import android.text.TextUtils;
import android.util.Log;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.carpediemsolution.englishcards.app.CardsApp;
import com.carpediemsolution.englishcards.webApi.WebService;
import com.carpediemsolution.englishcards.dagger.AppComponent;
import com.carpediemsolution.englishcards.dao.DatabaseHelper;
import com.carpediemsolution.englishcards.model.Card;
import com.carpediemsolution.englishcards.utils.PrefUtils;
import com.carpediemsolution.englishcards.views.InsertCardView;

import java.sql.SQLException;

import javax.inject.Inject;

/**
 * Created by Юлия on 18.08.2017.
 */
@InjectViewState
public class InsertCardPresenter extends MvpPresenter<InsertCardView> {

    private static final String LOG_TAG = "InsertCardPresenter";
    @Inject
    WebService cardsService;
    @Inject
    AppComponent appComponent;
    @Inject
    DatabaseHelper databaseHelper;

    public InsertCardPresenter() {
        CardsApp.getAppComponent().inject(this);
    }


    public void saveCard(Card card){

        String token = PrefUtils.insertToken();

        if (TextUtils.isEmpty(card.getWord())) {
            getViewState().showError();
        } else if (TextUtils.isEmpty(card.getTranslate())) {
            getViewState().showError();
        } else {
            cardsService
                    .uploadCards(token, card)
                    .doOnSubscribe(getViewState()::showLoading)
                    .doOnTerminate(getViewState()::hideLoading);
                  //  .subscribe(getViewState()::showSuccess, throwable -> getViewState().showError());

            try{
            databaseHelper.getCardDAO().create(card);
                Log.d(LOG_TAG, "created " + card);
              //  Log.d(LOG_TAG, "added cards  " + databaseHelper.getCardDAO().getAllTasks());
            }
            catch (SQLException e){
                Log.d(LOG_TAG, e.toString());
            }
        }
    }
}
