package com.firebaseapp.waterintakereminder.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebaseapp.waterintakereminder.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView mBottomNavigationView;
    private TextView mTextViewUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBottomNavigationView = findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        mTextViewUsername = findViewById(R.id.text_main_username);

        FirebaseUser currentUser =
                FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser == null) {
            startActivity(new Intent(this, HolderActivity.class));
            finish();
        }
        if (currentUser != null)
            mTextViewUsername.setText(currentUser.getDisplayName());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent
                        (MainActivity.this, SettingsActivity.class));
                return true;
            case R.id.action_profile:
                startActivity(new Intent
                        (MainActivity.this, ProfileActivity.class));
                return true;
            // TODO: 7/12/19 Add handler for this case
            case R.id.action_statistics:
                displayToast("TBA");
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
