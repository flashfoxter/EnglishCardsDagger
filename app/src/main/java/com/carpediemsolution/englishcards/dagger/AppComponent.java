package com.carpediemsolution.englishcards.dagger;

import android.content.Context;

import com.carpediemsolution.englishcards.app.CardsApp;
import com.carpediemsolution.englishcards.activities.presenters.ServerSortedPresenter;
import com.carpediemsolution.englishcards.activities.presenters.CardsPresenter;
import com.carpediemsolution.englishcards.activities.presenters.InsertCardPresenter;
import com.carpediemsolution.englishcards.activities.presenters.UserCardsPresenter;

import javax.inject.Singleton;

import dagger.Component;


/**
 * Created by Юлия on 18.08.2017.
 */
@Singleton
@Component(modules = {ContextModule.class, DaoModule.class, BusModule.class,
        ServiceModule.class})
public interface AppComponent {
     Context getContext();
    //WebService getService();
    //Bus getBus();

    //void inject(SignInPresenter presenter);
    void inject(CardsPresenter cardsPresenter);

    void inject(ServerSortedPresenter cardsByThemePresenter);

    void inject(InsertCardPresenter newUserPresenter);

    void inject(UserCardsPresenter userCardsPresenter);

    void inject(CardsApp cardsApp);

}
