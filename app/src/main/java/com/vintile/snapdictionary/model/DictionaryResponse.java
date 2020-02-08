package com.vintile.snapdictionary.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class DictionaryResponse {

    @SerializedName("results")
    @Expose
    private final List<Result> results = null;


    List<Result> getResults() {
        return results;
    }

}
