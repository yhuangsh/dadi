package com.dadi.customer.android.views;

import java.util.ArrayList;
import java.util.List;

import com.dadi.android.R;
import com.dadi.customer.android.models.DaDiOrder;
import com.dadi.customer.android.models.G;
import com.google.android.apps.analytics.easytracking.TrackedListActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends TrackedListActivity {
	private static final String TAG = "MainActivity";
	
	private Intent m_intent_to_login_account_activity;
	private Intent m_intent_to_city_activity;
	private Intent m_intent_to_pickup_activity;
	private Intent m_intent_to_dropoff_activity;
	private Intent m_intent_to_condition_activity;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        initIntents();
        initAdapter();
        initListActions();
        
    }	
	
    @Override
	protected void onResume() {
		super.onResume();

		updateList();
	}
    
    private void initIntents() {
		Log.d(TAG, "initIntents");

		m_intent_to_login_account_activity = new Intent(this, LoginAccountActivity.class);
        m_intent_to_city_activity = new Intent(this, CityActivity.class);
        m_intent_to_pickup_activity = new Intent(this, PickUpDropOffActivity.class);
        m_intent_to_pickup_activity.putExtra("is_pickup", true);
        m_intent_to_dropoff_activity = new Intent(this, PickUpDropOffActivity.class);
        m_intent_to_dropoff_activity.putExtra("is_pickup", false);
        m_intent_to_condition_activity = new Intent(this, ConditionActivity.class);
	}
	
    private void initAdapter() {
		Log.d(TAG, "initAdapter");
		
		ArrayList<String[]> items = new ArrayList<String[]>();
		String[] menu = getResources().getStringArray(R.array.main_menu_titles);
		
		for (int i=0;i<menu.length;i++) {
			String[] row = new String[2];
			row[0] = menu[i];
			row[1] = null;
			items.add(row);
		}
		
		setListAdapter(new Adapter(items)); 
	}
    
    private void initListActions() {
		Log.d(TAG, "initMenu");
		
		ListView lv = getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
				case 0:
					if (G.session.isLogin())
						startActivity(m_intent_to_login_account_activity);
					else
						new LoginTask().execute(G.theVoid);
					break;
				case 1:
					startActivity(m_intent_to_city_activity);
					break;
				case 2:
					startActivity(m_intent_to_pickup_activity);
					break;
				case 3:
					startActivity(m_intent_to_dropoff_activity);
					break;
				case 4:
					startActivity(m_intent_to_condition_activity);
					break;
				default:
					//startActivity(m_intent_to_search_activity);
					break;
				}
			}
        });
	}
	
	private void updateList() {
		Log.d(TAG, "updateMenuDesc");
		
		Adapter adapter = (Adapter)getListAdapter();

		adapter.setAcountDesc(
				G.session.isLogin() ?
						String.format(getString(R.string.already_login), G.session.getUser().getId()) :
							getString(R.string.not_login)); 
		
		adapter.setCityDesc(
				G.order == null || G.order.getCity() == null ? 
						getString(R.string.please_pick_a_city) :
							G.order.getCity());
		
		adapter.setFromDesc(
				G.order == null || G.order.getFrom() == null ?
						getString(R.string.not_decided) :
							G.order.getFrom());
		
		adapter.setToDesc(
				G.order == null || G.order.getTo() == null ?
						getString(R.string.not_decided) :
							G.order.getTo());
		
		adapter.setConditionDesc(
				G.order == null || G.order.getCondition() == null ?
						getString(R.string.not_decided) :
							String.format(
									getString(R.string.condition_format),
									G.order.getCondition().getTime() == DaDiOrder.Condition.TIME_ASAP ?
											getString(R.string.asap) :
												String.format(
														getString(R.string.within_minutes),
														G.order.getCondition().getTime()),
									G.order.getCondition().getUseBrandOnly() ?
											getString(R.string.yes) :
												getString(R.string.no), 
									G.order.getCondition().getTip()));
		
		adapter.notifyDataSetChanged();
	}
    
    private class Adapter extends ArrayAdapter<String[]> {
    	private Adapter(List<String[]> items) {
			super(MainActivity.this, 0, items);
		}
    	
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			//Log.d(TAG, "getView");
			
			LayoutInflater li = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			String[] item = getItem(position);
			TextView tv;
			
			switch(position) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
				if (convertView == null)
					convertView = li.inflate(R.layout.list_item_2_text, parent, false);
				
				if (item[0] != null) {
					tv = (TextView)convertView.findViewById(R.id.text1);
					tv.setText(item[0]);
				}
				
				if (item[1] != null) {
					tv = (TextView)convertView.findViewById(R.id.text2);
					tv.setText(item[1]);
				}
				return convertView;
			case 5:
			case 6:
				if (convertView == null)
					convertView = li.inflate(R.layout.list_item_1_text_1_arrow, parent, false);
									
				tv = (TextView)convertView.findViewById(R.id.text1);
				tv.setText(item[0]);
				return convertView;
			default:
				Log.e(TAG, "Wrong item length="+item.length);
				return null;
			}
		}
		
    	private void setText2(int position, String text) {
    		String[] item = (String[])getItem(position);
    		item[1] = text;
    	}
    	
    	private void setAcountDesc(String desc) {
    		setText2(0, desc);
    	}
    	
    	private void setCityDesc(String desc) {
    		setText2(1, desc);
    	}
    	
    	private void setFromDesc(String desc) {
    		setText2(2, desc);
		}
    	
    	private void setToDesc(String desc) {
    		setText2(3, desc);
		}
    	
    	private void setConditionDesc(String desc) {
    		setText2(4, desc);
			
		}
    }
    
    private class LoginTask extends AsyncTask<Void, Void, Void> {
    	private ProgressDialog m_dialog;
    	
    	@Override
		protected void onPreExecute() {
			super.onPreExecute();
			m_dialog = ProgressDialog.show(
					MainActivity.this, 
					"", 
					getString(R.string.login_in_progress), 
					true,
					false);
		}

		@Override
		protected Void doInBackground(Void... params) {
			G.session.resume();
			return null;
		}
		
	     protected void onPostExecute(Void v) {
	    	 if (G.session.isLogin()) {
	    		 m_dialog.dismiss();
	    		 startActivity(m_intent_to_login_account_activity);
	    	 } else {
	    		 m_dialog.dismiss();
	    		 Toast toast = Toast.makeText(
	    				 MainActivity.this, 
	    				 getString(R.string.fail_to_login), 
	    				 Toast.LENGTH_LONG);
	    		 toast.setGravity(Gravity.CENTER, 0, 0);
	    		 toast.show();
	    	 }
	     }
    }
}