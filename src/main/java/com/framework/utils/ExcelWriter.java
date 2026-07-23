package com.framework.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.framework.model.Elementinfo;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.List;

public class ExcelWriter {

    // ---- existing Excel method (unchanged) ----
    public void write(List<Elementinfo> elements, String filePath) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Elements");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Element Name");
        header.createCell(1).setCellValue("XPath");
        header.createCell(2).setCellValue("Tag");
        header.createCell(3).setCellValue("Type");

        int rowNum = 1;
        for (Elementinfo e : elements) {
            Row row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(e.getName());
            row.createCell(1).setCellValue(e.getXpath());
            row.createCell(2).setCellValue(e.getTag());
            row.createCell(3).setCellValue(e.getType());
            rowNum++;
        }

        for (int i = 0; i < 4; i++) sheet.autoSizeColumn(i);

        FileOutputStream out = new FileOutputStream(filePath);
        workbook.write(out);
        out.close();
        workbook.close();
    }

    // ---- NEW: CSV method ----
    public void writeCsv(List<Elementinfo> elements, String filePath) throws Exception {
        FileWriter writer = new FileWriter(filePath);

        // header line
        writer.write("Element Name,XPath,Tag,Type\n");

        // one line per element
        for (Elementinfo e : elements) {
            writer.write(
                quote(e.getName())  + "," +
                quote(e.getXpath()) + "," +
                quote(e.getTag())   + "," +
                quote(e.getType())  + "\n"
            );
        }

        writer.close();
    }

    // wrap a value in quotes so commas inside an xpath don't break columns
    private String quote(String value) {
        if (value == null) value = "";
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }
}