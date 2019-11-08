package email;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Mailing {
	static String key,data,from,to,password;
	static String[] AllToAdress;
	static String host = "zimpop.logix.in";
	
	
	public void GetData() throws IOException{
		
		File prop = new File("Input/credentials.properties");
		FileInputStream input =new FileInputStream(prop);
		Properties sysmProp = new Properties();
		sysmProp.load(input);
	    
		Enumeration value = sysmProp.keys();
		
		while(value.hasMoreElements()){
			key = (String) value.nextElement();
			data = sysmProp.getProperty(key);
			
			if(key.equals("to")){
				to=data;
			}
			if(key.equals("from")){
				from=data;
			}
			if(key.equals("password")){
				password=data;
			}
			
		}
		
		AllToAdress = to.split(",");
		
	}
	
public void mail() throws IOException, AddressException, MessagingException{

	GetData();
	
	try{
//	System properties	
	Properties sysmProp = System.getProperties(); 
	sysmProp.put("mail.starttls.enable", "true");
	sysmProp.put("mail.host", host);
	sysmProp.put("mail.user", from);
	sysmProp.put("mail.password", password);
	sysmProp.put("mail.port", "995");
	sysmProp.put("mail.auth", "true");
	 
	
//	Creating session and mime message object	
	Session session = Session.getInstance(sysmProp);
	MimeMessage message = new MimeMessage(session) ;
	message.setFrom(new InternetAddress(from)); 
		
//	Form Internet address array
	InternetAddress[] ia = new InternetAddress[AllToAdress.length];
	
	for(int i=0; i<AllToAdress.length;i++){
		ia[i] = new InternetAddress(AllToAdress[i]);		
	}
	
	for(int j=0;j<AllToAdress.length;j++){
		message.addRecipient(Message.RecipientType.TO, ia[j]);		
	}
	
	Date dt = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:MM:S");  
    String strDate= formatter.format(dt);  
	
//	Set subject and body and adding attachments.
	message.setSubject("Execution report on "+strDate);	
	BodyPart messageBodyPart = new MimeBodyPart();	
	messageBodyPart.setText("Hello, Good day! \n"
			+ "\n"
			+ "All scenarios have been executed. Please find the attached report of the execution.  \n"
			+ "\n"
			+ "Thanks,\n"
			+ "J Rahul.");
	Multipart multipart = new MimeMultipart();
	multipart.addBodyPart(messageBodyPart);
		
	File file2 = new File("D:\\BCSM2\\Email\\email\\test-output\\emailable-report.html");
	
	messageBodyPart = new MimeBodyPart();
	DataSource source = new FileDataSource(file2.getAbsolutePath());
    messageBodyPart.setDataHandler(new DataHandler(source));
    messageBodyPart.setFileName("Report_"+strDate+".html");
    multipart.addBodyPart(messageBodyPart);
    message.setContent(multipart);
	
//	Start transportation of message
	Transport trans = session.getTransport("smtps");
	trans.connect(host, from, password);
	trans.sendMessage(message, message.getAllRecipients());
	
	for(String address:AllToAdress){
		System.out.println("Mail has been sent to "+address);		
	}
	
	trans.close();
	}catch(AddressException ae ){
		System.out.println("Address exception: "+ae);
	}catch(MessagingException me){
		System.out.println("Messaging exception: "+me);
	}catch(Exception e){
		System.out.println(e);
	}
	

}
}   