package com.example.myproject.model;

import java.io.Serializable;

/**
 * Created by Kain on 2016/8/30.
 */
public class TestModel implements Serializable {
    private String version; // 1.3.0",
    private String is_force; // N",
    private String is_upgrade; // N",
    private String description; // 1、支持双11特惠活动、大量吃喝玩乐半价商品。 ",
    private String download_url; // http://ytfile.oss-cn-hangzhou.aliyuncs.com/youngt.apk"

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getIs_force() {
        return is_force;
    }

    public void setIs_force(String is_force) {
        this.is_force = is_force;
    }

    public String getIs_upgrade() {
        return is_upgrade;
    }

    public void setIs_upgrade(String is_upgrade) {
        this.is_upgrade = is_upgrade;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }
}
