package com.example.pickupgamefinder.views;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

import com.example.pickupgamefinder.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class SearchGamesOnMapActivity extends FragmentActivity {

	private GoogleMap gamesMap;
	private static Location currentLocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_games_on_map);
		setUpMap();
	}

	/**
	 * Sets the map to the users current location.
	 */
	private void setUpMap() {
		if (gamesMap == null) {
			SupportMapFragment mapFrag = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
			gamesMap = mapFrag.getMap();

			if (gamesMap != null) {
				setTouchListenerOnMap();
				gamesMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
				gamesMap.setMyLocationEnabled(true);

				LatLng userLocation = getCurrentLocation();
				gamesMap.addMarker(new MarkerOptions()
				.position(userLocation)
				.title("Current Location")
				.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

				//Zoom to new location.
				gamesMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15.0f));
			}
		}
	}
	
	/**
	 * Creates a touch listener on the map, so that users can set the venue of their games.
	 */
	private void setTouchListenerOnMap() {
		gamesMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
		
		        @Override
		        public void onMapClick(LatLng point) {
		            gamesMap.addMarker(new MarkerOptions().position(point));
		        }
		    });
	}

	/**
	 * Returns the current location of the user, so as to set a marker on the map.
	 * @return  a LatLng object containing the current location.
	 */
	private LatLng getCurrentLocation() {
		LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String provider = service.getBestProvider(criteria, false);
		Location location = service.getLastKnownLocation(provider);
		currentLocation = location;
		return new LatLng(location.getLatitude(),location.getLongitude());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_games_on_map, menu);
		return true;
	}
}
