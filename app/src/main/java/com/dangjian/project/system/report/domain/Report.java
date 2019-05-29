package com.dangjian.project.system.report.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * report实体类，和服务端对应
 */
public class Report implements Serializable {
    private static final long serialVersionUID = 2796051179715170058L;

    /**
     * 事件标号
     */
    private Integer id;
    /**
     * 关联sys_user表user_id
     */
    private Integer userId;
    /**
     * 关联sys_user表的user_name
     */
    private String name;
    /**
     * 事件标题
     */
    private String title;
    /**
     * 事件正文
     */
    private String text;
    /**
     * 事件图片
     */
    private String image;
    /**
     * 事件上报时间
     */
    private Date time;
    /**  */
    private String exp1;
    /**  */
    private String exp2;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getTime() {
        return time;
    }

    public void setExp1(String exp1) {
        this.exp1 = exp1;
    }

    public String getExp1() {
        return exp1;
    }

    public void setExp2(String exp2) {
        this.exp2 = exp2;
    }

    public String getExp2() {
        return exp2;
    }

    public Report(Integer userId, String title, String text, String image0, String image1, String image2) {
        this.userId = userId;
        this.title = title;
        this.text = text;
        this.image = image0;
        this.exp1 = image1;
        this.exp2 = image2;
    }

    /**
     * 无参构造方法
     */
    public Report() {

    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", image='" + image + '\'' +
                ", time=" + time +
                ", exp1='" + exp1 + '\'' +
                ", exp2='" + exp2 + '\'' +
                '}';
    }

}
