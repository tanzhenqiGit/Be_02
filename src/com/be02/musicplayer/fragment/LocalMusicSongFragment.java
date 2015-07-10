/**
 * @author tan_zhenq E-mail: tan_zhenqi@163.com
 * @date 创建时间：2015-7-8 上午10:03:13 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
package com.be02.musicplayer.fragment;

import java.util.List;

import com.be02.aidl.MusicItem;
import com.be02.data.MusicApplication;
import com.be02.data.MusicLog;
import com.be02.data.adapter.MusicListAdapter;
import com.be02.data.db.DBManager;
import com.be02.musicplayer.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;

/**
 * @author lz100
 *
 */
public class LocalMusicSongFragment extends Fragment{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.local_common_list_fragment, null);
		initialize();
		return mView;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	private void initialize()
	{
		if (mView == null) {
			MusicLog.e(SUB_TAG + "initialize mView == null");
			return;
		}
		mFrameLayout = (FrameLayout) mView.findViewById(R.id.local_common_framelayout);
		mListView = (ListView) mView.findViewById(R.id.local_common_list_view);
		mListData = DBManager.getInstance(MusicApplication.getInstance()).getMusicList();
		if (mListData != null && mListData.size() > 0 && mFrameLayout != null) {
			mFrameLayout.setVisibility(View.GONE);
		}
		mAapter = new MusicListAdapter(mListData, getActivity());
		if (mListView != null) {
			mListView.setAdapter(mAapter);
		}
		
	}
	
	private final String SUB_TAG = LocalMusicFragment.class.toString() + " ";
	private View mView;
	private ListView mListView;
	private MusicListAdapter mAapter;
	private List<MusicItem> mListData;
	private FrameLayout mFrameLayout;
	
}
