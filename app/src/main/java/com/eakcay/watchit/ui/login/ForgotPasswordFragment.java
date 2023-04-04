package com.eakcay.watchit.ui.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.eakcay.watchit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordFragment extends Fragment {

    private EditText editTextEmail;
    private Button buttonResetPassword;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        editTextEmail = view.findViewById(R.id.editTextEmail);
        buttonResetPassword = view.findViewById(R.id.buttonResetPassword);

        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle reset password button click
                resetPassword();
            }
        });

        return view;
    }

    private void resetPassword() {
        // Get user input email address
        String email = editTextEmail.getText().toString().trim();

        // Validate email address
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Please enter email address");
            editTextEmail.requestFocus();
            return;
        }

        // Send password reset email
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Handle successful password reset
                            Toast.makeText(getContext(), "Password reset email sent", Toast.LENGTH_SHORT).show();
                            ((LoginActivity) getActivity()).loadFragment(new LoginFragment());

                        } else {
                            // Handle password reset failure
                            Toast.makeText(getContext(), "Failed to send password reset email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
