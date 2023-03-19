package com.eakcay.watchit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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