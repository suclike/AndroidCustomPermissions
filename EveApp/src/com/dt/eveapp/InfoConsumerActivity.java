package com.dt.eveapp;

import com.dt.eveapp.R;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.dt.adamapp.InfoProviderRemote;

public class InfoConsumerActivity extends Activity {

	InfoProviderRemote providerRemote;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info_consumer);
		 Intent serviceIntent=new Intent();
	        serviceIntent.setClassName("com.dt.adamapp", 
	        		"com.dt.adamapp.InfoProviderService");
	        boolean ok=bindService(serviceIntent, mServiceConnection,Context.BIND_AUTO_CREATE);
	        Log.v("ok", String.valueOf(ok));
	}
	  private ServiceConnection mServiceConnection=new ServiceConnection() {

			@Override
			public void onServiceDisconnected(ComponentName name) {

			}

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				// get instance of the aidl binder
				providerRemote = InfoProviderRemote.Stub.asInterface(service);
				try {
					String message=providerRemote.getInfo("testString");
					Log.v("message", message);
					
					int duration = Toast.LENGTH_LONG;

					
					Toast toast = Toast.makeText(getApplicationContext(), message, duration);
					toast.show();
				} catch (RemoteException e) {
					Log.e("RemoteException", e.toString());
				}

			}
		};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.info_consumer, menu);
		return true;
	}

}
