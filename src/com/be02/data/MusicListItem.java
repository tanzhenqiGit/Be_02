/**
 * @author tan_zhenq E-mail: tan_zhenqi@163.com
 * @date 创建时间：2015-7-2 下午7:22:00 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
package com.be02.data;

import com.be02.musicplayer.R;


/**
 * @author lz100
 *
 */
public class MusicListItem 
{
	
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}
	public int getImageId() {
		return ImageId;
	}
	public void setImageId(int imageId) {
		ImageId = imageId;
	}

	public MusicListItem()
	{
		super();
		ImageId = R.drawable.abc;
		this.mName = "";
	}
	
	public MusicListItem(String mName)
	{
		super();
		ImageId = R.drawable.abc;
		this.mName = mName;
	}
	
	public MusicListItem(int imageId, String mName) {
		super();
		ImageId = imageId;
		this.mName = mName;
	}
	private String mName;
	private int ImageId;
}
