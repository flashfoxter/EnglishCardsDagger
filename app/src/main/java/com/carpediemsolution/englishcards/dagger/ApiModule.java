package com.carpediemsolution.englishcards.dagger;

import com.carpediemsolution.englishcards.api.WebApi;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Юлия on 18.08.2017.
 */
@Module(includes = {RetrofitModule.class})
public class ApiModule {
	@Provides
	@Singleton
	public WebApi provideApi(Retrofit retrofit) {
		return retrofit.create(WebApi.class);
	}
}
