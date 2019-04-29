package com.ss.android.common.applog;

public class UserInfo {

    public UserInfo() {
        super();
    }

    public static native void getPackage(String arg0);

    public static native String getUserInfo(int arg0, String arg1, String[] arg2);

    public static native String getUserInfoSkipGet(int arg0, String arg1, String[] arg2);

    public static native int initUser(String arg0) ;

    public static native void setAppId(int arg0) ;
}
