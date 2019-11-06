package fiftyfive.and_firebase_mcommerce;

import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

import java.util.ArrayList;

public class Payment extends AppCompatActivity {

    String selectedAdress = "";
    String selectedShipmentMethod = "";
    String selectedPaymentMethod = "";
    RadioGroup paymentGroup;
    RadioButton payment1, payment2, payment3;
    //Définition du toast
    CharSequence text = "Please select a payment method!";
    int duration = Toast.LENGTH_LONG;
    FirebaseAnalytics mFirebaseAnalytics;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //Get Firebase Instance
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //Get Adress & Shipment Method selected by user
        selectedAdress = getIntent().getStringExtra("SELECTED_ADRESS");
        selectedShipmentMethod = getIntent().getStringExtra("SELECTED_SHIPMENT");

        final Toast toast = Toast.makeText(this, text, duration);

        paymentGroup = (RadioGroup)findViewById(R.id.paymentGroup);
        payment1 = (RadioButton)findViewById(R.id.payment1);
        payment2= (RadioButton)findViewById(R.id.payment2);
        payment3 = (RadioButton)findViewById(R.id.payment3);
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

        //CHECKOUT PROGRESS Tag
        Bundle product1 = new Bundle();
        product1.putString(FirebaseAnalytics.Param.ITEM_ID, "ProductId 123");
        product1.putString(FirebaseAnalytics.Param.ITEM_NAME, "ProductName 123");
        product1.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "ProductCategory 123");
        product1.putString(FirebaseAnalytics.Param.ITEM_VARIANT, "ProductVariant 123");
        product1.putString(FirebaseAnalytics.Param.ITEM_BRAND, "ProductBrand 123");
        product1.putDouble(FirebaseAnalytics.Param.PRICE, 12.34 );
        product1.putString(FirebaseAnalytics.Param.CURRENCY, "EUR");
        product1.putLong(FirebaseAnalytics.Param.QUANTITY, 1);
        ArrayList items = new ArrayList();
        items.add(product1);
        Bundle ecommerceBundle = new Bundle();
        ecommerceBundle.putParcelableArrayList( "items", items );
        ecommerceBundle.putLong( FirebaseAnalytics.Param.CHECKOUT_STEP, 3 );
        ecommerceBundle.putString( FirebaseAnalytics.Param.CHECKOUT_OPTION, "" );
        ecommerceBundle.putString("screenName","Payment");
        ecommerceBundle.putString("userId", "1111111111");
        ecommerceBundle.putString("pageTopCategory", "Checkout");
        ecommerceBundle.putString("pageCategory", "Payment");
        ecommerceBundle.putString("pageSubCategory", "");
        ecommerceBundle.putString("pageType", "Checkout");
        ecommerceBundle.putString("loginStatus", "Logged");
        ecommerceBundle.putString("previousScreen", "Shipping");
        mFirebaseAnalytics.logEvent( FirebaseAnalytics.Event.CHECKOUT_PROGRESS, ecommerceBundle );

        final Button confirmationButton = (Button) findViewById(R.id.confirmationButton);
        confirmationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int checkedRadioButtonId = paymentGroup.getCheckedRadioButtonId();
                Intent i = new Intent(Payment.this, Confirm.class);

                if (checkedRadioButtonId == -1) {
                    toast.show();
                }
                else{
                    switch (checkedRadioButtonId){
                        case R.id.payment1:
                            selectedPaymentMethod = payment1.getText().toString();
                            break;
                        case R.id.payment2:
                            selectedPaymentMethod = payment2.getText().toString();
                            break;
                        case R.id.payment3:
                            selectedPaymentMethod = payment3.getText().toString();
                            break;
                    }
                    //Log.i("SELECTED_PAYMENT_METHOD", selectedPaymentMethod);

                    // SET_CHECOUT_OPTION tag
                    Bundle ecommerceBundle = new Bundle();
                    ecommerceBundle.putString("screenName", "Payment");
                    ecommerceBundle.putString( "eventCategory", "Enhanced Ecommerce" );
                    ecommerceBundle.putString( "eventAction", FirebaseAnalytics.Event.SET_CHECKOUT_OPTION);
                    ecommerceBundle.putString( "eventLabel", selectedPaymentMethod );
                    ecommerceBundle.putLong( FirebaseAnalytics.Param.CHECKOUT_STEP, 3 );
                    ecommerceBundle.putString( FirebaseAnalytics.Param.CHECKOUT_OPTION, "Credit card" );
                    mFirebaseAnalytics.logEvent( FirebaseAnalytics.Event.SET_CHECKOUT_OPTION, ecommerceBundle );

                    i.putExtra("SELECTED_PAYMENT", selectedPaymentMethod);
                    i.putExtra("SELECTED_ADRESS", selectedAdress);
                    i.putExtra("SELECTED_SHIPMENT", selectedShipmentMethod);
                    startActivity(i);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.menu_payment, menu);
        return true;
    }

    //gère le click sur une action de l'ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_informations:
                Intent i = new Intent(Payment.this, Informations.class);
                startActivity(i);
                return true;
            case R.id.action_legal:
                Intent j = new Intent(Payment.this, Legal.class);
                startActivity(j);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

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
