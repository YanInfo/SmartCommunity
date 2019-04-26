package com.yaninfo.smartcommunity.Entity;

import java.io.Serializable;

/**
 * @Author: zhangyan
 * @Date: 2019/4/26 15:10
 * @Description:
 * @Version: 1.0
 */
public class Report implements Serializable {

    private static final long serialVersionUID = 369840050351775312L;

    private int id;
    private String title;
    private String content;
    private String time;
    private int user_id;
    private String user_name;
    private String image0 ="num";
    private String image1 ="num";
    private String image2 ="num";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage0() {
        return image0;
    }

    public void setImage0(String image0) {
        this.image0 = image0;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                ", user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", image0='" + image0 + '\'' +
                ", image1='" + image1 + '\'' +
                ", image2='" + image2 + '\'' +
                '}';
    }


    public Report(int id, String title,String image0) {
        this.id = id;
        this.title = title;
        this.image0 = image0;
    }
}
