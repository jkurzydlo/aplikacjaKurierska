package jkkb.apps.aplikacjakurierska;

public class Address {
    public Address(String city, String street, String postal_code) {
        this.city = city;
        this.street = street;
        this.postal_code = postal_code;
    }
    public Address(){
        street="";
        postal_code="";
        city="";
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    private String street;
    private String postal_code;
    private String city;


}
