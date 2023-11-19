package jkkb.apps.aplikacjakurierska;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Button buyer_btn, seller_btn, courier_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Reszta kodu pozostaje bez zmian, z wyjątkiem kodu autoryzacji biometrycznej

        buyer_btn = findViewById(R.id.receiver_button);
        seller_btn = findViewById(R.id.sender_button);
        courier_btn = findViewById(R.id.courier_button);

        // Autoryzacja biometryczna - wykomentuj lub usuń ten fragment kodu

        // Inicjalizacja mapy
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        courier_btn.setOnClickListener((View view) -> {
            Intent intent = new Intent(this, CourierActivity.class);
            startActivity(intent);
            finish();
        });
        seller_btn.setOnClickListener((View view) -> {
            Intent intent = new Intent(this, SenderActivity.class);
            startActivity(intent);
            finish();
        });
        buyer_btn.setOnClickListener((View view) -> {
            // Możesz umieścić tutaj kod związany z akcją kupującego, jeśli potrzebujesz
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Konfiguracja mapy, np. dodanie markera
        LatLng location = new LatLng(49.622354, 20.693365); // Poczta 3 NS
        googleMap.addMarker(new MarkerOptions().position(location).title("Marker in Nowy Sącz"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }
}
