package com.studylab.study_lab_t.repository;

import com.studylab.study_lab_t.model.User;
import com.studylab.study_lab_t.datasource.DataSource;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private static volatile UserRepository INSTANCE = new UserRepository();
    private DataSource dataSource;
    private Map<String, User> userMap = new HashMap<>();
    public static UserRepository getInstance(){return INSTANCE;}



    public User getUser(String userId){
        if(userMap.containsKey(userId))
            return userMap.get(userId);
        return null;
    }

    public void setDataSource(DataSource ds) {
        this.dataSource = ds;
    }

    public interface UserRepositoryCallback<Result> {
        void onComplete(Result result);
    }
}
