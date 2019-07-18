package com.firebaseapp.waterintakereminder.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.firebaseapp.waterintakereminder.R;
import com.firebaseapp.waterintakereminder.WirApplication;
import com.firebaseapp.waterintakereminder.model.User;
import com.firebaseapp.waterintakereminder.util.ExtraConstants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;


/**
 * This activity is responsible for user registration and authentication,
 * and provides redirection to other activities based on user actions.
 */
public class HolderActivity extends AppCompatActivity {

    private static final String TAG = "HolderActivity";

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    // TODO: Edit this flag through SharedPrefs on account DELETE operation
    private boolean mExistsInDatabase;
    private String mExistsInDatabaseEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holder);

        mExistsInDatabase = WirApplication.getPreferenceDataBoolean("exists_in_db");
        mExistsInDatabaseEmail = WirApplication.getPreferenceDataString("email");

        Log.d(TAG, "onCreate: mExistsInDatabase: " + mExistsInDatabase);
        Log.d(TAG, "onCreate: mExistsInDatabaseEmail: " + mExistsInDatabaseEmail);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseDatabase.getReference();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {
                    if (mExistsInDatabaseEmail.equals(currentUser.getEmail())) {
                        Log.d(TAG, "User " + currentUser.getEmail() + " has logged in.");
                        updateUI();
                    }
                } else {
                    if (mExistsInDatabase) {
                        Log.d(TAG, "onAuthStateChanged: Checking if user exists in db");
                        showLoginScreen();
                    } else {
                        Log.d(TAG, "onAuthStateChanged: Authenticating user");
                        authenticateUser();
                    }
                }
            }
        };
    }

    /**
     * @param currentUser Authenticated user
     * @return <b>true</b> - if current user's email is equal to email that is stored
     * in database
     */
    private boolean existsInDatabase(@NonNull FirebaseUser currentUser) {
        final boolean[] flag = new boolean[1];
        mDatabase.child("users").child("uid").orderByChild("email")
                .equalTo(currentUser.getEmail())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        flag[0] = dataSnapshot.exists();
                        Log.d(TAG, "onDataChange: User exists.");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w(TAG, "onDataChange: Failed to read user info. \n"
                                + databaseError.toException());
                    }
                });
        return flag[0];
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthStateListener != null) {
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ExtraConstants.RC_LOG_IN) {
            if (resultCode == RESULT_OK) {
                Log.d(TAG, "onActivityResult: Login request processing");
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {
                    updateUI();
                } else {
                    authenticateUser();
                }
            }
        } else if (requestCode == ExtraConstants.RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Log.d(TAG, "onActivityResult: SignIn request processing");
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {
                    saveUserToDatabase(currentUser);
                    updateUI();
                } else {
                    authenticateUser();
                }
            }
        }
    }

    private void showLoginScreen() {
        Log.d(TAG, "showLoginScreen: Redirecting to login screen");
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    private void updateUI() {
        Log.d(TAG, "updateUI: Redirecting to main screen");
        Intent activityIntent = new Intent(this, MainActivity.class);
        startActivity(activityIntent);
        finish();
    }

    /**
     * Shows a signup screen with Email and Google sign in options.
     */
    private void authenticateUser() {
        AuthMethodPickerLayout customLayout = new AuthMethodPickerLayout
                .Builder(R.layout.activity_holder)
                .setGoogleButtonId(R.id.btn_google)
                .setEmailButtonId(R.id.btn_email)
                .build();

        Log.d(TAG, "authenticateUser: Loading signup screen");

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Arrays.asList(
                                // TODO: Fix Google sign in (returns 12500 error)
                                new AuthUI.IdpConfig.GoogleBuilder().build(),
                                new AuthUI.IdpConfig.EmailBuilder().build()))
                        .setAuthMethodPickerLayout(customLayout)
                        .setTheme(R.style.AppTheme)
                        .build(),
                ExtraConstants.RC_SIGN_IN);
    }

    /**
     * Saves user id, email and name to database, and stores email
     * and a special flag that user exists in database for extra authentication on app start.
     *
     * @param currentUser Authenticated user
     */
    private void saveUserToDatabase(@NonNull FirebaseUser currentUser) {

        Log.d(TAG, "saveUserToDatabase: Checking user " + currentUser.getEmail());

        String email = currentUser.getEmail();
        String uid = currentUser.getUid();
        String name = currentUser.getDisplayName();

        final User user = new User(uid, name, email);

        if (!existsInDatabase(currentUser)) {
            Log.d(TAG, "saveUserToDatabase: Writing values to SharedPrefs");
            mDatabase.child("users").child("uid").setValue(user);
            WirApplication.setPreferencesString("email", email);
            WirApplication.setPreferencesBoolean("exists_in_db", true);
        }
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
