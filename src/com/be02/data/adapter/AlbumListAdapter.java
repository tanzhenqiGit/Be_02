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
			MusicLog.e(SUB_TAG + "getCount ==0 ");
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
	public View getView(int pos, View view, ViewGroup arg2) {
		ViewHolder vh = null;
		if (view == null) {
			view = LayoutInflater.from(mContext).inflate(R.layout.common_list_item, arg2, false);
			vh = new ViewHolder(view);
			view.setTag(vh);
		} else {
			vh = (ViewHolder)view.getTag();
		}
			AlbumListItem item = getItem(pos);
			vh.mArtist.setText(item.getArtist());
			vh.mSonger.setText(item.getAblum());
			vh.mLinearLayout.setVisibility(View.VISIBLE);
			vh.mImg.setImageResource(R.drawable.albumart_mp_unknown_list);
			vh.mNumber.setText(pos + 1 + ".");
			vh.mTime.setVisibility(View.GONE);

		return view;
	}

	private final String SUB_TAG = AlbumListAdapter.class.toString() + " ";
	private List<AlbumListItem> mList;
	private Context mContext;
}
