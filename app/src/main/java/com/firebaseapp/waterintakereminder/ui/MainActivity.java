package com.firebaseapp.waterintakereminder.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.firebaseapp.waterintakereminder.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    final FragmentManager fm = getSupportFragmentManager();
    final Fragment homeFragment = new HomeFragment();
    final Fragment profileFragment = new ProfileFragment();
    Fragment active = homeFragment;

    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBottomNavigationView = findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);

        FirebaseUser currentUser =
                FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser == null) {
            startActivity(new Intent(this, HolderActivity.class));
            finish();
        }
        if (currentUser != null) {
            Log.d(TAG, "onCreate: loading HomeFragment");
            fm.beginTransaction()
                    .add(R.id.fragment_container, homeFragment)
                    .commit();
            fm.beginTransaction()
                    .add(R.id.fragment_container, profileFragment)
                    .hide(profileFragment)
                    .commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_home:
                Log.d(TAG, "onNavigationItemSelected: load home fragment");
                fm.beginTransaction().hide(active).show(homeFragment).commit();
                active = homeFragment;
                return true;
            case R.id.action_profile:
                Log.d(TAG, "onNavigationItemSelected: load profile fragment");
                fm.beginTransaction().hide(active).show(profileFragment).commit();
                active = profileFragment;
                return true;
            case R.id.action_statistics:
                displayToast("TBA");
                return true;
            case R.id.action_settings:
                // TODO: Add navigation to SettingsFragment
                startActivity(new Intent
                        (MainActivity.this, SettingsActivity.class));
                return true;
        }
        return false;
    }

    /**
     * A customizable toast message.
     *
     * @param message Message to be displayed
     */
    private void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

}
