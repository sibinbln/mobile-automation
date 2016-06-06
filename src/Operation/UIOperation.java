package Operation;

import java.io.File;

import org.apache.commons.io.FileUtils;

import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.TakesScreenshot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class UIOperation 
{
	static WebDriver driver;

	public UIOperation(WebDriver driver) throws Exception 
		{
			UIOperation.backupreport();
			UIOperation.driver = driver;
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		}

	public void perform(Properties p, String operation, String objectName, String objectType, String value) throws Exception 
		{
				try{
				switch (operation.toUpperCase()) 
				{
					case "CLICK":
						// Perform click
						driver.findElement(UIOperation.getObject(p, objectName, objectType)).click();
						break;
					case "SENDKEYS":
						// Perform Send keys
						driver.findElement(UIOperation.getObject(p, objectName, objectType)).sendKeys(value);
						break;

					case "GOTOURL":
						// Load specific URL
						driver.get(p.getProperty(value));
						break;
					case "GETTEXT":
						// Get text of an element
						driver.findElement(UIOperation.getObject(p, objectName, objectType)).getText();
						break;
						
					case "GETATTRIBUTE":
						// Get text of an element
						driver.findElement(UIOperation.getObject(p, objectName, objectType)).getAttribute("id");
						break;
					case "GETCSSVALUE":
						// Get text of an element
						driver.findElement(UIOperation.getObject(p, objectName, objectType)).getCssValue(value);
						break;
					case "GETLOCATION":
						// Get text of an element
						driver.findElement(UIOperation.getObject(p, objectName, objectType)).getLocation();
						break;
						
					case "CLEAR":
						// Clear field
						driver.findElement(UIOperation.getObject(p, objectName, objectType)).clear();
						break;
					case "SELECTBYINDEX":
						// Selecting index values
						new Select(driver.findElement(UIOperation.getObject(p, objectName, objectType))).selectByIndex(2);
						break;
					
					default:
						break;
						}
				}
				catch (Exception e)
				{
		    		//calls the method to take the screenshot.
		    		getscreenshot();
		    		throw new RuntimeException(e);
		     	  }
				
	}
	
	public static void backupreport() throws Exception
	{
		System.out.println("Taking back up of old report");
		File file = new File("../Brandywine/test-output");
		Calendar cal = Calendar.getInstance();
	    File file2 = new File("../Brandywine/TestResults/TestNG_Report_"+cal.get(Calendar.YEAR)+"_"+cal.get(Calendar.DAY_OF_MONTH)+"_"+(cal.get(Calendar.MONTH)+1)+"_"+cal.get(Calendar.HOUR)+"-"+cal.get(Calendar.MINUTE)+"-"+cal.get(Calendar.SECOND));
	    if(file2.exists()) throw new java.io.IOException("file exists");
	    //file.renameTo(file2);
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
            //The below method will save the screen shot in d drive with name "screenshot.png"
            Calendar cal = Calendar.getInstance();
            FileUtils.copyFile(scrFile, new File("../Brandywine/FailureScreenshots/failure"+cal.get(Calendar.HOUR)+cal.get(Calendar.MINUTE)+cal.get(Calendar.SECOND)+".png"));
    }
	
	public static String connectdb(String query) throws Exception 
	{
		try
		{
			String drv = "com.mysql.jdbc.Driver";
		    String url = "jdbc:mysql://stage-all.ccmx3ca7iwb0.us-west-1.rds.amazonaws.com/brandywine_erp";
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
	
	public static String check(Properties p, String operation, String objectName, String objectType, String value) throws Exception 
	{
			try
			{
				if (operation.toUpperCase()== "GETTEXT")
				{
					String values = driver.findElement(UIOperation.getObject(p, objectName, objectType)).getText();
					return values;
				}
			}
			catch (Exception e)
			{
				getscreenshot();
				throw new RuntimeException(e);
			}
			return value;
	}
	
	public static boolean isdisplayed(Properties p, String operation, String objectName, String objectType, String value) throws Exception 
	{
			try
			{
				if (operation.toUpperCase()== "ISDISPLAYED")
				{
					boolean exists = driver.findElement(UIOperation.getObject(p, objectName, objectType)).isDisplayed();
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
