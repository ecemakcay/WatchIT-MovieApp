package com.eakcay.watchit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.eakcay.watchit.login.LoginActivity;
import com.eakcay.watchit.login.LoginFragment;
import com.eakcay.watchit.ui.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    private Button buttonLogout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            // bottomNav erişimi sağladık
            bottomNav = findViewById(R.id.bottomNav);

            // navHostFragment erişimi sağladık
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().
                    findFragmentById(R.id.nav_host_fragment);

            // yukarıdaki erişimleri sağladıktan sonra aşağıda ikisini birleştirdik
            // artık bottomNav üzerindeki tuşlara basıldığında ilgili sayfa açılacak
            NavigationUI.setupWithNavController(bottomNav, navHostFragment.getNavController());

             }
    }
}