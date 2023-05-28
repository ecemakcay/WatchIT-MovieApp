package com.eakcay.watchit.auth;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.eakcay.watchit.MainActivity;
import com.eakcay.watchit.data.FirestoreHelper;
import com.eakcay.watchit.view.login.LoginActivity;
import com.eakcay.watchit.view.login.LoginFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class FirebaseAuthHelper {

    private final Context context;
    private FirestoreHelper firestoreHelper;

    public FirebaseAuthHelper(Context context) {
        this.context = context;
    }

    public void registerUser(String email, String password, String userName) {

        // Create user with Firebase Authentication
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Update user's display name with their username
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(userName)
                                .build();

                        assert user != null;
                        user.updateProfile(profileUpdates)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        // Handle successful registration
                                        Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT).show();

                                        //Firestore add user method
                                        firestoreHelper = new FirestoreHelper();
                                        String userID= user.getUid();
                                        firestoreHelper.addUser(email,userID);

                                        // Sign in the user with their email and password
                                        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                                                .addOnCompleteListener(task11 -> {
                                                    if (task11.isSuccessful()) {
                                                        // Sign in successful, redirect to MainActivity
                                                        Intent intent = new Intent(context, MainActivity.class);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        context.startActivity(intent);
                                                    } else {
                                                        // Handle sign-in failure
                                                        Toast.makeText(context, "Sign-in failed", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    } else {
                                        // Handle registration failure
                                        Toast.makeText(context, "Registration failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        // Handle registration failure
                        Toast.makeText(context, "Registration failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void loginUser(String email, String password){

        // Authenticate user with Firebase Authentication
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Handle successful login
                        Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(context, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    } else {
                        // Handle login failure
                        Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void forgotPassword(String email){

        // Send password reset email
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Handle successful password reset
                        Toast.makeText(context, "Password reset email sent", Toast.LENGTH_SHORT).show();
                        ((LoginActivity) context).loadFragment(new LoginFragment());

                    } else {
                        // Handle password reset failure
                        Toast.makeText(context, "Failed to send password reset email", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void signOut(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);

    }

    public String getUserName(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
       return  user.getDisplayName();
    }

    public String getUserEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return  user.getEmail();
    }
}
