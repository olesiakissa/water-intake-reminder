package com.firebaseapp.waterintakereminder.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.firebaseapp.waterintakereminder.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ProfileEditActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int DEFAULT_AGE_MIN = 1;
    private static final int DEFAULT_AGE_MAX = 100;

    private Toolbar mToolbar;

    private TextInputEditText mEditNameInput;
    private TextInputEditText mEditEmailInput;
    private ImageButton mBtnInfo;
    private ImageButton mBtnEditUserpic;

    private NumberPicker mAgePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        setupToolBar();

        mEditNameInput = findViewById(R.id.edit_name);
        mEditEmailInput = findViewById(R.id.edit_email);
        mBtnInfo = findViewById(R.id.action_info);
        mBtnInfo.setOnClickListener(this);
        mBtnEditUserpic = findViewById(R.id.edit_userpic);
        mBtnEditUserpic.setOnClickListener(this);

        // TODO: Set text to input fields that are already stored in db (e.g. name & email)
        setDefaultTextValues();

        setupAgeNumberPicker();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_info:
                displayLongToast(getString(R.string.info_popup));
                break;
            case R.id.edit_userpic:
                displayToast("Edit picture clicked");
                break;
        }

    }

    private void setupToolBar() {
        mToolbar = findViewById(R.id.toolbar_edit_profile);
        //TODO: Set on click listener for toolbar (handler for back and save buttons)
    }

    private void setDefaultTextValues() {
        FirebaseUser currentUser =
                FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            mEditNameInput.setText(currentUser.getDisplayName());
            mEditEmailInput.setText(currentUser.getEmail());
        }
    }

    private void setupAgeNumberPicker() {
        mAgePicker = findViewById(R.id.np_age);
        mAgePicker.setMinValue(DEFAULT_AGE_MIN);
        mAgePicker.setMaxValue(DEFAULT_AGE_MAX);
        mAgePicker.setWrapSelectorWheel(false);
    }

    /**
     * A customizable toast message.
     *
     * @param message Message to be displayed
     */
    private void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_LONG).show();
    }

    /**
     * Displays long toast message.
     *
     * @param message Message to be displayed
     */
    private void displayLongToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_LONG).show();
    }

}
