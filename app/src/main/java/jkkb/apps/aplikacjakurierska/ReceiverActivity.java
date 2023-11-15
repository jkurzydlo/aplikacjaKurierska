package jkkb.apps.aplikacjakurierska;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class ReceiverActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final PhoneNumberGrabber grabber = new PhoneNumberGrabber();
    private RecyclerView list_view;
    private ImageButton refresh_btn;
    private TextView empty_label;
    private ArrayList<Order> orders = new ArrayList<>();

    private void loadOrders(RecyclerView list_view){
        //Wyświetl tylko te zlecenia przypisane do numeru telefonu na używanym urządzeniu
        db.collection("orders").whereEqualTo("receiver.phoneNr",PhoneNumberGrabber.getPhoneNr()).get().addOnCompleteListener(task -> {
            if(task.isSuccessful())
                for(QueryDocumentSnapshot doc : task.getResult()){
                    Log.println(Log.INFO,"","klik");
                    //Dodaje zlecenia do listy
                    if(orders.size() < task.getResult().size())orders.add(doc.toObject(Order.class));
                    list_view.setAdapter(new OrderListAdapter(getApplicationContext(),orders));
                }
        });
        if(!orders.isEmpty()) empty_label.setVisibility(View.INVISIBLE);
        else empty_label.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);

        grabber.requestPhoneNumber(this,launcher);
        empty_label = findViewById(R.id.empty_list_label);
        refresh_btn = findViewById(R.id.receiver_button_referesh_list);
        list_view = findViewById(R.id.receiver_order_list);
        list_view.setLayoutManager(new LinearLayoutManager(this));

        //Dodanie poziomej linii oddzielającej zlecenia
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(list_view.getContext(), DividerItemDecoration.VERTICAL);


        //Zaktualizuj listę po naciśnięciu przycisku
        refresh_btn.setOnClickListener(view -> {
            orders.clear();
            loadOrders(list_view);
        });
    }

    //zmienna określa co wykonać w aktywności określonej w klasie PhoneNumberGrabber
    ActivityResultLauncher launcher = registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(), result -> {
        try {
            //pobiera number wybrany przez użytkownika
            PhoneNumberGrabber.setPhoneNr(Identity.getSignInClient(this).getPhoneNumberFromIntent(result.getData()).substring(3));
            Log.println(Log.INFO,"",PhoneNumberGrabber.getPhoneNr());
            loadOrders(list_view);
        } catch (ApiException e) {
            e.printStackTrace();
            Toast.makeText(this, "Błąd: Nie udało się pobrać numeru telefonu ", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    });
}