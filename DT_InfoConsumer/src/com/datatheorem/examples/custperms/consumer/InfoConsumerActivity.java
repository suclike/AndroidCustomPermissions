package com.datatheorem.examples.custperms.consumer;

import com.datatheorem.example.custperms.consumer.R;

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
import com.datatheorem.examples.custperms.provider.InfoProviderRemote;

public class InfoConsumerActivity extends Activity {

	InfoProviderRemote providerRemote;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info_consumer);
		 Intent serviceIntent=new Intent();
	        serviceIntent.setClassName("com.datatheorem.examples.custperms.provider", 
	        		"com.datatheorem.examples.custperms.provider.InfoProviderService");
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
				} catch (RemoteException e) {
					Log.e("RemoteException", e.toString());
				}

			}
		};
	
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.info_consumer, menu);
		return true;
	}

}
