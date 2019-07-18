package com.firebaseapp.waterintakereminder.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebaseapp.waterintakereminder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * Screen that is being shown to the registered user.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";

    private EditText mEmailField;
    private EditText mPasswordField;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailField = findViewById(R.id.login_email);
        mPasswordField = findViewById(R.id.login_password);

        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_sign_up).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_login) {
            logInWithEmail(mEmailField.getText().toString(), mPasswordField.getText().toString());
        } else if (view.getId() == R.id.btn_sign_up) {
            showSignupScreen();
        }
    }

    private void showSignupScreen() {
        Intent signUpIntent = new Intent(LoginActivity.this, HolderActivity.class);
        startActivity(signUpIntent);
        finish();
    }

    private void logInWithEmail(String email, String password) {
        if (!validateForm()) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Successfully logged in. \n" +
                                    "Redirecting back to holder activity for result processing.");
                            displayToast("Successfully logged in");
                            finish();
                        } else {
                            Log.w(TAG, "Failed to log in.", task.getException());
                            displayToast("Failed to log in. Check your info.");
                            finish();
                        }
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
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
