package com.MinimalSoft.BrujulaUniversitaria.Transportation;

import com.MinimalSoft.BrujulaUniversitaria.Models.Station;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Interface {
        // Request method and URL specified in the annotation
        // Callback for the parsed response is the last parameter

        @GET("/stations/all")
        Call<List<Station>> getAllStations();

        @GET("station/{id}")
        Call<List<Station>> getStationById(@Path("id") String id);
}