/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afweb.mail;

/**
 *
 * @author koed
 */
//https://stackoverflow.com/questions/46663/how-can-i-send-an-email-by-java-application-using-gmail-yahoo-or-hotmail
// login issue
//https://gist.github.com/darwin/ee9e7855882b6f6b450fe45e9a5aa0b0
//https://stackoverflow.com/questions/16512592/login-credentials-not-working-with-gmail-smtp
import com.afweb.service.ServiceAFweb;
import com.afweb.util.CKey;
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

public class GmailSender {

    private static String protocol = "smtp";

    private String username;
    private String password;

    private Session session;
    private Message message;
    private Multipart multipart;

    public void GmailMain() {
        // Google Security Turn on Less secure app access
        try {
            GmailSender sender = new GmailSender();
            sender.setSender("myEmailName", "mypassword");
            sender.addRecipient("recipient@somehost.com");
            sender.setSubject("The subject");
            sender.setBody("The body");
//            sender.addAttachment("TestFile.txt");
            sender.send();
        } catch (Exception ex) {

        }
    }

    public GmailSender() {
        this.multipart = new MimeMultipart();
    }

    public void setSender(String username, String password) {
        this.username = username + "@gmail.com";
        this.password = password;

        this.session = getSession();
        this.message = new MimeMessage(session);
    }

    public void addRecipient(String recipient) throws AddressException, MessagingException {
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
    }

    public void setSubject(String subject) throws MessagingException {
        message.setSubject(subject);
    }

    public void setBody(String body) throws MessagingException {
        BodyPart messageBodyPart = new MimeBodyPart();
//        messageBodyPart.setText(body);
        messageBodyPart.setContent(body, "text/html");
        multipart.addBodyPart(messageBodyPart);
        message.setContent(multipart);
    }

    public void send() throws MessagingException {
        if (CKey.PROXY == true) {
            throw new ArithmeticException("Proxy not working...");
        }
        Transport transport = session.getTransport(protocol);

        transport.connect(username, password);
        transport.sendMessage(message, message.getAllRecipients());

        transport.close();
    }

    public void addAttachment(String filePath) throws MessagingException {
        BodyPart messageBodyPart = getFileBodyPart(filePath);
        multipart.addBodyPart(messageBodyPart);

        message.setContent(multipart);
    }

    private BodyPart getFileBodyPart(String filePath) throws MessagingException {
        BodyPart messageBodyPart = new MimeBodyPart();
        DataSource dataSource = new FileDataSource(filePath);
        messageBodyPart.setDataHandler(new DataHandler(dataSource));
        messageBodyPart.setFileName(filePath);

        return messageBodyPart;
    }

    private Session getSession() {
        Properties properties = getMailServerProperties();
        Session session = Session.getDefaultInstance(properties);

        return session;
    }

    private Properties getMailServerProperties() {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", protocol + ".gmail.com");
        properties.put("mail.smtp.user", username);
        properties.put("mail.smtp.password", password);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
//        if (CKey.PROXY == true) {
//            properties.put("proxySet", "true");
//            properties.put("socksProxyHost", ServiceAFweb.PROXYURL);
//            properties.put("socksProxyPort", "8080");
//        }
        return properties;
    }
}
