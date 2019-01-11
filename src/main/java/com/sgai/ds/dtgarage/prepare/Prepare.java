package com.sgai.ds.dtgarage.prepare;

import com.sgai.ds.dtgarage.prepare.entity.CellParseResult;
import com.sgai.ds.dtgarage.prepare.util.CellUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Prepare {

    public Map<String, List<String[]>> parseToMap(Workbook workbook) {

        Map<String, List<String[]>> result = new HashMap<>();
        List<String[]> times;
        CellParseResult cpr1, cpr2;
        int i;
        for (Sheet sheet : workbook) {
            times = new LinkedList<>();
            i = 0;
            for (Row row : sheet) {
                cpr1 = CellUtil.parseTimeSport(row.getCell(0));
                cpr2 = CellUtil.parseTimeSport(row.getCell(1));
                times.add(new String[]{String.valueOf(cpr1.value), String.valueOf(
                        cpr2.value), String.valueOf(i)});
                ++i;
            }
            result.put(sheet.getSheetName(), times);
        }
        return result;
    }
}
