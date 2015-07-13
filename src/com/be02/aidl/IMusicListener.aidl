package com.be02.aidl;

import com.be02.aidl.MusicItem;

oneway interface IMusicListener
{
    void onTimeChanged(int time);
    void onSongChanged(out MusicItem item);
}