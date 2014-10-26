package com.launcher.parthlauncher;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorListener{

	DrawerAdapter drawerAdapterObject;
	class Pac
	{
		Drawable icon;
		String name;
		String packageName; 
		String label;
		
	}
	
	Pac pacs[];
	PackageManager pm;
	AppWidgetManager mAppWidgetManager;
	LauncherAppWidgetHost mAppWidgetHost;
	static boolean appLaunchable=true;
	GridView drawerGrid;
	SlidingDrawer slidingDrawer;
	RelativeLayout homeView;
	int REQUEST_CREATE_APPWIDGET=900;
	int REQUEST_CREATE_SHORTCUT=700;
	int numWidgets;
	//extra
    static int state=0;
    SensorManager sm = null;
    final String tag = "MainActivity";

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mAppWidgetManager = AppWidgetManager.getInstance(this);
		mAppWidgetHost =new LauncherAppWidgetHost(this, R.id.APPWIDGET_HOST_ID);
		pm=getPackageManager();
		drawerGrid=(GridView) findViewById(R.id.content);
		slidingDrawer=(SlidingDrawer)findViewById(R.id.drawer);
		homeView=(RelativeLayout)findViewById(R.id.home_view);
		set_pacs();
		slidingDrawer.setOnDrawerOpenListener(new OnDrawerOpenListener() {
			
			@Override
			public void onDrawerOpened() {
				// TODO Auto-generated method stub
				appLaunchable=true;
			}
		});
		
		homeView.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View arg0) {
				// TODO Auto-generated method stub
				
				AlertDialog.Builder b=new AlertDialog.Builder(MainActivity.this);
				String[] items={getResources().getString(R.string.widget),getResources().getString(R.string.shortcut)};
				b.setItems(items, new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						switch (which) {
						case 0:
							selectWidget();
										
							break;
						case 1:
							selectShortcut();
							break;
						default:
							break;
						}
					}
				});
				AlertDialog d=b.create();
				d.show();
				return false;
			}
		});
		IntentFilter filter=new IntentFilter();
		filter.addAction(Intent.ACTION_PACKAGE_ADDED);
		filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
		filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
		filter.addDataScheme("package");
		registerReceiver(new PacReceiver(), filter);
		
		//extra
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);

		
	}
	
	
	/*extra*/
	public void onSensorChanged(int sensor, float[] values) {
        synchronized (this) {
            Log.d(tag, "onSensorChanged: " + sensor + ", x: " +
values[0] + ", y: " + values[1] + ", z: " + values[2]);
                       if (sensor == SensorManager.SENSOR_ACCELEROMETER) {

                if(((int)values[2]*10)==-90)
                {
                	if(state!=1)
                	{
                		state=1;
                	}
                }
                else if(((int)values[1]*10)==-90)
                {
                	if(state!=2)
                	{
                		state=2;
                	}
                }
                else if(((int)values[0]*10)==-90)
                {
                	if(state!=3)
                	{
                	state=3;
                	PackageManager pmForListener=getPackageManager();
                	Intent launchIntent=pmForListener.getLaunchIntentForPackage("com.android.calculator2");
            		startActivity(launchIntent);

                	}
                }
                else if(((int)values[2]*10)==90)
                {
                	if(state!=4)
                	{
                		state=4;

                		PackageManager pmForListener=getPackageManager();
                    	Intent launchIntent=pmForListener.getLaunchIntentForPackage("com.android.calendar");
                		startActivity(launchIntent);

                	}
                }
                else if(((int)values[1]*10)==90)
                {
                	if(state!=5)
                	{
                		state=5;
                	PackageManager pmForListener=getPackageManager();
                	Intent launchIntent=pmForListener.getLaunchIntentForPackage("com.google.android.gm");
            		startActivity(launchIntent);

                	}
                }
                else if(((int)values[0]*10)==90)
                {
                	if(state!=6)
                	{
                		state=6;
                	}
                }
            }
        }
    }


public void onAccuracyChanged(int sensor, int accuracy) {
    Log.d(tag,"onAccuracyChanged: " + sensor + ", accuracy: " + accuracy);
}
@Override
protected void onResume() {
    super.onResume();
  // register this class as a listener for the orientation and accelerometer sensors
    sm.registerListener(this,
            SensorManager.SENSOR_ORIENTATION |SensorManager.SENSOR_ACCELEROMETER,
            SensorManager.SENSOR_DELAY_NORMAL);
}

/*protected void onStop() {
    // unregister listener
    sm.unregisterListener(this);
    super.onStop();
}
	*/

