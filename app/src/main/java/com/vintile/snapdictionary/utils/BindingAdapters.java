package com.vintile.snapdictionary.utils;

import android.os.Bundle;

import androidx.databinding.BindingAdapter;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.vintile.snapdictionary.R;
import com.vintile.snapdictionary.view.CameraResultActivity;
import com.vintile.snapdictionary.view.DictionaryResultFragment;
import com.vintile.snapdictionary.view.ImageProcessFragment;

import java.util.List;

/**
 * Created by Sharath on 2020/02/08
 **/
public class BindingAdapters {
    @BindingAdapter({"bind:words", "bind:frag"})
    public static void inflateData(ChipGroup chipGroup, List<String> words, ImageProcessFragment frag) {
        for (String word: words){
            Chip chip = new Chip(chipGroup.getContext());
            chip.setText(word);
            chip.setClickable(true);
            chip.setPadding(16, 8, 16, 0);
            chipGroup.addView(chip);
              chip.setOnClickListener(v -> clickedChip(chip.getText().toString(), frag));
        }
    }

    private static void clickedChip(String word, ImageProcessFragment imageProcessFragment) {

        word = word.replaceAll("[^a-zA-Z]", "");
        FragmentManager fragmentManager =((CameraResultActivity)imageProcessFragment.getActivity()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DictionaryResultFragment dictionaryResultFragment = new DictionaryResultFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.WORD, word);
        dictionaryResultFragment.setArguments(bundle);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fl_camera_result, dictionaryResultFragment).commit();
    }

}
