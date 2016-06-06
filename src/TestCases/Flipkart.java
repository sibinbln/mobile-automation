package TestCases;

import java.util.NoSuchElementException;
import java.util.Properties;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;

import Operation.Config;
import Operation.ReadExcel;
import Operation.ReadObjectRepository;
import Operation.SeleniumOperation;
import Operation.WriteExcel;

public class Flipkart
{
	static WebDriver driver;

@Test
public void testFlipkart() throws Exception
	{
	//Code to read the Config file, pass the test data xls sheet number as argument.
	driver = Config.testConfig(1);
	Thread.sleep(5000);
	
	//Code to read the Object Repository and call UI Operation
	ReadObjectRepository object = new ReadObjectRepository();
	Properties allObjects = object.getObjectRepository();
	SeleniumOperation operation = new SeleniumOperation(driver);
	
	//Code to read data from excel file
	ReadExcel re = new ReadExcel();
	String xllocation = System.getProperty("user.dir")+"\\TestData\\TestData.xls";
	re.setInputFile(xllocation, 1);
	String[][] values = re.readFile();
	
	try
		{
			//----operation.perform(allObjects, Keyword, ObjectName, Object Type, Value)----
			operation.execute(allObjects, "CLEAR", "FBEmail", "id", "");
			operation.execute(allObjects, "SENDKEYS", "FBEmail", "id", values[1][4]);
			operation.execute(allObjects, "CLICK", "FBPassword", "id", "");
			operation.execute(allObjects, "SENDKEYS", "FBPassword", "id", values[1][5]);
			operation.execute(allObjects, "CLICK", "FBLogin", "id", "");
			operation.execute(allObjects, "CLICK", "FBSearch", "id", ""); //To close the popup alert
			operation.execute(allObjects, "CLICK", "FBSearch", "id", "");
			operation.execute(allObjects, "CLICK", "FBTypeinSearchBox", "id", "");
			operation.execute(allObjects, "SENDKEYS", "FBTypeinSearchBox", "id", values[1][6]);
			operation.execute(allObjects, "CLICK", "FBFirstSearchList", "id", "");
			operation.execute(allObjects, "CLICK", "FBProductClick", "id", "");
			String ProductName = operation.execute(allObjects, "GETTEXT", "FBProductTitle", "id", "");
			String ProductPrice = operation.execute(allObjects, "GETTEXT", "FBProductPrice", "id", "");
			Thread.sleep(5000);
			
			//Code to write data to excel file
			WriteExcel objExcelFile = new WriteExcel();
			String ProductDetails = ProductName+"---"+ProductPrice;
			String[] PdtDetails = new String[] {ProductDetails};
			objExcelFile.writeExcel(System.getProperty("user.dir")+"\\TestData","Flipkart.xls","Flipkart",PdtDetails);
			driver.quit();
		}
	catch (NoSuchElementException e) 
		{	
			e.printStackTrace();
			driver.quit();
		}
	}
}


