package com.studylab.study_lab_t.datasource;

import com.studylab.study_lab_t.model.Result;

public interface DataSource {
    void saveAnswer(String answer, DataSourceCallback<Result> callback);
}
