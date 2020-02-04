package com.vintile.snapdictionary.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;

import com.vintile.snapdictionary.R;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        startCameraFragment();
    }

    private void startCameraFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CameraFragment cameraFragment = new CameraFragment();
        fragmentTransaction.add(R.id.frameLayout, cameraFragment).commit();
    }

    public File getOutputDirectory(Context context) {
        File[] file = context.getExternalMediaDirs();
        File mediaDir = null;
        if (file != null) {
            mediaDir = new File(file[0], context.getResources().getString(R.string.app_name));
            mediaDir.mkdirs();
        }

        if (mediaDir != null && mediaDir.exists()) {
            return mediaDir;
        } else {
            return context.getFilesDir();
        }
    }
}
