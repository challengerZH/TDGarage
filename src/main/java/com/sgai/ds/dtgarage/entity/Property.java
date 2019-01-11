package com.sgai.ds.dtgarage.entity;

/**
 * Created by zhouhua on 2018/11/29.
 */
public class Property {
    //穿梭车加速时间
    private double shuttleAccTime;
    //穿梭车满载速度
    private double shuttleSpdWeighted;
    //穿梭车空载速度
    private double ShuttleSpdEmpty;
    //穿梭车完成拖车出库耗时
    private double loadingTime;
    //地面缓冲车位数
    private int cacheOfFlots;
    //电梯经过一层楼耗时
    private double timeForLifter;

    public Property(double shuttleAccTime, double shuttleSpdWeighted, double shuttleSpdEmpty, double loadingTime,  double timeForLifter, int cacheOfFlots) {
        this.shuttleAccTime = shuttleAccTime;
        this.shuttleSpdWeighted = shuttleSpdWeighted;
        ShuttleSpdEmpty = shuttleSpdEmpty;
        this.loadingTime = loadingTime;
        this.cacheOfFlots = cacheOfFlots;
        this.timeForLifter = timeForLifter;
    }

    public double getShuttleAccTime() {
        return shuttleAccTime;
    }


    public double getShuttleSpdWeighted() {
        return shuttleSpdWeighted;
    }


    public double getShuttleSpdEmpty() {
        return ShuttleSpdEmpty;
    }


    public double getLoadingTime() {
        return loadingTime;
    }


    public int getCacheOfFlots() {
        return cacheOfFlots;
    }


    public double getTimeForLifter() {
        return timeForLifter;
    }

}
