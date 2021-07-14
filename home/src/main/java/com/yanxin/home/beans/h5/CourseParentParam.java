package com.yanxin.home.beans.h5;

import com.car.baselib.bean.BaseBean;

/**
 * @author zhouz
 * @date 2021/1/31
 */
public class CourseParentParam extends BaseBean {

    private String uuid;
    private String courseName;
    private int latestCourse;

    public int getLatestCourse() {
        return latestCourse;
    }

    public void setLatestCourse(int latestCourse) {
        this.latestCourse = latestCourse;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
