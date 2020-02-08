package com.vintile.snapdictionary.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.vintile.snapdictionary.utils.RetrofitApi;
import com.vintile.snapdictionary.utils.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sharath on 2020/01/28
 **/
public class Repository {

    private final MutableLiveData<List<Result>> results = new MutableLiveData<>();

    public LiveData<List<Result>> getMeaning(String searchString) {
        RetrofitApi apiService =
                RetrofitClient.getClient().create(RetrofitApi.class);

        Call<DictionaryResponse> call = apiService.getMeaning(searchString);
        call.enqueue(new Callback<DictionaryResponse>() {
            @Override
            public void onResponse(@NonNull Call<DictionaryResponse> call, @NonNull Response<DictionaryResponse> response) {
                if (response.body() != null && response.body().getResults() != null) {
                    results.setValue(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<DictionaryResponse> call, Throwable t) {
                Log.e("Error", t.toString());
            }
        });
        return results;
    }
}
