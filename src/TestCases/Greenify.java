package TestCases;

import java.util.NoSuchElementException;
import java.util.Properties;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;

import Operation.Config;
import Operation.ReadObjectRepository;
import Operation.SeleniumOperation;

public class Greenify
{
	static WebDriver driver;

@Test
public void testGreenify() throws Exception
	{
	//Code to read the Config file, pass the test data xls sheet number as argument.
	driver = Config.testConfig(0);
	Thread.sleep(5000);
	
	//Code to read the Object Repository and call UI Operation
	ReadObjectRepository object = new ReadObjectRepository();
	Properties allObjects = object.getObjectRepository();
	SeleniumOperation operation = new SeleniumOperation(driver);
	
	try
		{
			//----operation.perform(allObjects, Keyword, ObjectName, Object Type, Value)----
			operation.execute(allObjects, "CLICK", "Hibernate", "id", "");
		}
	catch (NoSuchElementException e) 
		{	
			e.printStackTrace();
			driver.quit();
		}
	}
}


