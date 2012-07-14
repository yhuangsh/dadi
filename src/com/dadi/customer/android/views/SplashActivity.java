package com.dadi.customer.android.views;

import com.dadi.android.R;
import com.dadi.customer.android.models.DaDiConfig;
import com.dadi.customer.android.models.DaDiOrder;
import com.dadi.customer.android.models.DaDiSession;
import com.dadi.customer.android.models.G;
import com.google.android.apps.analytics.easytracking.TrackedActivity;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

public class SplashActivity extends TrackedActivity {
	private static final String TAG = "SplashActivity";
	private ProgressBar m_pb;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_activity);
		
		m_pb = (ProgressBar)findViewById(R.id.progressBar1);
		m_pb.setMax(5);
		m_pb.setProgress(1);
		new InitTask().execute(G.theVoid);
	}
	
	private class InitTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			
			// Step 0: location
			LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
			Criteria cr = new Criteria();
			cr.setAccuracy(Criteria.ACCURACY_FINE);
			G.lm_provider = lm.getBestProvider(cr, true);
			G.location = lm.getLastKnownLocation(G.lm_provider);
			Log.d(TAG, "locaiton="+G.location.getLatitude()+","+G.location.getLongitude());
			
			// Step 1: configuration
			G.config = new DaDiConfig();
			G.config.pullServerConfig();
			publishProgress(G.theVoid);
			
			// Step 2: session
			G.session = G.config.getSavedSession();
			if (G.session == null) 
				G.session = new DaDiSession(G.config.getEndPoints());
			G.session.resume();
			publishProgress(G.theVoid);
			
			// Step 3: order
			if (G.session.isLogin()) 
				G.order = G.session.getLastOrder();
			else 
				G.order = new DaDiOrder();
			publishProgress(G.theVoid);

			// Step 4
			return null;
		}
		
		 protected void onProgressUpdate(Void... progress) {
	         m_pb.setProgress(m_pb.getProgress()+1);
	     }

	     protected void onPostExecute(Void v) {
	    	 startActivity(new Intent(SplashActivity.this, MainActivity.class));
	    	 finish();
	     }

	}
}
