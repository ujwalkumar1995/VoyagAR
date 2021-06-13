package com.example.voyagar.helper;

public class PlaceDetails {
    private String name = "";
    private String place_id = "";
    private int icon_id = 0;
    private String rating = "0";
    private String city = "";
    private String state = "";
    private String suburb = "";
    private String postcode = "";
    private String formatted_address = "";
    private String formatted_phone_number = "";
    private String kinds = "";

    public PlaceDetails() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setIcon_id(int icon_id) {
        this.icon_id = icon_id;
    }

    public int getIcon_id() {
        return icon_id;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRating() {
        return rating;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }
    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }

    public void setState(String state) { this.state = state; }

    public String getSuburb() { return suburb; }

    public void setSuburb(String suburb) { this.suburb = suburb; }

    public String getPostcode() { return postcode; }

    public void setPostcode(String postcode) { this.postcode = postcode; }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_phone_number(String formatted_phone_number) {
        this.formatted_phone_number = formatted_phone_number;
    }

    public String getFormatted_phone_number() {
        return formatted_phone_number;
    }

    public String getKinds() { return kinds; }

    public void setKinds(String kinds) { this.kinds = kinds; }

}