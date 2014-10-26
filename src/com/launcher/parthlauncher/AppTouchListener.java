package com.launcher.parthlauncher;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout.LayoutParams;

public class AppTouchListener implements OnTouchListener{
	int icon_size; 
	/*public AppTouchListener(int size) {
		// TODO Auto-generated constructor stub
		icon_size=size;
	
	}*/
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			LayoutParams lp=new LayoutParams(v.getWidth(), v.getHeight());
			lp.leftMargin =(int)event.getRawX()-v.getWidth()/2;
			lp.topMargin=(int)event.getRawY()-v.getHeight()/2;
			v.setLayoutParams(lp);
			
			break;
		case MotionEvent.ACTION_UP:
			v.setOnTouchListener(null);
			break;
		default:
			break;
		}
		return true;
	}

}
