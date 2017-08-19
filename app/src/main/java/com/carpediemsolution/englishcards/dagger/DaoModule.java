package com.carpediemsolution.englishcards.dagger;

import android.content.Context;

import com.carpediemsolution.englishcards.dao.DatabaseHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Юлия on 30.05.2017.
 */

@Module
public class DaoModule {

    @Provides
    @Singleton
    public DatabaseHelper provideDatabaseHelper(Context context) {
        return new DatabaseHelper(context);
    }
}
