package com.eakcay.watchit.view.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.eakcay.watchit.R;
import com.eakcay.watchit.auth.FirebaseAuthHelper;

public class RegisterFragment extends Fragment {

    private FirebaseAuthHelper firebaseAuthHelper;
    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextPassword;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        firebaseAuthHelper = new FirebaseAuthHelper(getContext());

        editTextUsername = view.findViewById(R.id.editTextUsername);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        Button buttonRegister = view.findViewById(R.id.buttonRegister);
        TextView textViewLogin = view.findViewById(R.id.textViewLogin);

        buttonRegister.setOnClickListener(v -> {
            // Handle register button click
            registerUser();
        });

        textViewLogin.setOnClickListener(v -> {
            // Load LoginFragment when login text view is clicked
            ((LoginActivity) requireActivity()).loadFragment(new LoginFragment());
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

        firebaseAuthHelper.registerUser(email,password,username);

    }

}

