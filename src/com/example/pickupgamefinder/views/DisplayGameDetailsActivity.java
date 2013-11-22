package com.example.pickupgamefinder.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.pickupgamefinder.R;
import com.example.pickupgamefinder.controllers.Controller;

public class DisplayGameDetailsActivity extends Activity {
	private String currentGameObjectId;

	/**
	 * Retrieves the game details from the MainActivity and sets the details of the selected game on the current activity.
	 */
	private void setGameDetails() {
		Intent currentIntent = getIntent();
		
		currentGameObjectId = currentIntent.getStringExtra("objectId");

		TextView nameTextView = (TextView)findViewById(R.id.nameTextView);
		nameTextView.setText(currentIntent.getStringExtra("name"));

		TextView sportTextView = (TextView)findViewById(R.id.sportTextView);
		sportTextView.setText(currentIntent.getStringExtra("sport"));

		TextView venueTextView = (TextView)findViewById(R.id.venueTextView);
		venueTextView.setText(currentIntent.getStringExtra("venue"));

		TextView infoTextView = (TextView)findViewById(R.id.infoTextView);
		infoTextView.setText(currentIntent.getStringExtra("info"));

		TextView dateTextView = (TextView)findViewById(R.id.dateTextView);
		dateTextView.setText(currentIntent.getStringExtra("date"));
		
		TextView playersTextView = (TextView)findViewById(R.id.playersTextView);
		playersTextView.setText(currentIntent.getStringExtra("current_players") + "/" + currentIntent.getStringExtra("total_players") 
				+ " Players");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_game_details);
		setGameDetails();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_game_details, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		finish();
	    startActivity(new Intent(this, MainActivity.class));
	}
	
	/**
	 * Called when the current Join Game' button is pressed.
	 * @param view  the view in which the 'Join Game' button resides.
	 */
	public void joinGameButtonPressed(View view) {
		Controller.getInstance().joinGameHandler(this, view, currentGameObjectId);
	}

}