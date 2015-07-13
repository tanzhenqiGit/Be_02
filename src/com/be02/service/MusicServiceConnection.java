/**
 * @author tan_zhenq E-mail: tan_zhenqi@163.com
 * @date 创建时间：2015-7-13 下午12:00:00 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
package com.be02.service;

import com.be02.aidl.IMusicService;
import com.be02.aidl.MusicListener;
import com.be02.data.MusicLog;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

/**
 * @author lz100
 *
 */
public final class MusicServiceConnection implements ServiceConnection {
	
	public void setListener(MusicListener listener)
	{
		mListener = listener;
	}

	public IMusicService getMusicProxy()
	{
		return mServiceProxy;
	}
	@Override
	public void onServiceDisconnected(ComponentName name) {
		MusicLog.d(SUB_TAG + "onServiceDisconnected name:" + name.toString());
		try {
			mServiceProxy.removeListener(mListener);
			mServiceProxy = null;
		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	
	}
	
	@Override
	public void onServiceConnected(ComponentName name, IBinder binder) {
		MusicLog.d(SUB_TAG + "onServiceConnected name:" + name);
		mServiceProxy = IMusicService.Stub.asInterface(binder);
		try {
			mServiceProxy.registerListener(mListener);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	private final String SUB_TAG = MusicServiceConnection.class.toString() + " ";
	private IMusicService mServiceProxy;
	private MusicListener mListener;
}
