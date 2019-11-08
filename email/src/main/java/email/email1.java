package email;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.testng.annotations.Test;

public class email1
{
	@Test
	public void a()
	{
		System.out.println("Hi");
	}
	@Test
	public void b() throws Exception
	{
		System.out.println("------------------Test Selenium Started------------------- ");
		String[] arrData = {"j.rahulcse@gmail.com","rahula0534@gmail.com"};
		Email email = new SimpleEmail();
		email.setHostName("zimpop.logix.in");
		email.setSmtpPort(995);
		email.setAuthenticator(new DefaultAuthenticator("rahul.j@jmrinfotech.com", "Rahul@534"));
		email.setSSLOnConnect(true);
		email.setFrom("rahul.j@jmrinfotech.com");
		email.setSubject("Test Email");
		email.setMsg("This is a test mail... ");
		//email.addTo("rahula0534@gmail.com");
		for(int i=0; i< arrData.length; i++)
		{
			email.addTo(arrData[i]);
		}
		email.send();
		System.out.println("------------------Test Selenium Ended------------------- ");
	}
}
