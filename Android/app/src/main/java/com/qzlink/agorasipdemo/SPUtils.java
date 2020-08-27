package com.qzlink.agorasipdemo;

import android.content.Context;
import android.content.SharedPreferences;


public class SPUtils {

    private static final String _key = "calls_up";
    private static final int _mode = 0;

    private static Context context = App.getInstance();
    private static SharedPreferences preferences = context.getSharedPreferences(_key, _mode);
    private static SharedPreferences.Editor editor = preferences.edit();

    public static void putString(String key, String value) {
        editor.putString(key, value).commit();
    }

    public static String getString(String key) {
        return getString(key, null);
    }

    public static String getString(String key, String def) {
        return preferences.getString(key, def);
    }

    public static void putInt(String key, int value) {
        editor.putInt(key, value).commit();
    }

    public static int getInt(String key, int def) {
        return preferences.getInt(key, def);
    }

    public static int getInt(String key) {
        return getInt(key, 0);
    }

    public static void putLong(String key, long value) {
        editor.putLong(key, value).commit();
    }

    public static long getLong(String key) {
        return getLong(key, 0);
    }

    public static long getLong(String key, long def) {
        return preferences.getLong(key, def);
    }

    public static void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value).commit();
    }

    public static boolean getBoolean(String key, boolean def) {
        return preferences.getBoolean(key, def);
    }

    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

}
