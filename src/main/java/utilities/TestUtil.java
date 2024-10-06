package utilities;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class TestUtil {

    public static Object[][] getTestData(String sheetName) {
        Object[][] data = null;

        try (FileInputStream file = new FileInputStream("src/test/java/testData/LoginData.xlsx")) {

            //Workbook workbook = new HSSFWorkbook(file);
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheet(sheetName);

            data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];

            for (int i = 0; i < sheet.getLastRowNum(); i++) {
                for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
                    data[i][j] = sheet.getRow(i + 1).getCell(j).toString();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
}
