package com.dadi.customer.android.views;

import java.util.ArrayList;
import java.util.List;

import com.dadi.android.R;
import com.dadi.customer.android.models.G;
import com.google.android.apps.analytics.easytracking.TrackedListActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LoginAccountActivity extends TrackedListActivity{
	private static final String TAG = "LoginAccountActivity";
	
	private Intent m_intent_to_change_password_activity;
	private Intent m_intent_to_login_activity;
	
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

		//m_intent_to_change_password_activity = new Intent(this, AccountActivity.class);
		//m_intent_to_login_activity = new Intent(this, CityActivity.class);
	}

	private void initAdapter() {
		Log.d(TAG, "initAdapter");
		
		String[] id_row = new String[2];
		String[] pwd_row = new String[2];
		String[] chg_pwd_btn = new String[1];
		String[] login_btn = new String[1];
	
		id_row[0] = getString(R.string.my_dadi_id);
		id_row[1] = getString(R.string.dadi_id_note);
		
		pwd_row[0] = getString(R.string.my_password);
		pwd_row[1] = getString(R.string.dadi_password_note);
		
		chg_pwd_btn[0] = getString(R.string.change_password);
		login_btn[0] = getString(R.string.use_another_dadi_id_password);
		
		ArrayList<String[]> l = new ArrayList<String[]>();
		
		l.add(id_row);
		l.add(pwd_row);
		l.add(chg_pwd_btn);
		l.add(login_btn);
		
		setListAdapter(new Adapter(l)); 
	}

	private void initListActions() {
		Log.d(TAG, "initMenu");
		
	    getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
				case 0:
					//startActivity(m_intent_to_change_password_activity);
					break;
				case 1:
					//startActivity(m_intent_to_login_activity);
					break;
				default:
					//do logout dialog
					break;
				}
			}
        });
	}
	
	private void updateList() {
		Adapter adapter = (Adapter)getListAdapter();
		adapter.setUserIdText(
				String.format(getString(R.string.my_dadi_id), 
						      G.session.getUser().getId()),
				getString(R.string.dadi_id_note));
		adapter.setPasswordText(
				String.format(getString(R.string.my_password), 
							  G.session.getUser().getInitialPassword()),
				getString(R.string.dadi_password_note));
		adapter.notifyDataSetChanged();
	}
	
	private class Adapter extends ArrayAdapter<String[]> {
		private Adapter(List<String[]> items) {
			super(LoginAccountActivity.this, 0, items);
			//super(LoginAccountActivity.this, R.layout.list_item_2_text, items);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Log.d(TAG, "getView");
			
			LayoutInflater li = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			String[] item = getItem(position);
			TextView tv;
			
			switch(position) {
			case 0:
			case 1:
				if (convertView == null)
					convertView = li.inflate(R.layout.list_item_2_text, parent, false);
				tv = (TextView)convertView.findViewById(R.id.text1);
				tv.setText(item[0]);
				tv = (TextView)convertView.findViewById(R.id.text2);
				tv.setText(item[1]);
				return convertView;
			case 2:
			case 3:
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
		
		public void setUserIdText(String text1, String text2) {
			String[] user_id_row = (String[])getItem(0);
			
			if (text1 != null) user_id_row[0] = text1;
			if (text2 != null) user_id_row[1] = text2;
		}

		public void setPasswordText(String text1, String text2) {
			String[] password_row = (String[])getItem(1);
			
			if (text1 != null) password_row[0] = text1;
			if (text2 != null) password_row[1] = text2;
		}
	}

}
