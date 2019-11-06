package fiftyfive.and_firebase_mcommerce;

import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

public class Informations extends AppCompatActivity {


    FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informations);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Get Firebase Instance
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // SCREEN VIEW HIT
        Bundle bundle=new Bundle();
        bundle.putString("screenName","Informations");
        bundle.putString("userId", "11111111111");
        bundle.putString("pageTopCategory", "Informations");
        bundle.putString("pageCategory", "");
        bundle.putString("pageSubCategory", "");
        bundle.putString("pageType", "Informations");
        bundle.putString("loginStatus", "Not logged");
        bundle.putString("previousScreen", "Menu bar");
        mFirebaseAnalytics.logEvent("screenView",bundle);
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
        Button crash = (Button) findViewById(R.id.crash);
        crash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Crashlytics.setString("screenName", "Informations");
                Crashlytics.log("This is a big crash!");
                Crashlytics.getInstance().crash(); // Force a crash
                        throw new  RuntimeException("Crash Activity ");

            }
        });

        //App Name
        TextView appName = (TextView) findViewById(R.id.appName);
        appName.setText(appName.getText() + " " + getResources().getString(R.string.app_name) );

        String UUID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        TextView udid = (TextView) findViewById(R.id.udid);
        udid.setText(UUID);
        Log.d("UUID", UUID);
        //appName.setText(appName.getText() + " " + Context.getSystemService(Context.TELEPHONY_SERVICE).getDeviceID() );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.menu_informations, menu);
        return true;
    }

    //gère le click sur une action de l'ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_cart:
                Intent i = new Intent(Informations.this, Basket.class);
                startActivity(i);
                return true;
            case R.id.action_legal:
                Intent j = new Intent(Informations.this, Legal.class);
                startActivity(j);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // The activity is killed when user click on "Back" button
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //Log.i("INFO", "SYSTEM BACK button cliqued");
            finish();
            return true;
        }

        return false;
    }
}
