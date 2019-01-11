package com.sgai.ds.dtgarage.entity;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by zhouhua on 2018/11/23.
 */

public class Apps {
    //所有升降梯，一个升降梯占两个车库为，为方便计算看成是两个升降梯，正真工作个数只是一半
    private final Lifter[] lifters = Constant.LIFTERS;
    //地面车位缓冲
    private Queue<Bus> busCache = new LinkedBlockingQueue<>();
    //宏观时间
    private TimeSpot systemTime;
    //所有车辆起始数据
    private ArrayList<Bus> buses;
    //立体楼停车场数组
    private ParkingLot[] parkingLots = new ParkingLot[Constant.NUM_OF_FLOOR - 1];
    //简历全局变量tmp记录buses数据下标
    private int busTmp = 0;
    //记录所有的出车数据
    private List<String[]> LD = new LinkedList<>();
    //当前调用的升降梯
    private Lifter lifter;
    //当前调用的穿梭机
    private Shuttle shuttle;
    //当前处理的车次
    private Bus newBus;
    //当前处理车位
    private ParkingSpot newPS;
    //车位号
    private int spotNumber = 1;
    //
    private int tmpNum = 1;
    //几点升降机上去
    private TimeSpot tLt = new TimeSpot(0);
    //几点车从库出来了
    private TimeSpot tBt = new TimeSpot(0);
    //几点车下楼到地
    private TimeSpot tDt = new TimeSpot(0);


    public Map<String, List<String[]>> simulatedOperation(Map<String, List<String[]>> datas, Property property) {
        List<Bus> endAllocation = new ArrayList<>();
        buses = analysis(datas);
        Collections.sort(buses);

//        System.out.println("---------数据源数据条数：   "+ buses.size() + " ----------------");

        //超出立体楼的车数
        int overNum = getNumOfThisLotBus(buses) - Constant.ALL_NUM_OF_SPOTS;
        //剩余缓冲车位数
        int overNum2 = overNum > 0 ? property.getCacheOfFlots() - overNum : property.getCacheOfFlots();
        if (overNum2 < 0) {
            return null;
        } else {
            for (Bus bus : buses) {
                if (overNum <= 0)
                    break;
                if (bus.getArrivalTime() == null) {
                    busCache.add(bus);
                    bus.setSpotNo(-1);
                    endAllocation.add(bus);
                    handle(bus);
                    overNum--;
                }
            }
        }
        //先把地面车位缓冲塞满
        for (int i = 0; i < overNum2; i++) {
            newBus = buses.get(busTmp++);
            while (newBus.getSpotNo() == -1) {
                newBus = buses.get(busTmp++);
            }
            if (newBus.getArrivalTime() == null) {
                newBus.setSpotNo(tmpNum++);
                endAllocation.add(newBus);
                handle(newBus);
            }
            busCache.add(newBus);
        }

        //初始化停车场数组,并完成车位坐标和车位号的映射
        for (int i = 0; i < parkingLots.length; i++) {
            parkingLots[i] = new ParkingLot(i + 2, createSpots(i + 2), createShuttles(i + 2));
            for (int j = 0; j < parkingLots[i].getSpots().length; j++) {
                if (parkingLots[i].getFloorNo() == 2 && parkingLots[i].getSpots()[j].getOrdinate() == 0 && parkingLots[i].getSpots()[j].getPSPosition().getAbscissaPosition() == 24) {
                    parkingLots[i].getSpots()[j].setStatus("1");
                    continue;
                }

//                System.out.println("**  " + spotNumber +"  **");

                parkingLots[i].getSpots()[j].setSpotNo(spotNumber);
                spotNumber++;
                //直接放到缓冲区的车分配车位
                if (tmpNum > 0) {
                    parkingLots[i].getSpots()[j].setStatus("1");
                    tmpNum--;
                }
            }
        }

        do {
            if (!busCache.isEmpty()){
                systemTime = busCache.poll().getDepartureTime();
            }
            if (busTmp < buses.size()) {
                newBus = buses.get(busTmp);
            }
            if (newBus.isBelongThisLot()) {
                //立体楼出车
                for (ParkingLot pl : parkingLots) {
                    ParkingSpot parkingSpot = findSpot(pl);
                    if (parkingSpot != null) {
                        newPS = parkingSpot;
                        newPS.setStatus("1");
                        break;
                    }
                }
                //比较发车时间和最早空闲的升降梯时间，大的最为本次车下楼作业开始时间
                systemTime = systemTime.getTotalSec() > lifter.getEnd().getTotalSec() ? systemTime : lifter.getEnd();
                if (newPS != null && lifter != null) {
                    timeCalculation(newPS, lifter, shuttle, property);
                    if (newBus.getDepartureTime().getTotalSec() < tDt.getTotalSec()) {
                        System.out.println("*******发车时间超时*********");
                        return null;
                    }

                    //程序调试输出语句
//                    System.out.println(newPS.getPSPosition().getFloor() + "  " + newPS.getSpotNo());

                    newBus.setSpotNo(newPS.getSpotNo());
                    busCache.add(newBus);
                    endAllocation.add(newBus);
                    handle(newBus);
                    shuttle.setEnd(new TimeSpot((int) (tDt.getTotalSec() - (newPS.getPSPosition().getFloor() - 1) * property.getTimeForLifter())));
                    shuttle.getPosition().setAbscissaPosition(lifter.getAbscissa());
                    lifter.setEnd(tDt);

                } else {
                    System.out.println("*******没找到合适车位*******");
                    return null;
                }

            } else {
                //外来车
                if (newBus.getArrivalTime() != null && newBus.getArrivalTime().getTotalSec() < systemTime.getTotalSec()) {
                    System.out.println("********外来车时间小于即将发车时间*********");
                    return null;
                } else {
                    busCache.add(newBus);
                }
            }
            busTmp++;
        } while (busTmp < buses.size());

//        System.out.println("*********结果集数据条数：   " + endAllocation.size() + "*********************");

        return endAnalysis(endAllocation);
    }

