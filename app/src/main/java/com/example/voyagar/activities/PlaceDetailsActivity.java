package com.example.voyagar.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.voyagar.R;
import com.example.voyagar.helper.Constants;
import com.example.voyagar.helper.PlaceDetails;
import com.example.voyagar.utilities.PlaceDetailsUtilities;
import com.example.voyagar.utilities.VolleySingleton;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.json.JSONObject;



public class PlaceDetailsActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private TextView mErrorTextView;
    private LinearLayout mDetailsContainer;
    private AppBarLayout mAppBarLayout;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ImageView mPhotoImageView;
    private TextView mNameTextView;
    private TextView mAddressTextView;
    private TextView mContactTextView;
    private ImageView mStar1ImageView;
    private ImageView mStar2ImageView;
    private ImageView mStar3ImageView;
    private TextView mListedUnder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);

        mProgressBar = (ProgressBar) findViewById(R.id.details_loading_indicator);
        mErrorTextView = (TextView) findViewById(R.id.details_error_text_view);
        mDetailsContainer = (LinearLayout) findViewById(R.id.details_container);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
            upArrow.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }
        mPhotoImageView = (ImageView) findViewById(R.id.details_photo_image_view);
        mNameTextView = (TextView) findViewById(R.id.details_name_text_view);
        mAddressTextView = (TextView) findViewById(R.id.details_address_text_view);
        mContactTextView = (TextView) findViewById(R.id.details_contact_text_view);
        mStar1ImageView = (ImageView) findViewById(R.id.details_star_1);
        mStar2ImageView = (ImageView) findViewById(R.id.details_star_2);
        mStar3ImageView = (ImageView) findViewById(R.id.details_star_3);
        mListedUnder =  (TextView) findViewById(R.id.details_listed_under);


        Intent detailsIntent = getIntent();
        if (detailsIntent != null) {
            String place_id = detailsIntent.getStringExtra(Constants.DETAILS_INTENT_PLACE_ID);
            int icon_id = detailsIntent.getIntExtra(Constants.DETAILS_INTENT_ICON_ID, 0);

            getDetails(place_id, icon_id);
        }
    }

    private void getDetails(final String place_id, final int icon_id) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                PlaceDetailsUtilities.getDetailsUrlString(place_id),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        PlaceDetails placeDetails = PlaceDetailsUtilities.processDetailsJson(response);
                        placeDetails.setIcon_id(icon_id);

                        // Set header
                        String restaurantName = placeDetails.getName();
                        mNameTextView.setText(restaurantName);
                        setHeaderColors(getApplicationContext(), icon_id);
                        String kinds = placeDetails.getKinds();
                        mListedUnder.setText(kinds);

                        // Set address
                        String formatted_address = placeDetails.getFormatted_address();
                        if (formatted_address.equals("")) {
                            formatted_address = getString(R.string.address_unavailable);
                        }
                        mAddressTextView.setText(formatted_address);

                        // Set contact information
                        String formatted_phone_number = placeDetails.getFormatted_phone_number();
                        if (formatted_phone_number.equals("")) {
                            formatted_phone_number = getString(R.string.contact_unavailable);
                        }
                        mContactTextView.setText(formatted_phone_number);

                        // Set rating
                        String rating = placeDetails.getRating();
                        if (rating.equals("") &&
                                formatted_address.equals(getString(R.string.address_unavailable))) {
                            LinearLayout ratingContainer = (LinearLayout) findViewById(R.id.details_rating_container);
                            ratingContainer.setVisibility(View.GONE);
                        } else {
                            setRatingStars(rating);
                        }
                        showDetailsContainer();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof NoConnectionError ||
                                error instanceof NetworkError ||
                                error instanceof TimeoutError) {
                            mErrorTextView.setText(getString(R.string.connection_unavailable));
                        } else {
                            mErrorTextView.setText(getString(R.string.service_unavailable));
                        }
                        showErrorTextView();
                    }
                }
        );

        // Get details for this place_id from Places API
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void showDetailsContainer() {
        mProgressBar.setVisibility(View.GONE);
        mAppBarLayout.setVisibility(View.VISIBLE);
        mDetailsContainer.setVisibility(View.VISIBLE);
    }

    private void showErrorTextView() {
        mProgressBar.setVisibility(View.GONE);
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void setHeaderColors(Context context, int icon_id) {
        int backgroundColor;
        int statusBarColor;
        switch (icon_id) {
            case Constants.CAFE_ID:
                backgroundColor = ContextCompat.getColor(context, R.color.cafeBrown);
                statusBarColor = ContextCompat.getColor(context, R.color.cafeStatusBar);
                break;
            case Constants.RESTAURANT_ID:
                backgroundColor = ContextCompat.getColor(context, R.color.restaurantRed);
                statusBarColor = ContextCompat.getColor(context, R.color.restaurantStatusBar);
                break;
            case Constants.BAR_ID:
                backgroundColor = ContextCompat.getColor(context, R.color.barGreen);
                statusBarColor = ContextCompat.getColor(context, R.color.barStatusBar);
                break;
            case Constants.PARK_ID:
                backgroundColor = ContextCompat.getColor(context, R.color.parkGreen);
                statusBarColor = ContextCompat.getColor(context, R.color.parkStatusBar);
                break;
            case Constants.FUEL_ID:
                backgroundColor = ContextCompat.getColor(context, R.color.fuelBlue);
                statusBarColor = ContextCompat.getColor(context, R.color.fuelStatusBar);
                break;
            case Constants.GALLERY_ID:
                backgroundColor = ContextCompat.getColor(context, R.color.galleryPurple);
                statusBarColor = ContextCompat.getColor(context, R.color.galleryStatusBar);
                break;
            case Constants.MOVIE_ID:
                backgroundColor = ContextCompat.getColor(context, R.color.moviePurple);
                statusBarColor = ContextCompat.getColor(context, R.color.movieStatusBar);
                break;
            case Constants.LODGING_ID:
                backgroundColor = ContextCompat.getColor(context, R.color.lodgingOrange);
                statusBarColor = ContextCompat.getColor(context, R.color.lodgingStatusBar);
                break;
            case Constants.GROCERY_ID:
                backgroundColor = ContextCompat.getColor(context, R.color.groceryGreen);
                statusBarColor = ContextCompat.getColor(context, R.color.groceryStatusBar);
                break;
            case Constants.ATM_ID:
                backgroundColor = ContextCompat.getColor(context, R.color.atmGold);
                statusBarColor = ContextCompat.getColor(context, R.color.atmStatusBar);
                break;
            case Constants.CAR_RENTAL_ID:
                backgroundColor = ContextCompat.getColor(context, R.color.carRed);
                statusBarColor = ContextCompat.getColor(context, R.color.carStatusBar);
                break;
            case Constants.TRANSIT_ID:
                backgroundColor = ContextCompat.getColor(context, R.color.transitBlue);
                statusBarColor = ContextCompat.getColor(context, R.color.transitStatusBar);
                break;
            default:
                backgroundColor = ContextCompat.getColor(context, R.color.defaultBlack);
                statusBarColor = ContextCompat.getColor(context, R.color.defaultStatusBar);
                break;
        }
        mCollapsingToolbarLayout.setBackgroundColor(backgroundColor);
        mCollapsingToolbarLayout.setContentScrimColor(backgroundColor);
        mNameTextView.setBackgroundColor(backgroundColor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(statusBarColor);
        }
    }

    private void setPlaceholderImage(int icon_id) {
        switch (icon_id) {
            case Constants.CAFE_ID:
                mPhotoImageView.setImageResource(R.drawable.ic_local_cafe_black_48dp);
                break;
            case Constants.RESTAURANT_ID:
                mPhotoImageView.setImageResource(R.drawable.ic_restaurant_black_48dp);
                break;
            case Constants.BAR_ID:
                mPhotoImageView.setImageResource(R.drawable.ic_local_bar_black_48dp);
                break;
            case Constants.PARK_ID:
                mPhotoImageView.setImageResource(R.drawable.ic_nature_people_black_48dp);
                break;
            case Constants.FUEL_ID:
                mPhotoImageView.setImageResource(R.drawable.ic_local_fuel_black_48dp);
                break;
            case Constants.GALLERY_ID:
                mPhotoImageView.setImageResource(R.drawable.ic_palette_black_48dp);
                break;
            case Constants.MOVIE_ID:
                mPhotoImageView.setImageResource(R.drawable.ic_local_movies_black_48dp);
                break;
            case Constants.LODGING_ID:
                mPhotoImageView.setImageResource(R.drawable.ic_hotel_black_48dp);
                break;
            case Constants.GROCERY_ID:
                mPhotoImageView.setImageResource(R.drawable.ic_shopping_cart_black_48dp);
                break;
            case Constants.ATM_ID:
                mPhotoImageView.setImageResource(R.drawable.ic_local_atm_black_48dp);
                break;
            case Constants.CAR_RENTAL_ID:
                mPhotoImageView.setImageResource(R.drawable.ic_local_car_black_48dp);
                break;
            case Constants.TRANSIT_ID:
                mPhotoImageView.setImageResource(R.drawable.ic_directions_bus_black_48dp);
                break;
            default:
                mPhotoImageView.setImageResource(R.drawable.ic_location_on_black_48dp);
                break;
        }
    }


    private void setRatingStars(String rating) {
        if (rating.equals("0") || rating.equals("0h")) {
        } else if (rating.equals("1") || rating.equals("1h")) {
            mStar1ImageView.setImageResource(R.drawable.ic_star_black_24dp);
        } else if (rating.equals("2") || rating.equals("2h")) {
            mStar1ImageView.setImageResource(R.drawable.ic_star_black_24dp);
            mStar2ImageView.setImageResource(R.drawable.ic_star_black_24dp);
        } else if (rating.equals("3") || rating.equals("3h")) {
            mStar1ImageView.setImageResource(R.drawable.ic_star_black_24dp);
            mStar2ImageView.setImageResource(R.drawable.ic_star_black_24dp);
            mStar3ImageView.setImageResource(R.drawable.ic_star_black_24dp);
        }
        else {
            mStar1ImageView.setImageResource(R.drawable.ic_star_black_24dp);
            mStar2ImageView.setImageResource(R.drawable.ic_star_black_24dp);
            mStar3ImageView.setImageResource(R.drawable.ic_star_black_24dp);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (getIntent().getFlags() > 0) {
            Intent overlayIntent = new Intent(this, OverlayActivity.class);
            overlayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(overlayIntent);
        }
    }
}