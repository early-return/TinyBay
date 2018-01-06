package info.zhiqing.tinybay.spider;

import android.util.Log;

import java.util.List;

import info.zhiqing.tinybay.entities.Torrent;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;

/**
 * Created by zhiqing on 18-1-6.
 */

public class SpiderClient {
    public static final String TAG = "SpiderClient";

    private Spider spider = new SpiderLocal();

    private static SpiderClient instance;

    public static SpiderClient getInstance() {
        if (instance == null) {
            instance = new SpiderClient();
        }
        return instance;
    }

    private SpiderClient() { }

    public Observable<Torrent> fetchTorrentsByUrl(final String url) {
        return Observable.create(new ObservableOnSubscribe<Torrent>() {
            @Override
            public void subscribe(ObservableEmitter<Torrent> e) throws Exception {
                List<Torrent> list = spider.list(url);
                Log.d("SpiderClient", "Get " + url + " : " + list.size());
                for (Torrent torrent : list) {
                    e.onNext(torrent);
                }
                e.onComplete();
            }
        });
    }
}
