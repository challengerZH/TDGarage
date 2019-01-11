package com.sgai.ds.dtgarage.entity;

/**
 * Created by zhouhua on 2018/11/22.
 */
public class Shuttle {

    // position
    private PSPosition position;

    // end
    private TimeSpot end;


    public Shuttle(int a, int b) {
        this.position = new PSPosition(a, b);
        this.end = Constant.INIT_ZERO_TIME;
    }

    public TimeSpot getEnd() {
        return end;
    }

    public void setEnd(TimeSpot end) {
        this.end = end;
    }


    public PSPosition getPosition() {
        return position;
    }

}
