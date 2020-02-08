package com.vintile.snapdictionary.viewmodel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.vintile.snapdictionary.MyApplication;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ImageProcessViewModel extends AndroidViewModel {

    private final MutableLiveData<List<String>> text = new MutableLiveData<>();

    public ImageProcessViewModel(@NonNull Application application) {
        super(application);
    }

    public void runTextRecognition(String imagePath) {

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

    private void processTextRecognitionResult(FirebaseVisionText texts) {
        List<String> words = new ArrayList<>();
        List<FirebaseVisionText.TextBlock> blocks = texts.getTextBlocks();

        for (int i = 0; i < blocks.size(); i++) {
            List<FirebaseVisionText.Line> lines = blocks.get(i).getLines();
            for (int j = 0; j < lines.size(); j++) {
                List<FirebaseVisionText.Element> elements = lines.get(j).getElements();
                for (int k = 0; k < elements.size(); k++) {
                   words.add(elements.get(k).getText());
                }
            }
        }
        text.setValue(words);
    }

    public LiveData<List<String>> getWords(){
        return text;
    }
}
