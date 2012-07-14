package com.dadi.customer.android.models;

import java.util.ArrayList;

public class DaDiUser {
	private String m_id;
	private String m_initial_password;
	private ArrayList<String> m_pickup_history;
	private ArrayList<String> m_dropoff_history;

	public DaDiUser() {
		m_id = "123495";
		m_initial_password = "******";
		m_pickup_history = new ArrayList<String>();
		m_dropoff_history = new ArrayList<String>();
		
		m_pickup_history.add("上车点1");
		m_pickup_history.add("上车点2");
		m_pickup_history.add("上车点3");
		m_pickup_history.add("上车点4");
		m_pickup_history.add("上车点5");
		m_pickup_history.add("上车点6");
		m_pickup_history.add("上车点7");
		
		m_dropoff_history.add("下车点1");
		m_dropoff_history.add("下车点2");
		m_dropoff_history.add("下车点3");
		m_dropoff_history.add("下车点4");
		m_dropoff_history.add("下车点5");
		m_dropoff_history.add("下车点6");
		m_dropoff_history.add("下车点7");
	}
	
	public String getId() {
		return m_id;
	}
	public String getInitialPassword() {
		return m_initial_password;
	}
	
	private String[] getHistory(ArrayList<String> src, int count) {
		if (src == null)
			return null;
		
		String[] ret;
		
		if (count < src.size())
			ret = new String[count];
		else
			ret = new String[src.size()];
		
		for (int i=0;i<ret.length;i++)
			ret[i] = src.get(i);
		
		return ret;
	}
	
	public String[] getFromHistory(int count) {
		return getHistory(m_pickup_history, count);
	}

	public String[] getToHistory(int count) {
		return getHistory(m_dropoff_history, count);
	}

}
