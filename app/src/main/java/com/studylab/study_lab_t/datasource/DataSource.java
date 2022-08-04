package com.studylab.study_lab_t.datasource;

import com.studylab.study_lab_t.model.Result;
import com.studylab.study_lab_t.model.User;

public interface DataSource {
    void saveAnswer(String answer, DataSourceCallback<Result> callback);

    void changeCheckInState(String userId, User changeUserCheckInState, DataSourceCallback<Result> callback);

    void loadUser(ListenerCallback<Result> callback);
}
