package jkkb.apps.aplikacjakurierska;

import java.io.Serializable;
import java.util.UUID;

//TODO: Dodać datę wysłania,odbioru
//Dodać stan przesyłki

public class Order{
    private Sender sender;
    private Receiver receiver;
    private String id = UUID.randomUUID().toString();

    OrdersState state = OrdersState.NONE;

    public Order(Sender sender, Receiver receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public Order(){}

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }
    public String getId(){return id;}
    public OrdersState getState() {return state;}
}
