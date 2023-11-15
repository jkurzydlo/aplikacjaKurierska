package jkkb.apps.aplikacjakurierska;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;

import jkkb.apps.aplikacjakurierska.QR.QRActivity;

public class CourierActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Button scan_qr_btn;
    private ImageButton back_btn,refresh_btn;
    private ArrayList<Order> orders = new ArrayList<>();

    private ActivityResultLauncher<IntentSenderRequest> someActivityResultLauncher;

    private void loadOrders(RecyclerView list_view){
        db.collection("orders").get().addOnCompleteListener(task -> {
            if(task.isSuccessful())
                for(QueryDocumentSnapshot doc : task.getResult()){
                    //Dodaje zlecenia do listy
                    if(orders.size() < task.getResult().size())orders.add(doc.toObject(Order.class));
                    list_view.setAdapter(new OrderListAdapter(getApplicationContext(),orders));
                }
        });
    }
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier);

        refresh_btn = findViewById(R.id.courier_button_referesh_list);
        RecyclerView list_view = findViewById(R.id.order_list);
        //Zaktualizuj listę po naciśnięciu przycisku
        refresh_btn.setOnClickListener(view -> {
                orders.clear();
                loadOrders(list_view);
            });

        back_btn = findViewById(R.id.courier_button_back);
        scan_qr_btn = findViewById(R.id.courier_scan_qr_button);
        scan_qr_btn.setOnClickListener((View view) -> readQR());
        back_btn.setOnClickListener((View view) -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        //lista

        Log.println(Log.INFO, "", getApplicationInfo().dataDir);

        list_view.setLayoutManager(new LinearLayoutManager(this));


        loadOrders(list_view);
        //Dodanie poziomej linii oddzielającej zlecenia
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