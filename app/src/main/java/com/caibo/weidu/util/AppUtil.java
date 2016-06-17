package com.caibo.weidu.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.AbsListView;

import com.caibo.weidu.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;


public class AppUtil {
	
	private static float DEVICE_DENSITY = 0;
	
	/**
	 * 退出app
	 */
	public static void exitApp(){
		System.exit(0);
	}
	
	/**
	 * 获取屏幕宽度
	 * @param activity
	 * @return
	 */
	public static int getSreenWidth(Activity activity){
		String width_key = "share_prefrence_screent_width";
		MySharedPreference sp = new MySharedPreference(activity);
		if(0!=sp.getKeyInt(width_key))
		{
			return sp.getKeyInt(width_key);
		}else {

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
				Point size = new Point();
				activity.getWindowManager().getDefaultDisplay().getSize(size);
				int screenWidth = size.x;
				sp.setKeyInt(width_key, screenWidth);
				return screenWidth;
			}else{
				int screenWidth = activity.getWindowManager().getDefaultDisplay().getWidth();
				sp.setKeyInt(width_key, screenWidth);
				return screenWidth;
			}


		}
	}
	
	/**
	 * 获取屏幕高度
	 */
	public static int getSreenHeight(Activity activity){
		String height_key = "share_prefrence_screent_height";
		MySharedPreference sp = new MySharedPreference(activity);
		if(0!=sp.getKeyInt(height_key))
		{
			return sp.getKeyInt(height_key);
		}else {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
				Point size = new Point();
				activity.getWindowManager().getDefaultDisplay().getSize(size);
				int screenHeight = size.y;
				sp.setKeyInt(height_key, screenHeight);
				return screenHeight;
			}else {
				int screenHeight = activity.getWindowManager().getDefaultDisplay().getHeight();
				sp.setKeyInt(height_key, screenHeight);
				return screenHeight;
			}
		}
	}
	
	//获取空白页中间图标的高度
	public static View  getEmptyLayoutView(View targetView ,Activity activity,List<View> views){
		int screenHeight = getSreenHeight(activity);
		int screenWidth = getSreenWidth(activity);
		int occupyheight = 0;
		for (int i = 0; i < views.size(); i++) {
			int height = views.get(i).getHeight();
			occupyheight+=height;
		}
		AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(screenWidth,screenHeight-occupyheight);
		targetView.setLayoutParams(layoutParams); 
		return targetView;
	}
	
	
	/**
	 * 判断是否是平板
	 * @param mContext
	 * @return
	 */
	public static boolean isTabletDevice(Context mContext) {
        TelephonyManager telephony = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        int type = telephony.getPhoneType();
        if (type == TelephonyManager.PHONE_TYPE_NONE) {
            return true;
        } else {
        	return false;
        }
    }
	
	/**
	 * 获取当前应用的版本号
	 * @param context
	 * @return
	 */
	public static String getVersionName(Context context){      

		try {
	      	return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "1.0";
	}
	
	
//	/**
//	 * 匹配图片
//	 */
//	public static String fitImage(Context context,String imageUrl, int needWidth, int needHeight){
//		if(imageUrl==null||"".equals(imageUrl))return "";
//
//		String finalImgUrlStr=imageUrl;
//		finalImgUrlStr = imageUrl+"."+needWidth+"x"+needHeight+".jpg";
//
//		return finalImgUrlStr;
//	}
//
	/**
	 * 获取手机联网状态
	 * @param context
	 * @return
	 */
	public static String getNetState(Context context) {
		
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		ConnectivityManager mConnectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = mConnectivity.getActiveNetworkInfo();
		if (info == null || !mConnectivity.getBackgroundDataSetting()) {
			return null;
		}
		if (info.isConnected()) {
			int netType = info.getType();
			int netSubtype = info.getSubtype();

			if (netType == ConnectivityManager.TYPE_WIFI) {
				return "WIFI";
			} else if (netType == ConnectivityManager.TYPE_MOBILE && netSubtype == TelephonyManager.NETWORK_TYPE_UMTS && !tm.isNetworkRoaming()) {
				return "3G";
			} else if (netType == ConnectivityManager.TYPE_MOBILE) {
				return "GPRS";
			} else {
				return "未知";
			}
		} else {
			return null;
		}
	}

	/**
	 * 获取设备id
	 * @param context
	 * @return
     */
	public static String getDeviceId(Context context){

		String deviceid_key = "share_prefrence_device_id";
		MySharedPreference sp = new MySharedPreference(context);
		String deviceIdString = sp.getKeyStr(deviceid_key);

		if("".equals(deviceIdString)){
			String device_id = null;
			boolean random = false;
			try {
				device_id = ((TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
			}catch (Exception e){
				e.printStackTrace();
				random = true;
				device_id = new Random().nextInt(16)+"";
			}
			String device_id_md5 = MD5Util.MD5Encode(device_id, "UTF-8");
			if(random){
				device_id_md5 = "rd_"+device_id_md5;
			}
			sp.setKeyStr(deviceid_key, device_id_md5);
			return device_id_md5;
		}else{
			return deviceIdString;
		}
	}


	/**
	 * 获取屏幕像素密度
	 * @param activity
     */
	public static void getDensity(Activity activity){
		if(DEVICE_DENSITY==0)
		{
			DisplayMetrics metrics = new DisplayMetrics(); 
	        float density = metrics.density;
	        
	        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
	        DEVICE_DENSITY = metrics.density;
		}
	}
	
	 /** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static int dip2px(Activity activity, double dpValue) {  
    	getDensity(activity);
    	return (int) (dpValue * DEVICE_DENSITY);
    }  
  
    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip(Activity activity, double pxValue) {  
    	getDensity(activity); 
        return (int) (pxValue / DEVICE_DENSITY);  
    }  
    
    /**
     * 将px值转换为sp值，保证文字大小不变
     * 
     * @param pxValue
     * @return
     */
    public static int px2sp(Activity activity, float pxValue) {
    	getDensity(activity); 
    	return (int) (pxValue / DEVICE_DENSITY);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     * 
     * @param spValue
     * @return
     */
    public static int sp2px(Activity activity, float spValue) {
    	getDensity(activity); 
    	return (int) (spValue * DEVICE_DENSITY);
   }

    /**
	 * 是否创建快捷方式
	 * @param context
	 * @return
     */
    public static boolean hasShortcut(Context context)
    {
        boolean isInstallShortcut = false;
        final ContentResolver cr = context.getContentResolver();
        final String AUTHORITY ="com.android.launcher.settings";
        final Uri CONTENT_URI = Uri.parse("content://" +AUTHORITY + "/favorites?notify=true");
        Cursor c = cr.query(CONTENT_URI,new String[] {"title","iconResource" },"title=?",
        new String[] {context.getResources().getString(R.string.app_name).trim()}, null);
        if(c!=null && c.getCount()>0){
            isInstallShortcut = true ;
        }
        return isInstallShortcut ;
    }
    
    /** 
     * 为程序创建桌面快捷方式 
     */ 
    public static void addShortcut(Context context){  
    	if(hasShortcut(context)){
//			FLDLog.e("has shortcut");
			return;
		}
        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");  
               
        //快捷方式的名称  
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, context.getResources().getString(R.string.app_name));  
        shortcut.putExtra("duplicate", false); //不允许重复创建  
 
        /****************************此方法已失效*************************/
        //ComponentName comp = new ComponentName(this.getPackageName(), "."+this.getLocalClassName());  
        //shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN).setComponent(comp));  　　
        Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
        shortcutIntent.setClassName(context, context.getClass().getName());
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
 
        //快捷方式的图标  
        ShortcutIconResource iconRes = ShortcutIconResource.fromContext(context, R.mipmap.ic_launcher);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);  
               
        context.sendBroadcast(shortcut);  
    }  
    
    /**
     * 时间转换 date转string
     * @param date
     * @param formatter
     * @return
     */
    public static String timeFormat(Date date,String formatter){
    	SimpleDateFormat dateformat = new SimpleDateFormat(formatter,Locale.CHINA);   
        String formattime = dateformat.format(date);
        return formattime;
    }
    
    /**
     * 从xml获取动态color
     * @param context
     * @param colorid
     * @return
     */
    public static ColorStateList getXMLColor(Context context,int colorid){
    	XmlResourceParser xrp = context.getResources().getXml(colorid);  
    	try {  
    	    ColorStateList csl = ColorStateList.createFromXml(context.getResources(), xrp);  
			return csl; 
    	} catch (Exception e) {  
    	} 
    	return null;
    }

    /**
	 * 程序是否在运行
	 * @param context
	 * @return
     */
	public static boolean isAppRunning(Context context) {
		String packageName = getPackageName(context);
		String topActivityClassName = getCurrentActivityName(context);
		if (packageName != null && topActivityClassName != null && topActivityClassName.startsWith(packageName)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isAppAlive(Context context) {
		String packageName = context.getPackageName();
		if (context == null || TextUtils.isEmpty(packageName)) {
			return false;
		}

		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

		if (activityManager != null) {
			List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
			if (procInfos != null && !procInfos.isEmpty()) {
				for (int i = 0; i < procInfos.size(); i++) {
					if (procInfos.get(i).processName.equals(packageName)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * 获取包名
	 * @param context
	 * @return
     */
	public static String getPackageName(Context context) {
		String packageName = context.getPackageName();
		return packageName;
	}

	/**
	 * 获取当前activity名称
	 * @param context
	 * @return
     */
	public static String getCurrentActivityName(Context context) {
		String topActivityClassName = null;
		ActivityManager activityManager = (ActivityManager) (context.getSystemService(Context.ACTIVITY_SERVICE));
		List<RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(1);
		if (runningTaskInfos != null) {
			ComponentName f = runningTaskInfos.get(0).topActivity;
			topActivityClassName = f.getClassName();
		}
		return topActivityClassName;
	}


	//判断当前网络是否是wifi
	public static boolean isWifiConnect(Context context){
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(connectivityManager==null){
			return false;
		}
		NetworkInfo info = connectivityManager.getActiveNetworkInfo();
		if (info == null) {
			return false;
		}
		String type = info.getTypeName();
		if(type.equalsIgnoreCase("wifi")){
			return true;
		}else{
			return false;
		}

	}

	public static Bitmap screenShot(Activity activity){

		// 获取windows中最顶层的view
		View view = activity.getWindow().getDecorView();
		view.buildDrawingCache();

		// 获取状态栏高度
		Rect rect = new Rect();
		view.getWindowVisibleDisplayFrame(rect);
		int statusBarHeights = rect.top;
		Display display = activity.getWindowManager().getDefaultDisplay();

		// 获取屏幕宽和高
		int widths = display.getWidth();
		int heights = display.getHeight();

		// 允许当前窗口保存缓存信息
		view.setDrawingCacheEnabled(true);

		// 去掉状态栏
		Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache(), 0,
				statusBarHeights, widths, heights - statusBarHeights);

		// 销毁缓存信息
		view.destroyDrawingCache();

		return bmp;
	}

}
