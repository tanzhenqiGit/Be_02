package com.be02.aidl;

import com.be02.aidl.MusicItem;
import com.be02.aidl.IMusicListener;

interface IMusicService
{
	int play();
	int pause();
	int next();
	int previous();
	int setCurMusicList(out List<MusicItem> list);
	int setCurPlayIndex(int index);
	int registerListener(IMusicListener listener);
	int removeListener(IMusicListener listener);
	int getCurPlayMode();
	int getCurPlayStatus();
}