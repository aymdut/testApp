package fiftyfive.and_firebase_mcommerce.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fiftyfive.and_firebase_mcommerce.R;
import fiftyfive.and_firebase_mcommerce.models.Product;

/**
 * Created by Francois on 06/08/2017.
 */

public class ProductCartAdapter extends BaseAdapter {


    private final ArrayList mData;

    //tweets est la liste des models à afficher
    public ProductCartAdapter(Map<String, Product> map) {
        mData = new ArrayList();
        mData.addAll(map.entrySet());
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Map.Entry<String, Product> getItem(int position) {
        return (Map.Entry) mData.get(position);
    }

    @Override
    public long getItemId(int i) {

        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final View result;

        if(convertView == null){
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_product,parent, false);
        }
        else{
            result = convertView;
        }

        ProductViewHolder viewHolder = new ProductViewHolder();
        if(viewHolder == null){
            viewHolder = new ProductViewHolder();
            viewHolder.productMiniature = (ImageView) convertView.findViewById(R.id.productMiniature);
            viewHolder.productName = (TextView) convertView.findViewById(R.id.productName);
            viewHolder.productBrand = (TextView) convertView.findViewById(R.id.productBrand);
            viewHolder.productPrice = (TextView) convertView.findViewById(R.id.productPrice);

            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Map.Entry<String, Product> product = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        // Aymen - Remplacement de with() par get()
        Picasso.get().load(product.getValue().getProductMiniature()).into(viewHolder.productMiniature);

        viewHolder.productName.setText(product.getValue().getName());
        viewHolder.productBrand.setText("from " + product.getValue().getBrand());
        viewHolder.productPrice.setText(product.getValue().getPrice().toString() + " €");


        return result;
    }

    private class ProductViewHolder {
        public ImageView productMiniature;
        public TextView productName;
        public TextView productBrand;
        public TextView productPrice;
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src",src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }
}
