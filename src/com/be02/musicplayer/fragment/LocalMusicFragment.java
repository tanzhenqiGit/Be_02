/**
 * @author tan_zhenq E-mail: tan_zhenqi@163.com
 * @date 创建时间：2015-7-6 下午7:48:25 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
package com.be02.musicplayer.fragment;

import com.be02.data.MusicLog;
import com.be02.musicplayer.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

	private View mView;
}
