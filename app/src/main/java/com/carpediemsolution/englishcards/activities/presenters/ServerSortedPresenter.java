package com.carpediemsolution.englishcards.activities.presenters;

import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.carpediemsolution.englishcards.app.CardsApp;
import com.carpediemsolution.englishcards.api.WebService;
import com.carpediemsolution.englishcards.dao.DatabaseHelper;
import com.carpediemsolution.englishcards.model.Card;
import com.carpediemsolution.englishcards.activities.views.CardsView;
import com.carpediemsolution.englishcards.utils.PrefUtils;

import java.sql.SQLException;
import java.util.UUID;

import javax.inject.Inject;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Юлия on 18.08.2017.
 */
@InjectViewState
public class ServerSortedPresenter extends MvpPresenter<CardsView> {

    @Inject
    WebService cardsService;
    @Inject
    DatabaseHelper databaseHelper;

    public ServerSortedPresenter() {
        CardsApp.getAppComponent().inject(this);
        // loadData();
    }

    public void init(String theme) {
                 cardsService.getCardsByTheme(theme)
                .doOnSubscribe(getViewState()::showLoading)
                .doOnTerminate(getViewState()::hideLoading)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getViewState()::showCards, throwable -> getViewState().showError());
    }

    public void onItemClick(@NonNull Card card) {
        getViewState().showDetails(card);
    }

    public void addCard(Card card) {
        PrefUtils.insertToken();

        card.setId(String.valueOf(UUID.randomUUID()));
        try {
            databaseHelper.getCardDAO().create(card);
        } catch (SQLException e) {
            Log.d("SQLException ", e.toString());
        }
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