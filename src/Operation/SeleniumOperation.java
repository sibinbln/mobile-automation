package Operation;

import java.io.File;

import org.apache.commons.io.FileUtils;

import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.TakesScreenshot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SeleniumOperation 
{
	static WebDriver driver;

	public SeleniumOperation(WebDriver driver) throws Exception 
		{
			SeleniumOperation.backupreport();
			SeleniumOperation.driver = driver;
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		}

	public String execute(Properties p, String operation, String objectName, String objectType, String value) throws Exception 
	{
			try
			{
				if (operation.toUpperCase()== "CLICK")
				{
					driver.findElement(SeleniumOperation.getObject(p, objectName, objectType)).click();
				}
				else if (operation.toUpperCase()== "SELECTLASTINDEX")
				{
					WebElement we = driver.findElement(SeleniumOperation.getObject(p, objectName, objectType));
					Select mySelect= new Select(we);
					int indexsize = mySelect.getOptions().size();
				    int size = indexsize - 1;
				    mySelect.selectByIndex(size);
				}
				else if (operation.toUpperCase()== "SENDKEYS")
				{
					driver.findElement(SeleniumOperation.getObject(p, objectName, objectType)).sendKeys(value);
				}
				else if (operation.toUpperCase()== "ACCESSURL")
				{
					driver.get(value);
				}
				else if (operation.toUpperCase()== "GETATTRIBUTE")
				{
					driver.findElement(SeleniumOperation.getObject(p, objectName, objectType)).getAttribute(value);
				}
				else if (operation.toUpperCase()== "GETCSSVALUE")
				{
					driver.findElement(SeleniumOperation.getObject(p, objectName, objectType)).getCssValue(value);
				}
				else if (operation.toUpperCase()== "GETLOCATION")
				{
					driver.findElement(SeleniumOperation.getObject(p, objectName, objectType)).getLocation();
				}
				else if (operation.toUpperCase()== "CLEAR")
				{
					driver.findElement(SeleniumOperation.getObject(p, objectName, objectType)).clear();
				}
				else if (operation.toUpperCase()== "SELECTBYINDEX")
				{
					new Select(driver.findElement(SeleniumOperation.getObject(p, objectName, objectType))).selectByIndex(2);
				}
				else if (operation.toUpperCase()== "SELECTBYINDEX1")
				{
					new Select(driver.findElement(SeleniumOperation.getObject(p, objectName, objectType))).selectByIndex(1);
				}
				else if (operation.toUpperCase()== "SWICTHTOIFRAME")
				{
					driver.switchTo().frame(driver.findElement(SeleniumOperation.getObject(p, objectName, objectType)));
				}
				else if (operation.toUpperCase()== "SWITCHFROMIFRAME")
				{
					driver.switchTo().defaultContent();
				}
				else if (operation.toUpperCase()== "SELECT")
				{
					new Select(driver.findElement(SeleniumOperation.getObject(p, objectName, objectType))).selectByValue(value);
				}
				else if (operation.toUpperCase()== "GETTEXT")
				{
					String values = driver.findElement(SeleniumOperation.getObject(p, objectName, objectType)).getText();
					return values;
				}
				else if (operation.toUpperCase()== "GETTEXTVALUE")
				{
					String data = driver.findElement(SeleniumOperation.getObject(p, objectName, objectType)).getAttribute("value");
					return data;
				}
				else if (operation.toUpperCase()== "SCROLLBROWSER")
				{
					JavascriptExecutor jse = (JavascriptExecutor)driver;
					jse.executeScript("window.scrollBy(0,500)", "");
				}
							
				}
			catch (Exception e)
			{
	    		//calls the method to take the screenshot.
	    		getscreenshot();
	    		System.out.println("TestScript Failed....");
	    		driver.quit();
	    		throw new RuntimeException(e);
	    		//System.out.println(e);
	    	 }
			return value;
}
	
	public static void backupreport() throws Exception
	{
		String reportlocation = System.getProperty("user.dir")+"\\test-output";
		File file = new File(reportlocation);
		Calendar cal = Calendar.getInstance();
		String backuplocation = System.getProperty("user.dir")+"\\TestResults\\TestNG_Report_"+cal.get(Calendar.YEAR)+"_"+cal.get(Calendar.DAY_OF_MONTH)+"_"+(cal.get(Calendar.MONTH)+1)+"_"+cal.get(Calendar.HOUR)+"-"+cal.get(Calendar.MINUTE)+"-"+cal.get(Calendar.SECOND);
	    File file2 = new File(backuplocation);
		if(file2.exists()) throw new java.io.IOException("file exists");
	    FileUtils.copyDirectory(file, file2);
	}

	private static By getObject(Properties p, String objectName, String objectType) throws Exception 
	{
		// Find by xpath
		if (objectType.equalsIgnoreCase("XPATH")) 
		{
			return By.xpath(p.getProperty(objectName));
		}
		// find by class
		else if (objectType.equalsIgnoreCase("CLASSNAME")) 
		{
			return By.className(p.getProperty(objectName));
		}
		// find by name
		else if (objectType.equalsIgnoreCase("NAME")) 
		{
			return By.name(p.getProperty(objectName));
		}
		// find by id
		else if (objectType.equalsIgnoreCase("ID")) 
		{
			return By.id(p.getProperty(objectName));
		}
		// Find by css
		else if (objectType.equalsIgnoreCase("CSS")) 
		{
			return By.cssSelector(p.getProperty(objectName));
		}
		// find by linkText
		else if (objectType.equalsIgnoreCase("LINK")) 
		{
			return By.linkText(p.getProperty(objectName));
		}
		
		// find by partial link
		else if (objectType.equalsIgnoreCase("PARTIALLINK")) 
		{
			return By.partialLinkText(p.getProperty(objectName));
		} else 
		{
			throw new Exception("Wrong object type");
		}
}

	public static void getscreenshot() throws Exception 
    {
            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            Calendar cal = Calendar.getInstance();
            String Screenshotlocation = System.getProperty("user.dir")+"\\FailureScreenshots";
            FileUtils.copyFile(scrFile, new File(Screenshotlocation+"/failure"+cal.get(Calendar.HOUR)+cal.get(Calendar.MINUTE)+cal.get(Calendar.SECOND)+".png"));
    }
	
	public static String querydb(String query, String database) throws Exception 
	{
		try
		{
			String drv = "com.mysql.jdbc.Driver";
		    String url = "jdbc:mysql://stage-all.ccmx3ca7iwb0.us-west-1.rds.amazonaws.com/"+database+"";
		    String username = "root";
		    String password = "br0nd1#321";
		    Class.forName(drv);
		    Connection conn = DriverManager.getConnection(url, username, password);
		    Statement st = conn.createStatement();
		       
		    ResultSet rs = st.executeQuery(query);
		    String db_email = null;
		    while(rs.next())
		    	{
		    		db_email=rs.getString(1);
		    		break;
		    	}
		    return db_email;
		}
		catch (Exception ex) 
			{
				System.out.println("Error while connecting to database:" + ex);
				return null;
			}
	}
	
	public static boolean isdisplayed(Properties p, String operation, String objectName, String objectType, String value) throws Exception 
	{
			try
			{
				if (operation.toUpperCase()== "ISDISPLAYED")
				{
					boolean exists = driver.findElement(SeleniumOperation.getObject(p, objectName, objectType)).isDisplayed();
					return exists;
				}
				else
				{
					return false;
				}
			}
			catch (Exception e)
			{
				getscreenshot();
				throw new RuntimeException(e);
			}
	}
	public static boolean verifyTextPresent(String value) throws Exception 
	{
	  return driver.getPageSource().contains(value);
	  
	}
}
