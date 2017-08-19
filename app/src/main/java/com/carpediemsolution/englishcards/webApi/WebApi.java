package com.carpediemsolution.englishcards.webApi;

import com.carpediemsolution.englishcards.model.Card;
import com.carpediemsolution.englishcards.model.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;


/**
 * Created by Юлия on 27.03.2017.
 */

public interface WebApi {

    @GET("/languageapp/cards/all")
    Observable<List<Card>> getCards();

    @GET("/languageapp/cards/all/{theme}")
    Observable<List<Card>> getCardsByTheme(@Path("theme") String theme);

    @POST("/languageapp/controller/update")
    Observable<Card> updateCards(@Header("Token") String token, @Body Card card);

    @POST("/languageapp/controller/post_new_card")
    Observable<Card> uploadCards(@Header("Token") String token, @Body Card card);

    @POST("/languageapp/users/password")
    Observable<ResponseBody> getUserPassword(@Body User user);

    //повторный вход в приложение
    @POST("/languageapp/users/user/token")
    Observable<ResponseBody> getUserToken(@Body User user);

    @POST("/languageapp/controller/user_cards")
    Observable<List<Card>> getUserCardsFromServer(@Header("Token") String token, @Body List<Card> cards);

    @POST("/languageapp/controller/post_all_cards")
    Observable<ResponseBody> postAllCardsToServer(@Header("Token") String token, @Body List<Card> cards);

    @POST("/languageapp/controller/delete")
    Observable<ResponseBody> deleteCard(@Header("Token") String token, @Body Card card);

    @POST("/languageapp/users/user")
    Observable<ResponseBody> loadUser(@Body User user);
}
