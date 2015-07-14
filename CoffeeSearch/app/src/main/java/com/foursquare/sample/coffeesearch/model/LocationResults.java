package com.foursquare.sample.coffeesearch.model;

import java.util.Comparator;

/**
 * Created by keyur on 13-07-2015.
 * This class is our Data Model Class
 * Currently we are utilizing only few items
 * so modeled only required items.
 */
public class LocationResults  {

    private int Distance;
    private String ShopName;
    private String Address;
    private double Latitude;
    private double Longitude;
    /*
     * This our default constructor to init data object.
     * @param void
     * @return LocationResults
     */

    public LocationResults()
    {
        super();
        this.Distance=0;
        this.ShopName="";
        this.Address="";
        this.Latitude=0;
        this.Longitude=0;


    }
    /*
     * Below are our get and set properties.
     * currently created them as public so that we can
     * create/modify objects on the fly
     */
    public int getDistance()
    {
        return this.Distance;
    }
    public void setDistance(int value)
    {
        this.Distance=value;
    }
    public String getAddress()
    {
        return this.Address;
    }
    public void setAddress(String value)
    {
        this.Address=value;
    }
    public double getLatitude()
    {
        return  this.Latitude;
    }
    public void setLatitude(double value)
    {
        this.Latitude=value;
    }
    public double getLongitude()
    {
        return  this.Longitude;
    }
    public void setLongitude(double value)
    {
        this.Longitude=value;
    }

    public String getShopName()
    {
        return this.ShopName;
    }
    public void setShopName(String value)
    {
        this.ShopName=value;
    }
    /*
      * We create a Comparator function to compare our class object.
      * currently we are sorting only by distance but going ahead
      * if sorting is required by other properties, we can define here
      * @param LocationResults
      * @param LocationResults
      * @return int
      */

    public static Comparator<LocationResults> CompareDistance = new Comparator<LocationResults>() {

        public int compare(LocationResults s1, LocationResults s2) {

            int Distance1 = s1.getDistance();
            int Distance2 = s2.getDistance();
	        /*For ascending order*/
            return Distance1-Distance2;


            /*For descending order*/
           // return Distance2-Distance1;
        }};
}