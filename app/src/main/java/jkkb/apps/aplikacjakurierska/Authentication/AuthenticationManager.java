package jkkb.apps.aplikacjakurierska.Authentication;

import android.content.Context;
import android.util.Log;

import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;

//Klasa odpowiedzialna za wybór obsługiwanych opcji autoryzacji i jej obsługę
public class AuthenticationManager {
    private BiometricPrompt.PromptInfo prompt;
    //Możliwe opcje autoryzacji
    private int auth_options =  BiometricManager.Authenticators.BIOMETRIC_STRONG |
            BiometricManager.Authenticators.DEVICE_CREDENTIAL;

    public AuthenticationManager(Context context){
        prompt = new BiometricPrompt.PromptInfo.Builder().
                setTitle("Logowanie biometryczne").
                setSubtitle("Zaloguj się używając odcisku palca").
                setAllowedAuthenticators(auth_options).
                build();
        BiometricManager manager = BiometricManager.from(context);

        switch (manager.canAuthenticate(auth_options)) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                Log.println(Log.INFO, "", "Autoryzacja jest wspierana");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Log.println(Log.INFO, "", "Brak odpowiedniego czujnika");
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Log.println(Log.INFO, "", "Czujnik niedostępny");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Log.println(Log.INFO, "", "Brak istniejącego poświadczenia");
                break;
            case BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED:
                Log.println(Log.INFO, "", "Wymagana aktualizacja oprogramowania");
                break;
            case BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED:
                Log.println(Log.INFO, "", "Biometria nie wspierana");
                break;
            case BiometricManager.BIOMETRIC_STATUS_UNKNOWN:
                Log.println(Log.INFO, "", "Nieznany stan czujnika");
                break;
        }
    }

    public BiometricPrompt.PromptInfo getPrompt() {
        return prompt;
    }
}
