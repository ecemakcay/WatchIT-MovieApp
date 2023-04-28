package com.eakcay.watchit.view.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.eakcay.watchit.MainActivity;
import com.eakcay.watchit.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity implements
        LoginFragment.OnForgotPasswordListener, LoginFragment.OnRegisterListener {
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        // Create instances of LoginFragment and ForgotPasswordFragment
        LoginFragment loginFragment = new LoginFragment();

        // Set listeners to LoginFragment instance
        loginFragment.setOnForgotPasswordListener(this);
        loginFragment.setOnRegisterListener(this);

        fragmentTransaction.add(R.id.frameLayout1, loginFragment);

        // Load login fragment by default
        loadFragment(loginFragment);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    // Helper method to load a fragment
    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Load LoginFragment to// frameLayout1 by default
        transaction.replace(R.id.frameLayout1, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    // Handle onForgotPassword event from LoginFragment
    @Override
    public void onForgotPassword() {
        loadFragment(new ForgotPasswordFragment());
    }

    // Handle onRegister event from LoginFragment
    @Override
    public void onRegister() {
        loadFragment(new RegisterFragment());
    }
}
