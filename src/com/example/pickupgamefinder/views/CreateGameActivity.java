package com.example.pickupgamefinder.views;

import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.pickupgamefinder.R;
import com.example.pickupgamefinder.controllers.Controller;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

/**
 * The activity class that's responsible for displaying and taking in input about a specific game.
 * @author Tarun Sharma(tsharma2)
 */
public class CreateGameActivity extends FragmentActivity implements OnItemSelectedListener {

	public static boolean isTimeSet = false;
	public static boolean isDateSet = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_game);
		// Show the Up button in the action bar.
		createSportsSpinner();
		createNumberOfPlayersSpinner() ;
	}
	/**
	 * Creates the spinner that allows a user to choose the number of players.
	 */
	private void createNumberOfPlayersSpinner() {
		Spinner spinner = (Spinner) findViewById(R.id.number_of_players_spinner);
		spinner.setOnItemSelectedListener(this);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, 
				R.array.number_of_players_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);	
	}

	/**
	 * Creates the spinner that allows a user to choose a sport.
	 */
	private void createSportsSpinner() {
		Spinner spinner = (Spinner) findViewById(R.id.spinner_sports);
		spinner.setOnItemSelectedListener(this);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, 
				R.array.sports_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);	
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/*
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		*/
		return super.onOptionsItemSelected(item);

	}

	 /** 
     * Called when the user clicks the 'Submit' button.
     * @param view  the view on which the 'Submit' button resides.
     **/
	public void submitButtonPressed(View view) {
		Controller.getInstance().submitButtonHandler(this, view);
	}
    
	/**
	 * Called when an item is selected on the spinner and is part of the OnItemSelectedListerner interface.
	 * This function is a stub.
	 */
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
    	if (parent.getId() == R.id.spinner_sports) {
    		Controller.getInstance().setSport(parent.getItemAtPosition(pos).toString());
    	}
    	else if (parent.getId()== R.id.number_of_players_spinner) {
    		Controller.getInstance().setPlayers(parent.getItemAtPosition(pos).toString());
    	}
    }

    /**
     * Function that handles the action for when nothing is selected on the spinner. It is part of the 
     * OnItemSelectedListerner interface. This function is a stub.
     */
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    /**
     * A class responsible for creating the time picker.
     * @author Tarun Sharma
     */
    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
	@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);
	
			// Create a new instance of TimePickerDialog and return it
			return new TimePickerDialog(getActivity(), this, hour, minute,
					DateFormat.is24HourFormat(getActivity()));
		}
	

		/**
		 * This function is called when the time is set.
		 * @param view   the current time picker view.
		 * @param hourOfDay  the chosen hour.
		 * @param minute  the chosen minute.
		 */
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			Controller.getInstance().setTime(hourOfDay, minute);
			isTimeSet = true;
		}
    }
    

    /**
     * A class responsible for creating the date picker.
     * @author Tarun Sharma
     */
    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			
			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}
			
		/**
		 * This function is called when the time is set.
		 * @param view   the current date picker view.
		 * @param year  the chosen year.
		 * @param month  the chosen month.
		 * @param day  the chosen day.
		 */
		public void onDateSet(DatePicker view, int year, int month, int day) {
			Controller.getInstance().setDate(year, month, day);
			isDateSet = true;
		}
    }

    /**
     * This function is called when the date picker is displayed.
     * @param view  the current view containing the date picker.
     */
    public void showDatePickerDialog(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    /**
     * This function is called when the time picker is displayed.
     * @param view  the current view containing the time picker.
     */
    public void showTimePickerDialog(View view) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    /**
     * Shows a toast message for a short duration.
     * @param text  the text to be displayed in the toast message.
     */
	public void showToast(String text) {
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}


	/**
	 * Sends the data to the Parse cloud datastore.
	 * @param name  the name of the person organising the game.
	 * @param sport  the chosen sport.
	 * @param time  the time of the game.
	 * @param date  the date of the game.
	 * @param info  additional info about the game.
	 * @param venue  the venue of the game.
	 * @param numberOfPlayers  the number of players required for the game.
	 */
	public void sendData(String name, String sport, Date date, String info, String venue, int numberOfPlayers) {
		ParseObject newPickupGame = new ParseObject("PickupGames");
		putDataIntoPost(name, sport, date, info, venue, numberOfPlayers, newPickupGame);
		newPickupGame.saveInBackground(new SaveCallback () {
			@Override
			public void done(ParseException e) {
				if (e == null) {
					setResult(RESULT_OK);
					finish();
					startMainActivity();
				} else {
					showToast("Error saving: " + e.getMessage());
				}
			}


		});
	}

	private void startMainActivity() {
	    startActivity(new Intent(this, MainActivity.class));
	}

	/**
	 * Adds the data to the post cloud post.
	 * @param name  the name of the person organising the game.
	 * @param sport  the chosen sport.
	 * @param time  the time of the game.
	 * @param date  the date of the game.
	 * @param info  additional info about the game.
	 * @param venue the venue of the game.
	 * @param numberOfPlayers  the number of players required for the game.
	 * @param newPickupGame  the post to be sent.
	 */
	private void putDataIntoPost(String name, String sport, Date date, String info, String venue, 
			int numberOfPlayers, ParseObject newPickupGame) {
		if (date == null) {
			System.out.println("date is null");
		}
		newPickupGame.put("name", name);
		newPickupGame.put("sport", sport);
		newPickupGame.put("date", date);
		newPickupGame.put("info", info);
		newPickupGame.put("venue", venue);
		newPickupGame.put("total_players", numberOfPlayers);
		newPickupGame.put("current_players", 0);
	}

	/**
	 * Called when the find venue button is pressed. It starts the activity that helps the user to 
	 * find the location of the game on google maps.
	 * @param view
	 */
	public void findVenueButtonClicked(View view) {
		startActivity(new Intent(this, SearchGamesOnMapActivity.class));
	}

	@Override
	public void onBackPressed() {
	}

}
