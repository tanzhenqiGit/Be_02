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
import com.be02.aidl.MusicListItem;
import com.be02.aidl.MusicListener;
import com.be02.data.MusicApplication;
import com.be02.data.MusicLog;
import com.be02.data.adapter.FolderListAdapter;
import com.be02.data.db.DBManager;
import com.be02.musicplayer.fragment.FavoriteMusicFragment;
import com.be02.musicplayer.fragment.LocalMusicFragment;
import com.be02.musicplayer.fragment.NetMusicFragment;
import com.be02.musicplayer.fragment.UserMusicFragment;
import com.be02.service.MusicServiceConnection;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

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
	public void onAttachFragment(Fragment fragment) {
		MusicLog.d(SUB_TAG + "onAttachFragment");
		super.onAttachFragment(fragment);
	}

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initialize();
		startAndBindService();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		MusicListItem item = mList.get(mCurLongPressPos);
		menu.setHeaderIcon(item.getImageId()).setHeaderTitle(item.getmName());
		menu.add(0,1, Menu.NONE,getString(R.string.play));
		menu.add(0,2, Menu.NONE,getString(R.string.delete));
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		int id = item.getItemId();
		MusicLog.d(SUB_TAG + "onContextItemSelected id =" + id);
		if (id == 1) {
			
		} else if (id == 2) {
			MusicListItem i = mList.get(mCurLongPressPos);
			try {
				mConnection.getMusicProxy().deleteLocalList(i);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} else {
			
		}
		return super.onContextItemSelected(item);
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
	
	
	private View getFooterView()
	{
		View view = LayoutInflater.from(this).inflate(R.layout.music_list_item, null);
		ImageView image = (ImageView) view.findViewById(R.id.music_list_item_img);
		TextView txt = (TextView) view.findViewById(R.id.music_list_item_txt);
		image.setImageResource(R.drawable.adcx);
		txt.setText(R.string.local_music_list);
		return view;
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
			mListView.addFooterView(getFooterView());
			mListView.setOnItemClickListener(mItemClickListener);
			mListView.setOnItemLongClickListener(mLongListener);
		} else {
			MusicLog.d(SUB_TAG + "initialize mListView == null");
		}
		initializeFragment();
		if (mLocalMusicFragment != null) {
			replaceFragment(mLocalMusicFragment);
		}
	}
	
	private void updateLocalList()
	{
		mList.clear();
		MusicListItem item1 = new MusicListItem(R.drawable.local_music, 
				getString(R.string.local_music_list));
		MusicListItem item2 = new MusicListItem(R.drawable.favorite_music, 
				getString(R.string.favorite_music_list));
		MusicListItem item3 = new MusicListItem(R.drawable.net_music_list, 
				getString(R.string.network_music_list));
		
		if(mList != null) {
			mList.add(item1);
			mList.add(item2);
			mList.add(item3);
		}
		mLocalList = DBManager.getInstance(MusicApplication.getInstance()).getLocalList();
		MusicLog.d(SUB_TAG + "updateLocalList size:" + mLocalList.size());
		if (mLocalList != null) {
			for (MusicListItem i : mLocalList) {
				if (!mList.contains(i)) {
					mList.add(i);
				}
			}
		} else {
			MusicLog.d(SUB_TAG + "mLocalList is null");
		}
	}
	
	private void initializeList()
	{
		mList = new ArrayList<MusicListItem>();
		updateLocalList();

	}
	
	private void initializeFragment()
	{
		mLocalMusicFragment = new LocalMusicFragment();
		mFavoriteMusicFragment = new FavoriteMusicFragment();
		mNetMusicFragment = new NetMusicFragment();
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
    	mConnection.setListener(new MusicListener(mHandler));
    	bindService(intentBind, mConnection, 0);
    }
    
    private void unBindService()
    {
    	MusicLog.d(SUB_TAG + "unBindService");
    	unbindService(mConnection);
    }
	
    private void onCreateLocalList()
    {
    	final View view = LayoutInflater.from(this).inflate(R.layout.local_music_dialog, null);
    	new AlertDialog.Builder(this)
    		.setTitle(getString(R.string.new_music_list))
    		.setView(view)
    		.setPositiveButton(getString(R.string.sure), new OnClickListener() {
    			EditText et = (EditText) view.findViewById(R.id.local_music_dialog_et);
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					try {
						MusicListItem item = new MusicListItem(et.getText().toString());
						if (!mList.contains(item)) {
							MusicLog.d(SUB_TAG + "add local list item = " +  item.getmName());
							mConnection.getMusicProxy().addLocalList(item);
						} else {
							new AlertDialog.Builder(MusicListActivity.this).setTitle(getString(R.string.warn))
							.setMessage(getString(R.string.current_list_exist))
							.setPositiveButton(getString(R.string.sure), null)
							.create()
							.show();
						}
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			})
    		.setNegativeButton(getString(R.string.cancel),  new OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int arg1) {
					dialog.dismiss();
				}
    			
    		})
    		.create()
    		.show();
    }
    
    private void OnUserLocalClicked(int pos)
    {
    	MusicListItem item = mList.get(pos);
    	UserMusicFragment user = new UserMusicFragment();
    	user.setListName(item.getmName());
    	replaceFragment(user);
    }
    
    private OnItemClickListener mItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adater, View arg1, int pos,long id) {
			mAdapter.setSelectPos(pos);
			switch (pos) {
			case LOCAL_MUSIC_LIST:
				replaceFragment(mLocalMusicFragment);
				break;
			case FAVORITE_MUSIC_LOST:
				replaceFragment(mFavoriteMusicFragment);
				break;
			case NET_MUSIC_LIST:
				replaceFragment(mNetMusicFragment);
				break; 
			default:
				// case must constant 
				int size = -1;
				if (mList != null) {
					size = mList.size();
				}
				if (pos == size) {
					onCreateLocalList();
				} else {
					OnUserLocalClicked(pos);
				}
				break;
			}
		}
    	
	};
	
	@SuppressLint("HandlerLeak") private Handler mHandler = new Handler()
	{
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MusicListener.MSG_LOCAL_LIST_UPDATE:
				if (mAdapter != null) {
					updateLocalList();
					MusicLog.d(SUB_TAG + "local list update size=" + mList.size());
					mAdapter.notifyDataSetChanged();
				}
				break;
			default:
				break;
			}
		};
	};
	private OnItemLongClickListener mLongListener = new OnItemLongClickListener() {
		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long id) {
			if (position > NET_MUSIC_LIST && position != mList.size()) {
				MusicLog.d(SUB_TAG + "OnItemLongClickListener onItemLongClick ");
				registerForContextMenu(mListView);
				mCurLongPressPos = position;
			} 
			else {
				MusicLog.d(SUB_TAG + "OnItemLongClickListener ------------ onItemLongClick ");
				unregisterForContextMenu(mListView);
				mCurLongPressPos = -1;
			}
			return false;
		}
	};
	
	private Fragment mLocalMusicFragment, mFavoriteMusicFragment, mNetMusicFragment,mCurrentFragment = null;
	private MusicServiceConnection mConnection = new MusicServiceConnection();
	private final String SUB_TAG = "MusicListActivity ";
	private FolderListAdapter mAdapter;
	private List<MusicListItem> mList;
	private List<MusicListItem> mLocalList;
	private ListView mListView;
	private int mCurLongPressPos = -1;
	
	private final int LOCAL_MUSIC_LIST = 0;
	private final int FAVORITE_MUSIC_LOST = 1;
	private final int NET_MUSIC_LIST = 2;
	
}
