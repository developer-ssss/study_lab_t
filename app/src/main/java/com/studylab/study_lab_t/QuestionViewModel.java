package com.studylab.study_lab_t;

import android.graphics.Bitmap;

import androidx.lifecycle.ViewModel;

import com.google.firebase.Timestamp;
import com.studylab.study_lab_t.model.Result;
import com.studylab.study_lab_t.repository.UserRepository;

public class QuestionViewModel extends ViewModel {
    private final UserRepository userRepository = UserRepository.getInstance();

    public void saveBitmapToMediaStore(Bitmap bm)
    {
        FileService fileService = App.getFileService();
        fileService.saveBitmapToMediaStore("problemImage_" + Timestamp.now().getSeconds(), bm, new FileService.FileServiceCallback<Result<String>>()
        {
            @Override
            public void onComplete(Result result)
            {
            }
        });
    }

    public void addProblemImage(Bitmap problemImage){
        userRepository.addProblem(problemImage, result->{
           if(result instanceof Result.Success){

           } else{

           }
        });
    }
}
