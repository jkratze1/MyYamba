package com.example.yamba;

import java.util.List;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.Twitter.Status;
import winterwell.jtwitter.TwitterException;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class UpdaterService extends Service {

	boolean running = false;
	int count = 0;
	long lastTimeStamp = 0;
	
	private static final String TAG = "UpdaterService";
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		running = false;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onStartCommand");
		new Thread() {
            public void run() {
            	running = true;
            	while(running) {
					count = 0;
					try {
						Twitter twitter = YambaApp.getTwitter();
						List<Status> timeline = twitter.getPublicTimeline();
						for( Status status : timeline ) {
							if ( status.createdAt.getTime() > lastTimeStamp ) {
								Log.d(TAG, String.format("%s  %s", status.user.name, status.text));
								YambaApp.getSd().insert(status);
								lastTimeStamp = status.createdAt.getTime();
								count++;
							}
						}
						Log.d(TAG, String.format("Inserted %d tweets\n", count));
						if ( count > 0 ) {
							sendBroadcast(new Intent(YambaApp.ACTION_NEW_STATUS));						}
					} catch(TwitterException e) {
						Log.d(TAG, e.getMessage());
					}
					
					try {
						sleep(10000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            	}
            }
		}.start();
		return super.onStartCommand(intent, flags, startId);
	}

	
}
