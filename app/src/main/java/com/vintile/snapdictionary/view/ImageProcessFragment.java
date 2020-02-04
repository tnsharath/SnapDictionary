package com.vintile.snapdictionary.view;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.vintile.snapdictionary.MyApplication;
import com.vintile.snapdictionary.R;
import com.vintile.snapdictionary.utils.AppConstants;
import com.vintile.snapdictionary.viewmodel.ImageProcessViewModel;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;


public class ImageProcessFragment extends Fragment {

    private ImageProcessViewModel mViewModel;

    private String imagePath;
    private ChipGroup chipGroup;

    private static final String TAG = "ImageProcessFragment";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_image_process, container, false);
        chipGroup = root.findViewById(R.id.chip_group);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imagePath = Objects.requireNonNull(getArguments()).getString(AppConstants.IMAGE_PATH);
        mViewModel = new ViewModelProvider(this).get(ImageProcessViewModel.class);
        runTextRecognition();
    }


    private void runTextRecognition() {

        FirebaseVisionImage image = null;
        try {
            image = FirebaseVisionImage.fromFilePath(MyApplication.getContext(), Uri.fromFile(new File(imagePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        FirebaseVisionTextRecognizer recognizer = FirebaseVision.getInstance()
                .getOnDeviceTextRecognizer();
        recognizer.processImage(Objects.requireNonNull(image))
                .addOnSuccessListener(
                        this::processTextRecognitionResult)
                .addOnFailureListener(
                        Throwable::printStackTrace);
    }

    private void showToast(String message) {
        Toast.makeText(MyApplication.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void processTextRecognitionResult(FirebaseVisionText texts) {
        List<FirebaseVisionText.TextBlock> blocks = texts.getTextBlocks();
        if (blocks.isEmpty()) {
            showToast("No text found");
            return;
        }

        for (int i = 0; i < blocks.size(); i++) {
            List<FirebaseVisionText.Line> lines = blocks.get(i).getLines();
            for (int j = 0; j < lines.size(); j++) {
                List<FirebaseVisionText.Element> elements = lines.get(j).getElements();
                for (int k = 0; k < elements.size(); k++) {
                    Chip chip = new Chip(chipGroup.getContext());
                    chip.setText(elements.get(k).getText());
                    chip.setClickable(true);
                    chip.setPadding(16, 8, 16, 0);
                    chipGroup.addView(chip);
                }
            }
        }

    }

}
