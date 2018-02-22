package info.zhiqing.tinypiratebay.ui;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.regex.Matcher;

import info.zhiqing.tinypiratebay.R;
import info.zhiqing.tinypiratebay.util.ConfigUtil;
import info.zhiqing.tinypiratebay.util.InitUtil;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class IntroSettingsUrlFragment extends Fragment {
    public static final String TAG = "IntroSettingsFragment";

    private RadioGroup radioGroup;
    private TextInputEditText editText;
    private RadioButton originButton;
    private RadioButton mineButton;
    private RadioButton customButton;


    public IntroSettingsUrlFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_intro_settings_url, container, false);

        editText = v.findViewById(R.id.custom_url_edit_text);

        originButton = v.findViewById(R.id.origin_url_radio);
        customButton = v.findViewById(R.id.custom_url_radio);

        if (ConfigUtil.BASE_URL.equals(originButton.getText().toString())) {
            originButton.setChecked(true);
        } else if (ConfigUtil.BASE_URL.equals(mineButton.getText().toString())) {
            mineButton.setChecked(true);
        } else {
            customButton.setChecked(true);
            editText.setText(ConfigUtil.BASE_URL);
            editText.setEnabled(true);
        }

        radioGroup = v.findViewById(R.id.url_radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.custom_url_radio) {
                    editText.setEnabled(true);
                } else {
                    editText.setEnabled(false);
                }
            }
        });

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d(TAG, "onPause: ");

        int checkedId = radioGroup.getCheckedRadioButtonId();
        if (checkedId == R.id.custom_url_radio) {
            Matcher matcher = Patterns.WEB_URL.matcher(editText.getText());
            if (matcher.matches()) {
                String url = editText.getText().toString().trim();
                if (url.endsWith("/")) {
                    url = url.substring(0, url.length() - 1);
                }
                setUrl(url);
            } else {
                setUrl(originButton.getText().toString());
            }
        } else if (checkedId == originButton.getId()) {
            setUrl(originButton.getText().toString());
        }

    }

    private void setUrl(String url) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        pref.edit()
                .putString(SettingsActivity.KEY_PREF_URL, url)
                .apply();
        InitUtil.init(getContext())
                .subscribeOn(Schedulers.io())
                .subscribe();
        Log.d(TAG, "setUrl: " + url);
    }
}
