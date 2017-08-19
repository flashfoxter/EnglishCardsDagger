package com.carpediemsolution.englishcards.dagger;


import com.carpediemsolution.englishcards.webApi.WebApi;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class BusModule {
	@Provides
	@Singleton
	public Bus provideBus(WebApi api) {
		return new Bus();
	}
}
