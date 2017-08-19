package com.carpediemsolution.englishcards.webApi;

import com.carpediemsolution.englishcards.model.Card;
import java.util.List;
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

    public Observable<Card> uploadCards(String token, Card card) {
        return mWebApi.uploadCards(token, card);
    }


/*	public Call<ResponseBody> getUserPassword(User user){
        return mWebApi.getUserPassword(user);
	}

	//повторный вход в приложение
	public Call<ResponseBody> getUserToken(User user){
		return mWebApi.getUserToken(user);
	}

	public Call<List<Card>> getUserCardsFromServer(String token,List<Card> cards){
		return mWebApi.getUserCardsFromServer(token, cards);
	}

	public Call<ResponseBody> postAllCardsToServer(String token,List<Card> cards){
		return mWebApi.postAllCardsToServer(token, cards);
	}


	public Call<ResponseBody> deleteCard(String token, Card card){
		return mWebApi.deleteCard(token, card);
	}


	public Call<ResponseBody> loadUser(User user){
		return mWebApi.loadUser(user);
	}*/
}
