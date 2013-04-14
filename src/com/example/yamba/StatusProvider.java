package com.example.yamba;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.yamba.StatusData.DbHelper;

public class StatusProvider extends ContentProvider {

	public static final String AUTHORITY = "content://com.example.mycontentprovider";
	public static final Uri CONTENT_URI = Uri.parse(AUTHORITY);
	public static final String SINGLE_RECORD_MIME_TYPE = "vnd.android.cursor.item/vnd.example.mycontentprovider";
	public static final String MULTIPLE_RECORD_MIME_TYPE = "vnd.android.cursor.dir/vnd.example.mycontentprovider";
	
	DbHelper dbHelper;
	SQLiteDatabase db;
	
	@Override
	public boolean onCreate() {
		
		
		return true;
	}

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		if ( uri.getLastPathSegment() == null ) {
			return MULTIPLE_RECORD_MIME_TYPE;
		} else {
			return SINGLE_RECORD_MIME_TYPE;
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		return null;
	}
}
