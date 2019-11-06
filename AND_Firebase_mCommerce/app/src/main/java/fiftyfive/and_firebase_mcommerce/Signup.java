package fiftyfive.and_firebase_mcommerce;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import fiftyfive.and_firebase_mcommerce.models.User;

/**
 * Created by Francois on 10/08/2017.
 */

public class Signup extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FirebaseAnalytics mFirebaseAnalytics;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        DatabaseReference rootDB = Utils.getDatabaseRoot();


        //Get Firebase auth instance
        mAuth = FirebaseAuth.getInstance();
        //Get Firebase ANalytics INstance
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signup.this, ResetPassword.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                // Si utilisateur Anonyme alors linking avec le compte mail
                if (mAuth.getCurrentUser().isAnonymous()) {

                    AuthCredential credential = EmailAuthProvider.getCredential(email, password);

                    Task<AuthResult> authResultTask = mAuth.getCurrentUser().linkWithCredential(credential)
                            .addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                                public void onComplete( Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        //Log.i("Linking: ", "linkWithCredential:success");
                                        FirebaseUser newUser = task.getResult().getUser();
                                        //Log.i("Linked user UID", newUser.getUid());
                                        //Log.i("Linked user email", newUser.getEmail());
                                        User.updateAnonymoustoSignedUpAfterLinking(newUser.getUid(), newUser.getEmail());
                                        startActivity(new Intent(Signup.this, Profile.class));
                                        finish();
                                    } else {
                                        //Log.i("Linking: ", "linkWithCredential:failure");
                                        Toast.makeText(Signup.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
                // Sinon si user n'existe pas, création d'un nouvel utisateur
                else if (mAuth.getCurrentUser() == null) {

                    //create user
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete( Task<AuthResult> task) {
                                    Toast.makeText(Signup.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(Signup.this, "Authentication failed." + task.getException(),
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        //Log.i("New user created UID", mAuth.getCurrentUser().getUid());
                                        // if sign in passes
                                        Crashlytics.setUserIdentifier(mAuth.getCurrentUser().getUid()); // identify user on crashlytics
                                        // Tag to track user signup
                                        Bundle params = new Bundle();
                                        params.putString("screenName", "Signup");
                                        params.putString("eventCategory", "User");
                                        params.putString("eventAction", "Sign up");
                                        params.putString(FirebaseAnalytics.Param.SIGN_UP_METHOD, "Email");
                                        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, params );

                                        startActivity(new Intent(Signup.this, Profile.class));
                                        finish();

                                    }
                                }
                            });
                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

}