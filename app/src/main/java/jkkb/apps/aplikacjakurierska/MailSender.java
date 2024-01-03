package jkkb.apps.aplikacjakurierska;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;

public class MailSender {
    public void send(Session session, String from, String to, String subject, String content, Path attachament_path){
        MimeMessage msg = new MimeMessage(session);

        try {
            msg.setFrom(from);
            msg.setRecipients(Message.RecipientType.TO, to);
            msg.setSubject(subject,"UTF-8");
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setReplyTo(InternetAddress.parse(from, false));

            msg.setSubject(subject, "UTF-8");

            msg.setSentDate(new Date());

            //Tworzy tekstową część wiadomości email
            BodyPart bodyPart = new MimeBodyPart();
            bodyPart.setText(content);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(bodyPart);


            //Wczytuje załącznik z pliku
             bodyPart = new MimeBodyPart();
             DataSource source;

            try(InputStream stream = Files.newInputStream(Paths.get(attachament_path.toString()))){
                source = new ByteArrayDataSource(stream,"image/jpeg");
            }
            bodyPart.setDataHandler(new DataHandler(source));
            bodyPart.setFileName(attachament_path.getFileName().toString());

            multipart.addBodyPart(bodyPart);

            //Łączy część tekstową i załącznik w całość
            msg.setContent(multipart);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(msg);
                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    }
                }
            });


        }catch (MessagingException | IOException e){
            Log.println(Log.ERROR,"",",Błąd przy wysyłaniu wiadomości email");
        }




    }
}
