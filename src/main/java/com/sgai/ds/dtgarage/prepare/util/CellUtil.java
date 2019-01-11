package com.sgai.ds.dtgarage.prepare.util;


import com.sgai.ds.dtgarage.prepare.entity.CellParseResult;
import com.sgai.ds.dtgarage.prepare.entity.ResultStatus;
import com.sgai.ds.dtgarage.prepare.entity.TimeSpot;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

public class CellUtil {
    /**
     * 尝试以时间方式解析单元格内容。
     *
     * @param cell 表示 Excel 中的一个单元格
     * @return 返回一个 {@link CellParseResult} 对象，其中 {@link CellParseResult#result} 是
     * {@link ResultStatus} 类型的枚举值。
     * <p>
     * {@link ResultStatus#OK} 代表，解析成功，时间字符串会保存在 {@link CellParseResult#value} 中；
     * <p>
     * {@link ResultStatus#WARNING} 代表，猜测成功，需要用户确认，时间字符串会保存在
     * {@link CellParseResult#value} 中；
     * <p>
     * {@link ResultStatus#ERROR} 代表，无法解析，需要用户仔细检查，{@link CellParseResult#value}将为null。
     */
    public static CellParseResult parseTimeSport(Cell cell) {
        CellParseResult result = new CellParseResult();
        if (cell == null || cell.getDateCellValue() == null) {
            result.value = null;
            result.result = ResultStatus.OK;
            return result;
        }
        switch (cell.getCellTypeEnum()) {
            case NUMERIC:
            case FORMULA:
                if (DateUtil.isCellDateFormatted(cell)) {
                    result.value = TimeSpot.newInstance(cell.getDateCellValue()).toString();
                    result.result = ResultStatus.OK;
                } else {
                    try {
                        int value = (int) cell.getNumericCellValue();
                        TimeSpot time = TimeSpot.newInstance(value);
                        if (time == null) {
                            result.result = ResultStatus.ERROR;
                        } else {
                            result.value = time.toString();
                            result.result = ResultStatus.WARNING;
                        }
                    } catch (IllegalStateException e) {
                        result.result = ResultStatus.ERROR;
                    }
                }
                break;
            case STRING:
                String time = cell.getStringCellValue();
                switch (TimeSpot.isTimeFormat(time)) {
                    case OK:
                        result.value = time;
                        result.result = ResultStatus.OK;
                        break;
                    case WARNING:
                        result.value = time + ":00";
                        result.result = ResultStatus.WARNING;
                        break;
                    case ERROR:
                        result.result = ResultStatus.ERROR;
                        break;
                }
                break;
            default:
                result.result = ResultStatus.ERROR;
        }
        return result;
    }
}
