package jkkb.apps.aplikacjakurierska;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;

import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.WriterException;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jkkb.apps.aplikacjakurierska.PDF.PDFBitmap;
import jkkb.apps.aplikacjakurierska.PDF.PDFBuilder;
import jkkb.apps.aplikacjakurierska.PDF.PDFText;
import jkkb.apps.aplikacjakurierska.QR.QRGenerator;

public class SenderActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Order order = new Order(new Sender(),new Receiver());
    private Button qr_generate_btn;
    private ImageButton back_btn;
    private Button next_btn;
    private TextView header_text;
    private EditText name_box,surname_box,city_box,street_box,postal_box,phone_box;
    private boolean qr_generated = false;
    private ImageView qr_code;
    private QRGenerator generator = new QRGenerator();
    private Bitmap qr_bitmap = Bitmap.createBitmap(1,1, Bitmap.Config.ALPHA_8);
    private CheckBox send_receipt_checkbox;
    private String attachment_path;

    // final int permission_status=0;


    private void generatePDF(){
        String qr_filename = "Order "+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));

        Paint title = new Paint();
        title.setColor(ContextCompat.getColor(this, R.color.black));
        title.setTextAlign(Paint.Align.CENTER);
        title.setTextSize(25);
        title.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));


        Paint rest = new Paint();
        rest.setColor(ContextCompat.getColor(this, R.color.black));
        title.setTextSize(15);


                attachment_path = PDFBuilder.
                create().
                addDimensions(800, 1200).
                addImage(new PDFBitmap(qr_bitmap,rest,new Point(200,200  ))).
                addText(new PDFText("Potwierdzenie odbioru przesyłki",new Paint(R.color.black), new Point(300,50))).
                addText(new PDFText("Nadawca: "+order.getSender(),rest,new Point(25,100))).
                addText(new PDFText("Odbiorca:"+order.getSender(),rest,new Point(25 ,125))).
                addText(new PDFText("Identyfikator przesyłki: "+order.getId(),rest,new Point(25,150))).
                addText(new PDFText("Data nadania: "+qr_filename.substring(6), rest,new Point(25,175))).
                addText(new PDFText("Wygenerowano z użyciem Aplikacji Kurierskiej",rest,new Point(25,650))).
                build().
                generate(this,qr_filename);

                Log.println(Log.INFO,"","PLIK: "+attachment_path);
    }
    private void fillOrderWithSenderData(Sender sender){
        sender.setName(name_box.getText().toString());
        sender.setSurname(surname_box.getText().toString());
        sender.getAddress().setCity(city_box.getText().toString());
        sender.getAddress().setStreet(street_box.getText().toString());
        sender.getAddress().setPostal_code(postal_box.getText().toString());
        sender.setPhoneNr(phone_box.getText().toString());
        order.setSender(sender);
    }

    private void fillOrderWithReceiverData(Receiver receiver){
        receiver.setName(name_box.getText().toString());
        receiver.setSurname(surname_box.getText().toString());
        receiver.getAddress().setCity(city_box.getText().toString());
        receiver.getAddress().setStreet(street_box.getText().toString());
        receiver.getAddress().setPostal_code(postal_box.getText().toString());
        receiver.setPhoneNr(phone_box.getText().toString());
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
            if(send_receipt_checkbox != null)send_receipt_checkbox.setActivated(false);

            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        });

        qr_code.setOnClickListener((View view)->{
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse(
                    "mailto:"));


            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Potwierdzenie nadania przesyłki");
            emailIntent.putExtra(Intent.EXTRA_STREAM, URI.create(attachment_path));
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Potwierdzenie w załączniku");
            emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(emailIntent, "Wybierz klienta e-mail"));

        });

        next_btn.setOnClickListener((View view)-> {

            send_receipt_checkbox = findViewById(R.id.sendReceiptcheckBox);
            header_text = findViewById(R.id.sender_data);
            next_btn = findViewById(R.id.sender_next_button);
            name_box=findViewById(R.id.editTextName);
            surname_box=findViewById(R.id.editTextSurname);
            city_box=findViewById(R.id.editTextCity);
            street_box=findViewById(R.id.editTextStreet);
            postal_box=findViewById(R.id.editTextTextPostalAddress);
            phone_box = findViewById(R.id.editTextPhone);

            //Jeśli użytkownik zostawił puste pola pojawi się komunikat
            if(!name_box.getText().toString().isEmpty() && !surname_box.getText().toString().isEmpty() &&
                    !city_box.getText().toString().isEmpty() && !street_box.getText().toString().isEmpty()
                    && !postal_box.getText().toString().isEmpty() && !phone_box.getText().toString().isEmpty()){

                if(next_btn.getText().toString().equals("dalej")){

                    fillOrderWithSenderData(order.getSender());
                    header_text.setText(getString(R.string.buyer_data_label));
                    next_btn.setText(R.string.generate_qr_label);
                    name_box.setText("");
                    surname_box.setText("");
                    city_box.setText("");
                    postal_box.setText("");
                    street_box.setText("");
                    phone_box.setText("");
                    send_receipt_checkbox.setVisibility(View.VISIBLE);


                }
                //Jeżeli tekst na przycisku = "Generuj kod qr", tzn. nadawca podał swoje dane, przejdź do formularza odbiorcy
                else if(next_btn.getText().equals(getString(R.string.generate_qr_label))){
                    //Czy kod QR nie jest pusty
                    boolean valid_qr = true;

                    //Generuj kod QR
                    try {
                        qr_bitmap = generator.generate(order.getId(),qr_code);
                    } catch (WriterException e) {
                        valid_qr = false;
                    }


                    fillOrderWithReceiverData(order.getReceiver());

                    //Dodanie zlecenia do bazy danych
                    order.setState(OrdersState.PREPARED_TO_SEND);
                    if(!qr_generated)db.collection("orders").document(order.getId()).set(order);
                    qr_generated = true;

                    name_box.clearFocus();
                    surname_box.clearFocus();
                    city_box.clearFocus();
                    postal_box.clearFocus();
                    street_box.clearFocus();
                    phone_box.clearFocus();

                    //Jeśli wygenerowano poprawnie kod qr i zaznaczono checkbox - stwórz dokument PDF
                    if(valid_qr && send_receipt_checkbox.isChecked()){
                        generatePDF();
                    }


                }


            }else{
                Toast.makeText(this,"Uzupełnij dane",Toast.LENGTH_SHORT).show();
                return;
            }



        });

    }


}