package com.sgai.ds.dtgarage.entity;

/**
 * Created by zhouhua on 2018/11/22.
 */
public class ParkingLot {

    //层
    private int floorNo;
    // 车位
    private ParkingSpot[] spots;

    //穿梭机
    private Shuttle[] shuttles;

    public  ParkingLot(int floorNo, ParkingSpot[] spots, Shuttle[] shuttles){
        this.floorNo = floorNo;
        this.spots = spots;
        this.shuttles = shuttles;
    }
    public ParkingSpot[] getSpots() {
        return spots;
    }

    public void setSpots(ParkingSpot[] spots) {
        this.spots = spots;
    }

    public Shuttle[] getShuttles() {
        return shuttles;
    }

    public void setShuttles(Shuttle[] shuttles) {
        this.shuttles = shuttles;
    }

    public int getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(int floorNo) {
        this.floorNo = floorNo;
    }
}
