package cn.lovezsm.bjcj.log;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("excel")
@Scope("singleton")
public class SFLoggerForExcel implements SFLogger {

    HSSFWorkbook hssfWorkbook;
    HSSFSheet sheet_raw;
    HSSFSheet sheet_record;
    HSSFSheet sheet_loc;
    public SFLoggerForExcel() {
        hssfWorkbook = new HSSFWorkbook();
        sheet_raw = hssfWorkbook.createSheet("元数据");
        sheet_record = hssfWorkbook.createSheet("均秒数据");
        sheet_loc = hssfWorkbook.createSheet("定位数据");

        HSSFRow rawTitle = sheet_raw.createRow(0);
        rawTitle.createCell(0).setCellValue("");
        rawTitle.createCell(1).setCellValue("");
        rawTitle.createCell(2).setCellValue("");
        rawTitle.createCell(3).setCellValue("");
        rawTitle.createCell(4).setCellValue("");
    }



    @Override
    public void log(String content) {

    }



}
