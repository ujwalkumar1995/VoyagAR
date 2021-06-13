package com.example.voyagar.utilities;

import android.content.Context;

import com.example.voyagar.R;
import com.example.voyagar.helper.Constants;
import com.example.voyagar.helper.PlaceDetails;

import org.json.JSONArray;
import org.json.JSONObject;


public class PlaceDetailsUtilities {

    public static String getDetailsUrlString(String place_id) {
        return Constants.DETAILS_BASE_URL +
                place_id +
                Constants.PLACES_DETAILS_API_KEY;
    }

    public static PlaceDetails processDetailsJson(JSONObject response) {
        PlaceDetails placeDetails = new PlaceDetails();
        try {
            String name = response.getString("name");
            placeDetails.setName(name);
            String place_id = response.getString("xid");
            placeDetails.setPlace_id(place_id);
            String rating = response.getString("rate");
            placeDetails.setRating(rating);
            JSONObject address = response.getJSONObject("address");
            String city = address.getString("city");
            String state = address.getString("state");
            String suburb = address.getString("suburb");
            String postcode = address.getString("postcode");
            String formatted_address = getFormattedAddress(name,suburb,city,state,postcode);
            placeDetails.setFormatted_address(formatted_address);
            String kinds = response.getString("kinds");
            String formattedKinds = getFormattedKinds(kinds);
            placeDetails.setKinds(formattedKinds);

        } catch (Exception e) {

        }
        return placeDetails;
    }

    private static String getFormattedKinds(String kinds) {
        StringBuilder kindsBuilder = new StringBuilder();
        kinds = kinds.replaceAll("_"," ");
        String kindsArray[] = kinds.split(",");
        for(String kind:kindsArray){
            String capitalizeKind = kind.substring(0, 1).toUpperCase() + kind.substring(1);
            kindsBuilder.append(capitalizeKind);
            kindsBuilder.append(", ");
        }
        kindsBuilder.deleteCharAt(kindsBuilder.length()-1);
        kindsBuilder.deleteCharAt(kindsBuilder.length()-1);
        return kindsBuilder.toString();
    }

    private static String getFormattedAddress(String name, String suburb, String city, String state, String postcode) {
        String formattedAddress = name + ", " + suburb + ", " + city + ", " + state + " - " + postcode;
        return formattedAddress;
    }

}