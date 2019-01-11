package com.sgai.ds.dtgarage.prepare.entity;

import org.apache.poi.ss.usermodel.Cell;

import java.util.Calendar;
import java.util.Date;

public class TimeSpot {
    private static final Calendar CALENDAR = Calendar.getInstance();
    private final int h;
    private final int m;
    private final int s;

    /**
     * 检测字符串是否符合要求的时间格式，要求时间格式为(h:m:s)
     *
     * @param time 用字符串表示的时间，
     * @return 返回 {@link ResultStatus#OK} 表示格式正确；
     * 返回 {@link ResultStatus#WARNING} 表示时间格式不满足要求，
     * 但可以猜测含义。
     * 返回 {@link ResultStatus#ERROR} 表示格式错误且无法猜测含义。
     */
    public static ResultStatus isTimeFormat(String time) {
        String[] timeSplit = time.split(":");
        try {
            int h = Integer.parseInt(timeSplit[0]);
            if (h > 23) {
                return ResultStatus.ERROR;
            }
        } catch (NumberFormatException e) {
            return ResultStatus.ERROR;
        }
        try {
            int m = Integer.parseInt(timeSplit[1]);
            if (m < 0 || m > 59) {
                return ResultStatus.ERROR;
            }
        } catch (NumberFormatException e) {
            return ResultStatus.ERROR;
        }
        switch (timeSplit.length) {
            case 2:
                return ResultStatus.WARNING;
            case 3:
                break;
            default:
                return ResultStatus.ERROR;
        }
        try {
            int s = Integer.parseInt(timeSplit[2]);
            if (s < 0 || s > 59) {
                return ResultStatus.ERROR;
            }
        } catch (NumberFormatException e) {
            return ResultStatus.ERROR;
        }
        return ResultStatus.OK;
    }

    /**
     * 用于猜测 Numeric 加显示格式，所表示时间
     *
     * @param value 对于 Numeric Cell, 使用 {@link Cell#getNumericCellValue()} 取得的值
     * @return 返回一个 {@link TimeSpot} 对象。返回 null 表示无法猜测。
     */
    public static TimeSpot newInstance(int value) {
        int h, m, s = 0;
        m = value % 100;
        h = value / 100;
        if (h > 23) {
            return null;
        }
        return new TimeSpot(h, m, s);
    }

    /**
     * 根据给定的时分秒数值构造时间点对象。
     *
     * @param h 时
     * @param m 分
     * @param s 秒
     */
    private TimeSpot(int h, int m, int s) {
        this.h = h;
        this.m = m;
        this.s = s;
    }

    /**
     * 根据 Date Cell 的值，转换得到 {@link TimeSpot}
     * @param date 使用 {@link Cell#getDateCellValue()} 得到的日期。
     * @return 返回转换后得到的 {@link TimeSpot}
     */
    public static TimeSpot newInstance(Date date) {
        CALENDAR.setTime(date);
        return new TimeSpot(CALENDAR.get(Calendar.HOUR_OF_DAY), CALENDAR.get(Calendar.MINUTE),
                CALENDAR.get(Calendar.SECOND));
    }

    /**
     * 将时间转换成为 {@link String}
     * @return 返回一个表示时间的 {@link String}, 格式为 (h:mm:ss)
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(8);
        sb.append(h).append(":");
        if (m < 10) {
            sb.append("0");
        }
        sb.append(m).append(":");
        if (s < 10) {
            sb.append("0");
        }
        sb.append(s);
        return sb.toString();
    }
}
