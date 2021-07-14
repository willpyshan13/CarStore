package com.yanxin.home.beans.res;

import com.car.baselib.bean.BaseBean;

/**
 * @author zhouz
 * @date 2021/2/27
 */
public class CourseParentInfoRes extends BaseBean {


    /**
     courseCover (string, optional): 教程封面图片url ,
     courseDesc (string, optional): 教程描述图文 ,
     courseTitle (string, optional): 教程名称 ,
     createdTime (string, optional): 创建时间 ,
     uuid (string, optional): uuid
     isNewest(Boolean, optional) 最新
     */

    private String courseCover;
    private String courseDesc;
    private String courseTitle;
    private String createdTime;
    private String uuid;
    private boolean newest;

    public String getCourseCover() {
        return courseCover;
    }

    public void setCourseCover(String courseCover) {
        this.courseCover = courseCover;
    }

    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isNewest() {
        return newest;
    }

    public void setNewest(boolean newest) {
        this.newest = newest;
    }
}
