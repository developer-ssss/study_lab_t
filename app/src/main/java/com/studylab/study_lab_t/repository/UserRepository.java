package com.studylab.study_lab_t.repository;

import android.graphics.Bitmap;
import android.net.Uri;

import com.studylab.study_lab_t.App;
import com.studylab.study_lab_t.FileService;
import com.studylab.study_lab_t.model.Result;
import com.studylab.study_lab_t.model.User;
import com.studylab.study_lab_t.datasource.DataSource;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

public class UserRepository {
    private static volatile UserRepository INSTANCE = new UserRepository();
    protected Executor executor;
    private DataSource dataSource;
    private FileService fileService;
    private Map<String, User> userMap = new HashMap<>();
    public static UserRepository getInstance(){return INSTANCE;}

    public User getUser(String userId){
        if(userMap.containsKey(userId))
            return userMap.get(userId);
        return null;
    }

    public void addProblem(final Bitmap problemImage, UserRepositoryCallback<Result> callback){
        String localDestinationPath = App.getProblemImagePath("todayQuestion");
        fileService.saveBitmapToDisk(localDestinationPath, problemImage, new FileService.FileServiceCallback<Result<File>>() {
            @Override
            public void onComplete(Result<File> result) {
                if(result instanceof Result.Success){
                    File localFile = ((Result.Success<File>)result).getData();
                    fileService.uploadFileToDatabase(localFile, localDestinationPath, new FileService.FileServiceCallback<Result<Uri>>() {
                        @Override
                        public void onComplete(Result<Uri> result) {
                            callback.onComplete(result);
                        }
                    });
                }else{
                    callback.onComplete(new Result.Error(new Exception("UserRepository : saveProblemImage() : Problem saving image bitmap to disk")));
                }
            }
        });
    }
    public void setExecutor(Executor exec) {
        this.executor = exec;
    }
    public void setDataSource(DataSource ds) {
        this.dataSource = ds;
    }

    public void setFileService(FileService fs) {
        this.fileService = fs;
    }

    public interface UserRepositoryCallback<Result> {
        void onComplete(Result result);
    }
}
