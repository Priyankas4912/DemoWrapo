package Utility;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailBO {

	public void sendEmailWithAttachments(String host, String port,final String userName, final String password, String toAddress,String toCC,String subject, String message, String[] attachFiles,PrintStream out)
			throws AddressException, MessagingException {
		try {
			// sets SMTP server properties
			Properties properties = new Properties();
			properties.put("mail.smtp.host", host);
			properties.put("mail.smtp.port", port);
			properties.put("mail.smtp.auth", "true");
			//  properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.user", userName);
			properties.put("mail.password", password);

			// creates a new session with an authenticator
			Authenticator auth = new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, password);
					// return new PasswordAuthentication(userName);
				}
			};
			Session session = Session.getInstance(properties, auth);

			// creates a new e-mail message
			Message msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress(userName));
			InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
			msg.setRecipients(Message.RecipientType.TO, toAddresses);

			InternetAddress[] myCcList = InternetAddress.parse(toCC);      
			msg.setRecipients(Message.RecipientType.CC,myCcList);

			msg.setSubject(subject);
			msg.setSentDate(new Date());

			// creates message part
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(message, "text/html");

			// creates multi-part
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			// adds attachments
			if (attachFiles != null && attachFiles.length > 0)
			{
				for (String filePath : attachFiles)
				{
					MimeBodyPart attachPart = new MimeBodyPart(); 
					try 
					{
						attachPart.attachFile(filePath);
					} 
					catch (IOException ex) 
					{
						ex.printStackTrace();
					} 
					multipart.addBodyPart(attachPart);
				}
			}
			// sets the multi-part as e-mail's content
			msg.setContent(multipart);
			// sends the e-mail
			Transport.send(msg);
			// System.out.println("EMail With attachment Doen");
		} catch (Exception e) {
			//  Auto-generated catch block
			System.out.println(e);
		}
		System.setOut(out);

	}

	public StringBuilder emailBody()
	{
		StringBuilder msg=new StringBuilder();
		msg.append("Hello Automation Edge User ,<p/>");
		msg.append("Thank you for your request in Automation Edge.<p/>");
		msg.append("Your request has been completed for Collection Update.<p/>");
		msg.append("Please Find Attachement in the Mail for the output file.<p/>");
		msg.append("Thanking You,<br>");
		msg.append("____________________________________________________________________________________________________<br>");
		msg.append("<b><u><font color='orange'>Automation <font color='grey'>Edge</p>");

		return msg;
	}

	public StringBuilder emailBodyFile()
	{
		StringBuilder msg=new StringBuilder();
		msg.append("Hello Automation Edge User ,<p/>");
		msg.append("Message : File uploaded by user is in Wrong Format/Type/Name/Sheet Name .<p/>");
		msg.append("Please check file format and resend the request.<p/>");
		msg.append("Thank you for your request in Automation Edge.<p/>");
		msg.append("Your request has been completed for Collection Update .<p/>");
		msg.append("Please Find Attachement in the Mail for the input file .<p/>");
		msg.append("Thanking You,<br>");
		msg.append("____________________________________________________________________________________________________<br>");
		msg.append("<b><u><font color='orange'>Automation <font color='grey'>Edge</p>");

		return msg;
	}

	public StringBuilder emailBodyGen(String genericError)
	{
		StringBuilder msg=new StringBuilder();
		msg.append("Hello Automation Edge User ,<p/>");
		msg.append("Message : BOT processing has stopped as Generic Error has occured.<p/>");
		msg.append("Error :<b><p/>"+genericError+"</b><p/>");
		msg.append("Please resolve the error.<p/>");
		msg.append("Thank you for your request in Automation Edge.<p/>");
		msg.append("Your request has been completed for Collection Update .<p/>");
		msg.append("Please Find Attachement in the Mail for the output file .<p/>");
		msg.append("Thanking You,<br>");
		msg.append("____________________________________________________________________________________________________<br>");
		msg.append("<b><u><font color='orange'>Automation <font color='grey'>Edge</p>");

		return msg;
	}

	public StringBuilder emailBodyInvMob()
	{
		StringBuilder msg=new StringBuilder();
		msg.append("Hello Automation Edge User ,<p/>");
		msg.append("Message : Mobility - Invalid Login Credentials<p/>");
		msg.append("Please check your Mobility Login Credentials and resend the request.<p/>");
		msg.append("Thank you for your request in Automation Edge.<p/>");
		msg.append("Your request has been completed for Collection Update .<p/>");
		//msg.append("Please Find Attachement in the Mail for the output file .<p/>");
		msg.append("Thanking You,<br>");
		msg.append("____________________________________________________________________________________________________<br>");
		msg.append("<b><u><font color='orange'>Automation <font color='grey'>Edge</p>");

		return msg;
	}

	public StringBuilder emailBodyInvLMS()
	{
		StringBuilder msg=new StringBuilder();
		msg.append("Hello Automation Edge User ,<p/>");
		msg.append("Message : LMS - Invalid Login Credentials<p/>");
		msg.append("Please check your LMS Login Credentials and resend the request.<p/>");
		msg.append("Thank you for your request in Automation Edge.<p/>");
		msg.append("Your request has been completed for Collection Update .<p/>");
		msg.append("Please Find Attachement in the Mail for the output file .<p/>");
		msg.append("Thanking You,<br>");
		msg.append("____________________________________________________________________________________________________<br>");
		msg.append("<b><u><font color='orange'>Automation <font color='grey'>Edge</p>");

		return msg;
	}

	public StringBuilder emailBodyProp()
	{
		StringBuilder msg=new StringBuilder();
		msg.append("Hello Automation Edge User ,<p/>");
		msg.append("Message : CollectionUpdate.properties File Not Found <p/>");
		msg.append("Solution : Property file Path= D:\\VyomLabs\\jarsBats\\collectionUpdate\\Util\\CollectionUpdateProcess.properties <p/>");
		msg.append("Please check your LMS Login Credentials and resend the request.<p/>");
		msg.append("Thank you for your request in Automation Edge.<p/>");
		msg.append("Your request has been completed for Collection Update .<p/>");
		msg.append("Please Find Attachement in the Mail for the output file .<p/>");
		msg.append("Thanking You,<br>");
		msg.append("____________________________________________________________________________________________________<br>");
		msg.append("<b><u><font color='orange'>Automation <font color='grey'>Edge</p>");

		return msg;
	}
	
	public StringBuilder emailStartMsg()
	{
		StringBuilder msg=new StringBuilder();
		msg.append("Hello Automation Edge User ,<p/>");
		msg.append("Thank you for your request in Automation Edge.<p/>");
		msg.append("Your request is received for Collection Update.<p/>");
		msg.append("Your Request has been Started.<p/>");
		msg.append("Thanking You,<br>");
		msg.append("____________________________________________________________________________________________________<br>");
		msg.append("<b><u><font color='orange'>Automation <font color='grey'>Edge</p>");

		return msg;
	}
	
	public StringBuilder emailSummary()
	{
		StringBuilder msg=new StringBuilder();
		msg.append("<table border=1><tr><th colspan=5><center>Process Summary</center</th></tr>");
		msg.append("<tr><td>Total Records Executed</td><td>500</td><td> </td><td> </td><td> </td></tr>");
		msg.append("<tr><td> </td><td>Mobility Hold</td><td>100</td><td> </td><td> </td></tr>");
		msg.append("<tr><td> </td><td>Mobility Hold(Exception)</td><td>100</td><td> </td><td> </td></tr>");
		msg.append("<tr><td> </td><td>Mobility Authorised</td><td>300</td><td> </td><td> </td></tr>");
		msg.append("<tr><td> </td><td> </td><td>LMS Authorised</td><td>200</td><td> </td></tr>");
		msg.append("<tr><td> </td><td> </td><td>LMS Rejected</td><td>100</td><td> </td></tr>");
		msg.append("<tr><td> </td><td> </td><td> </td><td>Date out of Range</td><td>50</td></tr>");
		msg.append("<tr><td> </td><td> </td><td> </td><td>Amount Mis-Matched</td><td>25</td></tr>");
		msg.append("<tr><td> </td><td> </td><td> </td><td>Challan Number not found</td><td>25</td></tr>");
		msg.append("<tr><td colspan=5><center>VYOMLABS</center</td></tr></table>");
		
		return msg;
	}
	
	public void sendEmailWithMessage(String host, String port,final String userName, final String password, String toAddress,String toCC,String subject, String message,PrintStream out)
			throws AddressException, MessagingException {
		try {
			// sets SMTP server properties
			Properties properties = new Properties();
			properties.put("mail.smtp.host", host);
			properties.put("mail.smtp.port", port);
			properties.put("mail.smtp.auth", "true");
			//properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.user", userName);
			properties.put("mail.password", password);

			// creates a new session with an authenticator
			Authenticator auth = new Authenticator()		
			{
				public PasswordAuthentication getPasswordAuthentication()
				{
					return new PasswordAuthentication(userName, password);
					// return new PasswordAuthentication(userName);
				}
			};
			Session session = Session.getInstance(properties, auth);

			// creates a new e-mail message
			Message msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress(userName));
			InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
			msg.setRecipients(Message.RecipientType.TO, toAddresses);

			InternetAddress[] myCcList = InternetAddress.parse(toCC);      
			msg.setRecipients(Message.RecipientType.CC,myCcList);

			msg.setSubject(subject);
			msg.setSentDate(new Date());

			// creates message part
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(message, "text/html");

			// creates multi-part
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			// adds attachments
			/*if (attachFiles != null && attachFiles.length > 0)
			{
				for (String filePath : attachFiles)
				{
					MimeBodyPart attachPart = new MimeBodyPart(); 
					try 
					{
						attachPart.attachFile(filePath);
					} 
					catch (IOException ex) 
					{
						ex.printStackTrace();
					} 
					multipart.addBodyPart(attachPart);
				}
			}*/

			// sets the multi-part as e-mail's content
			msg.setContent(multipart);

			// sends the e-mail
			Transport.send(msg);
			// System.out.println("EMail With attachment Done");
		} catch (Exception e) {
			//  Auto-generated catch block
			System.out.println(e);
		}
		System.setOut(out);
	}

	

	/*public static void main(String[] args) 
	{
		 EmailBO emailBO=new EmailBO();
        // SMTP info
        String host = "172.16.80.138";
        String port = "25";
        String mailFrom = "haridas.mate@automationedge.com";
        String password = "hari$20May";

        // message info
        String mailTo ="haridas.mate@automationedge.com";// "Dineshkumar.P@tvscredit.com";
        String mailCC = "haridas.mate@automationedge.com";//"Manickam.P@tvscredit.com";
        String subject = "AutomationEdgeFile";
        //StringBuilder message =new StringBuilder("I have some attachments for you.");
       // StringBuilder message =emailBO.emailStartMsg();
        StringBuilder message =emailBO.emailSummary();

        // attachments
        String[] attachFiles = new String[1];
        //attachFiles[0] = "D:/VyomLabs/zips/soa_67925200006240.zip";
        attachFiles[0] = "D:\\VyomLabs\\DataOUTPUT.txt";
      
        try
        {
        	 emailBO.sendEmailWithMessage(host, port, mailFrom, password, mailTo,mailCC, subject, message.toString());
           // emailBO.sendEmailWithAttachments(host, port, mailFrom, password, mailTo,mailCC, subject, message.toString(),attachFiles);
            System.out.println("Email sent.");
        } 
        catch (Exception ex) 
        {
            System.out.println("Could not send email.");
            ex.printStackTrace();
        }
    }*/
}