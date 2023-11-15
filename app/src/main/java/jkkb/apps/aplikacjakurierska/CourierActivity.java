package jkkb.apps.aplikacjakurierska;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.GetPhoneNumberHintIntentRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.common.api.ApiException;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;

import jkkb.apps.aplikacjakurierska.QR.QRActivity;

public class CourierActivity extends AppCompatActivity {

    private Button scan_qr_btn;
    private ImageButton back_btn;
    private ArrayList<Order> orders;
    private String userMob;

    private ActivityResultLauncher<IntentSenderRequest> someActivityResultLauncher;

    private void phoneSelection() {


    }


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier);

        back_btn = findViewById(R.id.courier_button_back);
        scan_qr_btn = findViewById(R.id.courier_scan_qr_button);
        scan_qr_btn.setOnClickListener((View view) -> readQR());
        back_btn.setOnClickListener((View view) -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        String nr;
        //lista
        RecyclerView list_view = findViewById(R.id.order_list);
        Log.println(Log.INFO, "", getApplicationInfo().dataDir);

        list_view.setLayoutManager(new LinearLayoutManager(this));


        //test
        orders = new ArrayList<>();
        orders.add(new Order(
                new Sender("Adam", "Nowak", "123456789", new Address("Warszawa", "Polna", "33-190")),
                new Receiver("Anna", "Kowalska", "987654321", new Address("Krakow", "Dluga", "22-222"))
        ));
        orders.add(new Order(
                new Sender("Anna", "Malinowska", "123456789", new Address("Olsztyn", "Ogrodowa", "11-111")),
                new Receiver("Robert", "Kowalski", "987654321", new Address("Krakow", "Dluga", "22-222"))
        ));

        orders.add(new Order(
                new Sender("Urszula", "Nowak", "123456789", new Address("Rzeszow", "Ogrodowa", "11-111")),
                new Receiver("Robert", "Piechocki", "987654321", new Address("Katowice", "Dluga", "22-222"))
        ));
        orders.get(0).setState(OrdersState.TRANSPORTED);
        orders.get(2).setState(OrdersState.READY_TO_RECEIVE);

        list_view.setAdapter(new OrderListAdapter(CourierActivity.this, orders));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(list_view.getContext(), DividerItemDecoration.VERTICAL);
        list_view.addItemDecoration(dividerItemDecoration);


    }

    private void readQR(){
        //Podłącza klasę QRActivity i ustawia właściwości skanu
        ScanOptions options = new ScanOptions();
        options.setPrompt("Zeskanuj kod QR z przesyłki");
        options.setOrientationLocked(true);
        options.setCaptureActivity(QRActivity.class);
        Log.println(Log.INFO,"","test");
        launcher.launch(options);
    }

    // Po wykryciu przesyłki pokazuje komunikat
    ActivityResultLauncher<ScanOptions> launcher = registerForActivityResult(new ScanContract(), res ->{
        if(res.getContents() != null){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Wykryto przesyłkę, nr identyfikacyjny: ");
            builder.setMessage(res.getContents());
            builder.setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss()).show();
        }
    });
}