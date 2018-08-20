package com.mannu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Properties;

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

public class SendMail extends Thread{
	private Connection con;

	public SendMail() {
		this.con=DbCon.DbCon();
	}
	
	DataUtility utility=new DataUtility();
	
	public void run() {
		
		try {
			List<AckDetails> adetails=utility.getErrorData();
			if(adetails.size()>0) {
				Properties prop=new Properties();
				prop.put("mail.smtp.port", "25");
				prop.put("mail.smtp.auth", "true");
				prop.put("mail.smtp.starttls.enable", "true");
				prop.put("mail.smtp.ssl.trust", "srv-mail-ch5.karvy.com");
				Session session = Session.getInstance(prop,new javax.mail.Authenticator() {
					protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
						return new javax.mail.PasswordAuthentication("cra.info@karvy.com","Aqafmf*22");
					}
				});
				 StringBuilder body = new StringBuilder(); 
				 body.append("<div style=\"font-style: oblique; font-size: 17px;\">Dear Team,</div>")
					.append("<p></p>")
					.append("<div style=\"font-style: oblique; font-size: 17px;\">Below mention Applications Photo / Signature is not cropped properly. Please check and crop all the application again..</div>");
				 
				 body.append("<p></p>")
					.append("<table border=\"2\" style=\"border-collapse:collapse;text-align:center\">")
					.append("  <tr>")
				     .append("    <th style=\"padding:5px\">SlNo</th>")
				     .append("    <th style=\"padding:5px\">InwardNo</th>")
				     .append("    <th style=\"padding:5px\">AcknowledgeNo</th>")
				     .append("    <th style=\"padding:5px\">CroppedBy</th>")
				     .append("    <th style=\"padding:5px\">DateTime</th>")
				     .append("    <th style=\"padding:5px\">PhotoStatus</th>")
				     .append("    <th style=\"padding:5px\">SignatureStatus</th>")
				     .append("  </tr>");
				 for(AckDetails ad : adetails) {
					 System.out.println("Inward: "+ad.getInwardNo());
					 body.append("  <tr>")
				     .append("    <td>"+ad.getId()+"</td>")
				     .append("    <td>"+ad.getInwardNo()+"</td>")
				     .append("    <td>"+ad.getAckNo()+"</td>")
				     .append("    <td>"+ad.getCropBy()+"</td>")
				     .append("    <td>"+ad.getCropTime()+"</td>")
				     .append("    <td>"+ad.getPhotoStatus()+"</td>")
				     .append("    <td>"+ad.getSignatureStatus()+"</td>")
				     .append("  </tr>")
				     .append("");
					 try {
						if(con.isClosed()) {
							this.con=DbCon.DbCon();
						}
						PreparedStatement update=con.prepareStatement("update PanCropvalidation set CheckStatus='Done' where InwardNo='"+ad.getInwardNo()+"' and AckNo='"+ad.getAckNo()+"'");
						update.execute();
						update.close();
					} catch (Exception e) {
						e.printStackTrace();
					} 
				 }
				 
				 body.append("</table>")
				 .append("<p></p>")
				 .append("<div style=\"font-style: oblique; font-size: 17px;\">Thanking you,</div>")
				 .append("<div style=\"font-style: oblique; font-size: 17px;\">Karvy Data Management Services Ltd.</div>")
				 .append("<br></br>")
					.append("<div>*** This is an automatically generated email, please do not reply ***</div>")
			     	.append("</div>")
			     	.append("<br></br>");
			    
				 MimeMessage msg = new MimeMessage(session);
					try {
						msg.setFrom(new InternetAddress("cra.info@karvy.com"));
						msg.setSubject("Pan Cropping Rejection");
						
						msg.addRecipient(Message.RecipientType.TO, new InternetAddress("KaladharReddy.P@karvy.com"));
						msg.addRecipient(Message.RecipientType.CC, new InternetAddress("help.tin@karvy.com"));
						 Multipart multipart = new MimeMultipart();
					        BodyPart htmlBodyPart = new MimeBodyPart();
					        htmlBodyPart.setContent(body.toString() , "text/html"); 
					        multipart.addBodyPart(htmlBodyPart);
					        msg.setContent(multipart);
						Transport transport = session.getTransport("smtp");
				        transport.connect("srv-mail-ch5.karvy.com", 25,"cra.info@karvy.com","Aqafmf*22");
				        transport.sendMessage(msg, msg.getAllRecipients());
				        transport.close();
						 
					} catch (AddressException e) {
						e.printStackTrace();
					} catch (MessagingException e) {
						e.printStackTrace();
					}
				 
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
