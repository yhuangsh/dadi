package com.dadi.customer.android.views;

import com.dadi.android.R;
import com.google.android.apps.analytics.easytracking.TrackedActivity;

import android.os.Bundle;

public class CityActivity extends TrackedActivity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_activity);
    }	
}
