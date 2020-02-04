package com.vintile.snapdictionary.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.vintile.snapdictionary.MyApplication;
import com.vintile.snapdictionary.R;

import java.util.Objects;

import static com.vintile.snapdictionary.utils.AppConstants.PERMISSION_REQUEST_CAMERA;

/**
 * Created by Sharath on 2020/01/30
 **/
public class PermissionFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions(new String[]{Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CAMERA);
    }

    public boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(MyApplication.getContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCameraFragment();
            } else {
                Toast.makeText(MyApplication.getContext(), "Camera permission is necessary to use this application", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startCameraFragment() {
        CameraFragment cameraFragment = new CameraFragment();
        FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, cameraFragment);
        fragmentTransaction.remove(new PermissionFragment());
        fragmentTransaction.commit();
    }
}
