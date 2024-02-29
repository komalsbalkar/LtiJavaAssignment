package stockAssignment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class xlsAssignment {

	static Map<String,String> testdata=new HashMap<String,String>();
	static Map<String,String> testdata1=new HashMap<String,String>();
	public static void main(String[] args) throws IOException {
				
		xlsAssignment xls=new xlsAssignment();
		xls.getMapData();
		xls.getMapLiveData();
		xls.storeInExcel();
		
	}
	public static Map<String,String> getMapData() throws IOException{
		String excelFilePath = "C:\\Users\\komal\\OneDrive\\Desktop\\stockName.xlsx"; 
			try {
			FileInputStream fis = new FileInputStream(excelFilePath);
            Workbook workbook = new XSSFWorkbook(fis);
			Sheet sheet=workbook.getSheetAt(0);	
			int lastRowNo= sheet.getLastRowNum();
						for(int i=1;i<=lastRowNo;i++) {
				Row row=sheet.getRow(i);
				Cell keycell=row.getCell(0);
				String key=keycell.getStringCellValue().trim();
				Cell valuecell=row.getCell(1);
				double value=valuecell.getNumericCellValue();
				String values = Double.toString(value);
				testdata.put(key, values);
				
			}
						
			for(Entry<String,String> map:testdata.entrySet()) {
				System.out.println(map.getKey()+"  "+map.getValue());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return testdata;
	}
	public static Map<String,String> getMapLiveData() throws IOException{

		//Map<String,String> testdata1=new HashMap<String,String>();
		WebDriver driver=new ChromeDriver();
		driver.get("https://money.rediff.com/losers/bse/daily/groupall?");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		List<WebElement> stockNames=driver.findElements(By.xpath("//tr//td/a"));
		List<WebElement> stockPrices=driver.findElements(By.xpath("//tr//td[4]"));
			
		for (int i = 1; i < stockPrices.size(); i++) {
            String key =  stockNames.get(i).getText();
            //System.out.println(key);
            String value = stockPrices.get(i).getText();
            value = value.replaceAll("[^\\d.]", "");      
            testdata1.put(key,value);
        }
								
			for(Entry<String,String> map:testdata1.entrySet()) {
				System.out.println(map.getKey()+"  "+map.getValue());
			}
			driver.quit();	
		return testdata1;
		
	}
	public static void storeInExcel() throws IOException {
		
		  XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet=workbook.createSheet("Common Stocks");
				int rowNo=0;
				for(String key:testdata.keySet()) {
					String value1 = testdata.get(key);
		            String value2 = testdata1.get(key);
		            if(value2 == null || !value1.equals(value2)) {
		            	XSSFRow row=sheet.createRow(rowNo++);
						row.createCell(0).setCellValue(key);
						row.createCell(1).setCellValue(value1);	
						row.createCell(2).setCellValue(value2 == null ?"":value2);
		            }
				}
				
				FileOutputStream fos = new FileOutputStream(".//target//stocks.xlsx");
				workbook.write(fos);
				fos.close();

	}


}
