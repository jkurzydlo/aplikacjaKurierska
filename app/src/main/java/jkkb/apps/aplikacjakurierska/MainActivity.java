package jkkb.apps.aplikacjakurierska;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.Executor;

import jkkb.apps.aplikacjakurierska.Authentication.AuthenticationManager;

//TODO: Dodać pobieranie kodu QR do pliku
public class MainActivity extends AppCompatActivity {
    private Button buyer_btn,seller_btn,courier_btn;
    private Executor executor;
    private BiometricPrompt biometric_prompt;
    private AuthenticationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Autoryzacja biometryczna
        executor = ContextCompat.getMainExecutor(this);
        biometric_prompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(), "Błąd autoryzacji: " + errString, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), "Autoryzacja powiodła się", Toast.LENGTH_SHORT).show();

                //Jeśli autoryzacja powiodła się - przejdź do sekcji odbiorcy
                Intent intent = new Intent(getApplicationContext(),ReceiverActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Autoryzacja nie powiodła się", Toast.LENGTH_SHORT).show();
            }
        });

        buyer_btn = findViewById(R.id.receiver_button);
        seller_btn = findViewById(R.id.sender_button);
        courier_btn = findViewById(R.id.courier_button);

        manager = new AuthenticationManager(this);



        courier_btn.setOnClickListener((View view)->{
            Intent intent = new Intent(this, CourierActivity.class); //Przejście z MainActivity (this) (menu) do trybu kuriera
            startActivity(intent);
            finish();
        });
        seller_btn.setOnClickListener((View view)->{
            Intent intent = new Intent(this,SenderActivity.class);
            startActivity(intent);
            finish();
        });
        buyer_btn.setOnClickListener((View view)->{
            biometric_prompt.authenticate(manager.getPrompt());
        });

    }
}