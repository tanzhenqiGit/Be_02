/**
 * @author tan_zhenq E-mail: tan_zhenqi@163.com
 * @date 创建时间：2015-7-9 上午10:20:03 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
package com.be02.data.db;

import java.util.ArrayList;
import java.util.List;

import com.be02.aidl.MusicItem;
import com.be02.data.AlbumListItem;
import com.be02.data.MusicLog;
import com.be02.musicplayer.R;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.MediaStore;

/**
 * @author lz100
 *
 */
public final class DBManager {

	public static DBManager getInstance(Context c)
	{
		if (mDBMgr == null) {
			mDBMgr = new DBManager(c);
			return mDBMgr;
		} 
		return mDBMgr;
	}
	
	
	public List<MusicItem> getMusicList()
	{
		synchronized (mMusicList) {
			return mMusicList;
		}
	}
	
	public List<String> getArtistList()
	{
		synchronized(mArtistList) {
			return mArtistList;
		}
	}
	
	public List<AlbumListItem> getAlbumList()
	{
		synchronized (mAlbumList) {
			return mAlbumList;
		}
	}
	
	private DBManager(Context c)
	{
		mContext = c;
		initialize();
	}
	
	private void initialize()
	{
		mMusicList = new ArrayList<MusicItem>();
		mArtistList = new ArrayList<String>();
		mAlbumList = new ArrayList<AlbumListItem>();
		mFavoriteList = new ArrayList<MusicItem>();
		mHelper = new DBMusicSQLite(mContext, DBMusicSQLite.SQL_NAME, null, mVersion);
		updateList();
	}

	public void updateList()
	{
		new Thread(new Runnable() {
			public void run() {
				updateMusicList();
				upateArtistList();
				updateAblumList();
				updateFavoriteList();
			}
		}).start();
	}
	
