package com.dadi.customer.android.views;

import java.util.ArrayList;

import com.dadi.android.R;
import com.dadi.customer.android.models.DaDiOrder;
import com.dadi.customer.android.models.G;
import com.google.android.apps.analytics.easytracking.TrackedListActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ConditionActivity extends TrackedListActivity {
	private static final String TAG = "ConditionActivity";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        initAdapter();
    }	
	
	private void initAdapter() {
		Log.d(TAG, "initAdapter");
		
		ArrayList<Boolean> items = new ArrayList<Boolean>();
				
		items.add(new Boolean(false));
		items.add(new Boolean(false));
		items.add(new Boolean(false));
		
		setListAdapter(new Adapter(items)); 
	}
	
    private class Adapter extends ArrayAdapter<Boolean> {
    	
    	private Adapter(ArrayList<Boolean> items) {
			super(ConditionActivity.this, 0, items);
		}
    	
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Log.d(TAG, "getView");
			
			LayoutInflater li = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			TextView tv;
			RadioGroup rg;
			CheckBox cb;
			Button btn;
			
			switch(position) {
			case 0:
				if (convertView == null) {
					convertView = li.inflate(R.layout.list_item_condition_1, parent, false);
					rg = (RadioGroup)convertView.findViewById(R.id.radiogroup1);
					rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
						@Override
						public void onCheckedChanged(RadioGroup group, int checkedId) {
							switch (checkedId) {
							case R.id.radio0:
								G.order.getCondition().setTime(DaDiOrder.Condition.TIME_ASAP);
								break;
							case R.id.radio1:
								G.order.getCondition().setTime(10);
								break;
							case R.id.radio2:
								G.order.getCondition().setTime(15);
								break;
							case R.id.radio3:
								G.order.getCondition().setTime(30);
								break;
							default:
								Log.d(TAG, "wrong tip button");
							}
						}
					});
				} else 
					rg = (RadioGroup)convertView.findViewById(R.id.radiogroup1);
				switch (G.order.getCondition().getTime()) {
				case DaDiOrder.Condition.TIME_ASAP:
					rg.check(R.id.radio0);
					break;
				case 10:
					rg.check(R.id.radio1);
					break;
				case 15:
					rg.check(R.id.radio2);
					break;
				case 30:
					rg.check(R.id.radio3);
					break;
				default:
					Log.d(TAG, "wrong time condition");
				}
				return convertView;
			case 1:
				if (convertView == null) {
					convertView = li.inflate(R.layout.list_item_condition_2, parent, false);
					cb = (CheckBox)convertView.findViewById(R.id.checkbox1);
					cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
						@Override
						public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
							G.order.getCondition().setUseBrandOnly(isChecked);
						}
					});
					btn = (Button)convertView.findViewById(R.id.button1);
					btn.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							// show the list
						}
					});
				} else
					cb = (CheckBox)convertView.findViewById(R.id.checkbox1);

				cb.setChecked(G.order.getCondition().getUseBrandOnly());
				return convertView;
			case 2:
				if (convertView == null) {
					convertView = li.inflate(R.layout.list_item_condition_3, parent, false);
					((Button)convertView.findViewById(R.id.button1)).setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							G.order.getCondition().zeroTip();
							((Adapter)getListAdapter()).notifyDataSetChanged();
						}
					});
					((Button)convertView.findViewById(R.id.button2)).setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							G.order.getCondition().addTip(1);
							((Adapter)getListAdapter()).notifyDataSetChanged();
						}
					});
					((Button)convertView.findViewById(R.id.button3)).setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							G.order.getCondition().addTip(2);
							((Adapter)getListAdapter()).notifyDataSetChanged();
						}
					});
					((Button)convertView.findViewById(R.id.button4)).setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							G.order.getCondition().addTip(5);
							((Adapter)getListAdapter()).notifyDataSetChanged();
						}
					});
				}
				tv = (TextView)convertView.findViewById(R.id.text1);
				tv.setText(String.format(getString(R.string.tips), G.order.getCondition().getTip()));
				return convertView;
			default:
				Log.e(TAG, "Wrong postion number");
				return null;
			}
		}
    }
}
