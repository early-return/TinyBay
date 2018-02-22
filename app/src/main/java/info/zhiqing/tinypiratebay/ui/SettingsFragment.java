package info.zhiqing.tinypiratebay.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.util.Log;

import info.zhiqing.tinypiratebay.R;
import info.zhiqing.tinypiratebay.util.InitUtil;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lizhi on 2018/1/11.
 */

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    public static final String TAG = "SettingsFragment";

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        InitUtil.init(getActivity())
                .subscribeOn(Schedulers.io())
                .subscribe();
        Log.d(TAG, "On shared preference changed");
    }
}
