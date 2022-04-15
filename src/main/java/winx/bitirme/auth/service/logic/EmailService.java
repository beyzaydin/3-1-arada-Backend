package winx.bitirme.auth.service.logic;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Properties;

@Service
public class EmailService {

    private JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

    public void sendSimpleEmail(String toEmail, String subject, String body) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        mailSender.setJavaMailProperties(prop);


        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("winxtobbgang@gmail.com", "bitirme12345");
                    }
                });

        mailSender.setPort(587);
        mailSender.setUsername("winxtobbgang@gmail.com");
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPassword("bitirme12345");
        mailSender.setSession(session);
        mailSender.setProtocol("smtp");

        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setFrom("winxtobbgang@gmail.com");
        messageHelper.setTo(toEmail);
        messageHelper.setSubject(subject);
        messageHelper.setText(body, true);
        File img = new File("src\\main\\java\\resources\\images\\logo_green.png");
        messageHelper.addInline("logo", img);
        mailSender.send(messageHelper.getMimeMessage());
        System.out.println("Mail Send...");
    }

    public void sendEmailWithAttachment(String toEmail,
                                        String body,
                                        String subject,
                                        String attachment
    ) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();


        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom("arjungautam8877@gmail.com");
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setText(body);
        mimeMessageHelper.setSubject(subject);

        FileSystemResource fileSystemResource =
                new FileSystemResource(new File(attachment));
        mimeMessageHelper.addAttachment(fileSystemResource.getFilename(),
                fileSystemResource
        );

        System.out.println("Mail with attachment sent..");
    }
}
