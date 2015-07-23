package com.foursquare.sample.coffeesearch.Activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import com.foursquare.sample.coffeesearch.R;
import com.foursquare.sample.coffeesearch.model.LocationResults;
import com.foursquare.sample.coffeesearch.adapter.SearchAdapter;
import com.foursquare.sample.coffeesearch.Presenter.LocationDataPresenter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by keyur on 13-07-2015.
 */

public class CoffeeResultActivity extends Activity {


     //Hardcoded stuff for foursquare API
    private static final String url="https://api.foursquare.com/v2/venues/search?";
    final String CLIENT_ID = "";
    final String CLIENT_SECRET = "";
    final String CATEGORY_ID ="4bf58dd8d48988d1e0931735";

    //UI elements for activity
    private Spinner Categorylist;
    private ListView listView;

    //Arrylist  which will hold the data to be displayed
    private List<LocationResults> ShopData = new ArrayList<>();

    //We have defined our custom adapter based on our JSON data
    private SearchAdapter adapter;
    //Following MVP architecture, the Presenter for our Activity
    private LocationDataPresenter jData;
    //for logs only
    private static final String TAG = CoffeeResultActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_list);
        LayoutInflater inflater = getLayoutInflater();

        //add header to list
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.searchresult_header, listView,
               false);
        listView = (ListView) findViewById(R.id.list_search);
        listView.addHeaderView(header,null,false);
        //set adapter for list
        adapter=new SearchAdapter(this,ShopData);
        listView.setAdapter(adapter);
        //init spinner/dropdown box
        //currently we are only querying data for coffee but going ahead we can
        //add more options
        Categorylist=(Spinner)findViewById(R.id.spinner);
        Categorylist.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {

                       // int position = Categorylist.getSelectedItemPosition();
                        //currently we are only querying data for coffee but going ahead we can
                        //add more options
                        loadFourSquareData();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }

                }
        );
        //set onclick event for list items
        //on selection this will open navigation with google map from
        //current location.

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                LocationResults SelectedShop = (LocationResults) adapter.getItem(position - 1);
                SelectedShop.setAddress(SelectedShop.getAddress().replaceAll("\\r\\n|\\r|\\n", " "));
                // Create a Uri from an intent string. Use the result to create an Intent.
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + SelectedShop.getAddress());
                // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                // Make the Intent explicit by setting the Google Maps package
                mapIntent.setPackage("com.google.android.apps.maps");
                // Attempt to start an activity that can handle the Intent
                startActivity(mapIntent);
            }
        });

    }
    /*
     * This function will append all the data required to construct our
     * request URL for foursquare api
     * @param  void
     * @return String
     */
    private String getURL()
    {
        StringBuilder clientURL=new StringBuilder();
        clientURL.append(url);
        clientURL.append("client_id=");
        clientURL.append(CLIENT_ID);
        clientURL.append("&client_secret=");
        clientURL.append(CLIENT_SECRET);
        clientURL.append("&categoryId=");
        clientURL.append(CATEGORY_ID);
        clientURL.append("&v=20150711");
        return clientURL.toString();
    }
    /*
     * This function is the entry point for our applcation to start fetching data
     * @param  void
     * @return void
     */
    private void loadFourSquareData()
    {
        //check if list already has data
        //if so clear old data
        if(ShopData!=null) {
            ShopData.clear();
            adapter.notifyDataSetChanged();
        }
        //create object of our Presenter class to handle
        //all BI and data access
        jData=new LocationDataPresenter(this,getURL(),parserListner);
        //start the JSON query to get data
        jData.loadJson();
    }

    /*
     *
     * Callback Interface from our presenter class
     * this function acts as a communication link
     * between presenter calls and our main activity.
     */


    private LocationDataPresenter.onJsonParseCompleted parserListner= new LocationDataPresenter.onJsonParseCompleted(){

        @Override
        public void onParseSuccess(List<LocationResults> feed) {
          //Log.d(TAG, "onParsesuccess size" + feed.size());


            //First clear the old data
            ShopData.clear();

            //Copy the response
            ShopData.addAll(feed);

            //Notify data change to update ListView on MainAcvity
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onParseFailure() {

            //Display Error Toast
       //     Log.d(TAG, "onParseFailure");
            Toast.makeText(getApplicationContext(), R.string.connection_error, Toast.LENGTH_SHORT).show();
        }
    };



}
