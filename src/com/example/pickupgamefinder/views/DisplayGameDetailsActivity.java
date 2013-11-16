package com.example.pickupgamefinder.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.example.pickupgamefinder.R;
import com.example.pickupgamefinder.R.layout;
import com.example.pickupgamefinder.R.menu;

public class DisplayGameDetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_game_details);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_game_details, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
	    startActivity(new Intent(this, MainActivity.class));
	}

}
