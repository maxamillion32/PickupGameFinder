package com.example.pickupgamefinder.views;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;

import com.example.pickupgamefinder.R;
import com.example.pickupgamefinder.controllers.Controller;
import com.parse.Parse;
import com.parse.ParseAnalytics;

public class MainActivity extends FragmentActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Parse.initialize(this, "50iiLuHoZu8Ld8nR73vqvthTvqHvdmstO4mChRq0", "tMjA4KpECrVyQMNYoSuF8n0iTDCZKn7UZpUae8C6"); 
		ParseAnalytics.trackAppOpened(getIntent());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
    
    /** 
     * Called when the user clicks the 'Create a Game' button is clicked.
     * @param view  the view on which the 'Create a Game' button resides.
     **/
    public void createGameButtonClicked(View view) {
        Controller.createGameHandler(this, view); 
    }

}
