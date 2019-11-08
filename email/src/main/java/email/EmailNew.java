package email;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class EmailNew
{
	public static WebDriver driver;
	@BeforeClass
	public void init()
	{
		System.setProperty("webdriver.chrome.driver", "D:\\BCSM2\\Email\\email\\chromedriver_win32_v2.30_s3.0.1.exe");
		driver = new ChromeDriver();
		driver.get("https://www.jmrinfotech.com");
	}
	
	@Test
	public void a()
	{
		System.out.println("Fist Test Method");
	}
	
	@Test
	public void b()
	{
		System.out.println("Second Test Method");
	}
	
	@AfterClass
	public static void close_and_sendmail() throws AddressException, IOException, MessagingException
	{
		driver.close();
		Runtime r = Runtime.getRuntime();
		r.addShutdownHook(new Thread(){  
			public void run(){  
				Mailing sm = new Mailing();
				try {
					sm.mail();
					System.out.println("Report has been sent"); 
				} catch (IOException | MessagingException e) {
					e.printStackTrace();
				}
				
			    }  
			}  
			);  
			try{Thread.sleep(5000);}catch (Exception e) 
			{
				System.out.println(e);
			}  
	}
}
