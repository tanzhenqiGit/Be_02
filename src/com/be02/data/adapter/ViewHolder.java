/**
 * @author tan_zhenq E-mail: tan_zhenqi@163.com
 * @date 创建时间：2015-7-10 下午5:45:31 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
package com.be02.data.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.be02.musicplayer.R;

/**
 * @author lz100
 * used for adapter optimize listview.
 */
final public class ViewHolder {
		public TextView mMidArtist;
		public TextView mNumber;
		public TextView mTime;
		public TextView mArtist;
		public TextView mSonger;
		public LinearLayout mLinearLayout;
		public ImageView mImg;

		public ViewHolder(View view) {
			if (view != null) {
				mMidArtist = (TextView) view.findViewById(R.id.common_artistlist_artist_txt);
				mImg = (ImageView) view.findViewById(R.id.common_list_imge);
				mTime = (TextView) view.findViewById(R.id.common_list_time_txt);
				mArtist = (TextView) view.findViewById(R.id.common_list_artist_txt);
				mSonger = (TextView) view.findViewById(R.id.common_list_song_txt);
				mNumber = (TextView) view.findViewById(R.id.common_list_number_txt);
				mLinearLayout = (LinearLayout) view.findViewById(R.id.common_list_song_artist_layout);
			}
		}

}
