package com.be02.aidl;

import com.be02.aidl.MusicItem;

interface IMusicService
{
	int play();
	int pause();
	int next();
	int previous();
	int getMusicList(out List<MusicItem> list);
}