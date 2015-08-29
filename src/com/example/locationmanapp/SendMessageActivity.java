package com.example.locationmanapp;

//import com.example.sendsms.R;
import com.example.locationmanapp.R;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//the activity that actually does the work of sending the text message
public class SendMessageActivity extends Activity {

	//edit text that would take in the number at which the sms is to be sent
	private EditText messageNumber1;
	//strings to store the latitude and longitude data
	private String lat;
	private String longit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//get the intent that started the current activity 
		Intent intent = getIntent();
		//extract the latitude from the intent using the key
		lat = intent.getStringExtra(MainActivity.LATITUDE_DATA);
		//extract the longitude from the intent using the key
		longit = intent.getStringExtra(MainActivity.LONGITUDE_DATA);
		setContentView(R.layout.activity_send_message);

	}

//the function that actually sends the message
	public void sndMsg(View v)
	{
		//extract the phone number from the edit text
		messageNumber1=(EditText)findViewById(R.id.messageNumber);
		String _messageNumber=messageNumber1.getText().toString();
		String messageText = lat+ " "+longit;
		String sent = "SMS SENT SUCCESSFULLY";

		//CRreating a pening intent which would indicate if the sms send was successful
		PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
				new Intent(sent), 0);

		//register the broadcast receiver 
		registerReceiver(new BroadcastReceiver(){
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				//check if send message was successful
				if(getResultCode() == Activity.RESULT_OK)
				{
					Toast.makeText(getBaseContext(), "SMS sent",
							Toast.LENGTH_SHORT).show();
				}
				//check if sms send was not successful
				else
				{
					Toast.makeText(getBaseContext(), "SMS could not sent",
							Toast.LENGTH_SHORT).show();
				}
			}
		}, new IntentFilter(sent));

		//create the sms manager object and pass in the required parameters
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(_messageNumber, null, messageText, sentPI, null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.send_message, menu);
		return true;
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
