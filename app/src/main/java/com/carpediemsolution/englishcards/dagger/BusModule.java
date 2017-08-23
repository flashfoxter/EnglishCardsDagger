package com.carpediemsolution.englishcards.dagger;

import com.carpediemsolution.englishcards.api.WebApi;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

//для передачи данных...
@Module
public class BusModule {
    @Provides
    @Singleton
    public Bus provideBus(WebApi api) {
        return new Bus();
    }
}
