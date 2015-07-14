package com.foursquare.sample.coffeesearch;


import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.foursquare.sample.coffeesearch.Presenter.LocationDataPresenter;

import com.foursquare.sample.coffeesearch.util.LocationTracker;
import com.robotium.solo.Solo;

import com.foursquare.sample.coffeesearch.Activity.CoffeeResultActivity;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ActivityInstrumentationTestCase2<CoffeeResultActivity> {
    private Solo solo;
    public ApplicationTest() {

        super(CoffeeResultActivity.class);
    }
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());

    }
    @Override
    protected void tearDown() throws Exception{
        try {
            solo.finalize();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        getActivity().finish();
        super.tearDown();
    }

    public void testCase1() throws Exception
    {

        View view1 = solo.getView(Spinner.class, 0);
        solo.clickOnView(view1);
        solo.clickOnView(solo.getView(TextView.class, 0));

        boolean actual = solo.isSpinnerTextSelected(0, "Select Option");
        assertEquals("Spinner text not found", true, actual);

    }
    public void testCase2() throws Exception
    {
        View view1 = solo.getView(Spinner.class, 0);
        solo.clickOnView(view1);
       // solo.searchText("Select Option");
        solo.clickOnView(solo.getView(TextView.class, 1));

        boolean actual = solo.isSpinnerTextSelected(0, "Coffee Shop");

       assertEquals("spinner Coffee Shop not selected",true, actual);

    }
    public void testCase3() throws Exception
    {
        View view1 = solo.getView(Spinner.class, 0);
        solo.clickOnView(view1);
        // solo.searchText("Select Option");
        solo.clickOnView(solo.getView(TextView.class, 2));

        boolean actual = solo.isSpinnerTextSelected(0, "Restaurant");

        assertEquals("spinner Restaurant not selected",true, actual);

    }
    public void testCase4() throws Exception
    {
        View view1 = solo.getView(Spinner.class, 0);
        solo.clickOnView(view1);
        // solo.searchText("Select Option");
        solo.clickOnView(solo.getView(TextView.class, 2));

        boolean actual = solo.isSpinnerTextSelected(0, "Coffee Shop");

        assertEquals("spinner Coffee Shop not selected", false, actual);


    }

    public void testCase5() throws Exception
    {
        String url="https://api.foursquare.com/v2/venues/search?client_id=ACAO2JPKM1MXHQJCK45IIFKRFR2ZVL0QASMCBCG5NPJQWF2G&client_secret=YZCKUYJ1WHUV2QICBXUBEILZI1DMPUIDP5SHV043O04FKBHL&v=20150711&ll=40.7463956,-73.9852992&categoryId=4bf58dd8d48988d1e0931735";
        LocationDataPresenter.JSONFeedParserTest(getActivity(), url);
        //Ensure that internet connection is available on device
        boolean expected = true;
        //boolean actual =solo.waitForText(getActivity().getResources().getString(R.string.success));
        boolean actual =solo.waitForLogMessage("JSONParseSuccess");
        assertEquals("success", expected, actual);
    }
    public void testCase6() throws Exception
    {
        //String url="https://api.foursquare.com/v2/venues/search?client_id=ACAO2JPKM1MXHQJCK45IIFKRFR2ZVL0QASMCBCG5NPJQWF2G&client_secret=YZCKUYJ1WHUV2QICBXUBEILZI1DMPUIDP5SHV043O04FKBHL&v=20150711&ll=40.7463956,-73.9852992&categoryId=4bf58dd8d48988d1e0931735";
        LocationTracker.LocationTest(this.getActivity());
        //Ensure that internet connection is available on device
        boolean expected = true;

        // boolean actual =solo.waitForText(getActivity().getResources().getString(R.string.success));
        boolean actual =solo.waitForLogMessage("canGetLocationSuccess");
        assertEquals("success", expected, actual);
    }


    //Turn off WIFI and GPS service for these cases to pass
    /*
    public void testCase7() throws Exception
    {
        String url="https://api.foursquare.com/v2/venues/search?client_id=ACAO2JPKM1MXHQJCK45IIFKRFR2ZVL0QASMCBCG5NPJQWF2G&client_secret=YZCKUYJ1WHUV2QICBXUBEILZI1DMPUIDP5SHV043O04FKBHL&v=20150711&ll=40.7463956,-73.9852992&categoryId=4bf58dd8d48988d1e0931735";
        LocationDataPresenter.JSONFeedParserTest(getActivity(), url);
        //Ensure that internet connection is available on device
        boolean expected = true;
        //boolean actual =solo.waitForText(getActivity().getResources().getString(R.string.success));
        boolean actual =solo.waitForLogMessage("JSONParsefailure");
        assertEquals("success", expected, actual);
    }
    public void testCase8() throws Exception
    {
        //String url="https://api.foursquare.com/v2/venues/search?client_id=ACAO2JPKM1MXHQJCK45IIFKRFR2ZVL0QASMCBCG5NPJQWF2G&client_secret=YZCKUYJ1WHUV2QICBXUBEILZI1DMPUIDP5SHV043O04FKBHL&v=20150711&ll=40.7463956,-73.9852992&categoryId=4bf58dd8d48988d1e0931735";
        LocationTracker.LocationTest(this.getActivity());
        //Ensure that internet connection is available on device
        boolean expected = true;

        // boolean actual =solo.waitForText(getActivity().getResources().getString(R.string.success));
        boolean actual =solo.waitForLogMessage("canGetLocationfailure");
        assertEquals("success", expected, actual);
    } */

}