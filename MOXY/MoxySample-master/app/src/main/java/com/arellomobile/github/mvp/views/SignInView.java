package com.arellomobile.github.mvp.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Date: 15.01.2016
 * Time: 18:41
 *
 * @author Yuri Shmakov
 */

//аннотация для управления стратегией добавления и удаления команды из очереди команд во ViewState
@StateStrategyType(AddToEndSingleStrategy.class)
public interface SignInView extends MvpView {
	void showProgress();

	void hideProgress();

	void showError(String message);

	void hideError();

	void showError(Integer emailError, Integer passwordError);

	@StateStrategyType(SkipStrategy.class)
	void successSignIn();
}