package fiftyfive.and_firebase_mcommerce;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.provider.Settings;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import fiftyfive.and_firebase_mcommerce.models.User;


public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1500;

    final String PREFS_NAME = "MyPrefsFile";

    private FirebaseAuth mAuth; // Firebase Authentication instance
    private FirebaseAnalytics mFirebaseAnalytics; // Firebase Analytics instance


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Database
        Utils.initDatabase();
        DatabaseReference rootDB = Utils.getDatabaseRoot();

        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        // FirebaseAnalytics init
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        // Fill the bundle
        Bundle bundle=new Bundle();
        bundle.putString("screenName","SplashScreen");
        //bundle.putString("clientId", FirebaseInstanceId.getInstance().getId()); // has been set with the real Firebase Instance Id of the app
        bundle.putString("deviceId", Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID)); // has been set with the real Android device id of the phone
        bundle.putString("environment", "Production");
        bundle.putString(FirebaseAnalytics.Param.SOURCE, "Source_de_la_campagne");
        bundle.putString(FirebaseAnalytics.Param.MEDIUM, "Media_de_la_campagne");
        bundle.putString(FirebaseAnalytics.Param.CAMPAIGN, "Nom_de_la_campagne");
        //Fire the event
        mFirebaseAnalytics.logEvent("applicationStart",bundle);

        //checke si connection internet
        if(Utils.isNetworkAvailable(this)) {
            Log.i("Connection : ", " Connextion is OK");

            //Retrieve and Keep synced categories and products
            DatabaseReference categoriesRef = rootDB.child("categories");
            categoriesRef.keepSynced(true);
            DatabaseReference productsRef = rootDB.child("products");
            productsRef.keepSynced(true);
            //TODO: Test de la BDD
            categoriesRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //Log.i("database", dataSnapshot.child("Jewelry").toString());
                    //Log.i("info", "Database is OK");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //Log.i("info", "Database is KO");
                }
            });

            if (settings.getBoolean("my_first_time", true)) {
                //the app is being launched for first time, do something
                //Log.i("Comments", "First time");
                // first time task
                mAuth = FirebaseAuth.getInstance();
                mAuth.signInAnonymously()
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete( Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    //Log.i("SignIn", "signInAnonymously:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    //Log.i("UID = ", user.getUid());
                                    Crashlytics.setUserIdentifier(user.getUid());
                                    User.createAnonymousUser(user.getUid());
                                    final Toast toast = Toast.makeText(getApplicationContext(), "Network OK. AnonymousAuth. Product database synced!", Toast.LENGTH_SHORT);
                                    toast.show();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    //Log.i("SignIn", "signInAnonymously:failure", task.getException());
                                }
                            }
                        });
                // record the fact that the app has been started at least once
                settings.edit().putBoolean("my_first_time", false).commit();
            } else {
                //Log.i("Comments", "Not the First time");
                final Toast toast = Toast.makeText(this, "Network OK. Product database synced!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        else{
            //Log.i("Connection : ", " Connection is KO");
            final Toast toast = Toast.makeText(this, "No Network available. Unable to sync product database!", Toast.LENGTH_SHORT);
            toast.show();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, HomePage.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
