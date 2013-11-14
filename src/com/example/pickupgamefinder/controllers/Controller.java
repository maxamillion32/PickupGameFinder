package com.example.pickupgamefinder.controllers;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.example.pickupgamefinder.R;
import com.example.pickupgamefinder.views.CreateGameActivity;
import com.example.pickupgamefinder.views.MainActivity;

/**
 * This class handles the inputs from the different views, and communicates changes to the cloud datastore.
 * @author Tarun Sharma (tsharma2)
 *
 */
public class Controller {
	private static String name, sport, time, date, info, venue;
	/**
	 * Handles the events for when the 'Create a Game' button is pressed.
	 * @param mainActivity  the current activity.
	 * @param view  the activity on which the 'Create a Game' button resides.
	 */
    public static void createGameHandler(MainActivity mainActivity, View view) {
        Intent createGameActivtyIntent = new Intent(mainActivity, CreateGameActivity.class);
        mainActivity.startActivity(createGameActivtyIntent);
    }

    /**
	 * Handles the events for when the 'Submit' button is pressed.
     * @param createGameActivity  the current activity.
     * @param view  the activity on which the 'Submit' button resides.
     */
    public static void submitButtonHandler(CreateGameActivity createGameActivity, View view) {
    	if (ValidateInput.isNameEntered(createGameActivity) && ValidateInput.isTimeSet(createGameActivity) 
    			&& ValidateInput.isDateSet(createGameActivity) && ValidateInput.isVenueSet(createGameActivity)){
    		storeInputs(createGameActivity);
    		//createGameActivity.sendData(name, sport, time, date, info, venue);
    		createGameActivity.finish();
    	}
    }

    /**
     * Store's the user inputs so that they can be accessed while communicating with the cloud datastore.
     * @param createGameActivity  the current activity.
     */
	private static void storeInputs(CreateGameActivity createGameActivity) {
    	EditText editTextName = (EditText)createGameActivity.findViewById(R.id.edit_text_name);
		name = new String(editTextName.getText().toString());
    	EditText editTextVenue = (EditText)createGameActivity.findViewById(R.id.edit_text_venue);
    	venue = new String(editTextVenue.getText().toString());
    	EditText editTextInfo = (EditText)createGameActivity.findViewById(R.id.edit_text_extra_info);
    	info = new String(editTextInfo.getText().toString());
	}

	/**
	 * Sets the sport selected by the user.
	 * @param selectedSport  the sport to be set.
	 */
	public static void setSport(Object selectedSport) {
		sport = new String((String)selectedSport);
	}

	/**
	 * Sets the time selected by the user.
	 * @param hourOfDay  the hour selected by the user.
	 * @param minute  the minute selected by the user.
	 */
	public static void setTime(int hourOfDay, int minute) {
		time = new String(hourOfDay + ":" + minute);
	}

	/**
	 * Sets the date selected by the user.
	 * @param selectedSport  the date to be set.
	 */
	public static void setDate(int year, int month, int day) {
		date = new String(month + "/" + day + "/" + year);
	}
    
}
