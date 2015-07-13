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
import android.content.Context;
import android.database.Cursor;
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
		updateList();
	}

	public void updateList()
	{
		new Thread(new Runnable() {
			public void run() {
				updateMusicList();
				upateArtistList();
				updateAblumList();
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
	private static DBManager mDBMgr = null;
	private ContentResolver mContentResolver;
	private List<MusicItem> mMusicList;
	private List<String> mArtistList;
	private List<AlbumListItem> mAlbumList;
	private Object mCurMusicListLock = new Object();
	private List<MusicItem> mCurMusicList;
	private Context mContext;
	private final String SUB_TAG = DBManager.class.toString() + " ";
}
