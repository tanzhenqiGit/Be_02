package com.be02.aidl;

import com.be02.aidl.MusicItem;
import com.be02.aidl.IMusicListener;

interface IMusicService
{
	int play();
	int pause();
	int next();
	int previous();
	int seek(int position);
	int setCurMusicList(out List<MusicItem> list);
	int setCurPlayIndex(int index);
	int setFavoriteStsChange();
	int registerListener(IMusicListener listener);
	int removeListener(IMusicListener listener);
	int getCurPlayMode();
	int getCurPlayStatus();
}