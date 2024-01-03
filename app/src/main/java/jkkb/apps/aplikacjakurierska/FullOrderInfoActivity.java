package jkkb.apps.aplikacjakurierska;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

//Klasa odpowiedzialna za wyświetlanie okna z pełnymi informacjami o przesyłce
public class FullOrderInfoActivity extends Activity {
    private TabLayout layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //test
        ArrayList<Order> orders;

        orders = new ArrayList<>();
        orders.add(new Order(
                new Sender("Adam","Nowak","123456789", new Address("Warszawa","Polna","33-190")),
                new Receiver("Anna","Kowalska","987654321",new Address("Krakow","Dluga","22-222"))
        ));
        orders.add(new Order(
                new Sender("Anna","Malinowska","123456789", new Address("Olsztyn","Ogrodowa","11-111")),
                new Receiver("Robert","Kowalski","987654321",new Address("Krakow","Dluga","22-222"))
        ));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_order_info_view);
        //Pobranie rozmiaru ekranu
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setLayout((int)(dm.widthPixels*0.8),(int)(dm.heightPixels*0.8));
    }
}
