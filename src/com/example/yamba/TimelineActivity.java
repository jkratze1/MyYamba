package com.example.yamba;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class TimelineActivity extends ListActivity {

	static final String[] FROM = { StatusData.C_ID, StatusData.C_USER, StatusData.C_CREATED_AT, StatusData.C_TEXT };
	static final int[] TO = { R.id.text_id, R.id.text_user, R.id.text_created_at, R.id.text_tweet };
	//ListView list;
	Cursor cursor;
	SimpleCursorAdapter adapter = null;
	TimelineReceiver receiver = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		cursor = YambaApp.getSd().query();
		if ( cursor.getCount() > 0 ) {
			String strMessage = String.format("getCount=%d", cursor.getCount());
			Log.d("TimelineActivity", strMessage);
		    adapter = new SimpleCursorAdapter(this, R.layout.row, cursor, FROM, TO, 0);
	    	adapter.setViewBinder(VIEW_BINDER);
	    	setTitle(R.string.timeline);
	    	setListAdapter(adapter);
		}
	}
	
	
    @Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(receiver);
	}


	@Override
	protected void onResume() {
		super.onResume();
		if ( receiver == null )
			receiver = new TimelineReceiver();
		registerReceiver(receiver, new IntentFilter(YambaApp.ACTION_NEW_STATUS));
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
		getMenuInflater().inflate(R.menu.status_options_menu, menu);
        return true;
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		Intent intent = new Intent(this, UpdaterService.class);
		Intent intentRefresh = new Intent(this, RefreshService.class);
		Intent intentStatus = new Intent(this, StatusActivity.class);
		
		switch( item.getItemId() )
		{
			case R.id.itemStartService:
				startService(intent);
				return true;
			case R.id.itemStopService:
				stopService(intent);
				return true;
			case R.id.itemStartRefreshService:
				startService(intentRefresh);
				break;
			case R.id.itemStatusActivity:
				startActivity(intentStatus);
			default:
				return false;
		}
		return super.onOptionsItemSelected(item);
	}

	static final ViewBinder VIEW_BINDER = new ViewBinder() {
		
		@Override
		public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
			if ( view.getId() != R.id.text_created_at )
			    return false;
			
			// date field processing
			long ltime = cursor.getLong(cursor.getColumnIndex(StatusData.C_CREATED_AT));
			CharSequence relativeTime = DateUtils.getRelativeTimeSpanString(ltime);
			((TextView)view).setText(relativeTime);
			return true;
		}
	};

	class TimelineReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			
			cursor = YambaApp.getSd().query();
			if ( cursor.getCount() > 0 ) {
				String strMessage = String.format("getCount=%d", cursor.getCount());
				Log.d("TimelineReceiver", strMessage);
				adapter.changeCursor(cursor);
			}
			
		}
		
	}
}
