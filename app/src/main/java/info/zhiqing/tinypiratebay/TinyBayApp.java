package info.zhiqing.tinypiratebay;

import android.app.Application;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import info.zhiqing.tinypiratebay.util.InitUtil;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by lizhi on 2018/1/9.
 */

public class TinyBayApp extends Application {
    public static final String TAG = "TinyBayApp";

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化设置项
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);



        InitUtil.init(this)
                .subscribe(new Observer<Void>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Void aVoid) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(TinyBayApp.this, R.string.tips_init_failed, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "分类信息初始化完成！");
                    }
                });

    }
}
