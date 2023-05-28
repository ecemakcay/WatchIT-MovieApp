package com.eakcay.watchit.data;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class FirestoreHelper {
    private FirebaseFirestore firestore;
    private CollectionReference favoriteMoviesCollection;
    private CollectionReference watchedMoviesCollection;

    public FirestoreHelper() {
        firestore = FirebaseFirestore.getInstance();
        favoriteMoviesCollection = firestore.collection("favorite_movies");
        watchedMoviesCollection = firestore.collection("watched_movies");
    }


    public void addUser(String email, String userID){
        DocumentReference userRef = firestore.collection("Users").document(userID);
        Map<String, Object> userData = new HashMap<>();
        userData.put("email", email);
        userRef.set(userData)
                .addOnSuccessListener(aVoid -> {

                })
                .addOnFailureListener(e -> {

                });
    }

}
