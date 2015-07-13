/**
 * @author tan_zhenq E-mail: tan_zhenqi@163.com
 * @date 创建时间：2015-7-2 下午5:24:33 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
package com.be02.musicplayer;

import java.util.ArrayList;
import java.util.List;

import com.be02.aidl.IMusicService;
import com.be02.data.MusicListItem;
import com.be02.data.MusicLog;
import com.be02.data.adapter.FolderListAdapter;
import com.be02.musicplayer.fragment.LocalMusicFragment;
import com.be02.service.MusicServiceConnection;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

/**
 * @author lz100
 *
 */
public class MusicListActivity extends FragmentActivity {

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
	}

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initialize();
		startAndBindService();
	}

	@Override
	public View onCreateView(String name, @NonNull Context context,
			@NonNull AttributeSet attrs) {
		return super.onCreateView(name, context, attrs);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unBindService();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	public IMusicService getServiceProxy()
	{
		return mConnection.getMusicProxy();
	}
	private void initialize()
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.music_list_activity);
		mListView = (ListView) findViewById(R.id.music_list_view);
		if (mListView != null) {
			initializeList();
			mAdapter = new FolderListAdapter(this, mList);
			mListView.setAdapter(mAdapter);
		} else {
			MusicLog.d(SUB_TAG + "initialize mListView == null");
		}
		initializeFragment();
		if (mLocalMusicFragment != null) {
			replaceFragment(mLocalMusicFragment);
		}

	}
	
	private void initializeList()
	{
		mList = new ArrayList<MusicListItem>();
		MusicListItem item1 = new MusicListItem(R.drawable.local_music, 
				getString(R.string.local_music_list));
		MusicListItem item2 = new MusicListItem(R.drawable.favorite_music, 
				getString(R.string.favorite_music_list));
		MusicListItem item3 = new MusicListItem(R.drawable.net_music_list, 
				getString(R.string.network_music_list));
		MusicListItem item4 = new MusicListItem(R.drawable.adcx, 
				getString(R.string.local_music_list));
		if(mList != null) {
			mList.add(item1);
			mList.add(item2);
			mList.add(item3);
			mList.add(item4);
		}

	}
	
	private void initializeFragment()
	{
		mLocalMusicFragment = new LocalMusicFragment();
	}
	
	private void replaceFragment(Fragment fragment)
	{
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		if (mCurrentFragment != null) {
			transaction.hide(mCurrentFragment);
		}
		if (fragment.isAdded()) {
			transaction.show(fragment).commit();
		} else {
			transaction.add(R.id.music_contain,fragment).show(fragment).commit();
		}
		mCurrentFragment = fragment;
	}
	
    private void startAndBindService()
    {
    	String actionName = "com.be02.service.MusicService";
    	Intent intentBind = new Intent(actionName);
    	bindService(intentBind, mConnection, 0);
    }
    
    private void unBindService()
    {
    	MusicLog.d(SUB_TAG + "unBindService");
    	unbindService(mConnection);
    }
	
	private ListView mListView;
	private FolderListAdapter mAdapter;
	private List<MusicListItem> mList;
	private Fragment mLocalMusicFragment, mCurrentFragment = null;
	private final String SUB_TAG = MusicListActivity.class.toString() + " ";
	private MusicServiceConnection mConnection = new MusicServiceConnection();
	
}
