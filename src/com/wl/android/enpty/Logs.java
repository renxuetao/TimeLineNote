package com.wl.android.enpty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.net.Uri;


public class Logs implements Serializable {
	private String titleView;
	private String textView;
	private String tipsView;
	private String date;
	private String[] urList = {"0","0","0","0"};
	public String[] getUrList() {
		return urList;
	}
	public void setUrList(String[] urList) {
		this.urList = urList;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getTitleView() {
		return titleView;
	}
	public void setTitleView(String titleView) {
		this.titleView = titleView;
	}
	public String getTextView() {
		return textView;
	}
	public void setTextView(String textView) {
		this.textView = textView;
	}
	public String getTipsView() {
		return tipsView;
	}
	public void setTipsView(String tipsView) {
		this.tipsView = tipsView;
	}
	
	
}
