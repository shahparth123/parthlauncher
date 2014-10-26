package com.launcher.parthlauncher;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class DrawerClickListener implements OnItemClickListener{

	Context mContext;
	MainActivity.Pac[] pacsForAdapter;
	PackageManager pmForListener;
	
	public DrawerClickListener(Context c,MainActivity.Pac[] pacs,PackageManager pm)
	{
		mContext =c;
		pacsForAdapter=pacs;
		pmForListener=pm;
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
		// TODO Auto-generated method stub
		if(MainActivity.appLaunchable)
		{
		//Intent launchIntent=pmForListener.getLaunchIntentForPackage(pacsForAdapter[pos].name);
		Intent launchIntent=new Intent(Intent.ACTION_MAIN);
		launchIntent.addCategory(Intent.CATEGORY_LAUNCHER);			
		ComponentName cp= new ComponentName(pacsForAdapter[pos].packageName, pacsForAdapter[pos].name);
		launchIntent.setComponent(cp);
		mContext.startActivity(launchIntent);
		}
	}

}
