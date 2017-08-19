package com.carpediemsolution.englishcards.dagger;

import com.carpediemsolution.englishcards.webApi.WebService;
import com.carpediemsolution.englishcards.webApi.WebApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Юлия on 18.08.2017.
 */

@Module(includes = {ApiModule.class})
public class ServiceModule {
	@Provides
	@Singleton
	public WebService provideService(WebApi webApi) {
		return new WebService(webApi);
	}
}