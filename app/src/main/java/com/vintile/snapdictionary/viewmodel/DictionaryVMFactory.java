package com.vintile.snapdictionary.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.vintile.snapdictionary.MyApplication;
import com.vintile.snapdictionary.model.Repository;

/**
 * Created by Sharath on 2020/02/06
 **/
@SuppressWarnings("unchecked")
public class DictionaryVMFactory implements  ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(DictionaryResultViewModel.class)){
            return (T) new DictionaryResultViewModel(MyApplication.getContext(), new Repository());
        }
        throw new IllegalArgumentException("Unknown view model class");
    }
}
