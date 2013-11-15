package com.example.pickupgamefinder.views;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pickupgamefinder.R;
import com.example.pickupgamefinder.controllers.Controller;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MainActivity extends Activity {
	private ListView listOfGames;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Parse.initialize(this, "50iiLuHoZu8Ld8nR73vqvthTvqHvdmstO4mChRq0", "tMjA4KpECrVyQMNYoSuF8n0iTDCZKn7UZpUae8C6"); 
		ParseAnalytics.trackAppOpened(getIntent());
		listOfGames = (ListView) findViewById(R.id.listOfGames);
		listOfGames.setClickable(true);

		listOfGames.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long index) {
				startActivity(new Intent());
			}
		});
		displayListOfGames();
	}


	private void displayListOfGames() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("PickupGames");
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> objects, ParseException e) {
				if (e == null) {
					setGamesListInListView(objects);
				} 
				else {
					showToast(e.getMessage());
				}
			}
		});
	}

	private void setGamesListInListView(List<ParseObject> games) {
		GamesArrayAdapter gamesArrayAdapter = new GamesArrayAdapter(this, games);
		listOfGames.setAdapter(gamesArrayAdapter);
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

    public class GamesArrayAdapter extends BaseAdapter {
    	private List<ParseObject> games;
    	private Context context;

		public GamesArrayAdapter(Context context, List<ParseObject> games) {
			this.games = games;
			this.context = context;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.
					LAYOUT_INFLATER_SERVICE);
	 
			View gamesView = inflater.inflate(R.layout.list_games, parent, false);
			if (games != null) {
				setDataInTextViews(position, gamesView);
			}
			return gamesView;
	    }

		private void setDataInTextViews(int position, View gamesView) {
			TextView nameTextView = (TextView) gamesView.findViewById(R.id.name);
			nameTextView.setText(games.get(position).getString("name"));

			TextView venueTextView = (TextView) gamesView.findViewById(R.id.venue);
			venueTextView.setText(games.get(position).getString("venue"));

			TextView sportTextView = (TextView) gamesView.findViewById(R.id.sport);
			sportTextView.setText(games.get(position).getString("sport"));

			TextView infoTextView = (TextView) gamesView.findViewById(R.id.info);
			infoTextView.setText(games.get(position).getString("info"));

			Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = formatter.format(games.get(position).get("date"));
			TextView dateTextView = (TextView) gamesView.findViewById(R.id.date);
			dateTextView.setText(dateString);
		}

		@Override
		public int getCount() {
			if (games != null) {
				return games.size();
			}
			else {
				return 0;
			}
		}

		@Override
		public Object getItem(int arg0) {
			if (games != null) {
				return games.get(arg0);
			}
			else 
				return null;
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}
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
}
