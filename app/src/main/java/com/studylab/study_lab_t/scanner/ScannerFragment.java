package com.studylab.study_lab_t.scanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.studylab.study_lab_t.R;

public class ScannerFragment extends Fragment {
    private ScannerViewModel scannerViewModel;
    private String id;

    public ScannerFragment() {
    }

    public static ScannerFragment newInstance() {
        ScannerFragment fragment = new ScannerFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerViewModel = new ViewModelProvider(requireActivity()).get(ScannerViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scanner, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        scannerViewModel.setUserMap();

        scannerViewModel.isUserLoaded().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoaded) {
                if(isLoaded){
                    scannerViewModel.changeCheckInState();
                }
            }
        });

        ActivityResultLauncher<Intent> launchScanner = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            IntentResult intentResult = IntentIntegrator.parseActivityResult(result.getResultCode(), result.getData());
                            String content = intentResult.getContents();
                            String format = intentResult.getFormatName();
                            String[] num = content.split("_");
                            id = num[2];
                            scannerViewModel.setCurrUser(id);
                        }
                    }
                });
        IntentIntegrator intentIntegrator = IntentIntegrator.forSupportFragment(ScannerFragment.this);
        intentIntegrator.setPrompt("Scan a barcode or QR code");
        intentIntegrator.setOrientationLocked(true);
        Intent i = intentIntegrator.createScanIntent();
        launchScanner.launch(i);
        NavHostFragment.findNavController(ScannerFragment.this).navigate(R.id.action_scannerFragment_to_homeFragment);
    }
}