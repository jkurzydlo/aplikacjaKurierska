package jkkb.apps.aplikacjakurierska;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import jkkb.apps.aplikacjakurierska.QR.QRActivity;

public class CourierActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Button scan_qr_btn;
    private ImageButton back_btn,refresh_btn;
    private ArrayList<Order> orders = new ArrayList<>();
    private OrderListAdapter adapter;
    private EditText search_bar;

    private ActivityResultLauncher<IntentSenderRequest> someActivityResultLauncher;


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add("Potwierdź możliwość odbioru przesyłki");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Log.println(Log.INFO,"",orders.get(adapter.getOrderPosition()).getId());
        if(item.getItemId() == 0 && orders.get(adapter.getOrderPosition()).getState().equals(OrdersState.TRANSPORTED)) {
            db.document("orders/" + orders.get(adapter.getOrderPosition()).getId()).
                    update("state", "READY_TO_RECEIVE");
        }
        return super.onContextItemSelected(item);
    }

    boolean loaded;
    private void loadOrders(RecyclerView list_view){
        db.collection("orders").get().addOnCompleteListener(task -> {
            if(task.isSuccessful())
                for(QueryDocumentSnapshot doc : task.getResult()){
                    //Dodaje zlecenia do listy
                    if(orders.size() < task.getResult().size())orders.add(doc.toObject(Order.class));
                }
            adapter = new OrderListAdapter(this,getApplicationContext(),orders);
            list_view.setAdapter(adapter);
            loaded = true;

            for(Order order : orders){
                db.document("orders/" + order.getId()).update("mapX",2.11);
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

        //search_bar = findViewById(R.id.courierSearchBar);



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
            //Wybiera z bazy danych tylko przesyłkę z id takim jak na zeskanowanym kodzie
            db.collection("orders").document(res.getContents()).update("state","TRANSPORTED");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Wykryto przesyłkę, nr identyfikacyjny: ");
            builder.setMessage(res.getContents());
            builder.setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss()).show();
        }
    });
}