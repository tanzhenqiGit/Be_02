/**
 * @author tan_zhenq E-mail: tan_zhenqi@163.com
 * @date 创建时间：2015-7-17 上午9:32:58 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
package com.be02.musicplayer.fragment;

import java.util.ArrayList;
import java.util.List;

import com.be02.aidl.MusicItem;
import com.be02.data.MusicApplication;
import com.be02.data.MusicLog;
import com.be02.data.adapter.MusicListAdapter;
import com.be02.data.db.DBManager;
import com.be02.musicplayer.R;

import android.app.Activity;
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
public class UserMusicFragment extends Fragment {

	@Override
	public void onAttach(Activity activity) {
		MusicLog.d(SUB_TAG + "onAttach");
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		MusicLog.d(SUB_TAG + "onCreate");
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
		MusicLog.d(SUB_TAG + "onDestroy");
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		MusicLog.d(SUB_TAG + "onDestroyView");
		super.onDestroyView();
	}
	
	public void setListName(String name) {
		mListName = name;
	}
	
	private void initialize()
	{
		mListView = (ListView) mView.findViewById(R.id.local_common_list_view);
		mFrameLayout = (FrameLayout) mView.findViewById(R.id.local_common_layout);
		mListItem = DBManager.getInstance(MusicApplication.getInstance()).getCommonListByName(mListName);
		if (mListView != null && mListItem != null && mListItem.size() != 0) {
			mAdapter = new MusicListAdapter(mListItem, getActivity());
			mListView.setAdapter(mAdapter);
			mFrameLayout.setVisibility(View.GONE);
		} else {
			mFrameLayout.setVisibility(View.VISIBLE);
		}
		
	}
	
	private View mView;
	
	private final String SUB_TAG = "UserMusicFragment";
	private String mListName;
	private List<MusicItem> mListItem;
	private MusicListAdapter mAdapter;
	private ListView mListView;
	private FrameLayout mFrameLayout;
}
