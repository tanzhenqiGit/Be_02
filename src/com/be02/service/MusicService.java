/**
 * @author tan_zhenq E-mail: tan_zhenqi@163.com
 * @date 创建时间：2015-7-8 下午3:38:30 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
package com.be02.service;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;

import com.be02.aidl.IMusicService;
import com.be02.data.ErrorCode;
import com.be02.data.MusicCmd;
import com.be02.data.MusicLog;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;


/**
 * @author lz100
 *
 */
public class MusicService extends Service {

	/* （非 Javadoc）
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent arg0) {
		MusicLog.d(SUB_TAG + "onBind");
		return mMusicBn;
	}

	@Override
	protected void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
		super.dump(fd, writer, args);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		MusicLog.d(SUB_TAG + "onCreate");
		initialize();
	}

	@Override
	public void onDestroy() {
		MusicLog.d(SUB_TAG + "onDestroy");
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		MusicLog.d(SUB_TAG + "onStartCommand");
		if (MusicCmd.PLAY.equals(intent.getExtras().get(MusicCmd.KEY))) {
			MusicLog.d(SUB_TAG + "start need play");
			play();
		}
		return super.onStartCommand(intent, flags, startId);
	}

	private int play()
	{
		return ErrorCode.NoError;
	}
	
	private int pause()
	{
		return ErrorCode.NoError;
	}
	private int next()
	{
		return ErrorCode.NoError;
	}
	private int previous()
	{
		return ErrorCode.NoError;
	}
	private class MusicBn extends IMusicService.Stub {

		public MusicBn(MusicService service)
		{
			mExtendService = new WeakReference<MusicService>(service);
		}
		@Override
		public int play() throws RemoteException {
			MusicLog.d(SUB_TAG + "MusicBn play");
			MusicService service = mExtendService.get();
			if (service != null) {
				return service.play();
			}
			return ErrorCode.NullPointError;
		}

		@Override
		public int pause() throws RemoteException {
			MusicLog.d(SUB_TAG + "MusicBn pause");
			MusicService service = mExtendService.get();
			if (service != null) {
				return service.pause();
			}
			return ErrorCode.NullPointError;
		}

		@Override
		public int next() throws RemoteException {
			MusicLog.d(SUB_TAG + "MusicBn next");
			MusicService service = mExtendService.get();
			if (service != null) {
				return service.next();
			}
			return ErrorCode.NullPointError;
		}

		@Override
		public int previous() throws RemoteException {
			MusicLog.d(SUB_TAG + "MusicBn previous");
			MusicService service = mExtendService.get();
			if (service != null) {
				return service.previous();
			}
			return ErrorCode.NullPointError;
		}
		
		WeakReference<MusicService> mExtendService;
	}
	
	
	private void initialize()
	{
		mMusicBn = new MusicBn(this);
	}
	
	private final String SUB_TAG = MusicService.class.toString()+ " ";
	private MusicBn mMusicBn;
	
	
}
