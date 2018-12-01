package android.fcmdjango.naradevi.fcm_django_android;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // private TextView fcm_registration_id_textView;
    private EditText fcm_registration_id_editText, recipient_email_editText;
    private Button delete_token_button, copy_token_button, email_token_button;
    private SharedPrefManager sharedPrefManager;
    private BroadcastReceiver broadcastReceiver;
    private ClipboardManager clipboard;
    private ClipData clipData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPrefManager = SharedPrefManager.getInstance(this);
        // fcm_registration_id_textView = findViewById(R.id.fcm_registration_id_textView);
        fcm_registration_id_editText = findViewById(R.id.fcm_registration_id_editText);
        // fcm_registration_id_textView.setText(sharedPrefManager.getToken());
        fcm_registration_id_editText.setText(sharedPrefManager.getToken());

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // fcm_registration_id_textView.setText(sharedPrefManager.getToken());
                fcm_registration_id_editText.setText(sharedPrefManager.getToken());
            }
        };

        registerReceiver(broadcastReceiver, new IntentFilter(MyFirebaseInstanceIDService.TOKEN_BROADCAST));

        initButtonComponents();
    }

    private void initButtonComponents() {
        initDeleteTokenButton();
        initCopyTokenButton();
        initEmailTokenButton();
    }

    private void initDeleteTokenButton() {
        delete_token_button = findViewById(R.id.delete_token_button);
        delete_token_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPrefManager.deleteToken();
            }
        });
    }

    private void initCopyTokenButton() {
        copy_token_button = findViewById(R.id.copy_token_button);
        copy_token_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = fcm_registration_id_editText.getText().toString();
                clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clipData = ClipData.newPlainText("FCM Token", text);
                clipboard.setPrimaryClip(clipData);
                Toast.makeText(getApplicationContext(), "Text Copied", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initEmailTokenButton() {
        recipient_email_editText = findViewById(R.id.recipient_email_editText);
        email_token_button = findViewById(R.id.email_token_button);
        email_token_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String recipient_email = recipient_email_editText.getText().toString();
                String email_content = fcm_registration_id_editText.getText().toString();
                sendEmail(recipient_email, email_content);
            }
        });
    }

    private void sendEmail(String recipient_email, String email_content) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipient_email});
        //emailIntent.setData(Uri.parse("mailto:" + recipient_email));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "FCM Token - Micromax");
        emailIntent.putExtra(Intent.EXTRA_TEXT, email_content);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send email using..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "No email clients installed.", Toast.LENGTH_SHORT).show();
        }

    }
}