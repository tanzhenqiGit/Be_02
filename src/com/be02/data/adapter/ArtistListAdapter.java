/**
 * @author tan_zhenq E-mail: tan_zhenqi@163.com
 * @date 创建时间：2015-7-10 上午10:15:13 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
package com.be02.data.adapter;

import java.util.List;

import com.be02.data.MusicLog;
import com.be02.musicplayer.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author lz100
 *
 */
public class ArtistListAdapter extends BaseAdapter {

	public ArtistListAdapter(Context context, List<String> list)
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
	public String getItem(int pos) {
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
			MusicLog.e(SUB_TAG + "getview mContext == null");
			return view;
		}
		@SuppressWarnings("static-access")
		LayoutInflater lf = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
		if (lf == null) {
			MusicLog.e(SUB_TAG + "getview LayoutInflater == null");
			return view;
		}
		view = lf.inflate(R.layout.common_list_item, null);
		if (view != null)
		{
			TextView artist = (TextView) view.findViewById(R.id.common_artistlist_artist_txt);
			if (artist != null) {
				artist.setText(getItem(pos));
				artist.setVisibility(View.VISIBLE);
			}
			ImageView img = (ImageView) view.findViewById(R.id.common_list_imge);
			if (img != null) {
				img.setImageResource(R.drawable.albumart_mp_unkn_own_list);
			}
			TextView number = (TextView) view.findViewById(R.id.common_list_number_txt);
			if (number != null) {
				number.setText(pos+1 + ".");
			}
			LinearLayout ll = (LinearLayout) view.findViewById(R.id.common_list_song_artist_layout);
			if (ll != null) {
				ll.setVisibility(View.GONE);
			}
			TextView time = (TextView) view.findViewById(R.id.common_list_time_txt);
			if (time != null) {
				time.setVisibility(View.GONE);
			}
		}
		
		return view;
	}

	private final String SUB_TAG = ArtistListAdapter.class.toString() + " ";
	private List<String> mList;
	private Context mContext;
}
