package jkkb.apps.aplikacjakurierska;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//klasa potrzebna do wyświetlania na RecyclerView (liście)
public class OrderViewHolder extends RecyclerView.ViewHolder {
    ImageView image_view;
    TextView receiver_info,sender_info;
    int order_nr;
    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);
        image_view = itemView.findViewById(R.id.list_image);
        receiver_info = itemView.findViewById(R.id.list_receiver_info);
        sender_info = itemView.findViewById(R.id.list_sender_info);

    }


}
