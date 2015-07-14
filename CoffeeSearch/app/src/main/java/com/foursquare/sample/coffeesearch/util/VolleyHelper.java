package com.foursquare.sample.coffeesearch.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;
import com.foursquare.sample.coffeesearch.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/*
 * Created by keyur on 13-07-2015.
 * VolleyHelper is Wrapper Class for Volley Implementation.
 * Volley is standard library provided by Google Developer Team.
 *
 * This class has Singleton implementation as per the guidelines.
 * More details can be obtained from below link
 * {@link https://developer.android.com/training/volley/index.html}
 *
 */
public class VolleyHelper {
    private static VolleyHelper INSTANCE;
    private RequestQueue requestQueue;
    private Context context;

    private static final String TAG = VolleyHelper.class.getSimpleName();
    /*Make the constructor private to implement
    * Singleton behaviour
    */
    private VolleyHelper(Context context){
        this.context = context;
        requestQueue = getRequestQueue();
    }
    /*This method is exposed as public method
    * So we ensure that only 1 instance is available
    */
    public static synchronized VolleyHelper getInstance(Context context){

            if(INSTANCE == null) {
                INSTANCE = new VolleyHelper(context);
             //   Log.d(TAG, "Volley init");
            }
            return INSTANCE;
    }
    /* Notify the user that Internet
    * connection is not available
    */
    public static void showInternetSettingAlert(final Context context) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle(context.getResources().getString(R.string.internet_connection));

        // Setting Dialog Message
        alertDialog.setMessage(context.getResources().getString(R.string.no_internet));

        // On pressing Settings button
        alertDialog.setPositiveButton(context.getResources().getString(R.string.action_settings), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                context.startActivity(intent);
            }
        });
        // on pressing cancel button
        alertDialog.setNegativeButton(context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
    /*
     *
     * Get the request queue to add request
     * @param  void
     * @return RequestQueue
     */
    public RequestQueue getRequestQueue(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }
    /*
     * Generic function to add request
     * @param  Request<T>
     * @return void
     */
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
    /*
     *
     * This function will check the network connectivity status
     * @param  void
     * @return boolean
     */
    public static boolean isNetworkAvailable(final Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    /**
     * Cancels all pending requests by the specified TAG, it is important
     * to specify a TAG so that the pending/ongoing requests can be cancelled.
     *
     * @param tag
     */
    public void cancelPendingRequests(Object tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }
}
