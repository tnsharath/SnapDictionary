package com.vintile.snapdictionary.utils;

import com.vintile.snapdictionary.model.DictionaryResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by Sharath on 2020/02/05
 **/
public interface RetrofitApi {
    @Headers({
            "x-rapidapi-host: wordsapiv1.p.rapidapi.com",
            "x-rapidapi-key: key"
    })
    @GET("/words/{query}")
    Call<DictionaryResponse> getMeaning(@Path("query") String query);
}
