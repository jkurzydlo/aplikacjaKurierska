package jkkb.apps.aplikacjakurierska;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private Button buyer_btn,seller_btn,courier_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buyer_btn = findViewById(R.id.receiver_button);
        seller_btn = findViewById(R.id.sender_button);
        courier_btn = findViewById(R.id.courier_button);

        courier_btn.setOnClickListener((View view)->{
            Intent intent = new Intent(this,CourierActivity.class); //Przejście z MainActivity (this) (menu) do trybu kuriera
            startActivity(intent);
            finish();
        });
        seller_btn.setOnClickListener((View view)->{
            Intent intent = new Intent(this,SenderActivity.class);
            startActivity(intent);
            finish();
        });
        buyer_btn.setOnClickListener((View view)->{
            Intent intent = new Intent(this,ReceiverActivity.class);
            startActivity(intent);
            finish();
        });

    }
}