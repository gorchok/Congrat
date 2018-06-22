package ru.pochivalin.congrat.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import ru.pochivalin.congrat.model.Person;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class ExcelServiceImpl {

    private static String[] columns = { "Имя", "Фамилия", "$"};
    static final Logger excelLogger = LogManager.getLogger(ExcelServiceImpl.class);

    public void CreateTable(List<Person> list) {

        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("answerpro");

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Create a Row
        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        // Create Other rows and cells with data
        int rowNum = 1;

        for (Person p : list) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(p.getLastName());
            row.createCell(1).setCellValue(p.getFirsName());
            //row.createCell(2).setCellValue(p.getEmail());
            //row.createCell(3).setCellValue(p.getDate());
        }

        // Resize all columns to fit the content size
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        try (FileOutputStream fileOut = new FileOutputStream("money.xls")) {
            excelLogger.info("save file");
            workbook.write(fileOut);
            fileOut.close();
        }
        catch(FileNotFoundException e){
            excelLogger.error(e.getMessage());
        }
        catch(IOException ex){
            excelLogger.error(ex.getMessage());
        }

    }

}
