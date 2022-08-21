package com.studylab.study_lab_t.question;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.studylab.study_lab_t.App;
import com.studylab.study_lab_t.FileService;
import com.studylab.study_lab_t.R;
import com.studylab.study_lab_t.databinding.FragmentQuestionBinding;

import java.io.File;

public class QuestionFragment extends Fragment {
    private FragmentQuestionBinding binding;
    private QuestionViewModel questionViewModel;

    private Button bt_takePicture;
    private Button bt_importPicture;
    private ImageView iv_problemImage;
    private EditText et_answer;
    private Button bt_add;

    private Uri selectedImage;
    private File imageFile;
    private Bitmap changeBitmap;

    public QuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        questionViewModel = new ViewModelProvider(this).get(QuestionViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentQuestionBinding.inflate(inflater, container, false);
        bt_takePicture = binding.questionBtTakePicture;
        bt_importPicture = binding.questionBtImportPicture;
        iv_problemImage = binding.questionIvProblemImage;
        et_answer = binding.questionEtAnswer;
        bt_add = binding.questionBtAdd;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ActivityResultLauncher<Intent> launchCamera = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                            iv_problemImage.setImageBitmap(bitmap);
                            questionViewModel.saveBitmapToMediaStore(bitmap);
                            iv_problemImage.invalidate();
                            changeBitmap = ((BitmapDrawable) iv_problemImage.getDrawable()).getBitmap();
                        }
                    }
                }
        );

        ActivityResultLauncher<Intent> launchGallery = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            selectedImage = result.getData().getData();
                            iv_problemImage.setImageURI(selectedImage);
                            iv_problemImage.invalidate();
                            changeBitmap = ((BitmapDrawable) iv_problemImage.getDrawable()).getBitmap();
                        }
                    }
                });


        bt_takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                FileService fileService = App.getFileService();
                imageFile = fileService.createFile("", "temp.jpg");
                if (imageFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(requireActivity(), App.getFileProvider(), imageFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                }
                launchCamera.launch(takePictureIntent);
            }
        });

        bt_importPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loadImageIntent = new Intent(Intent.ACTION_GET_CONTENT);
                loadImageIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                launchGallery.launch(loadImageIntent);
            }
        });

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionViewModel.saveAnswer(et_answer.getText().toString());
                questionViewModel.addProblemImage(changeBitmap);
                NavHostFragment.findNavController(QuestionFragment.this).navigate(R.id.action_questionFragment_to_homeFragment);
            }
        });
    }
}