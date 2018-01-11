package info.zhiqing.tinybay.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import info.zhiqing.tinybay.R;
import info.zhiqing.tinybay.util.InitUtil;

public class SettingsActivity extends AppCompatActivity {

    public static final String KEY_PREF_URL = "pref_key_url";
    public static final String KEY_PREF_PORN = "pref_key_porn_mode";

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
