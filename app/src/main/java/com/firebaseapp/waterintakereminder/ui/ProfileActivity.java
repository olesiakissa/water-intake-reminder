package com.firebaseapp.waterintakereminder.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebaseapp.waterintakereminder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;
    private TextView mTextViewUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mBottomNavigationView = findViewById(R.id.bottom_navigation);

        FirebaseUser currentUser =
                FirebaseAuth.getInstance().getCurrentUser();

        mTextViewUsername = findViewById(R.id.text_profile_username);
        mTextViewUsername.setText(currentUser.getDisplayName());
    }


    /**
     * Signs the current user out of application and redirects him
     * to the holder activity.
     * @param view View that is being clicked on
     */
    public void signOut(View view) {
        if (view.getId() == R.id.btn_signout) {
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            startActivity(new Intent
                                    (ProfileActivity.this, HolderActivity.class));
                        }
                    });
        }
    }
}
