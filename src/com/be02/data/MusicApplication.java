/**
 * @author tan_zhenq E-mail: tan_zhenqi@163.com
 * @date 创建时间：2015-7-9 下午4:03:01 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
package com.be02.data;

import android.app.Application;

/**
 * @author lz100
 *
 */
public class MusicApplication extends Application {

	@Override
	public void onCreate() {
		MusicLog.d(SUB_TAG + "onCreate");
		super.onCreate();
		mApplication = this;
	}

	@Override
	public void onLowMemory() {
		MusicLog.d(SUB_TAG + "onLowMemory");
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		MusicLog.d(SUB_TAG + "onTerminate");
		super.onTerminate();
	}
	
	public static MusicApplication getInstance()
	{
		return mApplication;
	}
	
	public MusicApplication()
	{
		
	}
	
	static MusicApplication mApplication = null;
	private final String SUB_TAG = MusicApplication.class.toString() + " ";

}
