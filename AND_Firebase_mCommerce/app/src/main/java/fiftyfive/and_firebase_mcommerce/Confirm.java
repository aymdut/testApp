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
import android.widget.TextView;
import android.widget.Toast;

//import com.contentsquare.android.ContentSquare;
//import com.contentsquare.android.internal.api.Currencies;
//import com.contentsquare.android.internal.api.model.Transaction;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

import java.util.ArrayList;

public class Confirm extends AppCompatActivity {

    //Définition du toast
    CharSequence text = "Your order has benn refund !";
    int duration = Toast.LENGTH_LONG;
    String selectedAdress = "";
    String selectedShipmentMethod = "";
    String selectedPaymentMethod = "";
    FirebaseAnalytics mFirebaseAnalytics;
    FirebaseAuth mAuth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        //Get Firebase Analytics Instance
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        final Toast toast = Toast.makeText(this, text, duration);
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
        //Get Adress & Shipment Method selected by user
        selectedAdress = getIntent().getStringExtra("SELECTED_ADRESS");
        selectedShipmentMethod = getIntent().getStringExtra("SELECTED_SHIPMENT");
        selectedPaymentMethod = getIntent().getStringExtra("SELECTED_PAYMENT");

        final TextView adress = (TextView) findViewById(R.id.adress);
        adress.setText(adress.getText()+ selectedAdress);

        final TextView shipment = (TextView) findViewById(R.id.shipment);
        shipment.setText(shipment.getText()+ selectedShipmentMethod);

        final TextView payment = (TextView) findViewById(R.id.payment);
        payment.setText(payment.getText()+ selectedPaymentMethod);

        //TODO: CONFIRMATION_CODE A RENDRE DYNAMIC
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
        ecommerceBundle.putString( FirebaseAnalytics.Param.TRANSACTION_ID, "555666" );
        ecommerceBundle.putString( FirebaseAnalytics.Param.AFFILIATION, "Acme Clothing" );
        ecommerceBundle.putDouble( FirebaseAnalytics.Param.VALUE, 20.49 );
        ecommerceBundle.putDouble( FirebaseAnalytics.Param.TAX, 3.65 );
        ecommerceBundle.putDouble( FirebaseAnalytics.Param.SHIPPING, 4.50);
        ecommerceBundle.putString( FirebaseAnalytics.Param.CURRENCY, "EUR" );
        ecommerceBundle.putString( FirebaseAnalytics.Param.COUPON, "Fifty Fifty" );
        ecommerceBundle.putString("screenName", "Confirmation");
        ecommerceBundle.putString("userId", user.getUid());
        ecommerceBundle.putString("pageTopCategory", "Checkout");
        ecommerceBundle.putString("pageCategory", "Confirmation");
        ecommerceBundle.putString("pageType", "Checkout");
        ecommerceBundle.putString("loginStatus", "Logged");
        ecommerceBundle.putString("previousScreen", "Payment");
        mFirebaseAnalytics.logEvent( FirebaseAnalytics.Event.ECOMMERCE_PURCHASE, ecommerceBundle );

        //Tag transaction ContentSquare
        //ContentSquare.send(Transaction.builder(21, Currencies.EUR).id("555666").build());


        final Button okButton = (Button) findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Confirm.this, HomePage.class);
                startActivity(i);
            }
        });
        final Button refundButton = (Button) findViewById(R.id.refundButton);
        refundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: REFUND CODE A RENDRE DYNAMIC
                Bundle ecommerceBundle = new Bundle();
                ecommerceBundle.putString("screenName", "Confirmation");
                ecommerceBundle.putString( FirebaseAnalytics.Param.TRANSACTION_ID, "333444" );
                ecommerceBundle.putDouble( FirebaseAnalytics.Param.VALUE, 20.49 );
                // (OPTIONAL) For partial refunds, define the item IDs and quantities of products being refunded
                Bundle refundedProduct = new Bundle();
                refundedProduct.putString(FirebaseAnalytics.Param.ITEM_ID, "ProductId 123"); // Required for partial refund
                refundedProduct.putLong(FirebaseAnalytics.Param.QUANTITY, 1); // Required for partial refund
                ArrayList items = new ArrayList();
                items.add(refundedProduct);
                ecommerceBundle.putParcelableArrayList( "items", items );
                mFirebaseAnalytics.logEvent( FirebaseAnalytics.Event.PURCHASE_REFUND, ecommerceBundle );

                toast.show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.menu_confirm, menu);
        return true;
    }

    //gère le click sur une action de l'ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_informations:
                Intent i = new Intent(Confirm.this, Informations.class);
                startActivity(i);
                return true;
            case R.id.action_legal:
                Intent j = new Intent(Confirm.this, Legal.class);
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
