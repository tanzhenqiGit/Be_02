/**
 * @author tan_zhenq E-mail: tan_zhenqi@163.com
 * @date 创建时间：2015-7-2 下午7:22:00 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
package com.be02.aidl;

import android.os.Parcel;
import android.os.Parcelable;

import com.be02.musicplayer.R;


/**
 * @author lz100
 *
 */
public class MusicListItem  implements Parcelable 
{
	
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}
	public int getImageId() {
		return mImageId;
	}
	public void setImageId(int imageId) {
		mImageId = imageId;
	}

	public MusicListItem()
	{
		super();
		mImageId = R.drawable.abc;
		this.mName = "";
	}
	
	public MusicListItem(String mName)
	{
		super();
		mImageId = R.drawable.abc;
		this.mName = mName;
	}
	
	public MusicListItem(int imageId, String mName) {
		super();
		mImageId = imageId;
		this.mName = mName;
	}

	public MusicListItem(Parcel p)
	{
		mName = p.readString();
		mImageId = p.readInt();
	}

	@Override
	public int describeContents() {
		
		return 0;
	}

	
	@Override
	public void writeToParcel(Parcel parcel, int arg1) {
		parcel.writeString(mName);
		parcel.writeInt(mImageId);
	}
	
	public void readFromParcel(Parcel in)
	{
		mName = in.readString();
		mImageId = in.readInt();
	}
	
	public static final Parcelable.Creator<MusicListItem> CREATOR = new Parcelable.Creator<MusicListItem>()
	{

		@Override
		public MusicListItem createFromParcel(Parcel p) {
			return new MusicListItem(p);
		}

		@Override
		public MusicListItem[] newArray(int size) {
			return new MusicListItem[size];
		}

	};
	
	@Override
	public boolean equals(Object o) {
		MusicListItem item = (MusicListItem)o;
		if (mName.equals(item.getmName())) {
			return true;
		} else {
			return false;
		}
	}

	private String mName;
	private int mImageId;
}