    //结果集处理
    private Map<String, List<String[]>> endAnalysis(List<Bus> bl) {
        Map<String, List<String[]>> result = new HashMap<>();
        List<String[]> l1 = new ArrayList<>();
        List<String[]> l2 = new ArrayList<>();
        for (Bus b : bl) {
            String[] s = new String[3];
            s[0] = b.getBusLine();
            s[1] = b.getNo();
            s[2] = b.getSpotNo() + "";
            if (b.getSpotNo() > 0) {
                l1.add(s);
            } else {
                l2.add(s);
            }
        }

//        System.out.println("++++++++   " + l1.size() + " " + l2.size() + " ++++++++++++++");

        result.put("in", l1);
        result.put("out", l2);
        result.put("all", LD);
        return result;
    }

    // 解析数据源
   private ArrayList<Bus> analysis(Map<String, List<String[]>> datas) {
        ArrayList<Bus> bl = new ArrayList<>();
       for(Map.Entry<String, List<String[]>> entry : datas.entrySet()) {
           String key = entry.getKey();
           for (String[] l: datas.get(key)) {
               bl.add(new Bus(key, l));
           }
       }
        return bl;
    }

    // 从录入的数据中找出从立体楼出发的车数量
    private int getNumOfThisLotBus(List<Bus> busList) {
        int a = 0;
        for (Bus bus : busList) {
            if (bus.isBelongThisLot()) {
                a++;
            }
        }
        return a;
    }

    //查找符合要求的空闲车位
    private ParkingSpot findSpot(ParkingLot lot) {
        lifter = getMisTimeLifter();
        Shuttle[] shuttles = lot.getShuttles();
        if (shuttles[0].getEnd().getTotalSec() < shuttles[1].getEnd().getTotalSec()) {
            for (int i = 0; i < lot.getSpots().length; i++) {
                if (lot.getSpots()[i] == null || lot.getSpots()[i].getPartition().equals("left")  || !lot.getSpots()[i].isEmpty())
                    continue;
                shuttle = shuttles[0];
                return lot.getSpots()[i];

            }
        } else {
            for (int i = 0; i < lot.getSpots().length; i++) {
                if (lot.getSpots()[i] == null || lot.getSpots()[i].getPartition().equals("right") || !lot.getSpots()[i].isEmpty())
                    continue;
                shuttle = shuttles[1];
                return lot.getSpots()[i];
            }
        }
        return null;
    }

