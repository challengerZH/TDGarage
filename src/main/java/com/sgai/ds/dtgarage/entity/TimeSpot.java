package com.sgai.ds.dtgarage.entity;

/**
 * Created by zhouhua on 2018/11/22.
 */

// L
public class TimeSpot implements Comparable<TimeSpot> {
    private final int hour;
    private final int min;
    private final int sec;
    private final int totalSec;

    //对时间切割
    public static TimeSpot newL(String l) {
        if (l == null || "null".equals(l)) {
            return null;
        }
        String[] lSplit = l.split(":");
        return new TimeSpot(Integer.parseInt(lSplit[0]), Integer.parseInt(lSplit[1]),
                Integer.parseInt(lSplit[2]));
    }

    public TimeSpot(int hour, int min, int sec, int totalSec) {
        this.hour = hour;
        this.min = min;
        this.sec = sec;
        this.totalSec = totalSec;
    }

    //把时分秒换算成秒
    public TimeSpot(int hour, int min, int sec) {
        this.hour = hour;
        this.min = min;
        this.sec = sec;
        this.totalSec = hour * 3600 + min * 60 + sec;
    }

    //把秒换算成时分秒
    public TimeSpot(int totalSec) {
        this.totalSec = totalSec;
        int m = totalSec / 60;
        this.sec = totalSec % 60;
        this.hour = m / 60;
        this.min = m % 60;
    }

    //完成某一时间后的新时间
    public TimeSpot add(int secs) {
        return new TimeSpot(this.totalSec + secs);
    }

    public TimeSpot sub(int secs) {
        return new TimeSpot(this.totalSec - secs);
    }

    //时间间隔
    public int interval(TimeSpot timeSpot) {
        return this.totalSec - timeSpot.totalSec;
    }


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer(7);
        sb.append(hour).append(":");
        if (min < 10) {
            sb.append("0");
        }
        sb.append(min).append(":");
        if (sec < 10) {
            sb.append("0");
        }
        sb.append(sec);
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TimeSpot) {
            return this.totalSec == ((TimeSpot) obj).totalSec;
        }
        return false;
    }

    public int compareTo(TimeSpot timeSpot) {
        return this.totalSec - timeSpot.totalSec;
    }

    public int getTotalSec() {
        return totalSec;
    }
}

