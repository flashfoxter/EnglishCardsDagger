package com.carpediemsolution.englishcards.presenters;

import android.support.annotation.NonNull;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.carpediemsolution.englishcards.app.CardsApp;
import com.carpediemsolution.englishcards.dao.DatabaseHelper;
import com.carpediemsolution.englishcards.model.Card;
import com.carpediemsolution.englishcards.views.UserCardsView;

import java.sql.SQLException;
import javax.inject.Inject;


/**
 * Created by Юлия on 20.08.2017.
 */
@InjectViewState
public class UserCardsPresenter extends MvpPresenter<UserCardsView> {

    private static final String LOG_TAG = "UserCardsPresenter";

    @Inject
    DatabaseHelper databaseHelper;

    public UserCardsPresenter() {
        CardsApp.getAppComponent().inject(this);
        loadData();
    }

    public void onItemClick(@NonNull Card card) {
        getViewState().showDetails(card);
    }

    private void loadData()  {
        try {
            databaseHelper.getCardDAO().getDataClass();
            getViewState().showCards(databaseHelper.getCardDAO().getAllCards());

        } catch (SQLException e) {
            getViewState().showError();
        }
    }
}