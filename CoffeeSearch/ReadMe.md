CoffeeSearch Application

Description :
This is a sample application to test foursquare venues api.
In order to run the application, please generate a new API from below url
https://foursquare.com/developers/register

Supported API : 
mini : 19
target: 22 

The application is designed using MVP architecture.

Model : LocationResults Class

This class models our data object. Currently I am displaying only Shop name,
Address and Distance from current location.
Also we capture the location information so that we can use it for navigation.

View : CoffeeResultActivity Class

This activity is the UI interaction layer for application.
It has a dropdown box to select sample category.
As of now, we only query for Coffee shops irrespective of the category selected.
Going ahead, we can query the category and flush the dropdown list accordingly.

Presenter : LocationDataPresenter Class

This class acts as a presenter for UI layer.
It has our core BI layer logic.
For getting location, it will interact with :

LocationTracker.java---Service to fetch current location and changes there on.
VolleyHelper.java   ---Helper Class to create Async HTTP request.

It will also update the data in adapter.

SearchAdapter.java  -- Our Custom adapter to display the shop details.
This class extends BaseAdapter and implements/overrides the required functions.

External Libraries :

1) Robotium : UI testing library.
This is open source library used for UI testing through Instrumentation testing.
We can create UI test scenarios to check the basic functionality of our application.
For more details refer :

https://code.google.com/p/robotium/

2) gson : To support JSON parsing.
This is open source library provided by google development team for handling JSON parsing.

It supports direct parsing in JSONArray and JSONObejct.

For more details refer :
https://github.com/google/gson

3) Volley : Asynchronous HTTP request library.
Volley is recommended library to create Asynchronous HTTP requests.
It can make 4 parallel request and also has a dedicated component for loading
Network Images. 
Instead of Volley we can also write the code from scratch with Loader class and HTTPConnection APIs.

For more details on Volley refer :
https://developer.android.com/training/volley/index.html


Testing :

In the project there are sample Unit test cases using Robotium.


ApplicationTest class contains few unit test cases.

Pre-condition : Please enable internet connection and location service before running test cases 1-6

1)testcase1 : checks the basic UI dropdown box populated data.
2)testcase2 : check the basic selection for dropdown box.
3)testcase3 : validates the item selected in dropdown box.
4)testcase4 : validates the item selected in dropdown box with negative testing.
5)testcase5 : This test case will check the JSON parsing feature.
6)testcase6 : This test case will check the Location Service wrapper.
7)testcase7 : Negative testing for JSON parsing.
8)testcase8 : Negative testing for Location Service Wrapper.

To test on device, execute below command from terminal

./gradlew connectedInstrumentTest

Verified all the test cases and appliation with LG G2 device with Lolllypop OS.


