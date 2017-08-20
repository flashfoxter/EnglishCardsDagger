package com.carpediemsolution.englishcards.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.carpediemsolution.englishcards.activities.UserCardsActivity;
import com.carpediemsolution.englishcards.app.CardsApp;

import java.util.Date;

/**
 * Created by Юлия on 18.08.2017.
 */

public final class PrefUtils {

	//private static final String PREF_NAME = "github";

    private PrefUtils(){}


    public static String getToken() {
        return CardsApp.getAppComponent().getContext().getSharedPreferences(Preferences.TOKEN, Context.MODE_PRIVATE).getString(Preferences.TOKEN, "");
    }

    public static String getAnonToken() {
        return CardsApp.getAppComponent().getContext().getSharedPreferences(Preferences.ANON_TOKEN, Context.MODE_PRIVATE).getString(Preferences.ANON_TOKEN, "");
    }

    public static SharedPreferences getTokenPrefs() {
		return CardsApp.getAppComponent().getContext().getSharedPreferences(Preferences.TOKEN, Context.MODE_PRIVATE);
	}

    public static SharedPreferences getAnonTokenPrefs() {
        return CardsApp.getAppComponent().getContext().getSharedPreferences(Preferences.ANON_TOKEN, Context.MODE_PRIVATE);
    }


	public static SharedPreferences.Editor getTokenEditor() {
		return getTokenPrefs().edit();
	}

    public static SharedPreferences.Editor getAnonTokenEditor() {
        return getAnonTokenPrefs().edit();
    }

	/*метод смотрит, существует ли токен зарегистрированного пользователя,
	 если токена нет, то смотрит наличие анонимного токена,
	если нет и его,значит пользователь в первый раз заносит карточку -> генерится анонимный токен пользователя*/
	public static String  insertToken(){
        String token = getTokenPrefs().getString(Preferences.TOKEN, "");
        if (CardUtils.isEmptyToken(token)) {
            token = getAnonTokenPrefs().getString(Preferences.ANON_TOKEN, "");

            if (CardUtils.isEmptyToken(token)) {
                token = Preferences.ANONUM + new Date().toString();
                getAnonTokenEditor().putString(Preferences.ANON_TOKEN, token).apply();
            }
        }
        return token;
    }

    public static String getUserToken(){
        String token = getTokenPrefs().getString(Preferences.TOKEN, "");
        if (CardUtils.isEmptyToken(token)) {
            token = getAnonTokenPrefs().getString(Preferences.ANON_TOKEN, "");
            }

        return token;
    }
}
