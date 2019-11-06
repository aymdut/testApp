package fiftyfive.and_firebase_mcommerce.models;

import android.graphics.Color;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import fiftyfive.and_firebase_mcommerce.Utils;

import static android.R.attr.category;
import static fiftyfive.and_firebase_mcommerce.R.id.productMiniature;
import static fiftyfive.and_firebase_mcommerce.models.Cart.currency;

/**
 * Created by Francois on 28/07/2017.
 */

@IgnoreExtraProperties
public class Product {

    private String mProductMiniature;
    private String mName;
    private String mVariant;
    private String mBrand;
    private String mCategory;
    private ArrayList<String> mDesc;
    private Double mPrice;
    private Boolean mIsDiscounted;
    private Double mOriginalPrice;
    private String mCurrency;
    private ArrayList<String> mProductPics;


    public Product(){
        // Default constructor required for calls to DataSnapshot.getValue(Product.class)

    }

    public Product(String productMiniature, String name, String variant, String brand,
                   String category, ArrayList<String> desc, Double price, Boolean isDiscounted,
                   Double originalPrice, String currency, ArrayList<String> productPics) {

        this.mProductMiniature = productMiniature;
        this.mName = name;
        this.mVariant = variant;
        this.mBrand = brand;
        this.mCategory = category;
        this.mDesc = desc;
        this.mPrice = price;
        this.mIsDiscounted = isDiscounted;
        this.mOriginalPrice = originalPrice;
        this.mCurrency = currency;
        this.mProductPics = productPics;
    }

    



    //...getters

    public String getProductMiniature(){
        return mProductMiniature;
    }

    public String getName(){
        return mName;
    }

    public String getVariant(){
        return mVariant;
    }

    public String getBrand(){
        return mBrand;
    }

    public String getCategory() {
        return mCategory;
    }

    public ArrayList<String> getDesc(){
        return mDesc;
    }

    public Double getPrice(){
        return mPrice;
    }

    public Boolean getIsDiscounted(){
        return mIsDiscounted;
    }

    public Double getOriginalPrice(){
        return mOriginalPrice;
    }

    public String getCurrency(){
        return mCurrency;
    }

    public ArrayList<String> getProductPics(){
        return mProductPics;
    }

    //...setters

    public void setProductMiniature(String productMiniature){
        mProductMiniature = productMiniature;
    }

    public void setName(String name){
        mName = name;
    }

    public void setBrand(String brand){
        mBrand = brand;
    }

    public void setPrice(Double price){
        mPrice = price;
    }

    //TODO: Compléter Setters

    /*public static Product getProductFromSku(String sku){
        final Product product = new Product();
        DatabaseReference productNode = Utils.getDatabaseRoot().child("products").child(sku).getRef();
        productNode.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                product.setColor(Color.BLUE);
                product.setName(dataSnapshot.child("name").toString());
                product.setDesc("blabla");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return product;
    }*/
}
