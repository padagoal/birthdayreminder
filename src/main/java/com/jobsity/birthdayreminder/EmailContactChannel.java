package com.jobsity.birthdayreminder;

import com.jobsity.birthdayreminder.beans.Friend;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileReader;
import java.util.Properties;

public class EmailContactChannel implements ContactChannel{

    private static final Logger logger = LogManager.getLogger(EmailContactChannel.class);
    private Properties prop = new Properties();
    private Session session;

    public EmailContactChannel(){
        setUpMail();
    }


    private void setUpMail(){
        FileReader reader = null;
        try{
            reader=new FileReader("./mail.properties");
            prop.load(reader);
        }catch (Exception e){
            logger.error("Error with the Properties File",e);
        }

        session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(prop.getProperty("mail.user"),
                                prop.getProperty("mail.password"));
                    }
                });

    }

    @Override
    public void sendBirthdayMessage(Friend friend) {
       logger.info("Sending Greeting Birthday to: "+friend.getFirstName() +" to:"+friend.getEmail());
       sendMail("Happy Birthday!","Happy Birthday, dear "+friend.getFirstName(), friend.getEmail());
    }

    @Override
    public void sendReminderMessage(Friend friend, Friend birthdayFriend) {
        logger.info ("SENDING MAIL BIRTHDAY REMINDER MESSAGE to: "+friend.getFirstName()+
                " it's "+birthdayFriend.getFirstName()+" birthdays today");

        String text = "Dear "+friend.getFirstName()+ ",\n\n Today is "+birthdayFriend.getFullName()+"'s birthday. \n\n"
                +" Don't forget to send him a message !";
        sendMail("Birthday Reminder",text, friend.getEmail());

    }

    private void sendMail(String subject,String text,String toEmail){
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(prop.getProperty("mail.from")));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);

            logger.info("Email sent successfully. to:"+toEmail);

        } catch (MessagingException e) {
            logger.error("Error with sending emails",e);
        }
    }


}
