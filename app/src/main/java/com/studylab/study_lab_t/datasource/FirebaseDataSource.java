package com.studylab.study_lab_t.datasource;

import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseDataSource implements DataSource{
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
}
