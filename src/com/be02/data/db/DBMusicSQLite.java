/**
 * @author tan_zhenq E-mail: tan_zhenqi@163.com
 * @date 创建时间：2015-7-14 下午2:31:12 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
package com.be02.data.db;

import com.be02.data.MusicLog;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author lz100
 *
 */
public class DBMusicSQLite extends SQLiteOpenHelper {

	/**
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	public DBMusicSQLite(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		if (db != null) {
			db.execSQL(CREATE_TABLE_MUSIC);
			db.execSQL(CREATE_TABLE_LOCAL_LIST);
			MusicLog.d(SUB_TAG + "onCreate");
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		MusicLog.d(SUB_TAG + "onUpgrade arg1=" + arg1 + ",arg2=" + arg2);
	}


	public static String SQL_NAME = "MusicListInfo.db";
	/**
	 * table music infor name
	 */
	public static String TABLE_NAME_MUSIC_INFO = "musicInfo";
	/**
	 * id just for record
	 */
	public static String ID = "_id";
	/**
	 * list name save song in which lsit
	 */
	public static String LIST_NAME = "list_name";
	
	public static String TITEL = "title";
	public static String ARTIST = "artist";
	public static String ALBUM = "album";
	public static String URI = "uri";
	public static String DISPLAY = "display";
	public static String SIZE = "size";
	public static String TIME = "time";
	public static String FAVORITE_LIST = "favorite_list";
	private final String SUB_TAG = "DBMusicSQLite"; 
	/**
	 * table music infor user to save favorite or local list's song info
	 */
	private String CREATE_TABLE_MUSIC ="CREATE TABLE "
			+ TABLE_NAME_MUSIC_INFO + "("
			+ ID		            + " INTEGER primary key AUTOINCREMENT,"
			+ LIST_NAME             + " TEXT,"
			+ TITEL                 + " TEXT,"
			+ ARTIST                + " TEXT,"
			+ ALBUM                 + " TEXT,"
			+ URI                   + " TEXT,"
			+ DISPLAY               + " TEXT,"
			+ SIZE                  + " INTEGER,"
			+ TIME                  + " INTEGER)";
	
	/**
	 * table local music used for save user new local fold
	 */
	public static String TABLE_NAME_LOCAL_LIST = "localListInfo";
	public static String IMGID = "imageId";
	private String CREATE_TABLE_LOCAL_LIST = "CREATE TABLE "
			+ TABLE_NAME_LOCAL_LIST + "("
			+ ID		            + " INTEGER primary key AUTOINCREMENT,"
			+ IMGID                 + " INTEGER,"
			+ LIST_NAME             + " TEXT)";

}
