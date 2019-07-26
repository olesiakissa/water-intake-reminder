package com.firebaseapp.waterintakereminder.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.firebase.ui.auth.AuthUI;
import com.firebaseapp.waterintakereminder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment
        implements View.OnClickListener {

    private static final String TAG = "ProfileFragment";

    private TextView mTextViewUsername;
    private ImageButton mBtnSignout;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mTextViewUsername = view.findViewById(R.id.text_profile_username);
        mBtnSignout = view.findViewById(R.id.btn_signout);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FirebaseUser currentUser =
                FirebaseAuth.getInstance().getCurrentUser();


        mTextViewUsername.setText(currentUser.getDisplayName());

        mBtnSignout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_signout:
                signOut();
                break;
            // TODO: Add cases for updating a profile pic and saving user info
        }
    }

    /**
     * Signs the current user out of application and redirects him
     * to the holder activity.
     */
    public void signOut() {
        AuthUI.getInstance()
                .signOut(getContext())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "signOut: Signing out");
                        startActivity(new Intent
                                (getActivity(), HolderActivity.class));
                        // Closing current fragment
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                });
    }


    // TODO: Create updateUserInfo(FirebaseUser currentUser)
}
