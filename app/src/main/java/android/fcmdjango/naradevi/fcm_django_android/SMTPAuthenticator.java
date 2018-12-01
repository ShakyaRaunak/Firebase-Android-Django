package android.fcmdjango.naradevi.fcm_django_android;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator extends Authenticator {

    SMTPAuthenticator() {
        super();
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        String username = "igctest12345@gmail.com";
        String password = "igc_test_12345";

        return new PasswordAuthentication(username, password);
    }
}