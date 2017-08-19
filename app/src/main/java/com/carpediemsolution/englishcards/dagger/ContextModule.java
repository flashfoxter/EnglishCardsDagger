package com.carpediemsolution.englishcards.dagger;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Юлия on 18.08.2017.
 */
@Module
public class ContextModule {
	private Context mContext;

	public ContextModule(Context context) {
		mContext = context;
	}

	@Provides
	@Singleton
	public Context provideContext() {
		return mContext;
	}
}
