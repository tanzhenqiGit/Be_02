/**
 * @author tan_zhenq E-mail: tan_zhenqi@163.com
 * @date 创建时间：2015-7-10 上午9:45:31 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
package com.be02.data;

/**
 * @author lz100
 *
 */
public class AlbumListItem {
	
	public AlbumListItem(String artist, String ablum)
	{
		mArtist = artist;
		mAblum = ablum;
	}

	public String getArtist() {
		return mArtist;
	}
	public String getAblum() {
		return mAblum;
	}
	public void setArtist(String mArtist) {
		this.mArtist = mArtist;
	}
	public void setAblum(String mAblum) {
		this.mAblum = mAblum;
	}
	private String mAblum;
	private String mArtist;
}
