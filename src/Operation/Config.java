package Operation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class Config 
{
	static WebDriver driver;
	public static WebDriver testConfig(int sheetno) throws Exception
	{
		ReadExcel re = new ReadExcel();
		String xllocation = System.getProperty("user.dir")+"\\TestData\\TestData.xls";
		re.setInputFile(xllocation, sheetno);
		String[][] data = re.readFile();
		
		Thread.sleep(2000);
		DesiredCapabilities capabilities  = new DesiredCapabilities();
		//capabilities.setCapability("BROWSER_NAME", "Andriod");
		//capabilities.setCapability("VERSION", "5.0.2");
		capabilities.setCapability("deviceName", data[1][0]);
		//Thread.sleep(3000);
		capabilities.setCapability("platformName", data[1][1]);
		//Thread.sleep(3000);
		capabilities.setCapability("appPackage", data[1][2]);
		//Thread.sleep(3000);
		capabilities.setCapability("appActivity", data[1][3]);
		//Thread.sleep(3000);
		return driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
	}
}