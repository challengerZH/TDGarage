package com.sgai.ds.dtgarage.entity;

/**
 * Created by zhouhua on 2018/11/23.
 */
public class PSPosition {

    // a
    private int floor;
    // b
    private int abscissaPosition;

    PSPosition(int floor, int abscissaPosition) {
        this.floor = floor;
        this.abscissaPosition = abscissaPosition;
    }

    public int getAbscissaPosition() {
        return abscissaPosition;
    }

    public void setAbscissaPosition(int abscissaPosition) {
        this.abscissaPosition = abscissaPosition;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }
}
