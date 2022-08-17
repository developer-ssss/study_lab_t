package com.studylab.study_lab_t.scanner;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.studylab.study_lab_t.model.Result;
import com.studylab.study_lab_t.model.User;
import com.studylab.study_lab_t.repository.UserRepository;

public class ScannerViewModel extends ViewModel {
    private final UserRepository userRepository = UserRepository.getInstance();
    private MutableLiveData<Boolean> userLoaded = new MutableLiveData<>(false);

    private User currUser;
    private User toReturnUser;

    public void setUserMap() {
        userRepository.setUserMap();
    }

    public void setCurrUser(String userId) {
        currUser = userRepository.getUser(userId);
        if (currUser != null) {
            userLoaded.postValue(true);
        }else{
            userLoaded.postValue(false);
        }
    }

    public void changeCheckInState() {
        toReturnUser = new User(currUser.getName(), currUser.getId(), currUser.getPassword(), currUser.getPhoneNumber(), "true");
        userRepository.changeCheckInState(currUser.getId(), toReturnUser, result -> {
            if (result instanceof Result.Success) {
                Log.d("DEBUG", "changeCheckInState: success to change checkIn state");
            } else {
                Log.d("DEBUG", "changeCheckInState: fail to change checkIn state");
            }
        });
    }

    public User getUser() {
        return currUser;
    }

    public LiveData<Boolean> isUserLoaded() {
        return userLoaded;
    }

}
