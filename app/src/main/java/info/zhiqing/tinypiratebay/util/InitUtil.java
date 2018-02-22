package info.zhiqing.tinypiratebay.util;

import android.content.Context;

import info.zhiqing.tinypiratebay.spider.SpiderClient;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by lizhi on 2018/1/9.
 */

public class InitUtil {

    public static Observable<Void> init(final Context context) {
        return Observable.create(new ObservableOnSubscribe<Void>() {
            @Override
            public void subscribe(ObservableEmitter<Void> e) throws Exception {
                ConfigUtil.init(context);
                CategoryUtil.init(context);

                SearchUtil.init(context);

                SpiderClient.buildNewInstance();

                e.onComplete();
            }
        });
    }
}
