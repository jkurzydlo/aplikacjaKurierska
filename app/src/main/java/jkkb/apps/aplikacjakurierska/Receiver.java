package jkkb.apps.aplikacjakurierska;

import androidx.annotation.NonNull;

//klasa nie może z niczego dziedziczyć bo Firebase nie obsłuży jej mapowania, w innym przypadku powinna dziedziczyć z ogólnej klasy abstrakcyjnej/ interfejsu Customer

public class Receiver {
    private String name,surname;
    private Address address;

    public Receiver(String name, String surname,Address address){
        this.name = name;
        this.surname = surname;
        this.address = address;
    }

    public Receiver(){
        this.name="";
        this.surname="";
        this.address = new Address("","","");
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

}
