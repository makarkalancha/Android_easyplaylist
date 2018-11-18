package com.makco.easyplaylist.engine;

import android.Manifest;
import android.app.Application;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Environment;

import com.google.common.collect.ImmutableList;

public class App extends Application{
	private static App singleton;
	
//	public static final String MUSIC_PATH = "/storage/emulated/0/Music/!_test";
	public static final String MUSIC_PATH = Environment.getExternalStorageDirectory().toString()+"/Music/!_test";
	public static final int SDK_VERSION = Build.VERSION.SDK_INT;
	public static final ImmutableList<String> PERMISSIONS = ImmutableList.of(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    );

	public App getInstance(){
		return singleton;
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
	 
	@Override
	public void onCreate() {
		super.onCreate();
		singleton = this;
	}
	 
	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}
	 
	@Override
	public void onTerminate() {
		super.onTerminate();
	}
}
