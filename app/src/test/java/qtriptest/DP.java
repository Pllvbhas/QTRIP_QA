package qtriptest;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.rmi.server.ObjID;
import com.microsoft.schemas.office.visio.x2012.main.CellType;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class DP {
    @DataProvider(name = "data_provider")
    public Object[][] dataProvider(Method method) throws IOException{
        File fileName = new File(System.getProperty("user.dir") + "/src/test/resources/DatasetsforQTrip.xlsx");
		FileInputStream file = new FileInputStream(fileName);
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheet(method.getName());
		int rowsCount = sheet.getPhysicalNumberOfRows();
        int colsCount = sheet.getRow(1).getLastCellNum();
		Object[][] array = new Object[rowsCount-1][colsCount-1];
		DataFormatter formatter = new DataFormatter();
        for (int outer = 1; outer < rowsCount; outer++) {
			XSSFRow rows = sheet.getRow(outer);
			for (int inner = 1; inner < colsCount; inner++) {
				XSSFCell cell = rows.getCell(inner);
				array[outer-1][inner-1]=formatter.formatCellValue(cell);
			}
		}
		file.close();
		workbook.close();     
		return array;
	}
}

