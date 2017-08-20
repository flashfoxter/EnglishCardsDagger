package com.carpediemsolution.englishcards.app;

import android.app.Application;

import com.carpediemsolution.englishcards.dagger.AppComponent;
import com.carpediemsolution.englishcards.dagger.ContextModule;
import com.carpediemsolution.englishcards.dagger.DaggerAppComponent;

/**
 * Created by Юлия on 18.08.2017.
 */
public class CardsApp extends Application {
	private static AppComponent sAppComponent;

	@Override
	public void onCreate() {
		super.onCreate();

		sAppComponent = DaggerAppComponent.builder()
				.contextModule(new ContextModule(this))
				.build();
	}

	public static AppComponent getAppComponent() {
		return sAppComponent;
	}
}
