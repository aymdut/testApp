package fiftyfive.and_firebase_mcommerce;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Francois on 29/08/2017.
 */

public class Utils {

    static DatabaseReference mDatabase;

    /*
     * isNetworkAvailable : checking network availability
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    /*
     * isOnline : checking internet access
     */
    public static Boolean isOnline() {
        try {
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = p1.waitFor();
            boolean reachable = (returnVal==0);
            return reachable;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public static void initDatabase(){
        // [START initialize_database_ref]
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        // [END initialize_database_ref]

    }

    public static DatabaseReference getDatabase(){
       return mDatabase;

    }

    public static DatabaseReference getDatabaseRoot(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        return mDatabase;
    }


    public static DatabaseReference getDatabaseNode(String nodeName){
        mDatabase = FirebaseDatabase.getInstance().getReference().child(nodeName);
        return mDatabase;
    }

    //public


}
