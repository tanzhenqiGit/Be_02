/**
 * @author tan_zhenq E-mail: tan_zhenqi@163.com
 * @date 创建时间：2015-7-7 上午11:08:16 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
package com.be02.data;

import android.util.Log;

/**
 * @author lz100
 *
 */
public final class MusicLog 
{
	private final static boolean DEBUG = true;
	private final static String TAG = "MusicPlayer";
	public static int d(String msg) 
	{
		if (DEBUG)
			return Log.d(TAG,msg);
		else 
			return 0;
	}
	public static int i(String msg) 
	{
		if (DEBUG)
			return Log.i(TAG,msg);
		else 
			return 0;
	}
	public static int e(String msg) 
	{
		if (DEBUG)
			return Log.e(TAG,msg);
		else 
			return 0;
	}
	public static int w(String msg) 
	{
		if (DEBUG)
			return Log.w(TAG,msg);
		else 
			return 0;
	}
}
