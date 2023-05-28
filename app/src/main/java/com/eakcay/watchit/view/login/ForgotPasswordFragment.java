package com.eakcay.watchit.view.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import com.eakcay.watchit.R;
import com.eakcay.watchit.auth.FirebaseAuthHelper;

public class ForgotPasswordFragment extends Fragment {
    private FirebaseAuthHelper firebaseAuthHelper;
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

        firebaseAuthHelper = new FirebaseAuthHelper(getContext());

        editTextEmail = view.findViewById(R.id.editTextEmail);
        buttonResetPassword = view.findViewById(R.id.buttonResetPassword);

        buttonResetPassword.setOnClickListener(v -> {
            // Handle reset password button click
            resetPassword();
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

        firebaseAuthHelper.forgotPassword(email);

    }
}
