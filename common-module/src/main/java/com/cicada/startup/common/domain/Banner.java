package com.cicada.startup.common.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * banner
 * <p>
 * Create time: 16/10/13 14:13
 *
 * @author liuyun.
 */
public class Banner implements Serializable, Parcelable {


    /**
     * image : http://static.imzhiliao.com/1465184389197nXKyyVGTRy.png
     * title : 宝宝心里苦，怎么办
     * url : http://imzhiliao.com
     */

    private int id;
    private String image;
    private String title;
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.image);
        dest.writeString(this.title);
        dest.writeString(this.url);
    }

    public Banner() {
    }

    protected Banner(Parcel in) {
        this.id = in.readInt();
        this.image = in.readString();
        this.title = in.readString();
        this.url = in.readString();
    }

    public static final Creator<Banner> CREATOR = new Creator<Banner>() {
        @Override
        public Banner createFromParcel(Parcel source) {
            return new Banner(source);
        }

        @Override
        public Banner[] newArray(int size) {
            return new Banner[size];
        }
    };
}
