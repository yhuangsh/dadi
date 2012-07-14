package com.dadi.customer.android.models;

import android.content.Context;

public class DaDiOrder {
	private String m_city;
	private String m_from;
	private String m_to;
	private Condition m_condition;
	
	public class Condition {
		public static final int TIME_ASAP = 0;
		
		private int m_within;
		private boolean m_use_brand_only;
		private int m_tip;
		
		public Condition() {
			m_within = TIME_ASAP;
			m_use_brand_only = true;
			m_tip = 0;
		}
		
		public void setTime(int within) {
			m_within = within;
		}

		public int getTime() {
			return m_within;
		}

		public void setUseBrandOnly(boolean use_brand_only) {
			m_use_brand_only = use_brand_only;
		}

		public boolean getUseBrandOnly() {
			return m_use_brand_only;
		}

		public void zeroTip() {
			m_tip = 0;
		}

		public void addTip(int inc) {
			m_tip += inc;
		}

		public int getTip() {
			return m_tip;
		}
		
	}
	
	public DaDiOrder() {
		m_condition = new Condition();
	}
	
	public String getCity() {
		return m_city;
	}

	public String getFrom() {
		return m_from;
	}

	public String getTo() {
		return m_to;
	}
	
	public Condition getCondition() {
		return m_condition;
	}

	public void setFrom(String from) {
		m_from = from;
	}

	public void setTo(String to) {
		m_to = to;
	}

	
}
