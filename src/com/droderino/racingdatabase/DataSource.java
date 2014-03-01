package com.droderino.racingdatabase;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;

public class DataSource {

	private SQLiteDatabase database;
	private DatabaseOpenHelper dbHelper;
	private String[] trackColumns = { DatabaseOpenHelper.TRACKS_ID, DatabaseOpenHelper.TRACKS_NAME };
	private String[] locationColumns = { DatabaseOpenHelper.LOCATIONS_ID, DatabaseOpenHelper.LOCATIONS_LATITUDE, DatabaseOpenHelper.LOCATIONS_LONGITUDE, 
			DatabaseOpenHelper.LOCATIONS_ALTITUDE, DatabaseOpenHelper.LOCATIONS_TIME, DatabaseOpenHelper.LOCATIONS_BEARING, 
			DatabaseOpenHelper.LOCATIONS_SPEED, DatabaseOpenHelper.LOCATIONS_ACCURACY, DatabaseOpenHelper.LOCATIONS_FKTRACK };
	
	public DataSource(Context context)
	{
		dbHelper = new DatabaseOpenHelper(context);
	}
	
	public void open() throws SQLException
	{
		database = dbHelper.getWritableDatabase();
	}
	
	public void close()
	{
		database.close();
	}
	
	public Track createTrack(String name)
	{
		ContentValues values = new ContentValues();
		values.put(DatabaseOpenHelper.TRACKS_NAME, name);
		long insertId = database.insert(DatabaseOpenHelper.TABLE_TRACKS, null, values);
		
		Cursor cursor = database.query(DatabaseOpenHelper.TABLE_TRACKS, trackColumns, DatabaseOpenHelper.TRACKS_ID + "=" + insertId, null, null, null, null);
		cursor.moveToFirst();
		Track newtrack = cursorToTrack(cursor);
		cursor.close();
		
		return newtrack;
	}
	
	public LocationItem createLocation(long idTrack, Location loc)
	{
		ContentValues values = new ContentValues();
		values.put(DatabaseOpenHelper.LOCATIONS_LATITUDE, loc.getLatitude());
		values.put(DatabaseOpenHelper.LOCATIONS_LONGITUDE, loc.getLongitude());
		values.put(DatabaseOpenHelper.LOCATIONS_ALTITUDE, loc.getAltitude());
		values.put(DatabaseOpenHelper.LOCATIONS_TIME, loc.getTime());
		values.put(DatabaseOpenHelper.LOCATIONS_BEARING, loc.getBearing());
		values.put(DatabaseOpenHelper.LOCATIONS_SPEED, loc.getSpeed());
		values.put(DatabaseOpenHelper.LOCATIONS_ACCURACY, loc.getAccuracy());
		values.put(DatabaseOpenHelper.LOCATIONS_FKTRACK, idTrack);
		long insertId = database.insert(DatabaseOpenHelper.TABLE_LOCATIONS, null, values);
		
		Cursor cursor = database.query(DatabaseOpenHelper.TABLE_TRACKS, locationColumns, DatabaseOpenHelper.LOCATIONS_ID + "=" + insertId, null, null, null, null);
		cursor.moveToFirst();
		LocationItem newLoc = cursorToLocation(cursor);
		cursor.close();
		
		return newLoc;
	}
	
	public void deleteTrack(Track track)
	{
		long id = track.getId();
		database.delete(DatabaseOpenHelper.TABLE_TRACKS, DatabaseOpenHelper.TRACKS_ID + "=" + id, null);
	}
	
	public void deleteLocation(LocationItem loc)
	{
		long id = loc.getId();
		database.delete(DatabaseOpenHelper.TABLE_LOCATIONS, DatabaseOpenHelper.LOCATIONS_ID + "=" + id, null);
	}
	
	public List<Track> getAllTracks()
	{
		List<Track> tracks = new ArrayList<Track>();
		
		Cursor cursor = database.query(DatabaseOpenHelper.TABLE_TRACKS, trackColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while( !cursor.isAfterLast() )
		{
			Track track = cursorToTrack(cursor);
			tracks.add(track);
			cursor.moveToNext();
		}
		cursor.close();
		return tracks;
	}
	
	public List<LocationItem> getAllLocationsByTrack(Track track)
	{
		List<LocationItem> locs = new ArrayList<LocationItem>();
		
		Cursor cursor = database.query(DatabaseOpenHelper.TABLE_LOCATIONS, locationColumns, DatabaseOpenHelper.LOCATIONS_FKTRACK + "=" + track.getId(), null, null, null, null);
		cursor.moveToFirst();
		while( !cursor.isAfterLast() )
		{
			LocationItem loc = cursorToLocation(cursor);
			locs.add(loc);
			cursor.moveToNext();
		}
		cursor.close();
		return locs;
	}
	
	private Track cursorToTrack(Cursor cursor)
	{
		Track track = new Track();
		track.setId(cursor.getLong(0));
		track.setName(cursor.getString(1));
		return track;
	}
	
	private LocationItem cursorToLocation(Cursor cursor)
	{
		LocationItem loc = new LocationItem();
		loc.setId(cursor.getLong(0));
		loc.setLatitude(cursor.getDouble(1));
		loc.setLongitude(cursor.getDouble(2));
		loc.setAltitude(cursor.getDouble(3));
		loc.setTime(cursor.getLong(4));
		loc.setBearing(cursor.getDouble(5));
		loc.setSpeed(cursor.getDouble(6));
		loc.setAccuracy(cursor.getDouble(7));
		loc.setFkTrack(cursor.getLong(8));
		return loc;
	}
}
