package fiftyfive.and_firebase_mcommerce;

import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
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

public class Shipment extends AppCompatActivity {

    String selectedAdress = "";
    String selectedShipmenttMethod = "";
    RadioGroup adressesgroup, shipmentsGroup;
    RadioButton adress1, adress2, adress3, shipment1, shipment2, shipment3;
    int duration = Toast.LENGTH_LONG;
    FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipment);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //Get Firebase Instance
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
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
        //Adresses Group
        adressesgroup = (RadioGroup)findViewById(R.id.adressesGroup);
        adress1 = (RadioButton)findViewById(R.id.adress1);
        adress2= (RadioButton)findViewById(R.id.adress2);
        adress3 = (RadioButton)findViewById(R.id.adress3);

        //ShipmentMethods Group
        shipmentsGroup = (RadioGroup)findViewById(R.id.shipmentsGroup);
        shipment1 = (RadioButton)findViewById(R.id.shipment1);
        shipment2= (RadioButton)findViewById(R.id.shipment2);
        shipment3 = (RadioButton)findViewById(R.id.shipment3);

        //TODO : Place here CECHOUT_PROGRESS_STEP_2 tracking code à dynamiser
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
        ecommerceBundle.putLong( FirebaseAnalytics.Param.CHECKOUT_STEP, 2 );
        ecommerceBundle.putString( FirebaseAnalytics.Param.CHECKOUT_OPTION, "" );
        ecommerceBundle.putString("screenName","Shipping");
        ecommerceBundle.putString("userId", "1111111111");
        ecommerceBundle.putString("pageTopCategory", "Checkout");
        ecommerceBundle.putString("pageCategory", "Shipping");
        ecommerceBundle.putString("pageSubCategory", "");
        ecommerceBundle.putString("pageType", "Checkout");
        ecommerceBundle.putString("loginStatus", "Logged");
        ecommerceBundle.putString("previousScreen", "Basket");
        mFirebaseAnalytics.logEvent( FirebaseAnalytics.Event.CHECKOUT_PROGRESS, ecommerceBundle );


        final Button paymentButton = (Button) findViewById(R.id.paymentButton);
        paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adressCheckedRadioButtonId = adressesgroup.getCheckedRadioButtonId();
                int shipmentCheckedRadioButtonId = shipmentsGroup.getCheckedRadioButtonId();
                Intent i = new Intent(Shipment.this, Payment.class);
                // if no adress or shipment metod is selected, display an error message
                if(adressCheckedRadioButtonId == -1 || shipmentCheckedRadioButtonId == -1){
                    Toast toast = Toast.makeText(getApplicationContext(), "Select an adress and a shipment method", duration);
                    toast.show();
                }
                // get selected adress and shipment method and go to Payment screen
                else{
                    switch (adressCheckedRadioButtonId){
                        case R.id.adress1:
                            selectedAdress = adress1.getText().toString();
                            break;
                        case R.id.adress2:
                            selectedAdress = adress2.getText().toString();
                            break;
                        case R.id.adress3:
                            selectedAdress = adress3.getText().toString();
                            break;
                    }
                    switch (shipmentCheckedRadioButtonId){
                        case R.id.shipment1:
                            selectedShipmenttMethod = shipment1.getText().toString();
                            break;
                        case R.id.shipment2:
                            selectedShipmenttMethod = shipment2.getText().toString();
                            break;
                        case R.id.adress3:
                            selectedShipmenttMethod = shipment3.getText().toString();
                            break;
                    }
                    i.putExtra("SELECTED_ADRESS", selectedAdress);
                    i.putExtra("SELECTED_SHIPMENT", selectedShipmenttMethod);
                    Log.i("SELECTED_ADRESS", selectedAdress);
                    Log.i("SELECTED_SHIPMENT", selectedShipmenttMethod);

                    // SET CHECKOUT OPTION TAG
                    Bundle ecommerceBundle = new Bundle();
                    ecommerceBundle.putString("screenName", "Shipping");
                    ecommerceBundle.putString( "eventCategory", "Enhanced Ecommerce" );
                    ecommerceBundle.putString( "eventAction",FirebaseAnalytics.Event.SET_CHECKOUT_OPTION);
                    ecommerceBundle.putString( "eventLabel", selectedShipmenttMethod );
                    ecommerceBundle.putLong( FirebaseAnalytics.Param.CHECKOUT_STEP, 2 );
                    ecommerceBundle.putString( FirebaseAnalytics.Param.CHECKOUT_OPTION, "Colissimo" );
                    mFirebaseAnalytics.logEvent( FirebaseAnalytics.Event.SET_CHECKOUT_OPTION, ecommerceBundle );
                    startActivity(i);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.menu_shipment, menu);
        return true;
    }

    //gère le click sur une action de l'ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_informations:
                Intent i = new Intent(Shipment.this, Informations.class);
                startActivity(i);
                return true;
            case R.id.action_legal:
                Intent j = new Intent(Shipment.this, Legal.class);
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
