package com.carpediemsolution.englishcards.dagger;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Юлия on 18.08.2017.
 */
@Module
public class RetrofitModule {
	@Provides
	@Singleton
	public Retrofit provideRetrofit() {

		return  new Retrofit.Builder()
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.addConverterFactory(GsonConverterFactory.create())
				.baseUrl("http://cards.carpediemsolutions.ru/")
				//.baseUrl("http://192.168.1.52:8081/")
				.build();
	}
}
