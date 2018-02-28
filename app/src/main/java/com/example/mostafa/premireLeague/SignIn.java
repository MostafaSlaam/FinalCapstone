package com.example.mostafa.premireLeague;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;


public class SignIn extends AppCompatActivity {


    private static final int RC_SIGN_IN = 100;
    FirebaseAuth mAuth;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if (requestCode == RC_SIGN_IN) {
            if (mAuth.getCurrentUser() == null) {

                startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                                .setAvailableProviders(Arrays.asList(
                                        new AuthUI.IdpConfig.GoogleBuilder().build()))
                                .setIsSmartLockEnabled(false)
                                .build(),
                        RC_SIGN_IN);

            } else {
                Toast.makeText(getApplicationContext(), mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();

                Intent i = new Intent(SignIn.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        // FirebaseApp.initializeApp(getApplicationContext());

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() == null) {

            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                            .setAvailableProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.GoogleBuilder().build()))
                            .setIsSmartLockEnabled(false)
                            .build(),
                    RC_SIGN_IN);

        } else {
            Toast.makeText(getApplicationContext(), mAuth.getCurrentUser().getDisplayName(), Toast.LENGTH_SHORT).show();

            Intent i = new Intent(SignIn.this, MainActivity.class);
            startActivity(i);
            finish();
        }


    }


}
