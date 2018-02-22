package info.zhiqing.tinypiratebay.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import info.zhiqing.tinypiratebay.ui.SettingsActivity;

/**
 * Created by lizhi on 2018/1/9.
 */

public class ConfigUtil {
    public static final String TAG = "ConfigUtil";

    public static String BASE_URL = "https://thepiratebay.org";
    public static boolean PORN_MODE = false;

    public static void init(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        BASE_URL = pref.getString(SettingsActivity.KEY_PREF_URL, "https://thepiratebay.org");
        Log.d(TAG, BASE_URL);
        PORN_MODE = pref.getBoolean(SettingsActivity.KEY_PREF_PORN, false);
        Log.d(TAG, "Porn mode: " + PORN_MODE);
    }
}
