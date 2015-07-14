package com.foursquare.sample.coffeesearch.Presenter;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;

import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.foursquare.sample.coffeesearch.R;
import com.foursquare.sample.coffeesearch.model.LocationResults;
import com.foursquare.sample.coffeesearch.util.LocationTracker;
import com.foursquare.sample.coffeesearch.util.VolleyHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by keyur on 13-07-2015.
 * This Class handles all the presentation logic
 * It interacts with Data Model, queries for JSON data
 * and also is responsible for parsing the data.
 */
public class LocationDataPresenter  {
    //Interface for communicating with UI Activity
    protected onJsonParseCompleted jParserListner;
    //reference of context
    Context mContext;
    //foursquare URL to fetch data
    String url;
   // GPS related variable
    private  Location CurrentLocation;
    //object of our GPS service which will update the location
    private LocationTracker mLocationTracker;
    VolleyHelper objVolley;
    //for logs only
    private static final String TAG = LocationDataPresenter.class.getSimpleName();
    /*
    * This our default constructor to init presenter object.
    * @param Context
    * @param String
    * @param onJsonParseCompleted
    * @return LocationDataPresenter
    */
    public LocationDataPresenter(Context context,String url, onJsonParseCompleted listner)
    {
        super();
        this.mContext=context;
        this.url=url;
        //this listner will perform call-back to our UI Activity
        this.jParserListner=listner;
        //start the location service
        mLocationTracker=new LocationTracker(mContext,LocationChanged);
        objVolley= VolleyHelper.getInstance(mContext);


    }
    /*
    * We add the latitude and longitude of our current location
    * This function will be called everytime our location changes
    * @param String
    * @return String
    */
    public String AddLatLong(String url)
    {
        StringBuilder finalURL=new StringBuilder();
        finalURL.append(url);
        finalURL.append("&ll=");
        finalURL.append(CurrentLocation.getLatitude());
        finalURL.append(",");
        finalURL.append(CurrentLocation.getLongitude());
        return finalURL.toString();
    }
    /*
    * This function is the entry point for core of the application
    * It will request data using Volley library and parse the response
    * JSON with gson library.
    * @param void
    * @return void
    */
    public void loadJson()
    {
        String FourSquareURL=new String();
        //Create Instance of JsonObjectRequest
        // This object will make asynchronous http call to fetch data
        // It will register callback function onResponse for the data
        // and will also provide callback function for error handling

        //First check if network connection is available or not
        if(VolleyHelper.isNetworkAvailable(mContext)) {
            //do we have access is GPS ??
            if (mLocationTracker.canGetLocation()) {
                //First get our current location
                CurrentLocation = mLocationTracker.getLocation();
               // Log.d(TAG, String.valueOf(CurrentLocation.getLatitude()));
              //  Log.d(TAG, String.valueOf(CurrentLocation.getLongitude()));
                //We will add latitude and longitude from our current location
                FourSquareURL = AddLatLong(url);
                //JsonObject request using volley
                JsonObjectRequest request = new JsonObjectRequest(FourSquareURL, null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                parseJSONFromString(response.toString());
                               // Log.d(TAG, response.toString());
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
                );
                //Create Instance of VolleyHelper Class
                //VolleyHelper class has singleton implementation so only 1 object
                //will be used throughout the life cycle of application.
                //All the requests should be added in the request queue
                 request.setTag("CoffeeShops");
                objVolley.addToRequestQueue(request);
            } else {
                //GPS is not turned on so notify the user
                //to turn of GPS first
                mLocationTracker.showSettingsAlert();
            }
        }
        else
        //Internet is not available
        //Notify the user to turn on data connection
            VolleyHelper.showInternetSettingAlert(mContext);

    }
    /*
     * This function will parse the JSON data into the logical Class data.
     * @param String
     * @return void
     */
    private void parseJSONFromString(final String JSONdata) {
        //Object to hold final arraylist
        ArrayList<LocationResults> ParserData = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(JSONdata);
            //check if we got correct response
            if (jsonObject.has("response"))
                //check if venues tag is present
                if (jsonObject.getJSONObject("response").has("venues")) {
                    //retrive the array of venues
                    JSONArray jsonArray = jsonObject.getJSONObject("response").getJSONArray("venues");
                    //we need to loop through each venues node to parse data
                    for (int i = 0; i < jsonArray.length(); i++) {
                        //retrive each venue details
                        JSONObject jsonVenues = (JSONObject) jsonArray.get(i);
                        LocationResults SingleLocationRow = new LocationResults();
                        //check if name of cafe available
                        if (jsonArray.getJSONObject(i).has("name")) {
                            SingleLocationRow.setShopName(jsonArray.getJSONObject(i).getString("name"));
                         //   Log.d(TAG, SingleLocationRow.getShopName());
                        }
                        //get the location array to parse address
                        //we can also query formattedaddress
                        //but from the data we can see it is not mandatory
                        //filed and is received empty also
                        JSONObject jlocation = jsonVenues.getJSONObject("location");
                        //error handling to ensure we do not
                        //get runtime error
                        if (jlocation!=null) {
                            //local stringbuilder as we will
                            //keep adding the address details
                            StringBuilder strAddres=new StringBuilder();
                            if (jlocation.has("address")) {
                                strAddres.append(jlocation.get("address"));
                                strAddres.append(" ");
                            }

                            if (jlocation.has("crossStreet")) {
                                strAddres.append(jlocation.get("crossStreet"));
                                strAddres.append(" ");

                            }
                            if (jlocation.has("city")) {
                                strAddres.append(jlocation.get("city"));
                                strAddres.append(" ");

                            }
                            if (jlocation.has("state")) {
                                strAddres.append(jlocation.get("state"));
                                strAddres.append(" ");

                            }
                            if (jlocation.has("country")) {
                                strAddres.append(jlocation.get("country"));
                                strAddres.append(" ");

                            }

                            if (jlocation.has("postalCode")) {
                                strAddres.append(jlocation.get("postalCode"));

                            }
                            //set the address of venue
                            SingleLocationRow.setAddress(strAddres.toString());
                           // Log.d(TAG, SingleLocationRow.getAddress());
                           //retrive latitude and longitude details
                            if (jlocation.has("lat")) {
                                SingleLocationRow.setLatitude(jlocation.getDouble("lat"));
                             //   Log.d(TAG, String.valueOf(SingleLocationRow.getLatitude()));
                            }
                            if (jlocation.has("lng")) {
                                SingleLocationRow.setLongitude(jlocation.getDouble("lng"));
                              //  Log.d(TAG, String.valueOf(SingleLocationRow.getLongitude()));

                            }
                            //retrive the distance information
                            if (jlocation.has("distance")) {
                                SingleLocationRow.setDistance(jlocation.getInt("distance"));
                            //    Log.d(TAG, String.valueOf(SingleLocationRow.getDistance()));

                            }
                        }

                        //add each single object to arraylist
                        ParserData.add(SingleLocationRow);
                    }

                }


        } catch (Exception e) {

            e.printStackTrace();
        }
        //check if we received any data
        if(ParserData == null) {
            //notify failure
            jParserListner.onParseFailure();

        }
        else {
            //as per the requirements, we need to sort
            //the location based on distance
            //we have defined our custom compartor to
            //support sorting
            Collections.sort(ParserData,LocationResults.CompareDistance);
            //notify success with sorted arraylist
            jParserListner.onParseSuccess(ParserData);

        }

    }
    /*
     * This interface provides a call-back mechanism for Activity class.
     * It has two callback functions
     * 1) onParseSuccess returns Arraylist with parsed data
     * 2) onParseFailure indicates error in parsing the data.
     */
    public interface onJsonParseCompleted {
         void onParseSuccess(List<LocationResults> Data);
         void onParseFailure();

    }
    /*
     * This is callback interface to get location updates.
     * It has 1 callback functions
     * 1) getNewLocation returns the GPS current location
     * from our GPS service running in background
     */
    private LocationTracker.onPositionChanged LocationChanged = new LocationTracker.onPositionChanged(){
        @Override
       public  void getNewLocation(Location location)
        {
            CurrentLocation=location;
              loadJson();
        }

    };
        /*
         * This function is used for Unit Testing only.
         * Using Robotium or any other such tools, we can verify if
         * JSON parsing is working correctly or not
         * Refer test case no. 5
         * It has two callback functions
         * 1) onParseSuccess returns Arraylist with parsed data
         * 2) onParseFailure indicates error in parsing the data.
         */
    public static void JSONFeedParserTest(final Context context, String url) {
        final LocationDataPresenter parser = new LocationDataPresenter(
                context,url, new onJsonParseCompleted() {

            @Override
            public void onParseSuccess(List<LocationResults>  feed) {
                Log.d("JsonParseData", "JSONParseSuccess " +
                        feed.size() + " records.");
                Toast.makeText(context, R.string.success , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onParseFailure() {
                Log.d("JsonParseData", "JSONParsefailure");
                Toast.makeText(context, R.string.fail , Toast.LENGTH_SHORT).show();
            }

        });
        parser.loadJson();
    }


}
