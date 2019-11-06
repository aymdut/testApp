package fiftyfive.and_firebase_mcommerce.models;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import fiftyfive.and_firebase_mcommerce.Utils;

/**
 * Created by Francois on 31/08/2017.
 */

public class Cart {

    static String cartId; //uid
    static String currency;
    static Integer nbOfArticles;
    private static double subTotal;

    public Cart (){

        // Constructor to get cart
    }

    public Cart(String currency, Integer nbOfArticles, double subTotal) {
        //Constructor to create new cart entry in the DB
        this.currency = currency;
        this.nbOfArticles = nbOfArticles;
        this.subTotal = subTotal;
    }


    public void decrementNbOfArticles(){
        this.nbOfArticles = nbOfArticles - 1;
    }

    public void incrementNbOfArticles(){
        this.nbOfArticles = nbOfArticles + 1;
    }

    public static void updateSubTotal(double value){
        subTotal = subTotal + value;
    }



    public static void addToCart(final String uid, final String sku, final Double value){

        //Check if cart already exists
        final DatabaseReference cartNode = Utils.getDatabaseRoot().child("carts").child(uid);
        final ValueEventListener cartEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    Log.i("TAG", "Cart does not exist!");
                    //createCart
                    createNewCart(uid);

                } else {
                    Log.i("TAG", "Cart exists!");
                    //Check if product is not already in the cart
                    if (!dataSnapshot.child("articles").child(sku).exists()){
                        //product is not in the cart, we will create it
                        //create the product in the cart and update the subTotal
                        //TODO : Create the product in the cart
                        updateSubTotal(value);

                    }
                    else{
                        //Product is in the cart. Increment quantity and upate the subTOtal
                        Integer previousQuantity = Integer.parseInt(dataSnapshot.child("articles").child(sku).child("quantity").getValue().toString());
                        cartNode.child("articles").child(sku).child("quantity").setValue((previousQuantity + 1));
                        updateSubTotal(value);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        cartNode.addListenerForSingleValueEvent(cartEventListener);
    }

    public static void createNewCart (String uid){
        Cart cart = new Cart("EUR", 0, 0.0);
        DatabaseReference cartsNode = Utils.getDatabaseRoot().child("carts").getRef();
        cartsNode.setValue(uid);
        //DatabaseReference uidNode = cartsNode.child(uid);
        //uidNode.setValue(cart);
        Log.i("Cart: ", "Cart of user "+ uid +" created in DB");
    }


    // Check if product exists in the cart
    public static boolean checkIfProductExistInTheCart(String uid, String sku){
        DatabaseReference productNode = Utils.getDatabaseRoot().child("carts").child(uid).child("Articles").child(sku).getRef();
        Log.i("productNode: ", productNode.toString());
        if(productNode.toString() == null){
            Log.i("Info : ", "Le produit "+sku+" n'existe pas dans le panier de "+uid+".");
            return false;
        }
        else{
            Log.i("Info : ", "Le produit "+sku+" existe dans le panier de "+uid+".");
            return true;
        }
    }

    // Create the product in the cart
    public static void createProductInCart(String uid, String sku){
        Product newProduct = new Product ();
        DatabaseReference articlesNode = Utils.getDatabaseRoot().child("carts").child(uid).child("Articles").getRef();
        articlesNode.setValue(newProduct);
        DatabaseReference productNode = Utils.getDatabaseRoot().child("carts").child(uid).child("Articles").child(sku).getRef();
        //Map<String, Object> quantityValue = this.toMap();
        //Map<String, Object> childUpdates = new HashMap<>();

        //childUpdates.
        //productNode.updateChildren()
        //productNode.setValue()
        //TODO : A FAIRE
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("quantity", "1");

        return result;
    }


}
