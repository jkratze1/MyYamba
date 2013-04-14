package com.example.yamba;

import java.util.List;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.Twitter.Status;
import winterwell.jtwitter.TwitterException;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class RefreshService extends IntentService {

	static final String TAG = "RefreshService";
	int count = 0;
	long lastTimeStamp = 0;
	
	public RefreshService() {
		super(TAG);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
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
		Log.d(TAG, "onHandleIntent");
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.d(TAG, "onCreate");
    }

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d(TAG, "onDestroy");
	}
	
	

}

