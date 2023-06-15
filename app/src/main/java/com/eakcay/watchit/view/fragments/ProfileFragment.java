package com.eakcay.watchit.view.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.eakcay.watchit.R;
import com.eakcay.watchit.auth.FirebaseAuthHelper;
import com.eakcay.watchit.data.FirestoreHelper;
import com.eakcay.watchit.view.login.LoginActivity;

public class ProfileFragment extends Fragment {
    private AppCompatActivity activity;
    private FirebaseAuthHelper firebaseAuthHelper;
    private FirestoreHelper firestoreHelper;
    private int runtime;
    private String movieCount;
    private TextView movieCountText;
    private TextView totalMovieTime;
    

    @SuppressLint("NonConstantResourceId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        firebaseAuthHelper = new FirebaseAuthHelper(getContext());
        firestoreHelper = new FirestoreHelper();

        TextView userName = view.findViewById(R.id.user_name_text);
        TextView email = view.findViewById(R.id.email_text);
        movieCountText = view.findViewById(R.id.count_movie_text);
        totalMovieTime = view.findViewById(R.id.total_movie_time_text);
        Button signOut = view.findViewById(R.id.sign_out_btn2);
        Button settings = view.findViewById(R.id.settings_btn);
        Button watchedList = view.findViewById(R.id.watched_list_btn);
        Button favoriList = view.findViewById(R.id.favori_list_btn);

        userName.setText(firebaseAuthHelper.getUserName());
        email.setText(firebaseAuthHelper.getUserEmail());

        getWatchedCount();
        getRuntime();

        settings.setOnClickListener(view13 -> {
            PopupMenu popupMenu = new PopupMenu(getContext(), settings);
            MenuInflater inflater1 = popupMenu.getMenuInflater();
            inflater1.inflate(R.menu.settings_popup_menu, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(menuItem -> {

                switch (menuItem.getItemId()) {
                    case R.id.update_user_name:
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Update username");


                        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
                        View dialogView = layoutInflater.inflate(R.layout.dialog_update_username, null);
                        EditText usernameEditText = dialogView.findViewById(R.id.usernameUpdate_edit_text);
                        builder.setView(dialogView);

                        builder.setPositiveButton("Save", (dialog, which) -> {
                            String newUsername = usernameEditText.getText().toString().trim();
                            firebaseAuthHelper.updateUserName(newUsername);

                        });

                        builder.setNegativeButton("Cancel", null);

                        AlertDialog dialog = builder.create();
                        dialog.show();
                        return true;

                    case R.id.update_email:
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                        builder1.setTitle("Update email");

                        LayoutInflater layoutInflater1 = requireActivity().getLayoutInflater();
                        View dialogView1 = layoutInflater1.inflate(R.layout.dialog_update_email, null);
                        EditText emailEditText = dialogView1.findViewById(R.id.emailUpdate_edit_text);
                        EditText passwordEditText = dialogView1.findViewById(R.id.password_edit_text);
                        builder1.setView(dialogView1);

                        builder1.setPositiveButton("Save", (dialog1, which) -> {
                            String newEmail = emailEditText.getText().toString().trim();
                            String password = passwordEditText.getText().toString().trim();
                            firebaseAuthHelper.updateEmail(newEmail, password);
                        });

                        builder1.setNegativeButton("Cancel", null);

                        AlertDialog dialog1 = builder1.create();
                        dialog1.show();
                        return true;


                    case R.id.update_password:
                        AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
                        builder2.setTitle("Update Password");


                        LayoutInflater layoutInflater2 = requireActivity().getLayoutInflater();
                        View dialogView2 = layoutInflater2.inflate(R.layout.dialog_update_password, null);
                        EditText currentPasswordEditText = dialogView2.findViewById(R.id.currentPassword_edit_text);
                        EditText newPasswordEditText = dialogView2.findViewById(R.id.newPassword_edit_text);
                        builder2.setView(dialogView2);

                        builder2.setPositiveButton("Save", (dialog2, which) -> {
                            String currentPassword = currentPasswordEditText.getText().toString().trim();
                            String newPassword = newPasswordEditText.getText().toString().trim();
                            firebaseAuthHelper.updatePassword(currentPassword, newPassword);
                        });

                        builder2.setNegativeButton("Cancel", null);

                        AlertDialog dialog2 = builder2.create();
                        dialog2.show();
                        return true;


                    case R.id.delete_account:
                        AlertDialog.Builder confirmDialogBuilder = new AlertDialog.Builder(getContext());
                        confirmDialogBuilder.setTitle("Delete Your Account");
                        confirmDialogBuilder.setMessage("Are you sure you want to delete your account? This action cannot be undone.");

                        confirmDialogBuilder.setPositiveButton("Delete", (dialog3, which) -> {

                            firebaseAuthHelper.deleteAccount();

                            Intent intent = new Intent(getContext(), LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        });

                        confirmDialogBuilder.setNegativeButton("Cancel", null);

                        AlertDialog confirmDialog = confirmDialogBuilder.create();
                        confirmDialog.show();
                        return true;

                    default:
                        return false;
                }
            });

            popupMenu.show();
        });
        signOut.setOnClickListener(view1 ->
                firebaseAuthHelper.signOut());

        favoriList.setOnClickListener(view12 -> {
            if (activity != null) {
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                FavoriMoviesFragment favoriMoviesFragment = new FavoriMoviesFragment();
                transaction.replace(R.id.nav_host_fragment, favoriMoviesFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        watchedList.setOnClickListener(view12 -> {
            if (activity != null) {
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                WatchedMoviesFragment watchedMoviesFragment = new WatchedMoviesFragment();
                transaction.replace(R.id.nav_host_fragment, watchedMoviesFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

    private void getWatchedCount() {
        String userId = firebaseAuthHelper.getUserId();
        firestoreHelper.getWatchedCount(userId, new FirestoreHelper.GetCountListener() {
            @Override
            public void onMoviesCounted(String count) {
                movieCount = count;
                updateMovieCountAndTime();
            }

            @Override
            public void onFailure(Exception e) {
                // Handle failure
            }
        });
    }

    private void getRuntime() {
        String userId = firebaseAuthHelper.getUserId();
        firestoreHelper.getRuntime(userId, new FirestoreHelper.GetRuntimeListener() {
            @Override
            public void onMoviesRuntime(int totalRuntime) {
                runtime = totalRuntime;
                updateMovieCountAndTime();
            }

            @Override
            public void onFailure(Exception e) {
                // Handle failure
            }
        });
    }

    private void updateMovieCountAndTime() {
        if (movieCount != null && runtime != 0) {
            movieCountText.setText(movieCount);
            totalMovieTime.setText(String.valueOf(runtime));
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AppCompatActivity) {
            activity = (AppCompatActivity) context;
        }
    }


}
