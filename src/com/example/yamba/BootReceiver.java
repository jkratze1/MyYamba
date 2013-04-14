package com.example.yamba;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {

	private static final String TAG = "BootReceiver";
	
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		
		long interval = 900000;
	    PendingIntent operation = PendingIntent.getService(arg0, -1, new Intent("com.example.yamba.StartRefreshService"), PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager alarmManager = (AlarmManager)arg0.getSystemService(Context.ALARM_SERVICE);
		alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, System.currentTimeMillis(), interval, operation);
		
		Intent intent = new Intent(arg0, UpdaterService.class);
		arg0.startService(intent);
		Log.d(TAG, "onReceive");
	}

}
