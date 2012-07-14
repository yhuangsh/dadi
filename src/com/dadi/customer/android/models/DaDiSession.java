package com.dadi.customer.android.models;

import android.location.Location;

public class DaDiSession {
	private DaDiUser m_user;
	private DaDiOrder m_last_order;
	private boolean m_is_login;
	
	public DaDiSession(String[] endPoints) {

	}

	public void resume() {
		m_user = new DaDiUser();
	}

	public boolean isLogin() {
		return false;
	}

	public DaDiUser getUser() {
		return m_user;
	}

	public String updateCity(Location location) {
		if (location != null)
			return "北京";
		else
			return null;
	}

	public DaDiOrder getLastOrder() {
		return m_last_order;
	}



}
