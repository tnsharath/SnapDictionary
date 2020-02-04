package com.vintile.snapdictionary.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.vintile.snapdictionary.R;
import com.vintile.snapdictionary.utils.AppConstants;

public class CameraResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_result);
        String imagePath = getIntent().getStringExtra(AppConstants.IMAGE_PATH);
        startImageProcessFragment(imagePath);
    }

    private void startImageProcessFragment(String imagePath) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ImageProcessFragment cameraFragment = new ImageProcessFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.IMAGE_PATH, imagePath);
        cameraFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.fl_camera_result, cameraFragment).commit();
    }
}
