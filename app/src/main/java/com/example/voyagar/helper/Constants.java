package com.example.voyagar.helper;

import android.location.Location;

import com.example.voyagar.BuildConfig;


public class Constants {

    private Constants() {

    }

    // Details Intents
    public static final String DETAILS_INTENT_PLACE_ID = "Details_Intent_Place_ID";
    public static final String DETAILS_INTENT_ICON_ID = "Details_Intent_Icon_ID";

    // Search Intents
    public static final String REFINE_SEARCH_INTENT = "Refine_Search";
    public static final int SEARCH_RESULT_CODE = 1000;
    public static final String SEARCH_RETURN_INTENT = "Search_Return";

    // Permissions
    public static final int BOTH_PERMISSIONS_REQUEST_CODE = 100;
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 200;
    public static final int FINE_LOCATION_PERMISSION_REQUEST_CODE = 300;

    // Sensors
    public static final float LOW_PASS_FILTER_CONSTANT = 0.25f;

    // Location
    public static final int LOCATION_UPDATE_INTERVAL = 1000; // seconds
    public static final int LOCATION_UPDATE_DISPLACEMENT = 10; // meters

    // Places

    public static final String PLACES_API_KEY = "&apikey=" + BuildConfig.API_KEY;
    public static final String BASE_URL = "https://api.opentripmap.com/0.1/en/places/radius?radius=2000";
    public static final String LATITUDE = "&lat=";
    public static final String LONGITUDE = "&lon=";
    public static final String LIMIT = "&limit=5";
    public static final String PLACE_TYPES = "&kinds=";
    public static final String TYPE_CAFE = "cafes,";
    public static final String TYPE_RESTAURANT = "restaurants,";
    public static final String TYPE_BAR = "bars,pubs,";
    public static final String TYPE_PARK = "gardens_and_parks,";
    public static final String TYPE_Fuel = "fuel,";
    public static final String TYPE_GALLERY = "art_galleries,";
    public static final String TYPE_MOVIE = "cinemas,";
    public static final String TYPE_LODGING = "other_hotels,guest_houses,resorts,motels,";
    public static final String TYPE_GROCERY = "supermarkets,marketplaces,conveniences,";
    public static final String TYPE_ATM = "atm,bank,";
    public static final String TYPE_CAR_RENTAL = "car_rental,";
    public static final String TYPE_TRANSIT = "railway_stations,";

    // Details
    public static final String DETAILS_BASE_URL = "https://api.opentripmap.com/0.1/en/places/xid/";
    public static final String PLACES_DETAILS_API_KEY = "?apikey=" + BuildConfig.API_KEY;

    // Icons and Colors
    public static final int DEFAULT_ID = 0;
    public static final int RESTAURANT_ID = 1;
    public static final int CAFE_ID = 2;
    public static final int BAR_ID = 3;
    public static final int PARK_ID = 4;
    public static final int FUEL_ID = 5;
    public static final int GALLERY_ID = 6;
    public static final int MOVIE_ID = 7;
    public static final int LODGING_ID = 8;
    public static final int GROCERY_ID = 9;
    public static final int ATM_ID = 10;
    public static final int CAR_RENTAL_ID = 11;
    public static final int TRANSIT_ID = 12;
    public static final String RESTAURANT = "restaurants";
    public static final String CAFE = "cafes";
    public static final String BAR = "bars";
    public static final String PUB = "pubs";
    public static final String PARK = "gardens_and_parks";
    public static final String FUEL = "fuel";
    public static final String GALLERY = "art_galleries";
    public static final String MOVIE = "cinemas";
    public static final String GUEST_HOUSES = "guest_houses";
    public static final String HOTELS = "other_hotels";
    public static final String RESORTS = "resorts";
    public static final String MOTELS = "motels";
    public static final String SUPERMARKET = "supermarkets";
    public static final String MARKET = "marketplaces";
    public static final String CONVENIENCE = "conveniences";
    public static final String ATM = "atm";
    public static final String BANK = "bank";
    public static final String CAR_RENTAL = "car_rental";
    public static final String TRANSIT = "railway_stations";

    public final static Location TEST_LOCATION = new Location("manual");
    static {
        TEST_LOCATION.setLatitude(45.4408f);
        TEST_LOCATION.setLongitude(12.3155f);
        TEST_LOCATION.setAltitude(0f);
    }

    // Widget
    public static final int WIDGET_LOADER_ID = 7000;
    public static final long ONE_DAY = 1000 * 60 * 60 * 24; // In milliseconds
}