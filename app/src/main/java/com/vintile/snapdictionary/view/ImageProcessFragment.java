package com.vintile.snapdictionary.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.vintile.snapdictionary.R;
import com.vintile.snapdictionary.databinding.FragmentImageProcessBinding;
import com.vintile.snapdictionary.utils.AppConstants;
import com.vintile.snapdictionary.viewmodel.ImageProcessViewModel;

import java.util.ArrayList;
import java.util.List;

import java.util.Objects;


public class ImageProcessFragment extends Fragment {

    private ImageProcessViewModel mViewModel;
    private FragmentImageProcessBinding fragmentImageProcessBinding;

    private final List<String> test = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        fragmentImageProcessBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_image_process, container, false);
        View root = fragmentImageProcessBinding.getRoot();
        fragmentImageProcessBinding.setList(test);
        fragmentImageProcessBinding.setFragment(this);
        try {
            Objects.requireNonNull(Objects.requireNonNull((CameraResultActivity)getActivity()).getSupportActionBar()).setTitle("Pick a word");
        }catch (Exception e){
            e.printStackTrace();
        }
        return root;
    }

    @Override
    public void onCreate(Bundle savedinstanceState){
        super.onCreate(savedinstanceState);
        mViewModel = new ViewModelProvider(this).get(ImageProcessViewModel.class);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String imagePath = Objects.requireNonNull(getArguments()).getString(AppConstants.IMAGE_PATH);
        mViewModel.runTextRecognition(imagePath);
        processWords();
    }


    private void processWords() {

        mViewModel.getWords().observe(getViewLifecycleOwner(), words ->{
            if (words != null && !words.isEmpty()) {
                fragmentImageProcessBinding.setList(words);
            }
        });
    }
}
