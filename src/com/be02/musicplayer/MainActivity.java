package com.be02.musicplayer;

import com.be02.aidl.MusicItem;
import com.be02.aidl.MusicListener;
import com.be02.data.MusicCmd;
import com.be02.data.MusicLog;
import com.be02.service.MusicServiceConnection;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        // Inflate the menu; this adds items to the action bar if it is present.
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
    	mSongName = (TextView) findViewById(R.id.main_song_name);
    	if (mSongName != null) {
    		
    	}
    	mSingerName = (TextView) findViewById(R.id.main_singer_name);
    	if (mSingerName != null) {
    		
    	}
    	mAlbumName = (TextView) findViewById(R.id.main_album_name);
    	if (mAlbumName != null) {
    		
    	}
    	mStartTime = (TextView) findViewById(R.id.main_start_time_txt);
    	if (mStartTime != null) {
    		
    	}
    	mTotleTime = (TextView) findViewById(R.id.main_total_time_txt);
    	if (mTotleTime != null) {
    		
    	}
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
    
    private void updateText(MusicItem item)
    {
    	mSongName.setText(item.getmTitle());
    	mAlbumName.setText(item.getmAblum());
    	mSingerName.setText(item.getmArtist());
    	mTotleTime.setText(item.converToStringTime(item.getmTime()));
    }
    
    private Handler mHandle = new Handler()
    {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what)
			{
			case MusicListener.MSG_SONG_UPDATE:
				MusicItem item = (MusicItem)msg.obj;
				updateText(item);
				break;
			case MusicListener.MSG_TIME_UPDATE:
			
				break;
			default:
				
				break;
			}
		}
    	
    };
    
    private TextView mSongName, mSingerName, mAlbumName;
    private final String SUB_TAG = MainActivity.class.toString() + " ";
    private TextView mStartTime, mTotleTime;
    ImageView mFavoriteImg;
    private LinearLayout mMainReturn, mMainList, mFavorite;
    private LinearLayout mAudioSetting, mAudioPrevious, mAudioPlay, mAudioPlayNext,mAudioCycleMode;
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
				
				break;
			case R.id.main_audio_next:
				
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

}
