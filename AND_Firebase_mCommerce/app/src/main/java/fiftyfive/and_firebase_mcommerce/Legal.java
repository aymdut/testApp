package fiftyfive.and_firebase_mcommerce;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

/**
 * Created by Francois on 03/08/2017.
 */

public class Legal extends AppCompatActivity{

    //FirebaseAnalytics object declaration
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_legal);
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
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        /*
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle=new Bundle();
        bundle.putString("screenName","Legal");
        bundle.putString("userId", "1111111111");
        bundle.putString("pageTopCategory", "Legal");
        bundle.putString("pageCategory", "");
        bundle.putString("pageSubCategory", "");
        bundle.putString("pageType", "User");
        bundle.putString("loginStatus", "Not logged");
        bundle.putString("previousScreen", "HomePage");
        mFirebaseAnalytics.logEvent("screenView", bundle);
        */

        Task<String> appInstanceId = mFirebaseAnalytics.getAppInstanceId();
        appInstanceId.addOnCompleteListener(listener);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, new Bundle());

        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, null);


    }

    OnCompleteListener<String> listener = new OnCompleteListener<String>() {
        @Override
        public void onComplete( Task<String> task) {
            //Log.e("appInstanceId", task.getResult());

            WebView myWebView = (WebView) findViewById(R.id.webview);
            //myWebView.loadUrl("https://www.20minutes.fr");
            myWebView.loadUrl("https://project-5176900217331074858.firebaseapp.com/?cid="+task.getResult());
            myWebView.setWebViewClient(new WebViewClient());
            myWebView.getSettings().setJavaScriptEnabled(true);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.menu_legal, menu);
        return true;
    }

    //gère le click sur une action de l'ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_cart:
                //Goto action cart
                return true;
            case R.id.action_informations:
                Intent i = new Intent(Legal.this, Informations.class);
                startActivity(i);
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
