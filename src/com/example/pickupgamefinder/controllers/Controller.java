package com.example.pickupgamefinder.controllers;

import java.util.Calendar;
import java.util.Date;

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
	private String name, sport, info, venue;
	private int hour, minute, year, month, day;
	private Date date; 
	private static Controller instance; 
	
	public static Controller getInstance() {
		if (instance == null)
			instance = new Controller();
		return instance;
	}

	/**
	 * Handles the events for when the 'Create a Game' button is pressed.
	 * @param mainActivity  the current activity.
	 * @param view  the activity on which the 'Create a Game' button resides.
	 */
    public static void createGameHandler(MainActivity mainActivity, View view) {
        Intent createGameActivtyIntent = new Intent(mainActivity, CreateGameActivity.class);
        mainActivity.startActivity(createGameActivtyIntent);
        mainActivity.finish();
    }

    /**
	 * Handles the events for when the 'Submit' button is pressed.
     * @param createGameActivity  the current activity.
     * @param view  the activity on which the 'Submit' button resides.
     */
    public void submitButtonHandler(CreateGameActivity createGameActivity, View view) {
    	if (ValidateInput.isNameEntered(createGameActivity) && ValidateInput.isTimeSet(createGameActivity) 
    			&& ValidateInput.isDateSet(createGameActivity) && ValidateInput.isVenueSet(createGameActivity)){
    		storeInputs(createGameActivity);
    		addTimeToDate();
    		createGameActivity.sendData(name, sport, date, info, venue);
    		createGameActivity.finish();
			createGameActivity.startActivity(new Intent(createGameActivity, MainActivity.class));
    	}
    }

    private void addTimeToDate() {
		Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DATE, day);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.HOUR, hour);
        cal.set(Calendar.MINUTE, minute);
        date = (Date)(cal.getTime()).clone();	
	}

	/**
     * Store's the user inputs so that they can be accessed while communicating with the cloud datastore.
     * @param createGameActivity  the current activity.
     */
	private void storeInputs(CreateGameActivity createGameActivity) {
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
	public void setSport(Object selectedSport) {
		sport = new String((String)selectedSport);
	}

	/**
	 * Sets the time selected by the user.
	 * @param hourOfDay  the hour selected by the user.
	 * @param minute  the minute selected by the user.
	 */
	public void setTime(int hourOfDay, int minute) {
		this.hour = hourOfDay;
		this.minute = minute;
	}

	/**
	 * Sets the date selected by the user.
	 * @param selectedSport  the date to be set.
	 */
	public void setDate(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;

	}
}
