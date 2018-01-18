package Business;

import java.util.Properties;
 
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 

public class sendEmail 
{
    public static void sendConfirmationEmail(String mailIdTo, String message , String subject)
    {
         
        final String username = "dbms.neu.edu@gmail.com";
        final String password = "Asdfgh1@"; 
        String smtpHost = "smtp.gmail.com";
        String smtpPort = "587";
         
        //setting the server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", smtpPort);
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
         
        //getting the session object
        Session session = Session.getInstance(properties,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            }
        );
 
        try {
           
            Message msg = new MimeMessage(session);
           
            msg.setFrom(new InternetAddress("dbms.neu.edu@gmail.com"));
 
            
            InternetAddress  ia = new InternetAddress(mailIdTo);
            
           
            msg.addRecipient(javax.mail.Message.RecipientType.TO, ia);
            //setting mail subject
            msg.setSubject(subject);
            //setting the message to send
            msg.setText(message);
            //sending the message
            Transport.send(msg);
 
            System.out.println("Mail sent successfully");
 
        } 
        catch (MessagingException e) 
        {
            e.printStackTrace();
            
        }
    }

}