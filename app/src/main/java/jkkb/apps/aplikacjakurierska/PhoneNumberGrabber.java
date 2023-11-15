package jkkb.apps.aplikacjakurierska;


import android.content.Context;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;

import com.google.android.gms.auth.api.identity.GetPhoneNumberHintIntentRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.common.api.ApiException;

public class PhoneNumberGrabber {

    private GetPhoneNumberHintIntentRequest request = GetPhoneNumberHintIntentRequest.builder().build();

    public static String getPhoneNr() {
        return phone_nr;
    }

    public static void setPhoneNr(String phone_nr) {
        PhoneNumberGrabber.phone_nr = phone_nr;
    }

    private static String phone_nr;

    //metoda pozwalająca na wyświetlenie okna pozwalającego na wybór numeru telefonu
    public void requestPhoneNumber(Context context, ActivityResultLauncher launcher){
        Identity.getSignInClient(context)
                .getPhoneNumberHintIntent(request)
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }).addOnSuccessListener(pendingIntent -> {
                    IntentSenderRequest intentSenderRequest = new IntentSenderRequest.Builder(pendingIntent.getIntentSender()).build();
                    System.out.println("xdds");
                    launcher.launch(intentSenderRequest);
                });
    }


}