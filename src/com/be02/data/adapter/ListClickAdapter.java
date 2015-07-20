/**
 * @author tan_zhenq E-mail: tan_zhenqi@163.com
 * @date 创建时间：2015-7-20 下午2:09:30 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
package com.be02.data.adapter;

import java.util.List;

import com.be02.aidl.IMusicService;
import com.be02.aidl.MusicItem;
import com.be02.aidl.MusicListItem;
import com.be02.data.MusicApplication;
import com.be02.data.db.DBManager;
import com.be02.musicplayer.MainActivity;
import com.be02.musicplayer.MusicListActivity;
import com.be02.musicplayer.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * @author lz100
 *
 */
public class ListClickAdapter {
	
	public ListClickAdapter(MusicListActivity parent,Fragment f, ListView listview)
	{
		mParent = parent;
		mFragment = f;
		mListView = listview;
	}
	
	public void OnItemLongClicked(int pos)
	{
		mFragment.registerForContextMenu(mListView);
		mCurrentIndex = pos;
	}
	
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
	{
		String title = " ";
		if (mList != null) {
			title = mList.get(mCurrentIndex).getmDisplayName();
		}
		menu.setHeaderTitle(title);
		menu.add(ID_GROUP_MENU, ID_ITEM_PLAY, Menu.NONE, R.string.play);
		menu.add(ID_GROUP_MENU, ID_ITEM_ADD_TO_LIST, Menu.NONE, R.string.add_to_play_lsit);
		menu.add(ID_GROUP_MENU, ID_ITEM_SET_CALL_RING, Menu.NONE, R.string.set_to_call_ring);
		menu.add(ID_GROUP_MENU, ID_ITEM_SONG_INFO, Menu.NONE, R.string.song_detail_info);
	}
	
	public boolean onContextItemSelected(MenuItem item)
	{
		if (item == null) {
			
		} else {
			switch (item.getItemId()) {
			case ID_ITEM_PLAY:
				return play();
			case ID_ITEM_ADD_TO_LIST:
				addToList();
				break;
			case ID_ITEM_SET_CALL_RING:
				break;
			case ID_ITEM_SONG_INFO:
				break;
			default: break;
			}
		}
		return false;
	}
	public void setList(List<MusicItem> list)
	{
		mList = list;
	}
	
	private boolean play()
	{
		if (mParent == null) {
			return false;
		}
		IMusicService proxy = mParent.getServiceProxy();
		try {
			proxy.setCurMusicList(mList);
			proxy.setCurPlayIndex(mCurrentIndex);
			mFragment.startActivity(new Intent(mParent, MainActivity.class));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	private void addToList()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(mParent);
		ListView lv = new ListView(mParent);
		List<MusicListItem> local = DBManager.getInstance(MusicApplication.getInstance()).getLocalList();
		LongClickListAdapter adapter = new LongClickListAdapter(mParent, local);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				
			}
			
		});
		MusicItem item = mList.get(mCurrentIndex);
		builder.setTitle("将" + item.getmDisplayName() + "添加到").setView(lv).create().show();
	}
	
	private ListView mListView;
	private MusicListActivity mParent;
	private Fragment mFragment;
	private List<MusicItem> mList;
	private int mCurrentIndex = -1;
	private final int ID_GROUP_MENU         = 0;
	private final int ID_ITEM_PLAY          = 0;
	private final int ID_ITEM_ADD_TO_LIST   = 1;
	private final int ID_ITEM_SET_CALL_RING = 2;
	private final int ID_ITEM_SONG_INFO     = 3;
}
