package uz.app.service;

import lombok.SneakyThrows;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.Properties;


public class SimpleMailSender {
    @SneakyThrows
    public  void sendSmsToUser(String email,String text) throws Exception {
//        DataSource source = new FileDataSource("M:\\Veb\\Lesson2\\assets\\img\\img.png");
        String subject = """
        <div>
        <h1>This is code to confirm your account <a href=https://kun.uz> %s </a>, please don't share list code with anyone </h1>
         </div>
        """.formatted(text);
        Properties properties = new Properties();
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","465");
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.ssl.enable","true");

        Authenticator auth=new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("youremail@gmail.com","your app parol");
            }
        };

        Session session = Session.getInstance(properties,auth);

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("youremail@gmail.com"));
        message.setRecipient(Message.RecipientType.TO,new InternetAddress(email));
        message.setSubject(text);
        message.setContent(subject,"text/html");
//        message.setContent(sendMedia(subject,"F:\\img.jpg"));
        Transport.send(message);
    }
//public Multipart sendMedia(String content ,String path) throws MessagingException {
//    Multipart mimeMultipart=new MimeMultipart();
//    BodyPart bodyPart=new MimeBodyPart();
//    bodyPart.setContent(content,"text/html");
//    mimeMultipart.addBodyPart(bodyPart);
//    bodyPart = new MimeBodyPart();
//    DataSource source = new FileDataSource(path); // Replace with the actual file path
//    bodyPart.setDataHandler(new DataHandler(source));
//    bodyPart.setFileName("img.jpg");
////    bodyPart.setContent(content,"text/html");
//    mimeMultipart.addBodyPart(bodyPart);
//    return mimeMultipart;
//}
//    private static String getImageAsBase64() throws IOException {
//        Base64.Encoder encoder = Base64.getEncoder();
//        Path path = Path.of("M:\\Veb\\Lesson2\\assets\\img\\img.png");
//        byte[] allBytes = Files.readAllBytes(path);
//        String encodeToString = encoder.encodeToString(allBytes);
//        Files.writeString(Path.of("M:\\Veb\\Lesson2\\assets\\img\\imageAstring.txt"), encodeToString, StandardOpenOption.TRUNCATE_EXISTING);
//        return encodeToString;
//    }

}
