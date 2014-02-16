package com.droderino.racingdatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "RacingTrackerDB";
	
	private static final String TABLE_TRACKS = "tracks";
	private static final String TRACKS_ID ="id_tracks";
	private static final String TRACKS_NAME = "name";
	private static final String CREATE_TRACKS = "create table"
			+ TABLE_TRACKS + "("
			+ TRACKS_ID + " integer not null primary key auto_increment, "
			+ TRACKS_NAME + " text);";
	
	private static final String TABLE_LOCATIONS = "locations";
	private static final String LOCATIONS_ID = "id_locations";
	private static final String LOCATIONS_LATITUDE = "latitude";
	private static final String LOCATIONS_LONGITUDE = "longitude";
	private static final String LOCATIONS_ALTITUDE = "altitude";
	private static final String LOCATIONS_TIME = "time";
	private static final String LOCATIONS_BEARING = "bearing";
	private static final String LOCATIONS_SPEED = "speed";
	private static final String LOCATIONS_ACCURACY = "accuracy";
	private static final String LOCATIONS_FKTRACK = "fk_track";
	private static final String CREATE_LOCATIONS = "create table"
			+ TABLE_LOCATIONS + "("
			+ LOCATIONS_ID + " integer not null primary key auto_increment, "
			+ LOCATIONS_LATITUDE + " real not null, "
			+ LOCATIONS_LONGITUDE + " real not null, "
			+ LOCATIONS_ALTITUDE + " real, "
			+ LOCATIONS_TIME + " integer not null, "
			+ LOCATIONS_BEARING + " real, "
			+ LOCATIONS_SPEED + " real, "
			+ LOCATIONS_ACCURACY + " real, "
			+ LOCATIONS_FKTRACK + " integer not null, "
			+ "FOREIGN KEY(" + LOCATIONS_FKTRACK + ") REFERENCES " + TABLE_TRACKS + "(" + TRACKS_ID + ") on delete cascade );";
	
	public DatabaseOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TRACKS);
		db.execSQL(CREATE_LOCATIONS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
