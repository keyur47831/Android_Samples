package com.foursquare.sample.coffeesearch.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.foursquare.sample.coffeesearch.R;
import com.foursquare.sample.coffeesearch.model.LocationResults;

import java.util.List;

/**
 * Created by keyur on 13-07-2015.
 * This class is our custom Adapter
 * derived from BaseAdapter Class
 */
public class SearchAdapter extends BaseAdapter {
    //reference of context from main activity
    private Context mContext;
    //Data layer mapping
    private List<LocationResults> LocationData;
    //required for sending notifications
    private LayoutInflater inflater;
    //for logs only
    private static final String TAG = SearchAdapter.class.getSimpleName();
    /*
     * This our default constructor to init Adapter object.
     * @param  Context
     * @param  List<LocationResults>
     * @return SearchAdapter
     */
    public SearchAdapter(Context context,List<LocationResults> Data)
    {
        this.mContext=context;
        this.LocationData=Data;

    }
    @Override
    public int getCount() {
        //null check
        if(LocationData!=null)
            return LocationData.size();
        else
            return 0;
    }
    @Override
    public Object getItem(int arg0) {
        //null check
        if(LocationData!=null)
            return LocationData.get(arg0);
        else
            return null;
    }
    @Override
    public long getItemId(int arg0) {

        return arg0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup Parent) {
        try {
            //error checking
            if (inflater == null)
                inflater = (LayoutInflater) mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //error checking
            if (convertView == null)
                convertView = inflater.inflate(R.layout.list_row, null);

            //get the position and init a local variable
            LocationResults LocationDisplayItem=LocationData.get(position);
            //In order to fill data, we need to init all UI
            //elements of listview
            TextView tv=(TextView) convertView.findViewById(R.id.ShopName);
            tv.setText(LocationDisplayItem.getShopName());

            tv=(TextView) convertView.findViewById(R.id.Address);
            tv.setText(LocationDisplayItem.getAddress());

            tv=(TextView) convertView.findViewById(R.id.Distance);
            //We want to display the data in Kilometers so converting on the fly
            //we can give UI option as well to select the Unit
            //but with current requirement, we will hard-code this logic
            tv.setText("Distance :"+String.valueOf(LocationDisplayItem.getDistance()) + " KM");
            //tv.setText("Distance :"+String.valueOf(Math.round(LocationDisplayItem.getDistance()*0.001)) + " KM");
        }
        catch(Exception Ex)
        {
    //        Log.d(TAG, Ex.getMessage());
        }
   return convertView;
    }
}
