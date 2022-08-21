package com.studylab.study_lab_t.question;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.Timestamp;
import com.studylab.study_lab_t.App;
import com.studylab.study_lab_t.FileService;
import com.studylab.study_lab_t.model.Result;
import com.studylab.study_lab_t.repository.UserRepository;

public class QuestionViewModel extends ViewModel {
    private final UserRepository userRepository = UserRepository.getInstance();
    private Bitmap loadedDrawable;
    private boolean isLoadedDrawable;

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

    public void saveAnswer(String answer){
        userRepository.saveAnswer(answer,result->{
           if(result instanceof Result.Success){

           }else{

           }
        });
    }

}
