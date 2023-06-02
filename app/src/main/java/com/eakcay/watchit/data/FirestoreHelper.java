package com.eakcay.watchit.data;

import android.util.Log;
import com.eakcay.watchit.model.MovieModel;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.reactivex.annotations.NonNull;

public class FirestoreHelper {
    private final FirebaseFirestore firestore;



    public FirestoreHelper() {
        firestore = FirebaseFirestore.getInstance();
    }

    public void addMovie(String userId, MovieModel movie, String movieList) {
        DocumentReference movieDocumentReference = firestore.collection("Users")
                .document(userId)
                .collection(movieList)
                .document();

        movieDocumentReference.set(movie)
                .addOnSuccessListener(aVoid ->
                        Log.d("FirestoreHelper", "Movie added successfully."))
                .addOnFailureListener(e ->
                        Log.e("FirestoreHelper", "Error adding movie: " + e.getMessage()));
    }



    public void removeMovie(String userId, int movieId,String movieList) {

        firestore.collection("Users")
                .document(userId)
                .collection(movieList)
                .whereEqualTo("id", movieId)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        // Favori film belgesini sil
                        document.getReference().delete()
                                .addOnSuccessListener(aVoid ->
                                        Log.d("FirestoreHelper", "Favorite movie deleted. ID: "
                                        + document.getId()))
                                .addOnFailureListener(e ->
                                        Log.e("FirestoreHelper", "Error deleting favorite movie: "
                                        + e.getMessage()));
                    }
                })
                .addOnFailureListener(e -> Log.e("FirestoreHelper", "Error querying movie: "
                        + e.getMessage()));
    }

    public void checkMovie(String userId, int movieId,String movieList,
                                   CheckMovieListener listener) {

        firestore.collection("Users")
                .document(userId)
                .collection(movieList)
                .whereEqualTo("id", movieId)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    if (!querySnapshot.isEmpty()) {
                        listener.onMovieFound();
                    } else {
                        listener.onMovieNotFound();
                    }
                })
                .addOnFailureListener(e -> {
                    // Firestore sorgusu sırasında hata oluştu
                    Log.e("FirestoreHelper", "Error querying movie: " + e.getMessage());
                });
    }

    public interface CheckMovieListener {
        void onMovieFound();
        void onMovieNotFound();
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

    public void getMovies(String userId, String movieList, GetMoviesListener listener) {
        firestore.collection("Users")
                .document(userId)
                .collection(movieList)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<MovieModel> movies = new ArrayList<>();
                        QuerySnapshot snapshots = task.getResult();
                        for (DocumentSnapshot document : snapshots.getDocuments()) {
                            MovieModel movie = document.toObject(MovieModel.class);
                            movies.add(movie);
                            Log.d("FirestoreHelper", document.getId() + " => " + document.getData());
                        }
                        listener.onMoviesLoaded(movies);
                    } else {
                        listener.onFailure(task.getException());
                        Log.d("FirestoreHelper", "Error getting documents: ", task.getException());
                    }
                });
    }

    public void getRuntime(String userId, GetRuntimeListener listener) {
        firestore.collection("Users")
                .document(userId)
                .collection("WatchedMovies")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<String> runtimes = new ArrayList<>();
                    int totalRuntime = 0;
                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        MovieModel movie = document.toObject(MovieModel.class);
                        runtimes.add(String.valueOf(Objects.requireNonNull(movie).getRuntime()));
                        totalRuntime += movie.getRuntime();
                    }
                    listener.onMoviesRuntime(totalRuntime);
                })
                .addOnFailureListener(e -> {
                    listener.onFailure(e);
                    Log.e("FirestoreHelper", "Error getting documents: ", e);
                });
    }


    public void getWatchedCount(String userId,GetCountListener listener) {
        firestore.collection("Users")
                .document(userId)
                .collection("WatchedMovies")
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String count = String.valueOf(task.getResult().size());
                        listener.onMoviesCounted(count);
                        Log.d("FirestoreHelper", task.getResult().size() + "");
                    } else {
                        listener.onFailure(task.getException());
                        Log.d("FirestoreHelper", "Error getting documents: ", task.getException());
                    }
                });

    }

    public interface GetCountListener{
        void onMoviesCounted(String count);
        void onFailure(@NonNull Exception e);
    }
    public interface GetRuntimeListener{
        void onMoviesRuntime(int totalRuntime);
        void onFailure(@NonNull Exception e);
    }
    public interface GetMoviesListener{
        void onMoviesLoaded(List<MovieModel> favoriteMovies);
        void onFailure(@NonNull Exception e);
    }

}

