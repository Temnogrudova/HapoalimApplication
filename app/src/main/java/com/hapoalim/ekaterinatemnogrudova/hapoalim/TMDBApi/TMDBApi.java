package com.hapoalim.ekaterinatemnogrudova.hapoalim.TMDBApi;

import com.hapoalim.ekaterinatemnogrudova.hapoalim.models.FilmsResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TMDBApi {
    @GET("3/movie/now_playing")
    Observable<FilmsResponse> getFilms(@Query("api_key") String api_key, @Query("page") String page);
}