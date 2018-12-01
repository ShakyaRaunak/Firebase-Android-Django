package android.fcmdjango.naradevi.fcm_django_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);
        TextView fcm_registration_id_textView = findViewById(R.id.fcm_registration_id_textView);
        fcm_registration_id_textView.setText(sharedPrefManager.getToken());
        sharedPrefManager.deleteToken();

    }
}