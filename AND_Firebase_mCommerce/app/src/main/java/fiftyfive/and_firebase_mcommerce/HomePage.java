package fiftyfive.and_firebase_mcommerce;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {

    //private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth; // Firebase Authentication instance
    FirebaseAnalytics mFirebaseAnalytics; // Firebase Analytics instance
    private FirebaseRemoteConfig firebaseRemoteConfig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Button button = findViewById(R.id.connect);
        TextView textView = findViewById(R.id.text);
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        firebaseRemoteConfig.setConfigSettingsAsync(new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600)
                .build());
        firebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);
        /*
        Setting color, size and string for TextView using parameters returned from
        remote config server
         */
        textView.setTextColor(Color.parseColor(firebaseRemoteConfig.getString("text_color")));
        textView.setTextSize((float) firebaseRemoteConfig.getValue("text_size").asDouble());
        textView.setText(firebaseRemoteConfig.getString("text_str"));
        firebaseRemoteConfig.fetch(1).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(HomePage.this, "Activated", Toast.LENGTH_SHORT).show();
                    /*
                     Activiting fetched parameters. The new parameters will now be available to your app
                     */
                    firebaseRemoteConfig.activate();
                }else {
                    Toast.makeText(HomePage.this, "Not Activated", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Obtain the FirebaseAnalytics instance.
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        Log.d("IID_TOKEN", task.getResult().getToken());
                    }
                });
        //mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        //get firebase auth instance
        auth = FirebaseAuth.getInstance();
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                        }


                        // Handle the deep link. For example, open the linked
                        // content, or apply promotional credit to the user's
                        // account.
                        // ...

                        // ...
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure( Exception e) {
                        //Log.w(TAG, "getDynamicLink:onFailure", e);
                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.menu_home_page, menu);
        return true;
    }

    //gere le click sur une action de l'ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cart:
                Intent i = new Intent(HomePage.this, Basket.class);
                startActivity(i);
                return true;
            case R.id.action_informations:
                Intent j = new Intent(HomePage.this, Informations.class);
                startActivity(j);
                return true;
            case R.id.action_legal:
                Intent k = new Intent(HomePage.this, Legal.class);
                startActivity(k);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onStart() {

        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        final FirebaseUser user = auth.getCurrentUser();

        final Intent j;
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                        }


                        // Handle the deep link. For example, open the linked
                        // content, or apply promotional credit to the user's
                        // account.
                        // ...

                        // ...
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure( Exception e) {
                       // Log.w(TAG, "getDynamicLink:onFailure", e);
                    }
                });
        ImageButton promoBanner = (ImageButton) findViewById(R.id.promoBanner);

        // VEIW_ITEM_LIST tag for promo
        Bundle promotion1 = new Bundle();
        promotion1.putString(FirebaseAnalytics.Param.ITEM_ID, "123");
        promotion1.putString(FirebaseAnalytics.Param.ITEM_NAME, "Solde sur les bijoux");
        promotion1.putString(FirebaseAnalytics.Param.CREATIVE_NAME, "HP_banner_solde_bijoux.jpg");
        promotion1.putString(FirebaseAnalytics.Param.CREATIVE_SLOT, "1");

        ArrayList promotions = new ArrayList();
        promotions.add(promotion1);

        Bundle ecommerceBundle = new Bundle();
        ecommerceBundle.putParcelableArrayList("promotions", promotions);
        ecommerceBundle.putString("screenName", "HomePage");
        //ecommerceBundle.putString("userId", user.getUid());
        ecommerceBundle.putString("pageTopCategory", "HomePage");
        ecommerceBundle.putString("pageType", "HomePage");
        /*if (!user.isAnonymous()) {
            ecommerceBundle.putString("loginStatus", "logged");
        } else {
            ecommerceBundle.putString("loginStatus", "not logged");
        }*/
        ecommerceBundle.putString("previousScreen", "SplashScreen");
        ecommerceBundle.putDouble(FirebaseAnalytics.Param.VALUE, 5.00);
        ecommerceBundle.putString(FirebaseAnalytics.Param.CURRENCY, "EUR");

        //mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM_LIST, ecommerceBundle);


        promoBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // SELECT_CONTENT LIST
                Bundle promotion1 = new Bundle();
                promotion1.putString(FirebaseAnalytics.Param.ITEM_ID, "123");
                promotion1.putString(FirebaseAnalytics.Param.ITEM_NAME, "Solde sur les bijoux");
                promotion1.putString(FirebaseAnalytics.Param.CREATIVE_NAME, "HP_banner_solde_bijoux.jpg");
                promotion1.putString(FirebaseAnalytics.Param.CREATIVE_SLOT, "1");

                ArrayList promotions = new ArrayList();
                promotions.add(promotion1);

                Bundle ecommerceBundle = new Bundle();
                ecommerceBundle.putParcelableArrayList("promotions", promotions);
                ecommerceBundle.putString("screenName", "HomePage");
                ecommerceBundle.putString("eventCategory", "Enhanced Ecommerce");
                ecommerceBundle.putString("eventAction", "SELECT_PROMO");
                ecommerceBundle.putString("eventLabel", "More information in ecommerce reports");
                ecommerceBundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "HP Banner Solde Bijoux");
                ecommerceBundle.putString(FirebaseAnalytics.Param.ITEM_ID, "123");

                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, ecommerceBundle);

                Intent i = new Intent(HomePage.this, Liste.class);
                i.putExtra("SELECTED_CATEGORY_ID", "Jewelry");
                startActivity(i);
                finish();
            }
        });


        ImageButton consoles = (ImageButton) findViewById(R.id.consoles);
        consoles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePage.this, Liste.class);
                i.putExtra("SELECTED_CATEGORY_ID", "Video Games");
                startActivity(i);
                finish();
            }
        });


        final Button connect = (Button) findViewById(R.id.connect);
        if (user == null) {
            //Log.i("TAG:", "User non connecté");
            connect.setText("Login");
        } else {
            if (user.isAnonymous()) {
                //Log.i("TAG:", "User Aonymous");
                //Log.i("UID: ", user.getUid());
                connect.setText("Signup");
            } else {
                //Log.i("TAG:", "User Connecté");
                //Log.i("UID: ", user.getUid());
                connect.setText("Profile");
            }
        }

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.i("TAG;", "Bouton clickqué");
                if (user == null) {
                    startActivity(new Intent(HomePage.this, Login.class));
                    finish();
                } else {
                    if (user.isAnonymous()) {
                        startActivity(new Intent(HomePage.this, Signup.class));
                        finish();
                    } else {
                        startActivity(new Intent(HomePage.this, Profile.class));
                        finish();
                    }
                }
            }
        });

    }

}