package com.be02.musicplayer;

import com.be02.aidl.IMusicService;
import com.be02.data.MusicCmd;
import com.be02.data.MusicLog;

import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        startAndBindService();
    }


    @Override
	protected void onDestroy() {
		super.onDestroy();
	}


	@Override
	protected void onPause() {
		super.onPause();
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
    }
    
    private void startAndBindService()
    {
    	String actionName = "com.be02.service.MusicService";
    	Intent intentStart = new Intent();
    	intentStart.setAction(actionName);
    	intentStart.putExtra(MusicCmd.KEY, MusicCmd.PLAY);
    	startService(intentStart);
    	
    	Intent intentBind = new Intent(actionName);
    	bindService(intentBind, mConnection, 0);
    }
    
    
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
	
	private IMusicService mServiceProxy;
	private ServiceConnection mConnection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			MusicLog.d(SUB_TAG + "onServiceDisconnected" + "name:" + name.toString());
			
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder binder) {
			MusicLog.d(SUB_TAG + "onServiceConnected name=" + name);
			mServiceProxy = IMusicService.Stub.asInterface(binder);
		}
	};
}
