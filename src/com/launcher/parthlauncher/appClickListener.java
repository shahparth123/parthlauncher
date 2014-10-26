package com.launcher.parthlauncher;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class appClickListener implements OnClickListener{

	MainActivity.Pac[] pacsForListener;
	Context mContext;
	
	public appClickListener(MainActivity.Pac[] pacs,Context context) {
	// TODO Auto-generated constructor stub
		pacsForListener=pacs;
		mContext=context;
}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String[] data;
		data=(String[])v.getTag();
		Intent launchIntent=new Intent(Intent.ACTION_MAIN);
		launchIntent.addCategory(Intent.CATEGORY_LAUNCHER);			
		ComponentName cp= new ComponentName(data[0], data[1]);
		launchIntent.setComponent(cp);
		mContext.startActivity(launchIntent);
		
	}
	
}
