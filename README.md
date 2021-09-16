# VoyagAR

* VoyagAR is an augemented reality navigation application that can be used to fetch points of interest based on the user location. 
* These points of interest can includes cafes, restaurants, petrol pumps etc. 
* It fetches the user location continuously and updates the nearby places of interest based on the user's selection. 
* The user can also know other useful information like actual address, distance, categories listed under of these particular places of interest. 
* The application uses the OpenTripMapAPI to fetch points of interest. You can use any other API as well to make this work.

## Project Structure Description

### Activites

#### OverlayActivity 

* This Activity is at the core where we are using the camera to display the points of interest using augmented reality. This activity refreshes in realtime with change in location and angle of the camera.

#### SearchActivity 

* This Activity allows the user to modify his selections and fetch for a different points of interest based on the location.

#### PlaceDetailsActivity 

* This Activity allows the user to view the additional information about the place like complete address, categories listed under etc.

#### PermissionsActivity

* Checks if user has providided the necessary permission for the application to function properly.

### helper

#### NearbyPlace

* Class that contains the basic information about the place of interest.

#### PlaceDetails

* Class that contains additional information about the place like address, id of the place on the API, rating etc.

#### Constants

* Class that is used to store certain constant values that can we used at other places.

### ui

#### CameraDisplayView

* This class helps us to open the back facing camera's preview.

#### OverlayDisaplyView

* This class draws the augmented reality overlay over the camera preview.

### utilities

#### PlacesUtilities

* Class that helps to parse the basic details of the place that is obtained from the places endpoint.

#### PlaceDetailsUtilities.java

*  Class that helps to parse the additional details of the place that is obtained from the places details endpoint.

#### VolleySingleton

* Class that uses a singleton pattern and helps to implement the volley library which helps us to fetch data from our endpoint.
