package com.launcher.parthlauncher;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class ShortcutClickListener implements OnClickListener{

	Context mContext;
	
	public ShortcutClickListener(Context context) {
	// TODO Auto-generated constructor stub
		mContext=context;
}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent data;
		data =(Intent) v.getTag();
		mContext.startActivity(data);
		
	}
	
}
