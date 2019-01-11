package com.sgai.ds.dtgarage.entity;

/**
 * Created by zhouhua on 2018/11/22.
 */
public class ParkingSpot {

    // 车位使用状态
    private String status;

    // 车位位置信息（楼层、横向位置）
    private PSPosition PSPosition;

    //纵向位置（取值0或1）
    private int ordinate;

    //区
    private String partition;

    //车位号
    private int spotNo;

    public ParkingSpot(int floor,int ordinate ,int abscissaPosition) {
        this.PSPosition = new PSPosition(floor, abscissaPosition);
        this.ordinate = ordinate;
        this.status = "0";
    }

    //设置车位status
    public void setStatus(String status) {
        this.status = status;
    }
    // 设置状态为待使用
    public boolean isEmpty() {
        return status=="0" ? true : false;
    }

    public int getOrdinate() {
        return ordinate;
    }


    public com.sgai.ds.dtgarage.entity.PSPosition getPSPosition() {
        return PSPosition;
    }

    public String getPartition() {
        return partition;
    }

    public void setPartition(String partition) {
        this.partition = partition;
    }

    public int getSpotNo() {
        return spotNo;
    }

    public void setSpotNo(int spotNo) {
        this.spotNo = spotNo;
    }
}
