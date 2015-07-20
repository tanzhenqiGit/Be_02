/**
 * @author tan_zhenq E-mail: tan_zhenqi@163.com
 * @date 创建时间：2015-7-20 下午3:15:17 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
package com.be02.data.adapter;

import java.util.List;

import com.be02.aidl.MusicListItem;
import com.be02.musicplayer.R;

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
public class LongClickListAdapter extends BaseAdapter {

	
	public LongClickListAdapter(Context context, List<MusicListItem> list)
	{
		mContext = context;
		mList = list;
	}
	@Override
	public int getCount() {
		if (mList == null) {
			return 0;
		} 
		return mList.size();
	}

	@Override
	public MusicListItem getItem(int arg0) {
		if (mList == null) {
			return null;
		}
		return mList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder vh = null;
		if (arg1 == null) {
			arg1 = LayoutInflater.from(mContext).inflate(R.layout.long_click_list, arg2, false);
			vh = new ViewHolder(arg1);
			arg1.setTag(vh);
		} else {
			vh = (ViewHolder) arg1.getTag();
		}
		vh.mTxt.setText(getItem(arg0).getmName());
		return arg1;
	}
	
	
	private class ViewHolder
	{
		public ViewHolder(View view)
		{
			mTxt = (TextView) view.findViewById(R.id.long_click_item_text);
		}
		
		public TextView mTxt;
	}
	
	private List<MusicListItem> mList;
	private Context mContext;

}