	private void updateMusicList()
	{
		if (mContext == null) {
			MusicLog.e(SUB_TAG + "updateList mContext == null");
			return;
		}
		
		mContentResolver = mContext.getContentResolver();
		if (mContentResolver == null) {
			return;
		}
		Cursor cursor = mContentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
				null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
		if (cursor == null) {
			MusicLog.e(SUB_TAG + "updateList cursor == null");
			return;
		}
		cursor.moveToFirst();
		do {
			String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
			String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
			artist = changeArtistName(artist);
			String ablum = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
			String dispName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
			String uri = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
			int size = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
			int time = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
			int isMusic = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));
			if (isMusic != 0 && time > 1000) {
				MusicItem item = new MusicItem(title, artist, ablum, dispName, uri, size, time);
				synchronized (mMusicList) {
					if (mMusicList != null) {
						mMusicList.add(item);
					}
				}
			}
			
		} while(cursor.moveToNext());
		cursor.close();
		cursor = null;
	}
	
	private void upateArtistList()
	{
		if (mContext == null) {
			MusicLog.e(SUB_TAG + "updateList mContext == null");
			return;
		}
		
		if (mContentResolver == null) {
			mContentResolver = mContext.getContentResolver();
		}
		Cursor cursor = mContentResolver.query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
				null, null, null, MediaStore.Audio.Artists.DEFAULT_SORT_ORDER);
		if (cursor == null) {
			MusicLog.e(SUB_TAG + "updateArtistList cursor == null");
			return;
		}
		cursor.moveToFirst();
		do {
			String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST));
			artist = changeArtistName(artist);
			synchronized (mArtistList) {
				if (mArtistList != null) {
					mArtistList.add(artist);
				}
			}
		} while(cursor.moveToNext());
		cursor.close();
		cursor = null;
	}
	
	private void updateAblumList()
	{
		if (mContext == null) {
			MusicLog.e(SUB_TAG + "updateList mContext == null");
			return;
		}
		
		if (mContentResolver == null) {
			mContentResolver = mContext.getContentResolver();
		}
		Cursor cursor = mContentResolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
				null, null, null, MediaStore.Audio.Albums.DEFAULT_SORT_ORDER);
		if (cursor == null) {
			MusicLog.e(SUB_TAG + "updateAblumList cursor == null");
			return;
		}
		cursor.moveToFirst();
		do {
			String ablum = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
			String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST));
			artist = changeArtistName(artist);
			AlbumListItem item = new AlbumListItem(ablum, artist);
			synchronized (mAlbumList) {
				if (mAlbumList != null) {
					mAlbumList.add(item);
				}
			}
		} while(cursor.moveToNext());
		cursor.close();
		cursor = null;
	}
	
	
	public void delete(String listName, MusicItem item)
	{
		SQLiteDatabase db = mHelper.getWritableDatabase();
		if (db != null && item != null) {
			db.delete(DBMusicSQLite.TABLE_NAME,
					DBMusicSQLite.URI + " = ? AND " + DBMusicSQLite.LIST_NAME + " = ? ", 
					new String[]{item.getmUri(), listName});
		}
	}
	
	public void insert(MusicItem item, String listName)
	{
		SQLiteDatabase db = mHelper.getWritableDatabase();
		if (isMusicItemExist(db, item)) {
			update(item, listName);
		} else {
			db.insert(DBMusicSQLite.TABLE_NAME, null, parserContentValues(item,listName));
		}
	}
	
	public void update(MusicItem item, String listName)
	{
		SQLiteDatabase db = mHelper.getWritableDatabase();
		if (db != null && item != null) {
			db.update(DBMusicSQLite.TABLE_NAME, parserContentValues(item, listName), 
				DBMusicSQLite.URI + " = ? ", 
				new String[] {item.getmUri()});
		}
	}
	
	private ContentValues parserContentValues(MusicItem item, String listName)
	{
		ContentValues cv = new ContentValues();
		cv.put(DBMusicSQLite.TITEL, item.getmTitle());
		cv.put(DBMusicSQLite.ARTIST, item.getmArtist());
		cv.put(DBMusicSQLite.ALBUM, item.getmAblum());
		cv.put(DBMusicSQLite.URI, item.getmUri());
		cv.put(DBMusicSQLite.DISPLAY, item.getmDisplayName());
		cv.put(DBMusicSQLite.SIZE, item.getmSize());
		cv.put(DBMusicSQLite.TIME, item.getmTime());
		cv.put(DBMusicSQLite.LIST_NAME, listName);
		return cv;
	}
	
	private boolean isMusicItemExist(SQLiteDatabase db,MusicItem item)
	{
		if (db == null || item == null) {
			return false;
		}
		Cursor cursor = db.query(DBMusicSQLite.TABLE_NAME,
				null , DBMusicSQLite.URI + " = ? ", 
				new String[]{item.getmUri()}, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			return true;
		}
		return false;
		
	}
	
	public Cursor query(String listName)
	{
		SQLiteDatabase db = mHelper.getReadableDatabase();
		Cursor cursor = null;
		if (db != null) {
			cursor = db.query(DBMusicSQLite.TABLE_NAME,
					new String[] {
					DBMusicSQLite.TITEL,
					DBMusicSQLite.ARTIST,
					DBMusicSQLite.ALBUM,
					DBMusicSQLite.URI,
					DBMusicSQLite.DISPLAY,
					DBMusicSQLite.SIZE,
					DBMusicSQLite.TIME},
			DBMusicSQLite.LIST_NAME + " = ? ", 
			new String[] {listName} ,
			null, null, null);
		}
		if (cursor == null) {
			MusicLog.d(SUB_TAG + "updateFavorite querry return nothing.");
		}
		return cursor;
	}
	
	public void updateFavoriteList()
	{
		mFavoriteList = converToList(query(DBMusicSQLite.FAVORITE_LIST));
	}
	
	private List<MusicItem> converToList(Cursor cursor)
	{
		if (cursor == null) {
			return null;
		}
		List<MusicItem> list = new ArrayList<MusicItem>();
		if (cursor.getCount() == 0) {
			cursor.close();
			return list;
		}
		MusicLog.d(SUB_TAG + "**cursor.siz=" + cursor.getCount());
		cursor.moveToFirst();
		do {
			String title = cursor.getString(cursor.getColumnIndex(DBMusicSQLite.TITEL));
			String artist = cursor.getString(cursor.getColumnIndex(DBMusicSQLite.ARTIST));
			String album = cursor.getString(cursor.getColumnIndex(DBMusicSQLite.ALBUM));
			String uri = cursor.getString(cursor.getColumnIndex(DBMusicSQLite.URI));
			String display = cursor.getString(cursor.getColumnIndex(DBMusicSQLite.DISPLAY));
			int size = cursor.getInt(cursor.getColumnIndex(DBMusicSQLite.SIZE));
			int time = cursor.getInt(cursor.getColumnIndex(DBMusicSQLite.TIME));
			MusicItem item = new MusicItem(title, artist, album, display, uri, size, time);
			list.add(item);
		} while (cursor.moveToNext());
		cursor.close();
		return list;
	}
	
	
	
	private String changeArtistName(String artist)
	{
		if (artist.equals("<unknown>")) {
			artist = mContext.getString(R.string.unKnowArtist);
		}
		return artist;
	}
	
	public void setCurMusicList(List<MusicItem> list)
	{
		synchronized (mCurMusicListLock) {
			mCurMusicList = list;
		}
		
	}
	
	public List<MusicItem> getCurMusicList()
	{
		synchronized (mCurMusicListLock) {
			return mCurMusicList;
		}
	}
	
	public List<MusicItem> getFavoriteList()
	{
		synchronized (mFavoriteList) {
			updateFavoriteList();
			return mFavoriteList;
		}
	}
	
	
	private static DBManager mDBMgr = null;
	private ContentResolver mContentResolver;
	private List<MusicItem> mFavoriteList;
	private List<MusicItem> mMusicList;
	private List<String> mArtistList;
	private List<AlbumListItem> mAlbumList;
	private Object mCurMusicListLock = new Object();
	private List<MusicItem> mCurMusicList;
	private Context mContext;
	private final String SUB_TAG = DBManager.class.toString() + " ";
	private DBMusicSQLite mHelper;
	public final int mVersion = 1;
}
