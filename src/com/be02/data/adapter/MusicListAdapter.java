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
			MusicLog.d(SUB_TAG + "getItem");
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
			vh = (ViewHolder) view.getTag();
		}
		MusicItem item = getItem(pos);
		vh.mSonger.setText(item.getmTitle());
		vh.mArtist.setText(item.getmArtist());
		vh.mTime.setText(MusicItem.converToStringTime(item.getmTime()));
		vh.mNumber.setText(pos+1 + ".");
	
		return view;
	}


	
	public MusicListAdapter(List<MusicItem> mList, Context mContext) {
		super();
		this.mList = mList;
		this.mContext = mContext;
	}
	
	private List<MusicItem> mList;
	private Context mContext;
	
	private final String SUB_TAG = MusicListAdapter.class.toString() + " ";
}
