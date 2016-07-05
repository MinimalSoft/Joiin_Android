package com.MinimalSoft.BrujulaUniversitaria.RecyclerViewer;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

public interface GetPlaces {
    @POST("/place/place.php")
    void getPlaces(@Body PlacesRequest request,
                          Callback<Place.PlaceResult> cb);
}

