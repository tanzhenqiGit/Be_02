/**
 * @author tan_zhenq E-mail: tan_zhenqi@163.com
 * @date 创建时间：2015-7-13 下午4:11:48 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
package com.be02.aidl;

import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;

/**
 * @author lz100
 *
 */
public class MusicListener extends IMusicListener.Stub {

	public final static int MSG_SONG_UPDATE = 0x01;
	public final static int MSG_TIME_UPDATE= 0x02;
	
	public MusicListener(Handler handle)
	{
		mHandle = handle;
	}
	@Override
	public void onTimeChanged(int time) throws RemoteException 
	{
		if (mHandle != null) {
			Message msg = mHandle.obtainMessage();
			msg.what = MSG_TIME_UPDATE;
			msg.arg1 = time;
			mHandle.sendMessage(msg);
		}
	}

	@Override
	public void onSongChanged(MusicItem item) throws RemoteException 
	{
		if (mHandle != null) {
			Message msg = mHandle.obtainMessage();
			msg.what = MSG_SONG_UPDATE;
			msg.obj = item;
			mHandle.sendMessage(msg);
		}
	}
	
	private Handler mHandle;

}
