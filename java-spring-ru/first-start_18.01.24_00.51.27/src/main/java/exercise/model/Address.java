package exercise.model;


import exercise.annotation.Inspect;

public class Address {
    private final String city;
    private final String postalCode;

    public Address(String city, String postalCode) {
        this.city = city;
        this.postalCode = postalCode;
    }

    @Inspect
    public String getCity(){
       return city;
    }
    @Inspect
    public String getPostalCode(){
        return postalCode;
    }
}
