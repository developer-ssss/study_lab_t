package com.studylab.study_lab_t.datasource;

public interface DataSourceCallback<Result> {
    void onComplete(Result result);
}