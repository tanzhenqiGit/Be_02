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

import com.be02.aidl.IMusicService;
import com.be02.aidl.MusicItem;
import com.be02.data.MusicApplication;
import com.be02.data.MusicLog;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;

/**
 * @author lz100
 *
 */
public class LocalMusicSongFragment extends Fragment{

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
		MusicLog.e(SUB_TAG + "onDestroy");
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		MusicLog.e(SUB_TAG + "onDestroyView");
		super.onDestroyView();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof MusicListActivity) {
			mParent = (MusicListActivity)activity;
			MusicLog.d(SUB_TAG + "************ onAttach ************");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mParent = null;
		mServiceProxy = null;
		MusicLog.d(SUB_TAG + "onDetach");
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
			mListView.setOnItemClickListener(mItemClickListener);
		}
		
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
		if (list == null || list != mListData) {
			MusicLog.d(SUB_TAG + "************* list changed!*************");
			DBManager.getInstance(MusicApplication.getInstance()).setCurMusicList(mListData);
			try {
				mServiceProxy.setCurMusicList(mListData);
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
	
	private OnItemClickListener mItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int pos,
				long id) {
			handleOnItemClicked(pos);
		}
		
	};
	
	private final String SUB_TAG = LocalMusicFragment.class.toString() + " ";
	private View mView;
	private ListView mListView;
	private MusicListAdapter mAapter;
	private List<MusicItem> mListData;
	private FrameLayout mFrameLayout;
	private MusicListActivity mParent;
	private IMusicService mServiceProxy;
	
}
