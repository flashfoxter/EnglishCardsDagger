package com.carpediemsolution.englishcards.api;

import com.carpediemsolution.englishcards.model.Card;
import com.carpediemsolution.englishcards.model.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import rx.Observable;


/**
 * Created by Юлия on 18.08.2017.
 */

public class WebService {

    private static final String LOG_TAG = "WebService";

    private WebApi mWebApi;

    public WebService(WebApi webApi) {
        mWebApi = webApi;
    }

    public Observable<List<Card>> getCards() {
        return mWebApi.getCards();
    }

    public Observable<List<Card>> getCardsByTheme(String theme) {
        return mWebApi.getCardsByTheme(theme);
    }

    public Observable<Card> updateCards(String token, Card card) {
        return mWebApi.updateCards(token, card);
    }

    public Call<Card> uploadCards(String token, Card card) {
        return mWebApi.uploadCards(token, card);
    }

	public Observable<ResponseBody> getUserPassword(User user){
        return mWebApi.getUserPassword(user);
	}

	//повторный вход в приложение
	public Observable<ResponseBody> getUserToken(User user){
		return mWebApi.getUserToken(user);
	}

	public Observable<List<Card>> getUserCardsFromServer(String token,List<Card> cards){
		return mWebApi.getUserCardsFromServer(token, cards);
	}

	public Observable<ResponseBody> postAllCardsToServer(String token,List<Card> cards){
		return mWebApi.postAllCardsToServer(token, cards);
	}

	public Observable<ResponseBody> deleteCard(String token, Card card){
		return mWebApi.deleteCard(token, card);
	}

	public Observable<ResponseBody> loadUser(User user){
		return mWebApi.loadUser(user);
	}
}
