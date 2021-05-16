package org.fis2021.services;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class MailService {

    private String user; // GMail user name (just the part before "@gmail.com")
    private String pass; // GMail password
    private String recipient;

    public MailService(String from, String pass, String to) {
        if(from.contains("@")){
            this.user = from.substring(0, from.indexOf("@"));
        } else {
            this.user = from;
        }
        this.pass = pass;
        this.recipient = to;
    }

    public MailService() {

    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void sendFromGMail(String from, String pass, String to, String subject, String body) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress toAddress = new InternetAddress(to);
            message.addRecipient(Message.RecipientType.TO, toAddress);
            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException ae) {
            ae.printStackTrace();
        }
    }
}