    //车位出车到一楼所花费时间(int型，单位是：秒)
    private void timeCalculation(ParkingSpot spot, Lifter lifter, Shuttle shuttle, Property property) {
        double time;
        int xSpot = spot.getPSPosition().getAbscissaPosition();
        int xShuttle = shuttle.getPosition().getAbscissaPosition();
        int xLifter = lifter.getAbscissa();
        if (xSpot > xLifter)
            xLifter += 1;
        double shuttle2Spot = Math.abs(xShuttle - xSpot) * (Constant.PARKINGSPOT_WIDT + Constant.PARKINGSPOT_SPACING);
        double spot2Lifter = Math.abs(xLifter - xSpot) * (Constant.PARKINGSPOT_WIDT + Constant.PARKINGSPOT_SPACING);

        //计算shuttle到停车位的时间
        double time1 = (shuttle2Spot - property.getShuttleSpdEmpty() * property.getShuttleAccTime()) / property.getShuttleSpdEmpty() + property.getShuttleAccTime() * 2;
        //计算shuttle从停车位到升降梯的时间
        double time2 = (spot2Lifter - property.getShuttleSpdWeighted() * property.getShuttleAccTime()) / property.getShuttleSpdWeighted() + property.getShuttleAccTime() * 2;
        //计算升降梯花费时间
        double time3 = (spot.getPSPosition().getFloor() - 1) * property.getTimeForLifter();
        //计算穿梭机取完车到达升降梯总耗时
        double time4 = time1 + time2 + property.getLoadingTime();
        //比较（满足升降梯上来途中穿梭机已经开始工作）
        time = time3 > time4 ? time3 : time4;
        //总耗时再加上升降梯下去的时间；
        time = time + property.getLoadingTime() + time3;

//        System.out.println("shuttle到停车位的时间: " + time1 + "  shuttle从停车位到升降梯的时间: " + time2 + "  升降梯花费时间:  " + time3 );

        //升降梯上去时间
        tLt = systemTime;
        //车出库时间
        tBt = new TimeSpot((int)Math.ceil(systemTime.getTotalSec() + time1 + property.getLoadingTime()));
        //车下地时间
        tDt = new TimeSpot((int)Math.ceil(systemTime.getTotalSec() + time));
    }

    //获取最早处于空闲的升降梯
    private Lifter getMisTimeLifter() {
        Lifter lif = lifters[0];
        for (int i = 2; i < lifters.length; i += 2) {
            if (lif.getEnd().getTotalSec() < lifters[i].getEnd().getTotalSec()) {
                lif = lifters[i];
            }
        }
        return lif;
    }

    //某一楼层停车场车位数组初始化
    private ParkingSpot[] createSpots(int floor) {
        int[][] tmp = new int[2][Constant.LENGTH_OF_SPOT_EACH_FLOORSPOT];
        ParkingSpot[] ps = new ParkingSpot[Constant.NUM_OF_SPOTS];
        int psNum = 0;
        //首先给升降梯位置打标记
        for (Lifter lf : lifters) {
            tmp[lf.getAbscissa()][lf.getOrdinate()] = 1;
        }
        ParkingSpot p;
        for (int i = 0; i < tmp.length; i++) {
            for (int j = 0; j < tmp[i].length; j++) {
                //如果是0说明是车位不是升降梯
                if (tmp[i][j] == 0) {
                    p = new ParkingSpot(floor, i, j);
                    p.setPartition(j < Constant.LENGTH_OF_SPOT_EACH_FLOORSPOT / 2 ? "left" : "right");
                    ps[psNum++] = p;
                }
            }
        }

        return ps;
    }

    //某一层穿梭机初始化,默认下标0的是
    private Shuttle[] createShuttles(int floor) {
        Shuttle[] shuttles = {new Shuttle(floor, 1), new Shuttle(floor, Constant.LENGTH_OF_SPOT_EACH_FLOORSPOT)};
        return shuttles;
    }

    //立体楼出去的车的数据记录处理
    private void handle(Bus b) {
        String[] so = new String[7];
        so[0] = b.getBusLine();
        so[1] = b.getNo();
        so[2] = b.getSpotNo() + "";
        so[3] = tLt.toString();
        so[4] = tBt.toString();
        so[5] = tDt.toString();
        so[6] = b.getDepartureTime().toString();
        LD.add(so);
    }

}
