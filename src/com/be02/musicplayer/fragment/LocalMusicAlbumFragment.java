/**
 * @author tan_zhenq E-mail: tan_zhenqi@163.com
 * @date 创建时间：2015-7-8 上午10:05:36 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
package com.be02.musicplayer.fragment;

import java.util.List;

import com.be02.aidl.IMusicService;
import com.be02.aidl.MusicItem;
import com.be02.data.AlbumListItem;
import com.be02.data.MusicApplication;
import com.be02.data.MusicLog;
import com.be02.data.adapter.AlbumListAdapter;
import com.be02.data.adapter.MusicListAdapter;
import com.be02.data.db.DBManager;
import com.be02.musicplayer.MainActivity;
import com.be02.musicplayer.MusicListActivity;
import com.be02.musicplayer.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author lz100
 * 
 */
public class LocalMusicAlbumFragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		MusicLog.d(SUB_TAG + "onCrate");
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

	private void initialize() {
		if (mView == null) {
			MusicLog.e(SUB_TAG + "initialize mView == null");
			return;
		}
		mListView = (ListView) mView.findViewById(R.id.local_common_list_view);
		if (mListView != null) {
			mListData = DBManager.getInstance(MusicApplication.getInstance())
					.getAlbumList();
			if (mListData != null && mListData.size() > 0) {
				mAdapter = new AlbumListAdapter(getActivity(), mListData);
				mListView.setAdapter(mAdapter);
				mFrameLayout = (FrameLayout) mView
						.findViewById(R.id.local_common_framelayout);
				if (mFrameLayout != null) {
					mFrameLayout.setVisibility(View.GONE);
				}

			} else {
				MusicLog.e(SUB_TAG + "mListData is invalid");
			}
			mListView.setOnItemClickListener(mItemClickListener);
		} else {
			MusicLog.e(SUB_TAG + "mListView == null");
		}

	}
	
	private OnItemClickListener mItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int pos, long id) 
		{
			MusicLog.d(SUB_TAG + "pos =" + pos);
			AlbumListItem item = mListData.get(pos);
			mMusicList = DBManager.getInstance(MusicApplication.getInstance())
					.getAlbumList(item.getAblum());
			
			MusicLog.d(SUB_TAG + "list.size =" + mMusicList.size());
			MusicListAdapter adapter = new MusicListAdapter(mMusicList, getActivity());
			mListView.setAdapter(adapter);
			mListView.setOnItemClickListener(mSecondListListener);
		}
		
	};
	
	private OnItemClickListener mSecondListListener = new OnItemClickListener()	{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int pos,long id) {
			handleOnItemClicked(pos);
		}
		
	};

	@Override
	public void onAttach(Activity activity) {
		if (activity instanceof MusicListActivity) {
			mParent = (MusicListActivity) activity;
		}
		super.onAttach(activity);
	}

	@Override
	public void onDetach() {
		mParent = null;
		mServiceProxy = null;
		super.onDetach();
	}

	@Override
	public void onPause() {
		MusicLog.d(SUB_TAG + "onPause");
		super.onPause();
	}

	@Override
	public void onResume() {
		MusicLog.d(SUB_TAG + "onResume");
		super.onResume();
	}

	@Override
	public void onStart() {
		MusicLog.d(SUB_TAG + "onStart");
		super.onStart();
	}

	@Override
	public void onStop() {
		MusicLog.d(SUB_TAG + "onStop");
		super.onStop();
	}

	private void handleOnItemClicked(int pos)
	{
		mServiceProxy = mParent.getServiceProxy();
		List<MusicItem> list = DBManager.getInstance(MusicApplication.getInstance()).getCurMusicList();
		if (mServiceProxy == null) {
			MusicLog.e(SUB_TAG + "handleOnItemClicked mServiceProxy == null");
			return;
		}
		
		// 1 set music play list.
		if (list == null || list != mMusicList) {
			MusicLog.d(SUB_TAG + "************* list changed!*************");
			DBManager.getInstance(MusicApplication.getInstance()).setCurMusicList(mMusicList);
			try {
				mServiceProxy.setCurMusicList(mMusicList);
			} catch (RemoteException e) {
				e.printStackTrace();
				MusicLog.e(SUB_TAG + "e=" + e.toString());
			}
		}  else {
			MusicLog.d(SUB_TAG + "************* list not changed!*************");
		}
		// 2 set music play list index.
		try {
			mServiceProxy.setCurPlayIndex(pos);
		} catch (RemoteException e) {
			MusicLog.e(SUB_TAG + "e=" + e.toString());
		}
		// 3 go to music play activity
		Intent intent = new Intent(mParent, MainActivity.class);
		startActivity(intent);
		
	}
	private View mView;
	private final String SUB_TAG = "LocalMusicAlbumFragment ";
	private ListView mListView;
	private List<AlbumListItem> mListData;
	private List<MusicItem> mMusicList;
	private AlbumListAdapter mAdapter;
	private FrameLayout mFrameLayout;
	private MusicListActivity mParent;
	private IMusicService mServiceProxy;

}
