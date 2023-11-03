package jkkb.apps.aplikacjakurierska;


//PropertyName
public enum OrdersState {
    //Wartość domyślna
     NONE,
    //Przesyłka przygotowana - zamówienie dodane do bazy danych
    PREPARED_TO_SEND,
     //Presyłka odebrana przez kuriera, w drodze
    TRANSPORTED, //Można potwierdzić odbiór przesyłki
    READY_TO_RECEIVE,

    REALISED //Zlecenie realizowane, można usunąć z bazy danych
}