void selectWidget() {
	    int appWidgetId = this.mAppWidgetHost.allocateAppWidgetId();
	    Intent pickIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_PICK);
	    pickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
	    addEmptyData(pickIntent);
	    startActivityForResult(pickIntent, R.id.REQUEST_PICK_APPWIDGET);
	}
	
	void selectShortcut()
	{
		Intent intent=new Intent(Intent.ACTION_PICK_ACTIVITY);
		intent.putExtra(Intent.EXTRA_INTENT, new Intent(Intent.ACTION_CREATE_SHORTCUT));
		startActivityForResult(intent, R.id.REQUEST_PICK_SHORTCUT);
	}
	
	void addEmptyData(Intent pickIntent) {
	    ArrayList customInfo = new ArrayList();
	    pickIntent.putParcelableArrayListExtra(AppWidgetManager.EXTRA_CUSTOM_INFO, customInfo);
	    ArrayList customExtras = new ArrayList();
	    pickIntent.putParcelableArrayListExtra(AppWidgetManager.EXTRA_CUSTOM_EXTRAS, customExtras);
	};
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (resultCode == RESULT_OK ) {
	        if (requestCode == R.id.REQUEST_PICK_APPWIDGET) {
	            configureWidget(data);
	        }
	        else if (requestCode == REQUEST_CREATE_APPWIDGET) {
	            createWidget(data);
	        }
	        else if(requestCode==R.id.REQUEST_PICK_SHORTCUT)
	        {
	        	configureShortcut(data);
	        }
	        else if(requestCode==REQUEST_CREATE_SHORTCUT)
	        {
	        	createShortcut(data);
	        }
	    }
	    else if (resultCode == RESULT_CANCELED && data != null) {
	        int appWidgetId = data.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
	        if (appWidgetId != -1) {
	            mAppWidgetHost.deleteAppWidgetId(appWidgetId);
	        }
	    }
	}

	void configureShortcut(Intent data)
	{
		startActivityForResult(data, REQUEST_CREATE_SHORTCUT);
	}
	
	
	private void configureWidget(Intent data) {
	    Bundle extras = data.getExtras();
	    int appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
	    AppWidgetProviderInfo appWidgetInfo = mAppWidgetManager.getAppWidgetInfo(appWidgetId);
	    if (appWidgetInfo.configure != null) {
	        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE);
	        intent.setComponent(appWidgetInfo.configure);
	        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
	        startActivityForResult(intent, REQUEST_CREATE_APPWIDGET);
	    } else {
	        createWidget(data);
	    }
	}
	
	public void createShortcut(Intent intent){
		Intent.ShortcutIconResource iconResource = intent.getParcelableExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE);
		Bitmap icon                              = intent.getParcelableExtra(Intent.EXTRA_SHORTCUT_ICON);
		String shortcutLabel                     = intent.getStringExtra(Intent.EXTRA_SHORTCUT_NAME);
		Intent shortIntent                       = intent.getParcelableExtra(Intent.EXTRA_SHORTCUT_INTENT);
		
		if (icon==null){
			if (iconResource!=null){
				Resources resources =null;
				try {
					resources = pm.getResourcesForApplication(iconResource.packageName);
				} catch (NameNotFoundException e) {
					e.printStackTrace();
				} 
				if (resources != null) {
				    int id = resources.getIdentifier(iconResource.resourceName, null, null); 
				    if(resources.getDrawable(id) instanceof StateListDrawable) {
				    	Drawable d = ((StateListDrawable)resources.getDrawable(id)).getCurrent();
				    	icon = ((BitmapDrawable) d).getBitmap();
				    }else
				    	icon = ((BitmapDrawable)resources.getDrawable(id)).getBitmap();
				}
			}
		}
		

		if (shortcutLabel!=null && shortIntent!=null && icon!=null){
			LayoutParams lp = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
			lp.leftMargin = 100;
			lp.topMargin = (int) 100;
				
			LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			LinearLayout ll = (LinearLayout) li.inflate(R.layout.drawer_item, null);
				
			((ImageView)ll.findViewById(R.id.icon_image)).setImageBitmap(icon);
			((TextView)ll.findViewById(R.id.icon_text)).setText(shortcutLabel);
				
			ll.setOnLongClickListener(new OnLongClickListener() {
					
				@Override
				public boolean onLongClick(View v) {
					v.setOnTouchListener(new AppTouchListener());
					return false;
				}
			});
			ll.setOnClickListener(new ShortcutClickListener(this));
			ll.setTag(shortIntent);
			homeView.addView(ll, lp);
			slidingDrawer.bringToFront();
		}
		
	}
	
	public void createWidget(Intent data) {
	    Bundle extras = data.getExtras();
	    int appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
	    AppWidgetProviderInfo appWidgetInfo = mAppWidgetManager.getAppWidgetInfo(appWidgetId);
	    LauncherAppWidgetHostView hostView = (LauncherAppWidgetHostView) mAppWidgetHost.createView(this, appWidgetId, appWidgetInfo);
	    hostView.setAppWidget(appWidgetId, appWidgetInfo);
	    RelativeLayout.LayoutParams lp =new RelativeLayout.LayoutParams(homeView.getWidth()/3,homeView.getHeight()/3);
	    lp.leftMargin=numWidgets*(homeView.getWidth()/3);
	    hostView.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				v.setBackgroundColor(Color.RED);
				return false;
			}
		});
	    homeView.addView(hostView,lp);
	    slidingDrawer.bringToFront();
	    numWidgets++;
	}
	
	@Override
	protected void onStart() {
	    super.onStart();
	    mAppWidgetHost.startListening();
	}
	@Override
	protected void onStop() {
	    super.onStop();
	    mAppWidgetHost.stopListening();
	    sm.unregisterListener(this);
	}
	
	public void set_pacs()
	{
		
		final Intent mainIntent=new Intent(Intent.ACTION_MAIN,null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		List<ResolveInfo> pacsList=pm.queryIntentActivities(mainIntent, 0);
		pacs=new Pac[pacsList.size()];
		for(int i=0;i<pacsList.size();i++)
		{
			
			
			pacs[i]=new Pac();
			pacs[i].icon=pacsList.get(i).loadIcon(pm);
			pacs[i].name=pacsList.get(i).activityInfo.name;
			pacs[i].packageName=pacsList.get(i).activityInfo.packageName;
			pacs[i].label=pacsList.get(i).loadLabel(pm).toString();
			
			
		}
		new SortApps().exchange_sort(pacs);
		drawerAdapterObject=new DrawerAdapter(this, pacs);
		drawerGrid.setAdapter(drawerAdapterObject);
		drawerGrid.setOnItemClickListener(new DrawerClickListener(this, pacs, pm));	
		drawerGrid.setOnItemLongClickListener(new DrawerLongClickListener(this, slidingDrawer, homeView,pacs));
	
	
	}
	
	public class PacReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			set_pacs();
		}
		
	}


}
