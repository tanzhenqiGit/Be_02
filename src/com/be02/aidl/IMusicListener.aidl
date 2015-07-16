package com.be02.aidl;

import com.be02.aidl.MusicItem;

oneway interface IMusicListener
{
    void onTimeChanged(int time);
    void onSongChanged(out MusicItem item);
    void onDurationChanged(int duration);
    void onPlayStatusChanged(int status);
    void onPlayModeChanged(int mode);
    void onFavoriteStsChaned(int status);
}