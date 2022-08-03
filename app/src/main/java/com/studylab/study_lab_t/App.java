package com.studylab.study_lab_t;

import android.app.Application;

import com.studylab.study_lab_t.repository.UserRepository;

public class App extends Application {
    private static FileService fileService;

    private static final UserRepository WORKSITE_REPOSITORY = UserRepository.getInstance();


    public static String getProblemImagePath(String problemId) {
        return "problemImages/problem_" + problemId + ".jpg";
    }


    public static String getFileProvider() {
        return "com.studylab.study_lab_t.fileprovider";
    }

    public static FileService getFileService() {
        return fileService;
    }

    public static void setFileService(FileService fs) {
        fileService = fs;
    }
}
