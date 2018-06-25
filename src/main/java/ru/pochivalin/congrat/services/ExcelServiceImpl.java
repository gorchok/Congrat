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

    private static String[] columns = {"Фамилия","Имя", "$"};

    static final Logger excelLogger = LogManager.getLogger(ExcelServiceImpl.class);

    public void CreateTable(List<Person> list, String subject) {

        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("answerpro");

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        Row headerRow0 = sheet.createRow(0);
        //String[] head = {person.getDate().toString(),person.getLastName(),person.getFirsName()};

        for (int i = 0; i < 1; i++) {
            Cell cell = headerRow0.createCell(0);
            cell.setCellValue(subject);
            cell.setCellStyle(headerCellStyle);
        }

        // Create a Row
        Row headerRow = sheet.createRow(1);

        for (int j = 0; j < columns.length; j++) {
            Cell cell = headerRow.createCell(j);
            cell.setCellValue(columns[j]);
            cell.setCellStyle(headerCellStyle);
        }

        // Create Other rows and cells with data
        int rowNum = 2;

        for (Person p : list) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(p.getLastName());
            row.createCell(1).setCellValue(p.getFirsName());
            //row.createCell(2).setCellValue(p.getEmail());
            //row.createCell(3).setCellValue(p.getDate());
        }

        // Resize all columns to fit the content size
        for (int k = 0; k < columns.length; k++) {
            sheet.autoSizeColumn(k);
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
