package Common;

import org.testng.annotations.Test;

import Operation.SendMail;
public class SendEmail 
{

	@Test
	public void testsendEmail() throws Exception 
	{
		SendMail.execute("emailable-report.html");
	}

}
