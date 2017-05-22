package com.MinimalSoft.BUniversitaria.Interfaces;

import com.MinimalSoft.BUniversitaria.Responses.DetailsResponse;
import com.MinimalSoft.BUniversitaria.Responses.Ecobici_Stop;
import com.MinimalSoft.BUniversitaria.Responses.PlacesResponse;
import com.MinimalSoft.BUniversitaria.Responses.PromosResponse;
import com.MinimalSoft.BUniversitaria.Responses.ReactionResponse;
import com.MinimalSoft.BUniversitaria.Responses.ReviewsResponse;
import com.MinimalSoft.BUniversitaria.Responses.TransactionResponse;
import com.MinimalSoft.BUniversitaria.Responses.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MinimalSoftServices {

    @FormUrlEncoded
    @POST("/controllers/user/user.php")
    Call<UserResponse> registerUser(@Field("action") String action,
                                    @Field("name") String name,
                                    @Field("lastName") String lastName,
                                    @Field("gender") String gender,
                                    @Field("birthday") String birthday,
                                    @Field("phone") String phone,
                                    @Field("email") String email,
                                    @Field("password") String password,
                                    @Field("fbImage") String urlImage,
                                    @Field("idFacebook") String facebookID,
                                    @Field("deviceToken") String deviceToken);

    @FormUrlEncoded
    @POST("/controllers/place/place")
    Call<PlacesResponse> getPlaces(@Field("action") String action,
                                   @Field("idType") String typeID,
                                   @Field("latitude") String latitude,
                                   @Field("longitude") String longitude,
                                   @Field("radio") String radio);

    @FormUrlEncoded
    @POST("/controllers/reviews/review")
    Call<ReviewsResponse> putReview(@Field("action") String action,
                                    @Field("idUser") String userID,
                                    @Field("idPlace") String placeID,
                                    @Field("text") String text,
                                    @Field("stars") String stars);

    @FormUrlEncoded
    @POST("/controllers/reviews/review.php")
    Call<ReviewsResponse> getAllReviews(@Field("action") String action,
                                        @Field("idUser") String userID,
                                        @Field("number") String number);

    @FormUrlEncoded
    @POST("/controllers/reviews/review.php")
    Call<ReviewsResponse> getReviews(@Field("action") String action,
                                     @Field("idUser") String userID,
                                     @Field("idPlace") String placeID);

    @FormUrlEncoded
    @POST("/controllers/reviews/review.php")
    Call<ReactionResponse> reaction(@Field("action") String action,
                                    @Field("idUser") String userID,
                                    @Field("idReview") String reviewID,
                                    @Field("idReaction") String reactionID);

    @FormUrlEncoded
    @POST("/controllers/place/place.php")
    Call<DetailsResponse> getPlaceDetails(@Field("action") String action,
                                          @Field("idPlace") String placeID);

    @FormUrlEncoded
    @POST("controllers/promos/promo.php")
    Call<PromosResponse> getPromos(@Field("action") String action,
                                   @Field("idType") String typeID);

    @FormUrlEncoded
    @POST("controllers/coins/coin.php")
    Call<TransactionResponse> getCoins(@Field("action") String action,
                                       @Field("idUser") String userID);
    /*@FormUrlEncoded
    @POST("/controllers/reviews/review.php")
    Call<ReactionResponse> reaction(@Field("action") String action,
                                    @Field("idUser") String idUser,
                                    @Field("idReview") String idReview,
                                    @Field("idReaction") String idReaction);

    @FormUrlEncoded
    @POST("/controllers/reviews/review.php")
    Call<ReviewsResponse> getReviews(@Field("action") String action,
                                     @Field("idUser") String idUser,
                                     @Field("idPlace") String idPlace);

    @FormUrlEncoded
    @POST("/controllers/place/place.php")
    Call<DetailsResponse> getPlaceDetails(@Field("action") String action,
                                          @Field("idPlace") String idPlace);

    @FormUrlEncoded
    @POST("/controllers/user/user.php")
    Call<UserResponse> logInUser(@Field("action") String action,
                                 @Field("email") String email,
                                 @Field("password") String password,
                                 @Field("idFacebook") String idFacebook,
                                 @Field("deviceToken") String deviceToken);

    @FormUrlEncoded
    @POST("/controllers/user/user.php")
    Call<UserResponse> registerUser(@Field("action") String action,
                                    @Field("name") String name,
                                    @Field("lastName") String lastName,
                                    @Field("gender") String gender,
                                    @Field("birthday") String birthday,
                                    @Field("phone") String phone,
                                    @Field("email") String email,
                                    @Field("password") String password,
                                    @Field("fbImage") String urlImage,
                                    @Field("idFacebook") String idFacebook,
                                    @Field("deviceToken") String deviceToken);
                                    */

    @GET("station/{id}")
    Call<List<Ecobici_Stop>> getStationById(@Path("id") String id);

    @GET("/stations/all")
    Call<List<Ecobici_Stop>> getAllStations();
}