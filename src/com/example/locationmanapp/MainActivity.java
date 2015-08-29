package com.example.locationmanapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements LocationListener  {

	//location manager object to extract location
	LocationManager lm;
	//constants used as keys in the intents that are sent 
	public final static String LONGITUDE_DATA = "com.example.locationmanapp.LONGITUDE";
	public final static String LATITUDE_DATA = "com.example.locationmanapp.LATITUDE";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//get a reference to the location services to initialize the location manager object
		lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		// Creating an empty criteria object
		Criteria criteria = new Criteria();

		// Get name of the provider in this case which would be gps
		String provider = lm.getBestProvider(criteria, false);

		if(provider!=null && !provider.equals(""))
		{ 
			// Get the location
			Location location = lm.getLastKnownLocation(provider);
			//set frequency of location update parameters
			lm.requestLocationUpdates(provider, 20000, 1, this);

			if(location!=null)
				onLocationChanged(location);
			else
			{
				TextView tvLongitude = (TextView)findViewById(R.id.curr_longitude);
				tvLongitude.setText("Location can't be retrieved");
				Toast.makeText(getBaseContext(), "Location can't be retrieved", Toast.LENGTH_SHORT).show();
			}

		}
		else
		{
			//create a toast indicating error.
			Toast.makeText(getBaseContext(), "OOPS something went wrong", Toast.LENGTH_SHORT).show();

		}
	}
	
	//function that is called on the click of the send message button 
	public void sendLocation(View view) 
	{
		//create an intent object t start a new activity
		Intent intent = new Intent(this, SendMessageActivity.class);
		// Getting reference to TextView curr_longitude
		TextView tvLongitude = (TextView)findViewById(R.id.curr_longitude);
		//get the longitude text
		String longit=tvLongitude.getText().toString();
		// Getting reference to TextView curr_latitude
		TextView tvLatitude = (TextView)findViewById(R.id.curr_latitude);
		//get the latitude text
		String latit=tvLatitude.getText().toString();
		//store the key value pair
		intent.putExtra(LONGITUDE_DATA, longit);
		intent.putExtra(LATITUDE_DATA, latit);
		//start a new activity that would trigger a change in the screen
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onLocationChanged(Location location) 
	{
		// Get TextView curr_longitude
		TextView tvLongitude = (TextView)findViewById(R.id.curr_longitude);

		// Getting TextView curr_latitude
		TextView tvLatitude = (TextView)findViewById(R.id.curr_latitude);

		// Setting Current Longitude in the TextView
		tvLongitude.setText("Longitude:" + location.getLongitude());

		// Setting Current Latitude in the TextView
		tvLatitude.setText("Latitude:" + location.getLatitude() );
	}

	@Override
	public void onProviderDisabled(String provider) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderEnabled(String provider) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
