package com.studylab.study_lab_t.datasource;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.studylab.study_lab_t.model.Result;
import com.studylab.study_lab_t.model.User;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseDataSource implements DataSource{
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();


    @Override
    public void loadUser(ListenerCallback<Result> callback) {
        db.collection("users")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error==null){
                            Map<String, User> toReturnMap = new HashMap<>();
                            List<User> toReturn = new ArrayList<>();
                            List<DocumentSnapshot> snaps = value.getDocuments();
                            for(DocumentSnapshot snap : snaps){
                                User toAdd = new User(snap.getString("name"),snap.getString("userId"),snap.getString("password"),snap.getString("phoneNumber"),snap.getString("checkIn"));
                                String toStringAdd = snap.getString("userId");
                                toReturnMap.put(toStringAdd,toAdd);
                            }
                            callback.onUpdate(new Result.Success<Map<String, User>>(toReturnMap));
                        }else{
                            callback.onUpdate(new Result.Error(new Exception("error")));
                        }
                    }
                });
    }

    @Override
    public void changeCheckInState(String userId, User changeUserCheckInState, DataSourceCallback<Result> callback) {
        db.collection("users")
                .document(userId)
                .set(changeUserCheckInState);
        callback.onComplete(new Result.Success<String>("Success"));
    }

    public void downloadFile(String downloadPath, File localFile, DataSourceCallback<Result<File>> callback) {
        Log.d("DEBUG:DataSource", "downloadFile: " + downloadPath);
        StorageReference ref = firebaseStorage.getReference().child(downloadPath);
        ref.getFile(localFile).addOnCompleteListener(new OnCompleteListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<FileDownloadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    callback.onComplete(new Result.Success<File>(localFile));
                } else {
                    callback.onComplete(new Result.Error(task.getException()));
                }
            }
        });
    }

    public void uploadFile(File toUpload, String destination, DataSourceCallback<Result<Uri>> callback) {
        Log.d("DEBUG:DataSource", "uploadFile: " + toUpload.getName() + " to " + destination);
        Uri localFile = Uri.fromFile(toUpload);
        StorageReference storageReference = firebaseStorage.getReference().child(destination);
        UploadTask uploadTask = storageReference.putFile(localFile);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri result = task.getResult();
                            callback.onComplete(new Result.Success<Uri>(result));
                        } else {

                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onComplete(new Result.Error(e));
                Log.d("DEBUG", "DataSource: storeImage() failed!");
            }
        });
    }

    @Override
    public void saveAnswer(String answer, DataSourceCallback<Result> callback) {
        Map<String, String> answerMap = new HashMap<>();
        answerMap.put("answer", answer);
        db.collection("answer")
                .document("answer")
                .set(answerMap);
        callback.onComplete(new Result.Success<String>("Success"));
    }
}
