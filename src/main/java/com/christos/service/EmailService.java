package com.christos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Service
public class EmailService {

    // This is used for thymeleaf
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender mailSender;

    // This gets the enabled value from the configuration.
    @Value("${mail.enable}")
    private Boolean enable;

    @Value("${site.url}")
    private String url;

    private void send(MimeMessagePreparator preparator) {

        if (enable) {
            mailSender.send(preparator);
        }

    }

    // Here we tell Spring where to find the templates of the thymeleaf
    @Autowired
    public EmailService(TemplateEngine templateEngine) {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("mail/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCacheable(false);           // We always want to send a new email, not a cached one
        templateEngine.setTemplateResolver(templateResolver);

        this.templateEngine = templateEngine;
    }

    @Async
    public void sendVerificationEmail(String emailAddress, String token) {

        StringBuilder sb = new StringBuilder();
        Context context = new Context();
        context.setVariable("token", token);
        context.setVariable("url", url);

        // Here we set the name of the template (prefix and suffix will be added automatically)
        String emailContents = templateEngine.process("verifyEmail", context);

        // We output the email to the console for debugging
        System.out.println(emailContents);

/*      // This will be replaced with a thymeleaf template
        sb.append("<HTML>");
        sb.append("<p>Hello, this is <strong>HTML</strong></p>");
        sb.append("</HTML>");
*/


        // We implement this interface
        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {

                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);

                message.setTo(emailAddress);
                message.setFrom(new InternetAddress("noreply@springtestcourse.com"));
                message.setSubject("Verify your email address");
                message.setSentDate(new Date());
/*                message.setText(sb.toString(), true);*/  // Got replaced by thymeleaf

                message.setText(emailContents, true);

            }
        };

        send(preparator);

    }


}
