/**

 * @author tan_zhenq E-mail: tan_zhenqi@163.com
 * @date 创建时间：2015-7-6 下午7:48:57 
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
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author lz100
 *
 */
public class FavoriteMusicFragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MusicLog.d(SUB_TAG + "onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		MusicLog.d(SUB_TAG + "onCreateView");
		mView = inflater.inflate(R.layout.favorite_music_fragment, null);
		initialize();
		return mView;
	}

	@Override
	public void onDestroy() {
		MusicLog.d(SUB_TAG + "onDestroy");
		super.onDestroy();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof MusicListActivity) {
			mParent = (MusicListActivity)activity;
		}
		
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mParent = null;
		mServiceProxy = null;
	}

	@Override
	public void onDestroyView() {
		MusicLog.d(SUB_TAG + "onDestroyView");
		super.onDestroyView();
	}
	
	private void initialize()
	{
		mListView = (ListView) mView.findViewById(R.id.favorite_music_fragment_list);
		mTxt = (TextView) mView.findViewById(R.id.favorite_music_fragment_txt);
		mList = DBManager.getInstance(MusicApplication.getInstance()).getFavoriteList();
		if (mList == null || mList.size() == 0) {
			mTxt.setVisibility(View.VISIBLE);
		} else {
			mTxt.setVisibility(View.GONE);
			MusicLog.d(SUB_TAG + "size=" + mList.size());
			MusicListAdapter adapter = new MusicListAdapter(mList, getActivity());
			mListView.setAdapter(adapter);
			mListView.setOnItemClickListener(mListener);
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
		if (list == null || list != mList) {
			MusicLog.d(SUB_TAG + "************* list changed!*************");
			DBManager.getInstance(MusicApplication.getInstance()).setCurMusicList(mList);
			try {
				mServiceProxy.setCurMusicList(mList);
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
	
	
	private OnItemClickListener mListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long item) {
			MusicLog.d(SUB_TAG + "onItemClick pos = " + pos);
			handleOnItemClicked(pos);
		}
		
	};
	
	private final String SUB_TAG = "FavoriteMusicFragment";
	private View mView;
	private TextView mTxt;
	private ListView mListView;
	private List<MusicItem> mList;
	private MusicListActivity mParent;
	private IMusicService mServiceProxy;

}
