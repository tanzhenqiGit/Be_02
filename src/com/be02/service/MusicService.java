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
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import com.be02.aidl.IMusicListener;
import com.be02.aidl.IMusicService;
import com.be02.aidl.MusicItem;
import com.be02.data.ErrorCode;
import com.be02.data.MusicApplication;
import com.be02.data.MusicCmd;
import com.be02.data.MusicLog;
import com.be02.data.db.DBManager;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
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
		MusicLog.d(SUB_TAG + "onStartCommand startId=" + startId + ",flags=" + flags);
		if (intent != null) {
			Bundle bundle = intent.getExtras();
			if ((bundle != null) && (MusicCmd.PLAY.equals(bundle.get(MusicCmd.KEY)))) {
				MusicLog.d(SUB_TAG + "start need play");
				play();
			}
		}
		return super.onStartCommand(intent, flags, startId);
	}

	private int play()
	{
		MusicItem item;
		if (getPlayStatus() == MUSIC_PAUSE && !mMediaPlayer.isPlaying()) {
			MusicLog.d(SUB_TAG + "current is pause just start");
			mMediaPlayer.start();
			updatePlayStatus(MUSIC_PLAY);
			return ErrorCode.NoError;
		}
		if (mCurMusicList != null) {
			if (mCurPlayerIndex < mCurMusicList.size()) {
				item = mCurMusicList.get(mCurPlayerIndex);
				try {
					if (mMediaPlayer.isPlaying()) {
						mMediaPlayer.pause();
						mMediaPlayer.stop();
					}
					
					mMediaPlayer.reset();
					MusicLog.d(SUB_TAG + "prepare set source and start " + item.getmUri());
					mMediaPlayer.setDataSource(item.getmUri());
					mMediaPlayer.prepare();
					mMediaPlayer.start();
					updatePlayStatus(MUSIC_PLAY);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					MusicLog.d(SUB_TAG + "*****catch IllegalArgumentException *****" + e.toString());
				} catch (SecurityException e) {
					e.printStackTrace();
					MusicLog.d(SUB_TAG + "*****catch SecurityException *****" + e.toString());
				} catch (IllegalStateException e) {
					e.printStackTrace();
					MusicLog.d(SUB_TAG + "*****catch IllegalStateException *****" + e.toString());
				} catch (IOException e) {
					e.printStackTrace();
					MusicLog.d(SUB_TAG + "*****catch IOException *****" + e.toString());
				}
			}
		} else {
			MusicLog.d(SUB_TAG + "play mCurMusicList == null do nothing.");
			return ErrorCode.NullPointError;
		}
		
		return ErrorCode.NoError;
	}
	
	private int pause()
	{
		if (mMediaPlayer.isPlaying()) 
		{
			updatePlayStatus(MUSIC_PAUSE);
			mMediaPlayer.pause();
		}
		return ErrorCode.NoError;
	}
	private int next()
	{
		if (mCurMusicList == null) {
			return ErrorCode.NullPointError;
		} 
		mCurPlayerIndex++;
		if (mCurPlayerIndex == mCurMusicList.size()) {
			mCurPlayerIndex = 0;
		}
		updatePlayStatus(MUSIC_COMPLETED);
		play();
		return ErrorCode.NoError;
	}
	private int previous()
	{
		
		if (mCurMusicList == null) {
			return ErrorCode.NullPointError;
		} 
	
		if (mCurPlayerIndex == 0) {
			mCurPlayerIndex = mCurMusicList.size() - 1;
		}
		mCurPlayerIndex--;
		updatePlayStatus(MUSIC_COMPLETED);
		play();
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

		@Override
		public int setCurMusicList(List<MusicItem> list) throws RemoteException {
			if (list != null && list.size() > 0) {
				mCurMusicList = list;
				return ErrorCode.NoError;
			}
			return ErrorCode.NullPointError;
		}
		
		@Override
		public int setCurPlayIndex(int index) throws RemoteException {
			mCurPlayerIndex = index;
			updatePlayStatus(MUSIC_COMPLETED);
			play();
			return ErrorCode.NoError;
		}
		
		@Override
		public int registerListener(IMusicListener listener) {
			if (listener  == null) {
				return ErrorCode.NoError;
			}
			MusicBinderListener l = new MusicBinderListener(listener);
			try {
				listener.asBinder().linkToDeath(l, 0);
				mListeners.add(l);
			} catch (RemoteException e) {
				e.printStackTrace();
			}

			return ErrorCode.NoError;
		}
		
		@Override
		public int removeListener(IMusicListener listener) {
			for (MusicBinderListener l : mListeners)
			{
				if (l.mListener.asBinder().equals(listener.asBinder())) {
					mListeners.remove(l);
					l.mListener.asBinder().unlinkToDeath(l, 0);
					break;
				}
			}
			return 0;
		}
		@Override
		public int getCurPlayMode() throws RemoteException {
			return 0;
		}
		@Override
		public int getCurPlayStatus() throws RemoteException {
			MusicService service = mExtendService.get();
			if (service != null) {
				return service.getPlayStatus();
			}
			return ErrorCode.NullPointError;
		}

	}
	
	
	private void initialize()
	{
		DBManager.getInstance(MusicApplication.getInstance());
		mMusicBn = new MusicBn(this);
		mMediaPlayer = new MediaPlayer();
		if (mMediaPlayer != null) {
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
			mMediaPlayer.setOnErrorListener(mOnErrorListener);
			mMediaPlayer.setOnInfoListener(mOnInfoListener);
			mMediaPlayer.setOnPreparedListener(mOnPreparedListener);
		}
		
		mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (mAudioManager == null) {	
			MusicLog.e(SUB_TAG + "initialize mAudioManager == null , do nothing");
		}
	}
	
	
	private MediaPlayer.OnCompletionListener mOnCompletionListener 
		= new MediaPlayer.OnCompletionListener() {
		
		@Override
		public void onCompletion(MediaPlayer mediaPlayer) {
			MusicLog.d(SUB_TAG + "*** onCompletion *** mediaPlayer=" + mediaPlayer);
			updatePlayStatus(MUSIC_COMPLETED);
			next();
		}
	};
	
	private MediaPlayer.OnErrorListener mOnErrorListener = new MediaPlayer.OnErrorListener() {
		
		@Override
		public boolean onError(MediaPlayer mediaPlayer, int arg1, int arg2) {
			MusicLog.d(SUB_TAG + "*** OnErrorListener *** onError mediaPlayer=" + 
					mediaPlayer+ arg1 + ":" + arg2);
			mediaPlayer.reset();
			updatePlayStatus(MUSIC_ERROR);
			return false;
		}
	};
	
	private MediaPlayer.OnInfoListener mOnInfoListener = new MediaPlayer.OnInfoListener() {
		
		@Override
		public boolean onInfo(MediaPlayer mediaPlayer, int arg1, int arg2) {
			MusicLog.d(SUB_TAG + "*** OnInfoListener *** onInfo mediaPlayer =" + 
					mediaPlayer + arg1 + ":" + arg2);
			return false;
		}
	};
	
	private MediaPlayer.OnPreparedListener mOnPreparedListener = new MediaPlayer.OnPreparedListener() {
		
		@Override
		public void onPrepared(MediaPlayer mediaPlayer) {
			MusicLog.d(SUB_TAG + "*** onPrepared *** mediaPlayer =" + mediaPlayer);
			MusicItem item = mCurMusicList.get(mCurPlayerIndex);
			updateSongInfo(item);
			updateCurPosition(0);
			updatePlayDuration(mediaPlayer.getDuration());
			
		}
	};

	AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
			new AudioManager.OnAudioFocusChangeListener() {
				
				@Override
				public void onAudioFocusChange(int status) {
					MusicLog.d(SUB_TAG + "OnAudioFocusChangeListener onAudioFocusChange status =" 
				+ status);
					
				}
	};
	
	private final class MusicBinderListener implements IBinder.DeathRecipient
	{
		public MusicBinderListener(IMusicListener listener)
		{
			mListener = listener;
		}

		@Override
		public void binderDied() {
			synchronized (mListener) {
				mListeners.remove(this);
				mListener.asBinder().unlinkToDeath(this, 0);
			}
		}
		private IMusicListener mListener;
	}
	private void updateSongInfo(MusicItem item)
	{
		if (mHandle != null) {
			Message msg = mHandle.obtainMessage();
			msg.what = MSG_SONG_INFO_CHANGED;
			msg.obj = item;
			mHandle.sendMessage(msg);
		}
	}
	private void onSongInfoChagned(Message msg)
	{
		MusicItem item = (MusicItem) msg.obj;
		for (MusicBinderListener l: mListeners) {
			try {
				l.mListener.onSongChanged(item);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void updateCurPosition(long delayMillis)
	{
		MusicItem item = mCurMusicList.get(mCurPlayerIndex);
		MusicLog.d(SUB_TAG + "updateCurPosition time:" + MusicItem.converToStringTime(item.getmTime())
				+ ",duration:"+  MusicItem.converToStringTime(mMediaPlayer.getDuration()));
		if (mHandle != null) {
			Message msg = mHandle.obtainMessage();
			msg.what = MSG_PLAY_TIME_UPDATE;
			mHandle.sendMessageDelayed(msg, delayMillis);
		}
	}
	
	private void onCurPositionChagned()
	{
		if (mMediaPlayer.isPlaying()) {
			updateCurPosition(1 * 1000);
		}
		int time =  mMediaPlayer.getCurrentPosition();
		for (MusicBinderListener l: mListeners) {
			try {
				l.mListener.onTimeChanged(time);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	private void updatePlayStatus(int status)
	{
		if (status == MUSIC_PLAY) {
			updateCurPosition(0);
		}
		
		if (mPlayStatus != status) {
	
			mPlayStatus = status;
			if (mHandle != null) {
				Message msg = mHandle.obtainMessage();
				msg.what = MSG_PLAY_STATUS_CHANGED;
				msg.arg1 = status;
				mHandle.sendMessage(msg);
			}
		}
	}
	
	private void onPlayStatusChanged(Message msg)
	{
		int status = msg.arg1;
		for (MusicBinderListener l: mListeners) {
			try {
				l.mListener.onPlayStatusChanged(status);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void updatePlayDuration(int duration)
	{
		if (mHandle != null) {
			Message msg = mHandle.obtainMessage();
			msg.what = MSG_PLAY_DURATION_CHANGED;
			msg.arg1 = duration;
			mHandle.sendMessage(msg);
		}
	}
	
	private void onDurationChanged(Message msg)
	{
		int duration = msg.arg1;
		for (MusicBinderListener l: mListeners) {
			try {
				l.mListener.onDurationChanged(duration);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void updatePlayMode(int mode)
	{
		if (mHandle != null) {
			Message msg = mHandle.obtainMessage();
			msg.what = MSG_PLAY_MODE_CHANGED;
			msg.arg1 = mode;
			mHandle.sendMessage(msg);
		}
	}
	
	private void onPlayModeChanged(Message msg)
	{
		int mode = msg.arg1;
		for (MusicBinderListener l: mListeners) {
			try {
				l.mListener.onPlayModeChanged(mode);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	@SuppressLint("HandlerLeak") private Handler mHandle = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case MSG_SONG_INFO_CHANGED:
				onSongInfoChagned(msg);
				break;
			case MSG_PLAY_TIME_UPDATE:
				onCurPositionChagned();
				break;
			case MSG_PLAY_STATUS_CHANGED:
				onPlayStatusChanged(msg);
				break;
			case MSG_PLAY_DURATION_CHANGED:
				onDurationChanged(msg);
				break;
			case MSG_PLAY_MODE_CHANGED:
				onPlayModeChanged(msg);
				break;
			default: break;
			}
		}
		
	};
	
	public int getPlayStatus()
	{
		return mPlayStatus;
	}
	
	private ArrayList<MusicBinderListener> mListeners 
		= new ArrayList<MusicService.MusicBinderListener>();
	private final String SUB_TAG = MusicService.class.toString()+ " ";
	private MusicBn mMusicBn;
	private MediaPlayer mMediaPlayer;
	private AudioManager mAudioManager;
	private List<MusicItem> mCurMusicList = null;
	private int mCurPlayerIndex = 0;
	
	
	private final int MSG_SONG_INFO_CHANGED = 0x01;
	private final int MSG_PLAY_TIME_UPDATE = 0x02;
	private final int MSG_PLAY_MODE_CHANGED = 0x03;
	private final int MSG_PLAY_STATUS_CHANGED = 0x04;
	private final int MSG_PLAY_DURATION_CHANGED = 0x05;
	
	/**
	 * define music play status
	 */
	
	public final static int MUSIC_PLAY = 0;
	public final static int MUSIC_PAUSE = 1;
	public final static int MUSIC_COMPLETED = 2;
	public final static int MUSIC_ERROR = 3;
	public final static int MUSIC_UNKNOW = 10;
	private int mPlayStatus = MUSIC_UNKNOW;
	
	/**
	 * define music play mode type
	 */
	public final static int MODE_ORDER_LOOP = 0;
	public final static int MODE_SINGLE_LOOP = 1;
	public final static int MODE_RANDOM_LOOP = 2;
	public final static int MODE_LIST_LOOP = 4;
	
}
