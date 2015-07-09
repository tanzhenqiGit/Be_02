/**
 * @author tan_zhenq E-mail: tan_zhenqi@163.com
 * @date 创建时间：2015-7-9 下午3:33:50 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
package com.be02.data.adapter;

import java.util.List;

import com.be02.aidl.MusicItem;
import com.be02.data.MusicLog;
import com.be02.musicplayer.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author lz100
 *
 */
@SuppressLint("DefaultLocale") public class MusicListAdapter extends BaseAdapter {

	/* （非 Javadoc）
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		if (mList == null) {
			return 0;
		}
		return mList.size();
	}

	@Override
	public MusicItem getItem(int pos) {
		if (mList == null) {
			return null;
		} else {
			return mList.get(pos);
		}
	}


	@Override
	public long getItemId(int id) {
		return id;
	}

	@Override
	public View getView(int pos, View arg1, ViewGroup arg2) {
		View view = null;
		if (mContext == null) {
			return view;
		} else {
			@SuppressWarnings("static-access")
			LayoutInflater lf = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
			if (lf != null) {
				view = lf.inflate(R.layout.common_list_item, null);
				TextView song = (TextView) view.findViewById(R.id.common_list_song_txt);
				TextView artist = (TextView) view.findViewById(R.id.common_list_artist_txt);
				TextView time = (TextView) view.findViewById(R.id.common_list_time_txt);
				TextView number = (TextView) view.findViewById(R.id.common_list_number_txt);
				MusicItem item = getItem(pos);
				if (item != null && song != null && artist != null && time != null && number != null) {
					MusicLog.d(SUB_TAG + item.getmTitle() + ":getView:" + pos);
					song.setText(item.getmTitle());
					artist.setText(item.getmArtist());
					time.setText(converToString(item.getmTime()));
					number.setText(pos+1 + ".");
				} else {
					MusicLog.d(SUB_TAG + ":getView other case:" + pos);
				}
			}
		}
		return view;
	}

	@SuppressLint("DefaultLocale") private String converToString(int time) 
	{
		time = time / 1000;
		int min = time / 60;
		int hor = min / 60;
		min = min % 60;
		int second = time % 60;
		
		if (hor > 0) {
			return String.format("%02:%02d:%02d", hor, min, second);
		}  else {
			return String.format("%02d:%02d", min, second);
		}
	}
	
	public MusicListAdapter(List<MusicItem> mList, Context mContext) {
		super();
		this.mList = mList;
		this.mContext = mContext;
		if (mList != null) {
			MusicLog.d(SUB_TAG + "MusicListAdapter 	mList.size =" + mList.size());
		} else { 
			MusicLog.d(SUB_TAG + "MusicListAdapter mList == null");
		}
	}
	
	private List<MusicItem> mList;
	private Context mContext;
	
	private final String SUB_TAG = MusicListAdapter.class.toString() + " ";
}
