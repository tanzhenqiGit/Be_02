/**
 * @author tan_zhenq E-mail: tan_zhenqi@163.com
 * @date 创建时间：2015-7-8 上午10:03:40 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
package com.be02.musicplayer.fragment;

import java.util.List;

import com.be02.data.MusicApplication;
import com.be02.data.MusicLog;
import com.be02.data.adapter.ArtistListAdapter;
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
public class LocalMusicArtistFragment extends Fragment{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		MusicLog.d(SUB_TAG + "onCreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		MusicLog.d(SUB_TAG + "onCreateView");
		mView = inflater.inflate(R.layout.local_common_list_fragment, null);
		initialize();
		return mView;
	}

	@Override
	public void onDestroy() {
		MusicLog.d(SUB_TAG + "onDestroy");
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		MusicLog.d(SUB_TAG + "onDestroyView");
		super.onDestroyView();
	}
	
	private void initialize()
	{
		mListView = (ListView) mView.findViewById(R.id.local_common_list_view);
		if (mListView != null) {
			mListData = DBManager.getInstance(MusicApplication.getInstance()).getArtistList();
			if (mListData != null && mListData.size() > 0) {
				mAdapter = new ArtistListAdapter(this.getActivity(), mListData);
				mListView.setAdapter(mAdapter);
				mFrameLayout = (FrameLayout) mView.findViewById(R.id.local_common_framelayout);
				if (mFrameLayout != null) {
					mFrameLayout.setVisibility(View.GONE);
				}
			} else {
				mListView.setVisibility(View.GONE);
			}
		} else {
			MusicLog.e(SUB_TAG + "initialize mListView == null");
		}
	}
	private View mView;
	private final String SUB_TAG = LocalMusicArtistFragment.class.toString() + " ";
	private ListView mListView;
	private ArtistListAdapter mAdapter;
	private List<String> mListData;
	private FrameLayout mFrameLayout;
}
