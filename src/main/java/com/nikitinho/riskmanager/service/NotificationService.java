package com.nikitinho.riskmanager.service;

import com.nikitinho.riskmanager.domain.Risk;
import com.nikitinho.riskmanager.dto.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.nio.file.Files;
import java.util.Properties;

@Service
public class NotificationService {

    @Autowired
    private Environment env;

    public void sendNotification(Risk risk, EventType eventType) {

        StringBuilder subject = new StringBuilder(risk.getText());
        String documentStatus;
        switch (eventType) {
            case CREATE:
                documentStatus = "was just created!";
                break;
            case UPDATE:
                documentStatus = "was just updated!";
                break;
            case REMOVE:
                documentStatus = "was just removed!";
                break;
            default:
                return;
        }
        subject.append(" - ");
        subject.append(documentStatus);


        String result = "";
        File file;
        try {
            file = ResourceUtils.getFile("classpath:templates/email.html");
            System.out.print(file.exists());
            result = new String(Files.readAllBytes(file.toPath()));
        } catch (Exception e) {
            e.printStackTrace();
        }


        String template = "<!-- Key values: [88 - risk.getText(), 99 - Risk was just (created || updated || removed), 110 - risk.getDescription(), 120 - href=\"link-to-risk\"] -->\n" + "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" + "<head>\n" + "    <meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\">\n" + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0;\">\n" + "    <meta name=\"format-detection\" content=\"telephone=no\"/>\n" + "\n" + "    <!-- Responsive Mobile-First Email Template by Konstantin Savchenko, 2015.\n" + "    https://github.com/konsav/email-templates/  -->\n" + "\n" + "    <style>\n" + "        /* Reset styles */\n" + "        body { margin: 0; padding: 0; min-width: 100%; width: 100% !important; height: 100% !important;}\n" + "        body, table, td, div, p, a { -webkit-font-smoothing: antialiased; text-size-adjust: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; line-height: 100%; }\n" + "        table, td { mso-table-lspace: 0pt; mso-table-rspace: 0pt; border-collapse: collapse !important; border-spacing: 0; }\n" + "        img { border: 0; line-height: 100%; outline: none; text-decoration: none; -ms-interpolation-mode: bicubic; }\n" + "        #outlook a { padding: 0; }\n" + "        .ReadMsgBody { width: 100%; } .ExternalClass { width: 100%; }\n" + "        .ExternalClass, .ExternalClass p, .ExternalClass span, .ExternalClass font, .ExternalClass td, .ExternalClass div { line-height: 100%; }\n" + "\n" + "        /* Rounded corners for advanced mail clients only */\n" + "        @media all and (min-width: 560px) {\n" + "            .container { border-radius: 8px; -webkit-border-radius: 8px; -moz-border-radius: 8px; -khtml-border-radius: 8px;}\n" + "        }\n" + "\n" + "        /* Set color for auto links (addresses, dates, etc.) */\n" + "        a, a:hover {\n" + "            color: #127DB3;\n" + "        }\n" + "        .footer a, .footer a:hover {\n" + "            color: #999999;\n" + "        }\n" + "\n" + "    </style>\n" + "\n" + "</head>\n" + "\n" + "<!-- BODY -->\n" + "<!-- Set message background color (twice) and text color (twice) -->\n" + "<body topmargin=\"0\" rightmargin=\"0\" bottommargin=\"0\" leftmargin=\"0\" marginwidth=\"0\" marginheight=\"0\" width=\"100%\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0; width: 100%; height: 100%; -webkit-font-smoothing: antialiased; text-size-adjust: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; line-height: 100%;\n" + "\tbackground-color: #F0F0F0;\n" + "\tcolor: #000000;\"\n" + "      bgcolor=\"#F0F0F0\"\n" + "      text=\"#000000\">\n" + "\n" + "<!-- SECTION / BACKGROUND -->\n" + "<!-- Set message background color one again -->\n" + "<table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0; width: 100%;\" class=\"background\"><tr><td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0;\"\n" + "                                                                                                                                                                                                   bgcolor=\"#F0F0F0\">\n" + "\n" + "    <!-- WRAPPER -->\n" + "    <!-- Set wrapper width (twice) -->\n" + "    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\"\n" + "           width=\"560\" style=\"border-collapse: collapse; border-spacing: 0; padding: 0; width: inherit;\n" + "\tmax-width: 560px;\" class=\"wrapper\">\n" + "\n" + "        <tr>\n" + "            <td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0; padding-left: 6.25%; padding-right: 6.25%; width: 87.5%;\n" + "\t\t\tpadding-top: 20px;\n" + "\t\t\tpadding-bottom: 20px;\">\n" + "\n" + "                <!-- PREHEADER -->\n" + "                <!-- Set text color to background color -->\n" + "                <div style=\"display: none; visibility: hidden; overflow: hidden; opacity: 0; font-size: 1px; line-height: 1px; height: 0; max-height: 0; max-width: 0;\n" + "\t\t\tcolor: #F0F0F0;\" class=\"preheader\">\n" + "                    Do not respond to this email. Donate or not donate? ^_^</div>\n" + "\n" + "            </td>\n" + "        </tr>\n" + "\n" + "        <!-- End of WRAPPER -->\n" + "    </table>\n" + "\n" + "    <!-- WRAPPER / CONTEINER -->\n" + "    <!-- Set conteiner background color -->\n" + "    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\"\n" + "           bgcolor=\"#FFFFFF\"\n" + "           width=\"560\" style=\"border-collapse: collapse; border-spacing: 0; padding: 0; width: inherit;\n" + "\tmax-width: 560px;\" class=\"container\">\n" + "\n" + "        <!-- HEADER -->\n" + "        <!-- Set text color and font family (\"sans-serif\" or \"Georgia, serif\") -->\n" + "        <tr>\n" + "            <td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0; padding-left: 6.25%; padding-right: 6.25%; width: 87.5%; font-size: 24px; font-weight: bold; line-height: 130%;\n" + "\t\t\tpadding-top: 25px;\n" + "\t\t\tcolor: #000000;\n" + "\t\t\tfont-family: sans-serif;\" class=\"header\">\n" + "                "+risk.getText()+"\n" + "            </td>\n" + "        </tr>\n" + "\n" + "        <!-- SUBHEADER -->\n" + "        <!-- Set text color and font family (\"sans-serif\" or \"Georgia, serif\") -->\n" + "        <tr>\n" + "            <td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0; padding-bottom: 3px; padding-left: 6.25%; padding-right: 6.25%; width: 87.5%; font-size: 18px; font-weight: 300; line-height: 150%;\n" + "\t\t\tpadding-top: 5px;\n" + "\t\t\tcolor: #000000;\n" + "\t\t\tfont-family: sans-serif;\" class=\"subheader\">\n" + "                "+
                "Risk " + documentStatus +"\n" + "            </td>\n" + "        </tr>\n" + "\n" + "        <!-- PARAGRAPH -->\n" + "        <!-- Set text color and font family (\"sans-serif\" or \"Georgia, serif\"). Duplicate all text styles in links, including line-height -->\n" + "        <tr>\n" + "            <td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0; padding-left: 6.25%; padding-right: 6.25%; width: 87.5%; font-size: 17px; font-weight: 400; line-height: 160%;\n" + "\t\t\tpadding-top: 25px;\n" + "\t\t\tcolor: #000000;\n" + "\t\t\tfont-family: sans-serif;\" class=\"paragraph\">\n" + "                "+risk.getDescription()+"\n" + "            </td>\n" + "        </tr>\n" + "\n" + "        <!-- BUTTON -->\n" + "        <!-- Set button background color at TD, link/text color at A and TD, font family (\"sans-serif\" or \"Georgia, serif\") at TD. For verification codes add \"letter-spacing: 5px;\". Link format: http://domain.com/?utm_source={{Campaign-Source}}&utm_medium=email&utm_content={{Button-Name}}&utm_campaign={{Campaign-Name}} -->\n" + "        <tr>\n" + "            <td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0; padding-left: 6.25%; padding-right: 6.25%; width: 87.5%;\n" + "\t\t\tpadding-top: 25px;\n" + "\t\t\tpadding-bottom: 5px;\" class=\"button\">"+
                "<a\n" + "                    href=\"link-to-risk\" target=\"_blank\" style=\"text-decoration: underline;\">\n" + "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"max-width: 240px; min-width: 120px; border-collapse: collapse; border-spacing: 0; padding: 0;\"><tr><td align=\"center\" valign=\"middle\" style=\"padding: 12px 24px; margin: 0; text-decoration: underline; border-collapse: collapse; border-spacing: 0; border-radius: 4px; -webkit-border-radius: 4px; -moz-border-radius: 4px; -khtml-border-radius: 4px;\"\n" + "                                                                                                                                                                                               bgcolor=\"#E9703E\"><a target=\"_blank\" style=\"text-decoration: underline;\n" + "\t\t\t\t\tcolor: #FFFFFF; font-family: sans-serif; font-size: 17px; font-weight: 400; line-height: 120%;\"\n" + "                                                                                                                                                                                                                    href=\"https://github.com/konsav/email-templates/\">\n" + "                    Open risk view\n" + "                </a>\n" + "                </td></tr></table></a>\n" + "            </td>\n" + "        </tr>\n" + "\n" + "        <!-- LINE -->\n" + "        <!-- Set line color -->\n" + "        <tr>\n" + "            <td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0; padding-left: 6.25%; padding-right: 6.25%; width: 87.5%;\n" + "\t\t\tpadding-top: 25px;\" class=\"line\"><hr\n" + "                    color=\"#E0E0E0\" align=\"center\" width=\"100%\" size=\"1\" noshade style=\"margin: 0; padding: 0;\" />\n" + "            </td>\n" + "        </tr>\n" + "\n" + "        <!-- PARAGRAPH -->\n" + "        <!-- Set text color and font family (\"sans-serif\" or \"Georgia, serif\"). Duplicate all text styles in links, including line-height -->\n" + "        <tr>\n" + "            <td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0; padding-left: 6.25%; padding-right: 6.25%; width: 87.5%; font-size: 17px; font-weight: 400; line-height: 160%;\n" + "\t\t\tpadding-top: 20px;\n" + "\t\t\tpadding-bottom: 25px;\n" + "\t\t\tcolor: #000000;\n" + "\t\t\tfont-family: sans-serif;\" class=\"paragraph\">\n" + "                Have a&nbsp;question? <a href=\"mailto:support@ourteam.com\" target=\"_blank\" style=\"color: #127DB3; font-family: sans-serif; font-size: 17px; font-weight: 400; line-height: 160%;\">support@ourteam.com</a>\n" + "            </td>\n" + "        </tr>\n" + "\n" + "        <!-- End of WRAPPER -->\n" + "    </table>\n" + "\n" + "    <!-- WRAPPER -->\n" + "    <!-- Set wrapper width (twice) -->\n" + "    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\"\n" + "           width=\"560\" style=\"border-collapse: collapse; border-spacing: 0; padding: 0; width: inherit;\n" + "\tmax-width: 560px;\" class=\"wrapper\">\n" + "\n" + "        <!-- FOOTER -->\n" + "        <!-- Set text color and font family (\"sans-serif\" or \"Georgia, serif\"). Duplicate all text styles in links, including line-height -->\n" + "        <tr>\n" + "            <td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0; padding-left: 6.25%; padding-right: 6.25%; width: 87.5%; font-size: 13px; font-weight: 400; line-height: 150%;\n" + "\t\t\tpadding-top: 20px;\n" + "\t\t\tpadding-bottom: 20px;\n" + "\t\t\tcolor: #999999;\n" + "\t\t\tfont-family: sans-serif;\" class=\"footer\">\n" + "\n" + "                This email template was sent to&nbsp;you becouse we&nbsp;want to&nbsp;make the&nbsp;world a&nbsp;better place. You&nbsp;could change your <a href=\"https://github.com/konsav/email-templates/\" target=\"_blank\" style=\"text-decoration: underline; color: #999999; font-family: sans-serif; font-size: 13px; font-weight: 400; line-height: 150%;\">subscription settings</a> anytime.\n" + "\n" + "                <!-- ANALYTICS -->\n" + "                <!-- https://www.google-analytics.com/collect?v=1&tid={{UA-Tracking-ID}}&cid={{Client-ID}}&t=event&ec=email&ea=open&cs={{Campaign-Source}}&cm=email&cn={{Campaign-Name}} -->\n" + "                <img width=\"1\" height=\"1\" border=\"0\" vspace=\"0\" hspace=\"0\" style=\"margin: 0; padding: 0; outline: none; text-decoration: none; -ms-interpolation-mode: bicubic; border: none; display: block;\"\n" + "                     src=\"https://raw.githubusercontent.com/konsav/email-templates/master/images/tracker.png\" />\n" + "\n" + "            </td>\n" + "        </tr>\n" + "\n" + "        <!-- End of WRAPPER -->\n" + "    </table>\n" + "\n" + "    <!-- End of SECTION / BACKGROUND -->\n" + "</td></tr></table>\n" + "\n" + "</body>\n" + "</html>";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(env.getProperty("spring.mail.username"),
                                env.getProperty("spring.mail.password"));
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(env.getProperty("spring.mail.username")));
            message.setSubject(subject.toString());
            message.setContent(result, "text/html");

            risk.getResponsible().forEach(user -> {
                try {
                    message.setRecipients(Message.RecipientType.TO,
                            InternetAddress.parse(user.getEmail()));
                    Transport.send(message);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
