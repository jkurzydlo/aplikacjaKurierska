package jkkb.apps.aplikacjakurierska;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

//klasa potrzebna do obsługi własnych typów w RecyclerView (na liście)
public class OrderListAdapter extends RecyclerView.Adapter<OrderViewHolder> {

    public OrderListAdapter(Context context, List<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

     Context context;
     List<Order> orders;

    //dodaje pola z widoku order_view do listy (RecyclerView)
    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(LayoutInflater.from(context).inflate(R.layout.order_view,parent,false));
    }

    //ustawia pola w widoku na wybrane dane z listy zamówień (orders)
    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.sender_info.setText("Od: "+orders.get(position).getSender().toString());
        holder.receiver_info.setText("Do: "+orders.get(position).getReceiver().toString());
        holder.itemView.setOnClickListener(view -> {
            //Intent intent = new Intent(context, FullOrderInfoActivity.class);

            //TODO: Przy bazie danych zmienić na getID()
            //intent.putExtra("order_id",position);
            //context.startActivity(intent);

        });

        //Dodaje ikony poszczególnych stanów zamówienia
        HashMap<OrdersState,Integer> state_icons = new HashMap<>();
        state_icons.put(OrdersState.PREPARED_TO_SEND,R.drawable.sender_waiting);
        state_icons.put(OrdersState.TRANSPORTED,R.drawable.delivery_truck);
        state_icons.put(OrdersState.READY_TO_RECEIVE,R.drawable.delivery_received);
        state_icons.put(OrdersState.NONE,R.drawable.order_unknown);
        state_icons.put(OrdersState.REALISED,R.drawable.delivery_completed);

        //Ustawia ikonę stanu
        holder.image_view.setImageBitmap(BitmapFactory.decodeResource
                (context.getResources(),state_icons.get(orders.get(position).getState())));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}
