/**
 * @author tan_zhenq E-mail: tan_zhenqi@163.com
 * @date 创建时间：2015-7-9 上午10:26:53 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
package com.be02.aidl;


import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import com.be02.data.MusicLog;


/**
 * @author lz100
 *
 */
public class MusicItem implements Parcelable {

	public MusicItem(String mTitle, String mArtist, String mAblum,
			String mDisplayName, String mUri, int mSize, int mTime) {
		super();
		this.mTitle = mTitle;
		this.mArtist = mArtist;
		this.mAblum = mAblum;
		this.mDisplayName = mDisplayName;
		this.mUri = mUri;
		this.mSize = mSize;
		this.mTime = mTime;
		this.mIsFavorite = NOT_FAVORITE;
		//print();
	}
	public MusicItem() {}
	
	public MusicItem(Parcel parcel)
	{
		mTitle = parcel.readString();
		mArtist = parcel.readString();
		mAblum =  parcel.readString();
		mDisplayName = parcel.readString();
		mUri = parcel.readString();
		mSize = parcel.readInt();
		mTime = parcel.readInt();
		mIsFavorite = parcel.readInt();

	}
	
	public String getmTitle() {
		return mTitle;
	}
	public void setmTitle(String mTitle) {
		this.mTitle = mTitle;
	}
	public String getmArtist() {
		return mArtist;
	}
	public void setmArtist(String mArtist) {
		this.mArtist = mArtist;
	}
	public String getmAblum() {
		return mAblum;
	}
	public void setmAblum(String mAblum) {
		this.mAblum = mAblum;
	}
	public String getmDisplayName() {
		return mDisplayName;
	}
	public void setmDisplayName(String mDisplayName) {
		this.mDisplayName = mDisplayName;
	}
	public String getmUri() {
		return mUri;
	}
	public void setmUri(String mUri) {
		this.mUri = mUri;
	}
	public int getmSize() {
		return mSize;
	}
	public void setmSize(int mSize) {
		this.mSize = mSize;
	}
	public int getmTime() {
		return mTime;
	}
	public void setmTime(int mTime) {
		this.mTime = mTime;
	}
	public int getIsFavorite()
	{
		return mIsFavorite;
	}
	public void setFavoriteSong(int isFavorite)
	{
		mIsFavorite = isFavorite;
	}
	
	@SuppressLint("DefaultLocale") public static String converToStringTime(int time) 
	{
		time = time / 1000;
		int min = time / 60;
		int hor = min / 60;
		min = min % 60;
		int second = time % 60;
		
		if (hor > 0) {
			if (hor < 99) {
				return String.format("%02d:%02d:%02d", hor, min, second);
			} else if (hor < 999) {
				return String.format("%03d:%02d:%02d", hor, min, second);
			} else {
				MusicLog.d("MusicItem + error");
				return "error";
			}
		}  else {
			return String.format("%02d:%02d", min, second);
		}
	}
	
	public void print()
	{
		MusicLog.d("----------------------------");
		MusicLog.d("-> mTitle       : " + mTitle);
		MusicLog.d("-> mArtist      : " + mArtist);
		MusicLog.d("-> mAblum       : " + mAblum);
		MusicLog.d("-> mDisplayName : " + mDisplayName);
		MusicLog.d("-> mUri         : " + mUri);
		MusicLog.d("-> mSize        : " + mSize);
		MusicLog.d("-> mTime        : " + mTime);
		
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int arg1) {
		parcel.writeString(mTitle);
		parcel.writeString(mArtist);
		parcel.writeString(mAblum);
		parcel.writeString(mDisplayName);
		parcel.writeString(mUri);
		parcel.writeInt(mSize);
		parcel.writeInt(mTime);
		parcel.writeInt(mIsFavorite);
	}
	
    public static final Parcelable.Creator<MusicItem> CREATOR = new Parcelable.Creator<MusicItem>() { 

        @Override 
        public MusicItem createFromParcel(Parcel source) { 
                return new MusicItem(source); 
        } 

        @Override 
        public MusicItem[] newArray(int size) { 
                return new MusicItem[size]; 
        } 

    }; 

	private String mTitle;
	private String mArtist;
	private String mAblum;
	private String mDisplayName;
	private String mUri;
	private int mSize;
	private int mTime;
	private int mIsFavorite;
	public final static int NOT_FAVORITE = 0;
	public final static int FAVORITE = 1;
	
	
}
