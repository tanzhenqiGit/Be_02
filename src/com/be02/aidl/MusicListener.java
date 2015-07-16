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
	public final static int MSG_DURATION_UPDATE = 0x03;
	public final static int MSG_PLAY_STATUS_UPDATE = 0X04;
	public final static int MSG_PLAY_MODE_UPDATE = 0x05;
	public final static int MSG_FAVORITE_STS_UPDATE = 0x06;
	
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

	@Override
	public void onDurationChanged(int duration) throws RemoteException {
		if (mHandle != null) {
			Message msg = mHandle.obtainMessage();
			msg.what = MSG_DURATION_UPDATE;
			msg.arg1 = duration;
			mHandle.sendMessage(msg);
		}
	}
	@Override
	public void onPlayStatusChanged(int status) throws RemoteException {
		if (mHandle != null) {
			Message msg = mHandle.obtainMessage();
			msg.what = MSG_PLAY_STATUS_UPDATE;
			msg.arg1 = status;
			mHandle.sendMessage(msg);
		}
	}
	@Override
	public void onPlayModeChanged(int mode) throws RemoteException {
		if (mHandle != null) {
			Message msg = mHandle.obtainMessage();
			msg.what = MSG_PLAY_MODE_UPDATE;
			msg.arg1 = mode;
			mHandle.sendMessage(msg);
		}
	}
	private Handler mHandle;

	@Override
	public void onFavoriteStsChaned(int status) throws RemoteException {
		if (mHandle != null) {
			Message msg = mHandle.obtainMessage();
			msg.what = MSG_FAVORITE_STS_UPDATE;
			msg.arg1 = status;
			mHandle.sendMessage(msg);
		}
	}

}
