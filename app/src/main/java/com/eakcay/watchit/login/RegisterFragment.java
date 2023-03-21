package com.eakcay.watchit.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.eakcay.watchit.MainActivity;
import com.eakcay.watchit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterFragment extends Fragment {

    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonRegister;
    private TextView textViewLogin;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        editTextUsername = view.findViewById(R.id.editTextEmail);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        buttonRegister = view.findViewById(R.id.buttonRegister);
        textViewLogin = view.findViewById(R.id.textViewLogin);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle register button click
                registerUser();
            }
        });

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load LoginFragment when login text view is clicked
                ((LoginActivity) getActivity()).loadFragment(new LoginFragment());
            }
        });

        return view;
    }

    private void registerUser() {
        // Get user inputs from EditText views
        String username = editTextUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Validate user inputs
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Please enter email");
            editTextEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter password");
            editTextPassword.requestFocus();
            return;
        }

        // Create user with Firebase Authentication
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Update user's display name with their username
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(username)
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                // Handle successful registration
                                                Toast.makeText(getContext(), "Registration successful", Toast.LENGTH_SHORT).show();
                                                // Sign in the user with their email and password
                                                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                                if (task.isSuccessful()) {
                                                                    // Sign in successful, redirect to MainActivity
                                                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                    startActivity(intent);
                                                                } else {
                                                                    // Handle sign-in failure
                                                                    Toast.makeText(getContext(), "Sign-in failed", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                            } else {
                                                // Handle registration failure
                                                Toast.makeText(getContext(), "Registration failed", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            // Handle registration failure
                            Toast.makeText(getContext(), "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}

