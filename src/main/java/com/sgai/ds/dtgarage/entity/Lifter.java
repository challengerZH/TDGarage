package com.sgai.ds.dtgarage.entity;

/**
 * Created by zhouhua on 2018/11/22.
 */
// M
public class Lifter {

    private int abscissa;
    private int ordinate;
    private TimeSpot end;
    private String status;

    public Lifter(int abscissa, int ordinate) {
        this.abscissa = abscissa;
        this.ordinate = ordinate;
        this.end = Constant.INIT_ZERO_TIME;
        this.status = "0";
    }

    public TimeSpot getEnd() {
        return end;
    }

    public void setEnd(TimeSpot end) {
        this.end = end;
    }

    public int getAbscissa() {
        return abscissa;
    }

    public int getOrdinate() {
        return ordinate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}