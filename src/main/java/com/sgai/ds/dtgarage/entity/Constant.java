package com.sgai.ds.dtgarage.entity;

import java.util.List;

/**
 * Created by zhouhua on 2018/11/22.
 */
// I
public class Constant {
    // A楼层
    public static final int NUM_OF_FLOOR = 4;
    // 每层单排位置数
    public static final int LENGTH_OF_SPOT_EACH_FLOORSPOT = 25;
    //停车位宽
    public static final double PARKINGSPOT_WIDT = 3.65;
    //车位间距
    public static final double PARKINGSPOT_SPACING = 0;
    //每层车位数
    public  static final int NUM_OF_SPOTS = 42;
    //立体楼总车位数
    public static final  int ALL_NUM_OF_SPOTS = 125;
    // L
    public static final TimeSpot INIT_ZERO_TIME = new TimeSpot(0, 0, 0);
    //升降梯
    public static Lifter[] LIFTERS = {new Lifter(0, 4), new Lifter(0, 5), new Lifter(0, 8), new Lifter(0, 9), new Lifter(0, 12), new Lifter(0, 13), new Lifter(0, 18), new Lifter(0, 19)};

//    //可配项，此处先固定
//    // shuttle加速、减速时间
//    public static final double SHUTTLE_ACC_TIME = 2;
//    // E
//    public static final double SHUTTLE_SPD_WEIGHTED = 0.5;
//    // F
//    public static final double SHUTTLE_SPD_EMPTY = 1;
//    // H
//    public static final double COMBINE_LOADING_TIME_TOTAL = 60.444;
//    //地面缓冲车位
//    public static final int CACHE_NUM_OF_FLOTS = 10;
//    //升降梯走过一层的时间
//    public static final double TIME_FOR_LIFTER = 3;




}