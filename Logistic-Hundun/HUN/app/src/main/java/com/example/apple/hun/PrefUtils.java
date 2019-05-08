package com.example.apple.hun;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharePreference封装
 */
public class PrefUtils {

    private static final String PREF_NAME = "config";

    public static final String GUIDE_SHOWED = "guide_showed";

    public static boolean getBoolean(Context ctx, String key,
                                     boolean defaultValue) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    public static void setBoolean(Context ctx, String key, boolean value) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }
}
