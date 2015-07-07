/**
 * @author tan_zhenq E-mail: tan_zhenqi@163.com
 * @date 创建时间：2015-7-3 上午9:39:35 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
package com.be02.data.adapter;

import java.util.List;

import com.be02.data.MusicListItem;
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
public class FolderListAdapter extends BaseAdapter {

	/* （非 Javadoc）
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		if (mList == null) {
			return 0;
		} else {
			return mList.size();
		}
	}

	/* （非 Javadoc）
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public MusicListItem getItem(int position) {
		if (mList == null) {
			return null;
		} else {
			return mList.get(position);
		}
	}

	/* （非 Javadoc）
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int id) {
		return id;
	}

	/* （非 Javadoc）
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View v, ViewGroup g) {
		if (mContext == null) {
			return null;
		}
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (inflater != null) {
			View view = inflater.inflate(R.layout.music_list_item, null);
			ImageView image = (ImageView) view.findViewById(R.id.music_list_item_img);
			image.setImageResource(getItem(position).getImageId());
			TextView txt = (TextView) view.findViewById(R.id.music_list_item_txt);
			txt.setText(getItem(position).getmName());
			return view;
		}
		return null;
	}

	
	public FolderListAdapter(Context mContext, List<MusicListItem> mList) {
		super();
		this.mContext = mContext;
		this.mList = mList;
	}
	
	private Context mContext;
	private List<MusicListItem> mList;
	
}
