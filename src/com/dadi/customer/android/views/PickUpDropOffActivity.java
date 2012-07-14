package com.dadi.customer.android.views;

import java.util.ArrayList;
import java.util.List;

import com.dadi.android.R;
import com.dadi.customer.android.models.G;
import com.google.android.apps.analytics.easytracking.TrackedListActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PickUpDropOffActivity extends TrackedListActivity {
	private static final String TAG = "PickUpDropOffActivity";
	private static final int MAX_HISTORY_ITEM_COUNT = 10; 
	
	private boolean m_is_pickup;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        m_is_pickup = getIntent().getBooleanExtra("is_pickup", true);
        initAdapter();
        initListActions();
    }	
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
    private void initAdapter() {
		Log.d(TAG, "initAdapter");
		
		ArrayList<String> items = new ArrayList<String>();
				
		items.add(getString(R.string.input_from_hint));
		String[] history = 
				m_is_pickup ? 
						G.session.getUser().getFromHistory(MAX_HISTORY_ITEM_COUNT) :
							G.session.getUser().getToHistory(MAX_HISTORY_ITEM_COUNT);
		for (int i=0;i<history.length;i++)
			items.add(history[i]);
		
		setListAdapter(new Adapter(items)); 
	}
    
    private void initListActions() {
		Log.d(TAG, "initMenu");
		
		ListView lv = getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				Adapter adapter = (Adapter)getListAdapter();
				
				if (position > 0) {
					if (m_is_pickup) 
						G.order.setFrom((String)adapter.getItem(position));
					else
						G.order.setTo((String)adapter.getItem(position));
					finish();
				} 			
			}
        });
	}
	
    private class Adapter extends ArrayAdapter<String> {
  	
    	private Adapter(List<String> items) {
			super(PickUpDropOffActivity.this, 0, items);
		}
    	
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Log.d(TAG, "getView");
			
			LayoutInflater li = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			String item = getItem(position);
			
			if (position == 0) {
				if (convertView == null) {
					convertView = li.inflate(R.layout.list_item_1_input_1_button, parent, false);
					final EditText e = (EditText)convertView.findViewById(R.id.edit1);
					Button b = (Button)convertView.findViewById(R.id.button1);
					b.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							String address = e.getText().toString();
							
							if (address == null || "".equals(address)) {
								Toast toast = Toast.makeText(
										PickUpDropOffActivity.this, 
										getString(R.string.you_have_not_entered_address), 
										Toast.LENGTH_LONG);
								toast.setGravity(Gravity.CENTER, 0, 0);
								toast.show();
							} else {
								if (m_is_pickup) 
									G.order.setFrom(e.getText().toString());
								else
									G.order.setTo(e.getText().toString());
								finish();
							}
						}
					});
				}
				
				((EditText)convertView.findViewById(R.id.edit1)).setHint(item);
				((Button)convertView.findViewById(R.id.button1)).setText(getString(android.R.string.ok));
				return convertView;
			} else {
				if (convertView == null)
					convertView = li.inflate(R.layout.list_item_1_text, parent, false);
				
				((TextView)convertView.findViewById(R.id.text1)).setText(item);
				return convertView;
			} 
		}
    }
}
