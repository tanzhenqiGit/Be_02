/**
 * @author tan_zhenq E-mail: tan_zhenqi@163.com
 * @date 创建时间：2015-7-6 下午7:48:25 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
package com.be02.musicplayer.fragment;

import java.util.ArrayList;
import java.util.List;

import com.be02.data.MusicLog;
import com.be02.musicplayer.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * @author lz100
 *
 */
public class LocalMusicFragment extends Fragment{

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		MusicLog.d("LocalMusicFragment onAttach");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MusicLog.d("LocalMusicFragment onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		MusicLog.d("LocalMusicFragment onCreateView");
		mView = inflater.inflate(R.layout.local_music, null);
		initialize();
		return mView;
	}

	@Override
	public void onDestroy() {
		MusicLog.d("LocalMusicFragment onDestroy");
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		MusicLog.d("LocalMusicFragment onDestroyView");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		MusicLog.d("LocalMusicFragment onDetach");
	}

	private void initialize()
	{
		if (mView == null) {
			MusicLog.d("LocalMusicFragment initialize mView == null , just return");
			return;
		}
		mSongList = (ImageView) mView.findViewById(R.id.local_music_song_list_img);
		if (mSongList != null) {
			mSongList.setOnClickListener(mListener);
		}
		mAblumList = (ImageView) mView.findViewById(R.id.local_music_album_list_img);
		if (mAblumList != null) {
			mAblumList.setOnClickListener(mListener);
		}
		mArtistList = (ImageView) mView.findViewById(R.id.local_music_artist_list_img);
		if (mArtistList != null) {
			mArtistList.setOnClickListener(mListener);
		}
		mScanList = (ImageView) mView.findViewById(R.id.local_music_scan_img);
		if (mScanList != null) {
			mScanList.setOnClickListener(mListener);
		}
		mSongFragment = new LocalMusicSongFragment();
		mArtistFragment = new LocalMusicArtistFragment();
		mAlbumFragment = new LocalMusicAlbumFragment();
		mList.add(mSongFragment);
		mList.add(mArtistFragment);
		mList.add(mAlbumFragment);
		mFragmentPagerAdapter = new FragmentPagerAdapter(getFragmentManager()) {
			
			@Override
			public int getCount() {
				return mList.size();
			}
			
			@Override
			public Fragment getItem(int position) {
				return mList.get(position);
			}
		};

		mViewPager = (ViewPager) mView.findViewById(R.id.local_music_view_pager);
		if (mViewPager != null) {
			mViewPager.setAdapter(mFragmentPagerAdapter);
			mViewPager.setOffscreenPageLimit(3);
			mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
				
				@Override
				public void onPageSelected(int index) {
					MusicLog.d(SUB_TAG + "onPageSelected arg0=" + index);
					updateImageSrc(index);
				}
				
				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					//MusicLog.d(SUB_TAG + "onPageScrolled arg0=" + arg0 + ",arg1=" + arg1 + ",arg2=" + arg2);
				}
				
				@Override
				public void onPageScrollStateChanged(int arg0) {
					//MusicLog.d(SUB_TAG + "onPageScrollStateChanged arg0=" + arg0);
				}
			});
		}
	}
	
	private void updateImageSrc(int index)
	{
		if (mSongList == null || mAblumList == null || mArtistList == null) {
			MusicLog.e(SUB_TAG + "updateimageSrc mSongList == null");
			return;
		}
		switch (index) {
		case 0:
			mSongList.setImageResource(R.drawable.default_music_list_d);
			mAblumList.setImageResource(R.drawable.album_music_list_u);
			mArtistList.setImageResource(R.drawable.artist_music_list_u);
			break;
		case 1:
			mSongList.setImageResource(R.drawable.default_music_list_u);
			mAblumList.setImageResource(R.drawable.album_music_list_u);
			mArtistList.setImageResource(R.drawable.artist_music_list_d);
			break;
		case 2:
			mSongList.setImageResource(R.drawable.default_music_list_u);
			mAblumList.setImageResource(R.drawable.album_music_list_d);
			mArtistList.setImageResource(R.drawable.artist_music_list_u);
			break;
		default: break;
		}
	}

	private void setCurrentItem(int index)
	{
		if (mViewPager != null || mList != null) {
			if (index == mViewPager.getCurrentItem() || index > mList.size()) {
				MusicLog.e(SUB_TAG + "setCurrentItem index=" + index);
				return;
			}
			mViewPager.setCurrentItem(index);
		}
	}
	
	private OnClickListener mListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.local_music_song_list_img:
				setCurrentItem(0);
				break;
			case R.id.local_music_artist_list_img:
				setCurrentItem(1);
				break;
			case R.id.local_music_album_list_img:
				setCurrentItem(2);
				break;
			case R.id.local_music_scan_img:
				
				break;
			default:
				
				break;
			}
			
		}
	};
	private final String SUB_TAG = this.getClass().toString();
	private View mView;
	private ImageView mSongList, mArtistList, mAblumList, mScanList;
	private ViewPager mViewPager;
	private List<Fragment> mList = new ArrayList<Fragment>();
	private Fragment mSongFragment, mArtistFragment, mAlbumFragment;
	private FragmentPagerAdapter mFragmentPagerAdapter;
}
