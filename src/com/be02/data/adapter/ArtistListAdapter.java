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


/**
 * @author lz100
 * 
 */
public class ArtistListAdapter extends BaseAdapter {

	public ArtistListAdapter(Context context, List<String> list) {
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
			MusicLog.d(SUB_TAG + "getItem return null");
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
	public View getView(int pos, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		
		if (view == null) {
			view = LayoutInflater.from(mContext).inflate(R.layout.common_list_item, arg2, false);
			viewHolder = new ViewHolder(view);
			view.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) view.getTag();
		}

		viewHolder.mMidArtist.setText(getItem(pos));
		viewHolder.mMidArtist.setVisibility(View.VISIBLE);
		viewHolder.mImg.setImageResource(R.drawable.albumart_mp_unkn_own_list);
		viewHolder.mNumber.setText(pos + 1 + ".");
		viewHolder.mLinearLayout.setVisibility(View.GONE);
		viewHolder.mTime.setVisibility(View.GONE);
		return view;
	}



	private final String SUB_TAG = ArtistListAdapter.class.toString() + " ";
	private List<String> mList;
	private Context mContext;
}
