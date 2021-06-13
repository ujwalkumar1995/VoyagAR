package com.example.voyagar.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import com.example.voyagar.helper.Constants;
import com.example.voyagar.R;
import com.example.voyagar.helper.NearbyPlace;


import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PlacesUtilities {

    public static String getPlacesUrlString(String userLocation, String typesString) {
        String lat = userLocation.split(",")[0];
        String lng = userLocation.split(",")[1];
        typesString = typesString.replaceAll(",","%2C");
        return Constants.BASE_URL +
                Constants.LONGITUDE +
                lng +
                Constants.LATITUDE +
                lat +
                Constants.PLACE_TYPES +
                typesString +
                Constants.LIMIT +
                Constants.PLACES_API_KEY;
    }

    public static String getTypesString(Context context,
                                        SharedPreferences sharedPreferences) {
        StringBuilder builder = new StringBuilder();

        boolean cafesAreActivated = sharedPreferences.getBoolean(
                context.getString(R.string.preferences_type_cafe), true);
        if (cafesAreActivated) {
            builder.append(Constants.TYPE_CAFE);
        }
        boolean restaurantsAreActivated = sharedPreferences.getBoolean(
                context.getString(R.string.preferences_type_restaurant), true);
        if (restaurantsAreActivated) {
            builder.append(Constants.TYPE_RESTAURANT);
        }
        boolean barsAreActivated = sharedPreferences.getBoolean(
                context.getString(R.string.preferences_type_bar), false);
        if (barsAreActivated) {
            builder.append(Constants.TYPE_BAR);
        }
        boolean parksAreActivated = sharedPreferences.getBoolean(
                context.getString(R.string.preferences_type_park), false);
        if (parksAreActivated) {
            builder.append(Constants.TYPE_PARK);
        }
        boolean fuelAreActivated = sharedPreferences.getBoolean(
                context.getString(R.string.preferences_type_fuel), false);
        if (fuelAreActivated) {
            builder.append(Constants.TYPE_Fuel);
        }
        boolean galleriesAreActivated = sharedPreferences.getBoolean(
                context.getString(R.string.preferences_type_gallery), false);
        if (galleriesAreActivated) {
            builder.append(Constants.TYPE_GALLERY);
        }
        boolean moviesAreActivated = sharedPreferences.getBoolean(
                context.getString(R.string.preferences_type_movie), false);
        if (moviesAreActivated) {
            builder.append(Constants.TYPE_MOVIE);
        }
        boolean lodgingsAreActivated = sharedPreferences.getBoolean(
                context.getString(R.string.preferences_type_lodging), false);
        if (lodgingsAreActivated) {
            builder.append(Constants.TYPE_LODGING);
        }
        boolean groceriesAreActivated = sharedPreferences.getBoolean(
                context.getString(R.string.preferences_type_grocery), false);
        if (groceriesAreActivated) {
            builder.append(Constants.TYPE_GROCERY);
        }
        boolean atmsAreActivated = sharedPreferences.getBoolean(
                context.getString(R.string.preferences_type_atm), false);
        if (atmsAreActivated) {
            builder.append(Constants.TYPE_ATM);
        }
        boolean carsAreActivated = sharedPreferences.getBoolean(
                context.getString(R.string.preferences_type_car), false);
        if (carsAreActivated) {
            builder.append(Constants.TYPE_CAR_RENTAL);
        }
        boolean transitIsActivated = sharedPreferences.getBoolean(
                context.getString(R.string.preferences_type_transit), false);
        if (transitIsActivated) {
            builder.append(Constants.TYPE_TRANSIT);
        }
        return builder.toString();
    }


    public static ArrayList<NearbyPlace> processPlacesJson(Context context,
                                                           Location currentLocation,
                                                           JSONObject response,
                                                           String currentTypesString) {

        ArrayList<NearbyPlace> nearbyPlaces = new ArrayList<>();

        try {
            JSONArray results = response.getJSONArray("features");
            for (int i = 0; i < results.length(); i++) {
                JSONObject place = results.getJSONObject(i);
                JSONObject properties = place.getJSONObject("properties");
                String dis = properties.getString("dist");
                JSONObject geometry = place.getJSONObject("geometry");
                JSONArray location = geometry.getJSONArray("coordinates");
                String lat = location.get(1).toString();
                String lng = location.get(0).toString();
                String name = properties.getString("name");
                if(name==null || name.isEmpty() || name.equals(""))
                    continue;
                String place_id = properties.getString("xid");
                String kinds = properties.getString("kinds");
                String type = getPlaceType(kinds, currentTypesString);
                name = formatName(name);
                int icon_id = getIconId(type);
                float[] bearingAndDistance = getBearingAndDistance(currentLocation, lat, lng, place_id);
                float bearingToPlace = bearingAndDistance[0];
                String distanceToPlace = formatDistance(context, bearingAndDistance[1]);
                nearbyPlaces.add(new NearbyPlace(name, place_id, icon_id, bearingToPlace, distanceToPlace));
            }
        } catch (Exception e) {

        }
        return nearbyPlaces;
    }

    private static float[] getBearingAndDistance(Location currentLocation,
                                                 String lat, String lng, String place_id) {
        Location placeLocation = new Location(place_id);
        placeLocation.setLatitude(Float.valueOf(lat));
        placeLocation.setLongitude(Float.valueOf(lng));

        float bearingToPlace = currentLocation.bearingTo(placeLocation);
        if (bearingToPlace < 0) {
            bearingToPlace += 360;
        }

        float distanceToPlace = currentLocation.distanceTo(placeLocation);

        return new float[]{bearingToPlace, distanceToPlace};
    }

    private static String getPlaceType(String kinds, String currentTypesString) {
        String typeString = "";
        String types[] = kinds.split(",");
        for (int i = 0; i < types.length; i++) {
            try {
                String type = types[i];
                if (currentTypesString.contains(type)) {
                    typeString = type;
                }
            } catch (Exception e) {

            }
        }
        return typeString;
    }

    public static int getIconId(String type) {
        int iconId;
        switch (type) {
            case Constants.RESTAURANT:
                iconId = Constants.RESTAURANT_ID;
                break;
            case Constants.CAFE:
                iconId = Constants.CAFE_ID;
                break;
            case Constants.BAR:
                iconId = Constants.BAR_ID;
                break;
            case Constants.PUB:
                iconId = Constants.BAR_ID;
                break;
            case Constants.PARK:
                iconId = Constants.PARK_ID;
                break;
            case Constants.FUEL:
                iconId = Constants.FUEL_ID;
                break;
            case Constants.GALLERY:
                iconId = Constants.GALLERY_ID;
                break;
            case Constants.MOVIE:
                iconId = Constants.MOVIE_ID;
                break;
            case Constants.HOTELS:
                iconId = Constants.LODGING_ID;
                break;
            case Constants.MOTELS:
                iconId = Constants.LODGING_ID;
                break;
            case Constants.GUEST_HOUSES:
                iconId = Constants.LODGING_ID;
                break;
            case Constants.RESORTS:
                iconId = Constants.LODGING_ID;
                break;
            case Constants.CONVENIENCE:
                iconId = Constants.GROCERY_ID;
                break;
            case Constants.SUPERMARKET:
                iconId = Constants.GROCERY_ID;
                break;
            case Constants.MARKET:
                iconId = Constants.GROCERY_ID;
                break;
            case Constants.ATM:
                iconId = Constants.ATM_ID;
                break;
            case Constants.BANK:
                iconId = Constants.ATM_ID;
                break;
            case Constants.CAR_RENTAL:
                iconId = Constants.CAR_RENTAL_ID;
                break;
            case Constants.TRANSIT:
                iconId = Constants.TRANSIT_ID;
                break;
            default:
                iconId = Constants.DEFAULT_ID;
                break;
        }
        return iconId;
    }

    private static String formatName(String name) {
        if (name.length() > 16) {
            name = name.substring(0, 15);
            if (name.contains(" ")) {
                if (name.endsWith(" ")) {
                    name = name.substring(0, 14);
                } else {
                    String[] nameArray = name.split(" ");
                    for (int i = 0; i < nameArray.length - 1; i++) {
                        if (i == 0) {
                            name = nameArray[i];
                        } else {
                            name = name + " " + nameArray[i];
                        }
                    }
                }
            }
        }
        return name;
    }

    private static String formatDistance(Context context, float distanceToPlace) {
        String formattedDistance = "";
        if (distanceToPlace > 1000) {
            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            distanceToPlace = distanceToPlace / 1000;
            formattedDistance = decimalFormat.format(distanceToPlace) + context.getString(R.string.kilometers);
        } else {
            formattedDistance = String.valueOf((int) distanceToPlace) + context.getString(R.string.meters);
        }
        return formattedDistance;
    }
}