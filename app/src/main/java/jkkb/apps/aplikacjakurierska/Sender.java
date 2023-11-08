package jkkb.apps.aplikacjakurierska;


import android.telephony.PhoneNumberUtils;

import androidx.annotation.NonNull;

import java.util.Locale;
//klasa nie może z niczego dziedziczyć bo Firebase nie obsłuży jej mapowania, w innym przypadku powinna dziedziczyć z ogólnej klasy abstrakcyjnej/ interfejsu Customer

public class Sender {
    private String name;
    private String surname;
    private String phone_nr;
    private Address address;

    public Sender(String name, String surname,String phone_nr,Address address){
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phone_nr = phone_nr;
    }

    public Sender(){
        this.name="";
        this.surname="";
        this.address = new Address("","","");
        this.phone_nr = "";
    }

    @NonNull
    @Override
    public String toString() {
        return name+" "+surname+" "+address.getStreet()+" "+address.getCity()+" "+address.getPostal_code();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhoneNr() {
        return phone_nr;
    }

    public void setPhoneNr(String phone_nr) {
        this.phone_nr = phone_nr;
    }

}
