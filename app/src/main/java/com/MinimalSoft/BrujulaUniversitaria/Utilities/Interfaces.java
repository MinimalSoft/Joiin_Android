package com.MinimalSoft.BrujulaUniversitaria.Utilities;

import com.MinimalSoft.BrujulaUniversitaria.Models.BU_Response;
import com.MinimalSoft.BrujulaUniversitaria.Models.BU_Response_Details;
import com.MinimalSoft.BrujulaUniversitaria.Models.Ecobici_Stop;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Interfaces {

    @GET("/stations/all")
    Call<List<Ecobici_Stop>> getAllStations();

    @GET("station/{id}")
    Call<List<Ecobici_Stop>> getStationById(@Path("id") String id);

    @FormUrlEncoded
    @POST("/app/controllers/place/place.php")
    Call<BU_Response_Details> getPlaceDetails(@Field("action") String action,
                                              @Field("idPlace") String idPlace);

    @FormUrlEncoded
    @POST("/app/controllers/place/place.php")
    Call<BU_Response> getPlaces(@Field("action") String action,
                                @Field("idType") String idType,
                                @Field("latitude") String latitude,
                                @Field("longitude") String longitude,
                                @Field("radio") String radio);

    @FormUrlEncoded
    @POST("/app/controllers/user/user.php")
    Call<BU_Response> logInUser(@Field("action") String action,
                                @Field("email") String idType,
                                @Field("password") String latitude,
                                @Field("idFacebook") String longitude,
                                @Field("deviceToken") String radio);

    @FormUrlEncoded
    @POST("/app/controllers/user/user.php")
    Call<BU_Response> registerUser(@Field("action") String action,
                                   @Field("name") String name,
                                   @Field("lastName") String lastName,
                                   @Field("gender") String gender,
                                   @Field("phone") String phone,
                                   @Field("email") String email,
                                   @Field("password") String password,
                                   @Field("idFacebook") String idFacebook,
                                   @Field("birthday") String birthday,
                                   @Field("deviceToken") String deviceToken);

    @FormUrlEncoded
    @POST("/app/controllers/user/user.php")
    Call<BU_Response> registerPlace(@Field("action") String action,
                                    @Field("idType") String idType,
                                    @Field("idUser") String idUser,
                                    @Field("placeName") String placeName,
                                    @Field("street") String street,
                                    @Field("number") String number,
                                    @Field("neighborhood") String neighborhood,
                                    @Field("county") String county,
                                    @Field("state") String state,
                                    @Field("country") String country,
                                    @Field("zip") String zip,
                                    @Field("description") String description,
                                    @Field("latitude") String latitude,
                                    @Field("longitude") String longitude,
                                    @Field("name") String name,
                                    @Field("phone1") String phone1,
                                    @Field("email") String email,
                                    @Field("idPackage") String idPackage);

    @FormUrlEncoded
    @POST("/app/controllers/user/user.php")
    Call<BU_Response> writeReview(@Field("action") String action,
                                  @Field("idUser") String idUser,
                                  @Field("idPlace") String idPlace,
                                  @Field("text") String text,
                                  @Field("stars") String stars);

    @FormUrlEncoded
    @POST("/app/controllers/user/user.php")
    Call<BU_Response> getReview(@Field("action") String action,
                                @Field("idPlace") String idPlace);

    @FormUrlEncoded
    @POST("/app/controllers/user/user.php")
    Call<BU_Response> getPromos(@Field("action") String action,
                                @Field("idType") String idType);

}