package com.carpediemsolution.englishcards.activities.views;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.carpediemsolution.englishcards.general.LoadingView;
import com.carpediemsolution.englishcards.model.Card;

/**
 * Created by Юлия on 19.08.2017.
 */
@StateStrategyType(AddToEndSingleStrategy.class)
public interface InsertCardView extends MvpView, LoadingView, ErrorView {

    void showSuccess(@NonNull Card card);
}
