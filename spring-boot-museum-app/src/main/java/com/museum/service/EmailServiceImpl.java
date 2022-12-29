package com.museum.service;

import java.util.Properties;

import javax.mail.*;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import org.springframework.stereotype.Service;

@SuppressWarnings("unused")
@Service
public class EmailServiceImpl {

	 public boolean sendEmail(String subject,String message,String to) {
		 
		 boolean flag = false;
		 
		 String from = "testproject6409@gmail.com";
		 
		 String host="smtp.gmail.com";
		 
		 Properties properties = System.getProperties();
		 System.out.println("PROPERTIES"+properties);
		 
		 properties.put("mail.smtp.host",host);
		 properties.put("mail.smtp.port","465");
		 properties.put("mail.smtp.ssl.enable","true");
		 properties.put("mail.smtp.auth","true");
		 
		 Session session = Session.getInstance(properties,new Authenticator() {

			

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				
				return new PasswordAuthentication(from,"hvyemqzmvohcuyqc");
			}
			 
		 });
		 
		 session.setDebug(true);
		 
		MimeMessage messages = new MimeMessage(session);
		
		try {
		
			messages.setFrom();
			messages.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			messages.setSubject(subject);
			//messages.setText(message);
			messages.setContent(message, "text/html");
			Transport.send(messages);
			System.out.println("send Success..");
		
			 flag = true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		

		 
		 return flag;
	 }
}
