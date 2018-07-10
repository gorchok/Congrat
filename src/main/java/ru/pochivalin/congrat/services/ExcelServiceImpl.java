package ru.pochivalin.congrat.services;

import lombok.extern.log4j.Log4j2;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.pochivalin.congrat.model.Person;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Log4j2
@Component
public class ExcelServiceImpl {

    private static String[] columns = {"Фамилия", "Имя", "$"};

    @Value("${excel.headerFont.height}")
    private short headerFontHeight;

    public void createTable(final List<Person> list, final String subject) {

        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("answerpro");

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints(headerFontHeight);
        headerFont.setColor(IndexedColors.RED.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        Row headerRow0 = sheet.createRow(0);

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
            log.info("save file");
            workbook.write(fileOut);
            fileOut.close();
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }

    }

}
