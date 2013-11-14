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
		/*
		try {
			testing();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
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

    /*
    public void testing() throws IOException {
    	CloudCallbackHandler<List<CloudEntity>> handler = new CloudCallbackHandler<List<CloudEntity>>() {
    		@Override
    		public void onComplete(List<CloudEntity> results) {
    			listOfGames = results;
    			System.out.println("reached async func");
    			for (CloudEntity ce : listOfGames) {
    				listOfGames.add(0, ce);
    			}
    			for (CloudEntity game : listOfGames) {
    				System.out.println("reached loop");
    				StringBuilder name = new StringBuilder();
    				name.append(game.get("name"));
    				System.out.println(name.toString());
    			}
    		}

    		@Override
    		public void onError(IOException exception) {
    			handleEndpointException(exception);
    		}
    	};

    	// Executing the query.
		CloudQuery cq = new CloudQuery("PickupGames");
	    cq.setLimit(50);
	    cq.setSort(CloudEntity.PROP_UPDATED_AT, Order.DESC);
	    cq.setScope(Scope.FUTURE_AND_PAST);
	    getCloudBackend().list(cq, handler);
    }
    */

    /**
     * Displays any exceptions that occur during the post.
     * @param e  an exception that may occur during the post.
    private void handleEndpointException(IOException e) {
    	Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
    }
     */

}
