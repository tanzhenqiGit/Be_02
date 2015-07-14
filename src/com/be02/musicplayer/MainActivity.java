package com.be02.musicplayer;

import com.be02.aidl.MusicItem;
import com.be02.aidl.MusicListener;
import com.be02.data.MusicCmd;
import com.be02.data.MusicLog;
import com.be02.service.MusicService;
import com.be02.service.MusicServiceConnection;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

@SuppressLint("HandlerLeak") public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MusicLog.d(SUB_TAG + "onCreate");
        initialize();
        startAndBindService();
    }


    @Override
	protected void onDestroy() {
		super.onDestroy();
		MusicLog.d(SUB_TAG + "onDestroy");
		unBindService();
		
	}


	@Override
	protected void onPause() {
		super.onPause();
		MusicLog.d(SUB_TAG + "onPause");
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    private void initialize()
    {
    	// set Window feature must before setContentview , if after may catch exception.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
    	mMainReturn = (LinearLayout) findViewById(R.id.main_layout_return);
    	if (mMainReturn != null) {
    		mMainReturn.setOnClickListener(mListener);
    	}
    	mMainList = (LinearLayout) findViewById(R.id.main_layout_list);
    	if (mMainList != null) {
    		mMainList.setOnClickListener(mListener);
    	}
    	mFavorite = (LinearLayout) findViewById(R.id.main_layout_favorite);
    	if (mFavorite != null) {
    		mFavorite.setOnClickListener(mListener);
    	}
    	mAudioSetting = (LinearLayout) findViewById(R.id.main_audio_setting);
    	if (mAudioSetting != null) {
    		mAudioSetting.setOnClickListener(mListener);
    	}
    	
    	mAudioPrevious = (LinearLayout) findViewById(R.id.main_audio_previous);
    	if (mAudioPrevious != null) {
    		mAudioPrevious.setOnClickListener(mListener);
    	}
    	
    	mAudioPlay = (LinearLayout) findViewById(R.id.main_audio_play);
    	if (mAudioPlay != null) {
    		mAudioPlay.setOnClickListener(mListener);
    	}
    	
    	mAudioPlayNext = (LinearLayout) findViewById(R.id.main_audio_next);
    	if (mAudioPlayNext != null) {
    		mAudioPlayNext.setOnClickListener(mListener);
    	}
    	
    	mAudioCycleMode = (LinearLayout) findViewById(R.id.main_audio_cycle_mode);
    	if (mAudioCycleMode != null) {
    		mAudioCycleMode.setOnClickListener(mListener);
    	}
    	mFavoriteImg = (ImageView) findViewById(R.id.main_favorite_image);
    	mAudioPlayImg = (ImageView) findViewById(R.id.main_audio_play_img);
    	mSongName = (TextView) findViewById(R.id.main_song_name);
    	mSingerName = (TextView) findViewById(R.id.main_singer_name);
    	mAlbumName = (TextView) findViewById(R.id.main_album_name);
    	mStartTime = (TextView) findViewById(R.id.main_start_time_txt);
    	mTotleTime = (TextView) findViewById(R.id.main_total_time_txt);
    	mSeekBar = (SeekBar) findViewById(R.id.main_seek_bar);
    	mMusicServiceListener = new MusicListener(mHandle);
    }
    
    private void startAndBindService()
    {
    	String actionName = "com.be02.service.MusicService";
    	Intent intentStart = new Intent();
    	intentStart.setAction(actionName);
    	intentStart.putExtra(MusicCmd.KEY, MusicCmd.PLAY);
    	startService(intentStart);
    	
    	Intent intentBind = new Intent(actionName);
    	mConnection.setListener(mMusicServiceListener);
    	bindService(intentBind, mConnection, 0);
    }
    
    private void unBindService()
    {
    	MusicLog.d(SUB_TAG + "unBindService");
    	unbindService(mConnection);
    }
    
    private void updateText(Message msg)
    {
		MusicItem item = (MusicItem)msg.obj;
    	if (item != null) {
	    	mSongName.setText(item.getmTitle());
	    	mAlbumName.setText(item.getmAblum());
	    	mSingerName.setText(item.getmArtist());
	    	mTotleTime.setText(MusicItem.converToStringTime(item.getmTime()));
	    	//mSeekBar.setMax(item.getmTime());
    	} else {
    		mSongName.setText("");
    		mAlbumName.setText("");
    		mSingerName.setText("");
    		mTotleTime.setText(MusicItem.converToStringTime(0));
    		//mSeekBar.setMax(0);
    		
    	}
    }
    private void updateTime(Message msg)
    {
    	int time = msg.arg1;
    	mStartTime.setText(MusicItem.converToStringTime(time));
    	mSeekBar.setProgress(time);
    }
    
    private void updatePlayStatus(Message msg)
    {
    	int status = msg.arg1;
    	if (status == MusicService.MUSIC_PLAY) {
    		mAudioPlayImg.setImageResource(R.drawable.main_audio_play);
    	} else {
    		mAudioPlayImg.setImageResource(R.drawable.main_audio_pause);
    	}
    }
    
    private Handler mHandle = new Handler()
    {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what)
			{
			case MusicListener.MSG_SONG_UPDATE:
				updateText(msg);
				break;
			case MusicListener.MSG_TIME_UPDATE:
				updateTime(msg);
				break;
			case MusicListener.MSG_DURATION_UPDATE:
				mSeekBar.setMax(msg.arg1);
				break;
			case MusicListener.MSG_PLAY_STATUS_UPDATE:
				updatePlayStatus(msg);
				break;
			case MusicListener.MSG_PLAY_MODE_UPDATE:
				
				break;
			default:
				
				break;
			}
		}
    	
    };
    
    private View.OnClickListener mListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.main_layout_return:
				finish();
				break;
			case R.id.main_layout_list:
				Intent list = new Intent(MainActivity.this, MusicListActivity.class);
				startActivity(list);
				break;
			case R.id.main_layout_favorite:
				
				break;
			case R.id.main_audio_setting:
				
				break;
			case R.id.main_audio_previous:
				try {
					mConnection.getMusicProxy().previous();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				break;
			case R.id.main_audio_play:
				try {
					int status = mConnection.getMusicProxy().getCurPlayStatus();
					if (status == MusicService.MUSIC_PLAY) {
						mConnection.getMusicProxy().pause();
					} else if (status == MusicService.MUSIC_PAUSE) {
						mConnection.getMusicProxy().play();
					} else {
						
					}
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				break;
			case R.id.main_audio_next:
				try {
					mConnection.getMusicProxy().next();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				break;
			case R.id.main_audio_cycle_mode:
				
				break;
			default:
				
				break;
			}
		}
	};
	
	private MusicServiceConnection mConnection = new MusicServiceConnection();
	private MusicListener mMusicServiceListener;
    private TextView mSongName, mSingerName, mAlbumName;
    private final String SUB_TAG = MainActivity.class.toString() + " ";
    private TextView mStartTime, mTotleTime;
    private SeekBar mSeekBar;
    private ImageView mFavoriteImg, mAudioPlayImg;
    private LinearLayout mMainReturn, mMainList, mFavorite;
    private LinearLayout mAudioSetting, mAudioPrevious, mAudioPlay, mAudioPlayNext,mAudioCycleMode;
}
