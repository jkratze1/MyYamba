package com.example.yamba;

import winterwell.jtwitter.Twitter;
import android.app.Application;

public class YambaApp extends Application {

	public static Twitter twitter;
	public static StatusData sd;
	public static final String ACTION_NEW_STATUS = "com.example.yamba.NEW_STATUS";
	
	public static StatusData getSd() {
		return sd;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		twitter = new Twitter("student", "password");
		twitter.setAPIRootUrl("http://yamba.marakana.com/api");

		sd = new StatusData(this);
	}

	public static Twitter getTwitter() {
		return twitter;
	}
}
