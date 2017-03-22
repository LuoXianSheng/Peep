package com.github.lxs.peep.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by cl on 2017/3/22.
 */

@Entity
public class Photo {

    @Id(autoincrement =  true)
    private long photoId;
    private String url;

    @Generated(hash = 2071605928)
    public Photo(long photoId, String url) {
        this.photoId = photoId;
        this.url = url;
    }

    @Generated(hash = 1043664727)
    public Photo() {
    }

    public long getPhotoId() {
        return this.photoId;
    }

    public void setPhotoId(long photoId) {
        this.photoId = photoId;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
