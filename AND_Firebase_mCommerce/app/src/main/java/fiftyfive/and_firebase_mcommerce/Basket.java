package fiftyfive.and_firebase_mcommerce;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

import fiftyfive.and_firebase_mcommerce.models.Product;

/**
 * Created by Francois on 06/08/2017.
 */

public class Basket extends AppCompatActivity {


    private FirebaseAuth auth; // Firebase Authentication instance
    private FirebaseAnalytics mFirebaseAnalytics; // Firebase Analytics instance

    //Définition du toast
    Toast toast;
    int duration = Toast.LENGTH_LONG;
    int amount = 600;

    //Product cart lsit static
    private String[] staticProductsList = new String[]{
            "SONY Playstation 3 - 500 Go Slim |  200€",
            "SONY Playstation 4 - 500 Go Slim |  300€",
            "SONY PSP Go |  100€",
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = auth.getCurrentUser();

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        final ListView cartListView = (ListView) findViewById(R.id.listView);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(Basket.this,
                android.R.layout.simple_list_item_1, staticProductsList);
        cartListView.setAdapter(adapter);

        final TextView amountText = (TextView) findViewById(R.id.amount);

        //final HashMap<String, Product> cartList = generateCartList();
        //ProductCartAdapter adapter = new ProductCartAdapter(cartList);
        //cartListView.setAdapter(adapter);

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

        final Button emptyButton = (Button) findViewById(R.id.emptyButton);
        emptyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount = 0;
                amountText.setText("0 €");
                cartListView.setAdapter(null);

                //TODO: A RENDRE DYNAMIQUE
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
                ecommerceBundle.putString("screenName","Detail");
                ecommerceBundle.putString("eventCategory", "Enhanced Ecommerce");
                ecommerceBundle.putString("eventAction", FirebaseAnalytics.Event.REMOVE_FROM_CART);
                ecommerceBundle.putString("eventLabel", "More information in ecommerce reports");
                mFirebaseAnalytics.logEvent( FirebaseAnalytics.Event.REMOVE_FROM_CART, ecommerceBundle );

                toast = Toast.makeText(getApplicationContext(), "Your cart is now empty !", duration);
                toast.show();
            }
        });


        final Button checkoutButton = (Button) findViewById(R.id.checkoutButton);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (amount>0) {
                    //If user is logged ==> Begin checkout
                    //is user is anonymous or numll ==> Login
                    if (user == null) {
                        //Log.i("TAG:", "User non connecté");
                        Intent i = new Intent(Basket.this, Login.class);
                        startActivity(i);
                    } else {
                        if (user.isAnonymous()) {
                            //Log.i("TAG:", "User Anonymous");
                            Intent i = new Intent(Basket.this, Login.class);
                            startActivity(i);
                        } else {
                            //Log.i("TAG:", "User Connecté");
                            //Log.i("UID: ", user.getUid());
                            //TODO : A RENDRE DyNAMIQUE

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
                            ecommerceBundle.putLong( FirebaseAnalytics.Param.CHECKOUT_STEP, 1 );
                            ecommerceBundle.putString("screenName","Basket");
                            ecommerceBundle.putString("userId", user.getUid());
                            ecommerceBundle.putString("pageTopCategory", "Checkout");
                            ecommerceBundle.putString("pageCategory", "Basket");
                            ecommerceBundle.putString("pageType", "Checkout");
                            ecommerceBundle.putString("loginStatus", "Logged");
                            ecommerceBundle.putString("previousScreen", "Login"); // A MODIFIER
                            mFirebaseAnalytics.logEvent( FirebaseAnalytics.Event.BEGIN_CHECKOUT, ecommerceBundle );

                            Intent i = new Intent(Basket.this, Shipment.class);
                            startActivity(i);
                        }
                    }
                }
                else{
                    toast = Toast.makeText(getApplicationContext(), "Can't checkout cause your cart is empty!", duration);
                    toast.show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.menu_basket, menu);
        return true;
    }

    //gère le click sur une action de l'ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_informations:
                Intent i = new Intent(Basket.this, Informations.class);
                startActivity(i);
                return true;
            case R.id.action_legal:
                Intent j = new Intent(Basket.this, Legal.class);
                startActivity(j);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private HashMap<String, Product> generateCartList() {
        //TODO: Load articles form Firebase Real-Time Database
        HashMap<String, Product> cartList = new HashMap<>();
        //cartList.add(new Product(Color.BLUE, "SONY Playstation 4 - 500 Go Slim", "- Plate-forme : PlayStation 4\n" +
        //        "- Edition : Slim 500Go\n" +
        //        "- Des couleurs riches et éclatantes avec les graphismes HDR d’une qualité exceptionnelle."));
        return cartList;
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

