package com.vintile.snapdictionary.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.vintile.snapdictionary.model.Repository;
import com.vintile.snapdictionary.model.Result;

import java.util.List;

/**
 * Created by Sharath on 2020/02/05
 **/
public class DictionaryResultViewModel extends AndroidViewModel {
    private static final String TAG = "DictionaryResultViewMod";
    private final Repository repository;
    DictionaryResultViewModel(Application application, Repository repository){
        super(application);
        this.repository = repository;

    }

    private MutableLiveData<List<Result>> res = new MutableLiveData<>();

    public void requestDict(String searchString) {
        res = (MutableLiveData<List<Result>>) repository.getMeaning(searchString);
        Log.d(TAG, "requestDict: size");

    }

    public LiveData<List<Result>> getDictResult(){
        Log.d(TAG, "getDictResult: Requested");
        return res;
    }
}
