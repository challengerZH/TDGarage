package com.sgai.ds.dtgarage.entity;

import java.util.Comparator;

/**
 * Created by zhouhua on 2018/11/22.
 */
public class Bus implements Comparable {


    // 车牌号
    final String no;

    // 公交线路
    private String busLine;

    // 发车时间
    private TimeSpot departureTime;

    // 到达时间
    private TimeSpot arrivalTime;

    //停车位号
    private int spotNo;

    public Bus(String busLine, String[] ls) {
        this.busLine = busLine;
        this.arrivalTime = TimeSpot.newL(ls[0]);
        this.departureTime = TimeSpot.newL(ls[1]);
        this.no = ls[2];
        this.spotNo = 0;
    }

    @Override
    public int compareTo(Object o) {
        // TODO Auto-generated method stub
        if (o instanceof Bus) {
            Bus s = (Bus) o;
            if (this.departureTime.getTotalSec() > s.departureTime.getTotalSec()) {
                return 1;
            } else if (this.departureTime.getTotalSec() == s.departureTime.getTotalSec()) {
                return 0;
            } else {
                return -1;
            }
        }
        return 0;
    }

    // getL
    public TimeSpot getDepartureTime() {
        return departureTime;
    }

    public TimeSpot getArrivalTime() {
        return arrivalTime;
    }

    // isL
    public boolean isBelongThisLot() {
        return arrivalTime == null && departureTime != null;
    }

    //
    public String getBusLine() {
        return busLine;
    }

    // getZ
    public String getNo() {
        return no;
    }

    public int getSpotNo() {
        return spotNo;
    }

    public void setSpotNo(int spotNo) {
        this.spotNo = spotNo;
    }
}
