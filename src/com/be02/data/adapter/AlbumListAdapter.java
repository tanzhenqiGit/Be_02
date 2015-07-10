/**
 * @author tan_zhenq E-mail: tan_zhenqi@163.com
 * @date 创建时间：2015-7-10 上午10:16:35 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
package com.be02.data.adapter;

import java.util.List;

import com.be02.data.AlbumListItem;
import com.be02.data.MusicLog;
import com.be02.musicplayer.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author lz100
 *
 */
public class AlbumListAdapter extends BaseAdapter {

	public AlbumListAdapter(Context context, List<AlbumListItem> list)
	{
		mContext = context;
		mList = list;
	}
	
	@Override
	public int getCount() {
		if (mList == null) {
			return 0;
		} else {
			return mList.size();
		}
	}

	@Override
	public AlbumListItem getItem(int pos) {
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
			MusicLog.e(SUB_TAG + "getView mContext == null");
			return view;
		}
		@SuppressWarnings("static-access")
		LayoutInflater lf = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
		view = lf.inflate(R.layout.common_list_item, null);
		AlbumListItem item = getItem(pos);
		TextView artist = (TextView) view.findViewById(R.id.common_list_artist_txt);
		TextView song = (TextView) view.findViewById(R.id.common_list_song_txt);
		if (item != null && artist != null && song != null) {
			artist.setText(item.getArtist());
			song.setText(item.getAblum());
		}
		TextView number = (TextView) view.findViewById(R.id.common_list_number_txt);
		ImageView image = (ImageView) view.findViewById(R.id.common_list_imge);
		if (number != null) {
			number.setText(pos + 1 + ".");
		}
		if (image != null) {
			image.setImageResource(R.drawable.albumart_mp_unknown_list);
		}
		
		TextView time = (TextView) view.findViewById(R.id.common_list_time_txt);
		if (time != null) {
			time.setVisibility(View.GONE);
		}
		return view;
	}

	private final String SUB_TAG = AlbumListAdapter.class.toString() + " ";
	private List<AlbumListItem> mList;
	private Context mContext;
}
