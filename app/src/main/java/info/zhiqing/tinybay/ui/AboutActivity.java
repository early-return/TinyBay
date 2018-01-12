package info.zhiqing.tinybay.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import info.zhiqing.tinybay.R;
import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

/**
 * Created by lizhi on 2018/1/12.
 */

public class AboutActivity extends AppCompatActivity {

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Element versionElement = new Element();
        versionElement.setTitle("Version: " + getString(R.string.app_version));

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setDescription(getString(R.string.app_desc_detail))
                .setImage(R.mipmap.ic_launcher)
                .addItem(versionElement)
                .addGroup(getString(R.string.app_group_name))
                .addEmail("lizhiqing1996@gmail.com")
                .addWebsite("http://zhiqing.info/")
                .addTwitter("ZhiqingHello")
                .addGitHub("zhiqing-lee")
                .create();

        setContentView(aboutPage);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
