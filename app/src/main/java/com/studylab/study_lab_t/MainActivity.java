package com.studylab.study_lab_t;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.studylab.study_lab_t.datasource.FirebaseDataSource;
import com.studylab.study_lab_t.repository.UserRepository;

public class MainActivity extends AppCompatActivity {
    UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDataSource ds = new FirebaseDataSource();
        userRepository = UserRepository.getInstance();
        userRepository.setDataSource(ds);
        setContentView(R.layout.activity_main);
    }
}