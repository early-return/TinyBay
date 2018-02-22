package info.zhiqing.tinypiratebay.entities;

import java.io.Serializable;

/**
 * Created by zhiqing on 18-1-5.
 */

public class Category implements Serializable {
    String title;
    String code;

    public Category() {

    }

    public Category(String title, String code) {
        this.title = title;
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
