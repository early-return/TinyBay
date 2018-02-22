package info.zhiqing.tinypiratebay.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import info.zhiqing.tinypiratebay.R;

/**
 * Created by lizhi on 2018/1/12.
 */

public class IntroActivity extends AppIntro {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSkipText(getString(R.string.tips_skip));
        setDoneText(getString(R.string.tips_done));
        setSeparatorColor(getResources().getColor(R.color.colorPrimaryDark));
        setNavBarColor(R.color.colorPrimaryDark);

        addSlide(AppIntroFragment.newInstance(
                getString(R.string.intro_title_welcome),
                getString(R.string.intro_desc_welcome),
                R.drawable.ic_favorite_white_200dp,
                getResources().getColor(R.color.colorPrimary)
        ));

        addSlide(new IntroSettingsUrlFragment());


        addSlide(AppIntroFragment.newInstance(
                getString(R.string.intro_title_complete),
                getString(R.string.intro_desc_complete),
                R.drawable.ic_faces_white_200dp,
                getResources().getColor(R.color.colorPrimary)
        ));


    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }
}
