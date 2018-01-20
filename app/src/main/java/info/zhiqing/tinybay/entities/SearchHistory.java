package info.zhiqing.tinybay.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

/**
 * Created by lizhi on 2018/1/20.
 */

public class SearchHistory implements SearchSuggestion {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getBody() {
        return title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
    }

    public static final Parcelable.Creator<SearchHistory> CREATOR = new Creator<SearchHistory>() {
        @Override
        public SearchHistory createFromParcel(Parcel parcel) {
            SearchHistory history = new SearchHistory();
            history.setTitle(parcel.readString());
            return history;
        }

        @Override
        public SearchHistory[] newArray(int i) {
            return new SearchHistory[0];
        }
    };
}
