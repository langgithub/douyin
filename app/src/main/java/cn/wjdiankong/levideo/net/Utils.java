package cn.wjdiankong.levideo.net;

import java.math.RoundingMode;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.MediaStore.Images.Thumbnails;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import cn.wjdiankong.levideo.data.LevideoData;

public class Utils {
	
	public static double[] lnglat = new double[2];
	
	public static int width, height;
	
	public static double getLng(){
		return lnglat[0];
	}
	
	public static double getLat(){
		return lnglat[1];
	}
	
	public static void sortLevideoData(List<LevideoData> dataList){
		Collections.sort(dataList, new Comparator<LevideoData>() {
			@Override
			public int compare(LevideoData lhs, LevideoData rhs) {
				return lhs.createTime > rhs.createTime ? -1 : 1;
			}
		});
	}
	
	public static String formatDuration(String duration){
		String value = "";
		try{
			int dur = Integer.parseInt(duration);
			dur = dur/1000;
			int min = dur/60;
			int sec = dur%60;
			if(min < 10){
				value = "0"+min+":";
			}else{
				value = min+":";
			}
			if(sec < 10){
				value = value + "0"+sec;
			}else{
				value = value + sec;
			}
			return value;
		}catch(Exception e){
			return duration;
		}
	}
	
	public static String formatSize(long size) {
        String suffix;
        float fSize;
        if (size >= 1000) {
            suffix = "KB";
            fSize = (float) (size / 1024.0);
            if (fSize >= 1000) {
                suffix = "MB";
                fSize /= 1024;
            }
            if (fSize >= 1000) {
                suffix = "GB";
                fSize /= 1024;
            }
        } else {
            suffix = "B";
            fSize = size;
        }
        java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0");
        if(fSize>0.0f && fSize<0.1f){
            df.setRoundingMode(RoundingMode.UP);
        }
        StringBuilder resultBuffer = new StringBuilder(df.format(fSize));
        resultBuffer.append(suffix);
        return resultBuffer.toString();
    }
	
	public static String getVersion(Context ctx){
	    try {
	        PackageManager manager = ctx.getPackageManager();
	        PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
	        String version = info.versionName;
	        return version;
	    } catch (Exception e) {
	        return "";
	    }
	}
	
	public static Bitmap getVideoThumbnail(String videoPath, int width, int height) {
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, Thumbnails.MINI_KIND);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }
	
	public static int getVideoDuration(String videoPath){
		MediaPlayer mediaPlayer = new MediaPlayer();
		try{
			mediaPlayer.setDataSource(videoPath);
			mediaPlayer.prepare();
			return mediaPlayer.getDuration();
		}catch(Exception e){
			return 0;
		}
	}
	
	public static void copyContent(Context ctx, String content){
    	ClipboardManager clipboard = (ClipboardManager)ctx.getSystemService(Context.CLIPBOARD_SERVICE);  
		ClipData clip =ClipData.newPlainText("simple text", content);
		clipboard.setPrimaryClip(clip);
    }
	
    public static String getMD5(String inputStr) {
    	if(TextUtils.isEmpty(inputStr)){
    		return inputStr;
    	}
        try {  
        	MessageDigest md5 = MessageDigest.getInstance("MD5"); 
        	char[] charArray = inputStr.toCharArray(); //将字符串转换为字符数组  
            byte[] byteArray = new byte[charArray.length]; //创建字节数组  
            for (int i = 0; i < charArray.length; i++){  
                byteArray[i] = (byte) charArray[i];  
            }
            //将得到的字节数组进行MD5运算  
            byte[] md5Bytes = md5.digest(byteArray);  
            StringBuffer md5Str= new StringBuffer();  
            for (int i = 0; i < md5Bytes.length; i++){  
                if (Integer.toHexString(0xFF & md5Bytes[i]).length() == 1)    
                    md5Str.append("0").append(Integer.toHexString(0xFF & md5Bytes[i]));    
                else    
                    md5Str.append(Integer.toHexString(0xFF & md5Bytes[i]));
            }  
            return md5Str.toString(); 
        } catch (Exception e) {
            return inputStr;  
        }  
    } 
	
	public static String getWifiMac(Context ctx) {
        WifiManager wifi = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String str = info.getMacAddress();
        if (str == null){
        	str = "";
        }
        return str;
	}
	
	public static String filterStrBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
	
	public static String formatNumber(long val){
		if(val < 10000){
			return val+"";
		}
		DecimalFormat df = new DecimalFormat("######0.0");
		double d = val/10000.0;
		return df.format(d)+"万";
	}

	@SuppressLint("SimpleDateFormat")
	public static String formatTimeStr(long time) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
		String dateString = formatter.format(time);
		return dateString;
	}
	
	@SuppressLint("SimpleDateFormat")
	public static String formatTimeDetailStr(long time) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd hh:mm");
		String dateString = formatter.format(time);
		return dateString;
	}

	@SuppressWarnings("deprecation")
	public static String getOSSDK(){
		return android.os.Build.VERSION.SDK;
	}

	public static String getOSRelease(){
		return android.os.Build.VERSION.RELEASE;
	}

	public static String getDeviceName(){
		return android.os.Build.MODEL;
	}

	public static String getDeviceFactory(){
		return android.os.Build.BRAND;
	}

	public static int getDeviceWidth(Activity act){
		DisplayMetrics metric = new DisplayMetrics();
		act.getWindowManager().getDefaultDisplay().getMetrics(metric);
		return metric.widthPixels;
	}
	
	public static int getDeviceHeight(Activity act){
		DisplayMetrics metric = new DisplayMetrics();
		act.getWindowManager().getDefaultDisplay().getMetrics(metric);
		return metric.heightPixels;
	}
	
	public static int getDeviceDpi(Activity act){
		DisplayMetrics metric = new DisplayMetrics();
		act.getWindowManager().getDefaultDisplay().getMetrics(metric);
		return metric.densityDpi;
	}
	
	public static float getDeviceDensity(Activity act){
		DisplayMetrics metric = new DisplayMetrics();
		act.getWindowManager().getDefaultDisplay().getMetrics(metric);
		return metric.density;
	}
	
	@SuppressLint("NewApi")
	public static String getDeviceId(){
		return android.os.Build.SERIAL;
	}
	
	public static String getDeviceUUID(Context ctx){
		try {
			return UUID.nameUUIDFromBytes(getDeviceAndroidId(ctx).getBytes("utf8")).toString();
		} catch (Exception e) {
			return "";
		}
	}
	
	public static String getDeviceAndroidId(Context ctx){
		try{
			return Secure.getString(ctx.getContentResolver(), Secure.ANDROID_ID);  
		}catch(Exception e){
			return "";
		}
	}
	
	public static String getDeviceIMEI(Context ctx){
		try{
			return ((TelephonyManager)ctx.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
		}catch(Exception e){
			return "";
		}
	}
	
}
