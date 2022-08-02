package com.studylab.study_lab_t.scanner;

import androidx.lifecycle.ViewModel;

import com.studylab.study_lab_t.model.User;
import com.studylab.study_lab_t.repository.UserRepository;

public class ScannerViewModel extends ViewModel {

    private final UserRepository userRepository = UserRepository.getInstance();
    private User currUser;
    public void setCurrUser(String userId){
        currUser = userRepository.getUser(userId);
    }
    public User getUser(){return currUser;}
}
