package com.bjlz.musicplayer;

import android.os.Bundle;
import android.app.Activity;
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
    }


    @Override
	protected void onDestroy() {
		// TODO 自动生成的方法存根
		super.onDestroy();
	}


	@Override
	protected void onPause() {
		// TODO 自动生成的方法存根
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
    	mSingerName = (TextView) findViewById(R.id.main_singer_name);
    	mAlbumName = (TextView) findViewById(R.id.main_album_name);
    	mStartTime = (TextView) findViewById(R.id.main_start_time_txt);
    	mTotleTime = (TextView) findViewById(R.id.main_total_time_txt);
    }
    
    private TextView mSongName, mSingerName, mAlbumName;
    private TextView mStartTime, mTotleTime;
    private ImageView mFavoriteImg;
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
}
