package com.carpediemsolution.englishcards.dagger;

import com.carpediemsolution.englishcards.webApi.WebService;
import com.carpediemsolution.englishcards.webApi.WebApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Date: 8/26/2016
 * Time: 11:58
 *
 * @author Artur Artikov
 */

@Module(includes = {ApiModule.class})
public class ServiceModule {
	@Provides
	@Singleton
	public WebService provideService(WebApi webApi) {
		return new WebService(webApi);
	}
}