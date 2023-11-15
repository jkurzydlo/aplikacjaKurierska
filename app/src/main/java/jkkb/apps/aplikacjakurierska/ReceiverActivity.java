package jkkb.apps.aplikacjakurierska;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.common.api.ApiException;

public class ReceiverActivity extends AppCompatActivity {
    private final PhoneNumberGrabber grabber = new PhoneNumberGrabber();
    private String phone_nr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);
        grabber.requestPhoneNumber(this,launcher);
    }

    //zmienna określa co wykonać w aktywności określonej w klasie PhoneNumberGrabber
    ActivityResultLauncher launcher = registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(), result -> {
        try {
            //pobiera number wybrany przez użytkownika
            phone_nr = Identity.getSignInClient(this).getPhoneNumberFromIntent(result.getData()).substring(3);

        } catch (ApiException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error : "+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    });
}