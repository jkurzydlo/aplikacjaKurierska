package jkkb.apps.aplikacjakurierska;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;

public class CourierActivity extends AppCompatActivity {

    private Button scan_qr_btn;
    private ImageButton back_btn;
    private ArrayList<Order> orders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier);
        back_btn = findViewById(R.id.courier_button_back);

        scan_qr_btn = findViewById(R.id.courier_scan_qr_button);
        scan_qr_btn.setOnClickListener((View view)-> readQR());
        back_btn.setOnClickListener((View view)->{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        //lista
        RecyclerView list_view = findViewById(R.id.order_list);
        Log.println(Log.INFO,"",getApplicationInfo().dataDir);

        list_view.setLayoutManager(new LinearLayoutManager(this));

        //test
        orders = new ArrayList<>();
        orders.add(new Order(
                new Sender("Adam","Nowak","123456789", new Address("Warszawa","Polna","33-190")),
                new Receiver("Anna","Kowalska","987654321",new Address("Krakow","Dluga","22-222"))
        ));
        list_view.setAdapter(new OrderListAdapter(getApplicationContext(),orders));

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