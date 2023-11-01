package jkkb.apps.aplikacjakurierska;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;

public class SenderActivity extends AppCompatActivity {

    private Order order = new Order(new Sender(),new Receiver());
    private Button qr_generate_btn;
    private ImageButton back_btn;
    private Button next_btn;
    private TextView header_text;
    private EditText name_box,surname_box,city_box,street_box,postal_box,phone_box; //Dodać pole z telefonem/mailem
    private boolean qr_generated = false;
    private ImageView qr_code;
    private QRGenerator generator = new QRGenerator();
    private Bitmap qr_bitmap = Bitmap.createBitmap(1,1, Bitmap.Config.ALPHA_8);

    // final int permission_status=0;


    private void fillOrderWithSenderData(Sender sender){
        sender.setName(name_box.getText().toString());
        sender.setSurname(surname_box.getText().toString());
        sender.getAddress().setCity(city_box.getText().toString());
        sender.getAddress().setStreet(street_box.getText().toString());
        sender.getAddress().setPostal_code(postal_box.getText().toString());
        order.setSender(sender);
    }

    private void fillOrderWithReceiverData(Receiver receiver){
        receiver.setName(name_box.getText().toString());
        receiver.setSurname(surname_box.getText().toString());
        receiver.getAddress().setCity(city_box.getText().toString());
        receiver.getAddress().setStreet(street_box.getText().toString());
        receiver.getAddress().setPostal_code(postal_box.getText().toString());
        order.setReceiver(receiver);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender);

        next_btn = findViewById(R.id.sender_next_button);
        qr_code = findViewById(R.id.generated_qr);
        back_btn = findViewById(R.id.sender_back_button);

        //Przycisk wróć
        back_btn.setOnClickListener((View view) ->{
            Log.println(Log.INFO,"",order.getSender().getName());
            qr_generated = false;
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        });

        next_btn.setOnClickListener((View view)-> {

            header_text = findViewById(R.id.sender_data);
            next_btn = findViewById(R.id.sender_next_button);
            name_box=findViewById(R.id.editTextName);
            surname_box=findViewById(R.id.editTextSurname);
            city_box=findViewById(R.id.editTextCity);
            street_box=findViewById(R.id.editTextStreet);
            postal_box=findViewById(R.id.editTextTextPostalAddress);

            //Jeśli użytkownik zostawił puste pola pojawi się komunikat
            if(!name_box.getText().toString().isEmpty() && !surname_box.getText().toString().isEmpty() &&
                    !city_box.getText().toString().isEmpty() && !street_box.getText().toString().isEmpty()
                    && !postal_box.getText().toString().isEmpty()){

                if(next_btn.getText().toString().equals("dalej")){

                    fillOrderWithSenderData(order.getSender());

                    header_text.setText(getString(R.string.buyer_data_label));
                    next_btn.setText(R.string.generate_qr_label);
                    name_box.setText("");
                    surname_box.setText("");
                    city_box.setText("");
                    postal_box.setText("");
                    street_box.setText("");

                }
                //Jeżeli tekst na przycisku = "Generuj kod qr", tzn. nadawca podał swoje dane, przejdź do formularza odbiorcy
                else if(next_btn.getText().equals(getString(R.string.generate_qr_label))){
                    //Generuj kod QR
                    try {
                        qr_bitmap = generator.generate(order.getId(),qr_code);
                    } catch (WriterException e) {}

                    qr_generated = true;
                    fillOrderWithReceiverData(order.getReceiver());
                    name_box.clearFocus();
                    surname_box.clearFocus();
                    city_box.clearFocus();
                    postal_box.clearFocus();
                    street_box.clearFocus();

                }


            }else{
                Toast.makeText(this,"Uzupełnij dane",Toast.LENGTH_SHORT).show();
                return;
            }



        });

    }


}