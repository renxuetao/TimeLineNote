package com.wl.android.DateSQLite;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.wl.android.enpty.Logs;

public class DateSQlite {
			SQLiteDatabase db;
			Context context;
			public final static int KEY = 3;
			public final static int PASSWORD = 1;
			public final static int QUESTION = 2;
			public final static int DATE = 0;
			public final static int TITLE = 1;
			public final static int CONTEXT = 2;
			public final static int IMG0 = 3;
			public final static int IMG1 = 4;
			public final static int IMG2 = 5;
			public final static int IMG3 = 6;
			
	public DateSQlite(Context context) {
		this.context = context;	
		db = SQLiteDatabase.openOrCreateDatabase(
					context.getCacheDir().toString() + "/my.db3", null);
		try {
			db.execSQL("create table Logs("
					+"date varchar(10) primary key"
					+ ", title varchar(50)"
					+ ", context varchar(400)"
					+ ", img0 varchar(20)"
					+ ", img1 varchar(20)"
					+ ", img2 varchar(20)"
					+ ", img3 varchar(20))");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			db.execSQL("create table user_table("
					+ " id varchar(2) primary key,"
					+ " password varchar(8),"
					+ " question varchar(200),"
					+ " keys varchar(80))"
					);
			db.execSQL("update user_table"
					+ " set password = '0', "
					+ " question = '0',"
					+ " keys = '0'"
					+ " where id = 'Only'");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void insertInformation(String password, String question,  String keys)
	{
		try {
			inserData(db, question, password, keys);
		} catch (Exception e) {
			db.execSQL("create table if not exists user_table("
					+ " id varchar(2) primary key,"
					+ " password varchar(8),"
					+ " question varchar(200),"
					+ " keys varchar(80))"
					);
			
		inserData(db, question, password, keys);
		}
		
	}
	public void deleteUser(){
		deleteImfromation();
	}
	private void deleteImfromation(){
		try {
			db.execSQL("update user_table"
					+ " set password = '0', "
					+ " question = '0',"
					+ " keys = '0'"
					+ " where id = 'Only'");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	private void inserData(SQLiteDatabase db2, String question, String password, String keys) {
		
		try {
			db.execSQL("insert into user_table values(?, ?, ?, ?)"
					, new String[]{"Only", password, question,keys});
		} catch (Exception e) {
			// TODO: handle exception
			db.execSQL("update user_table"
					+ " set password = '"+ password+"', "
					+ " question = '"+question+"',"
					+ " keys = '"+keys+"'"
					+ " where id = 'Only'");
		}
		
	}
	public Cursor getInformation()
	{
		return selectInformation();
	}
	private Cursor selectInformation()
	{
		Cursor cursor;
		try {
			cursor = db.rawQuery("select * from user_table", null);
			cursor.moveToFirst();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
		return cursor;
	}
	public void deleteLog(Logs logs)
	{
		db.execSQL("delete from Logs where date = '"+ logs.getDate() +"'");
	}
	public void deleteLogs()
	{
		db.execSQL("delete from Logs where date = '*'");
	}
	public void updataLog(Logs logs)
	{
		db.execSQL("update Logs"
				+ " set title = '"+ logs.getTitleView()+"', "
				+ " context = '"+logs.getTextView()+"',"
				+ " img0 = '"+logs.getUrList()[0]+"',"
				+ " img1 = '"+logs.getUrList()[1]+"',"
				+ " img2 = '"+logs.getUrList()[2]+"',"
				+ " img3 = '"+logs.getUrList()[3]+"'"
				+ " where date = '"+logs.getDate()+"'");
	}
	public Boolean hasLogs(Logs logs)
	{
		try {
			Cursor cursor;
			cursor = db.rawQuery("select * from Logs where date = '"+ logs.getDate()+"'",null);
			cursor.close();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		
	}
	
	public ArrayList<Logs> getLogs()
	{
		Cursor cursor;
		ArrayList<Logs> list= new ArrayList<Logs>();
		cursor = db.rawQuery("select * from Logs", null);
		cursor.moveToFirst();
		Boolean haveNext = true;  
		while (haveNext) {
			Logs logs = new Logs();
			logs.setDate(cursor.getString(0));
			logs.setTitleView(cursor.getString(1));
			logs.setTextView(cursor.getString(2));
			String string[] = {cursor.getString(3)
					, cursor.getString(4)
					, cursor.getString(5)
					, cursor.getString(6)};
			logs.setUrList(string);
			list.add(logs);
			haveNext = cursor.moveToNext();
		}
		cursor.close();
		return list;
		
	}
	public void insertLogs(ArrayList<Logs> list)
	{
		for (int i = 0; i < list.size(); i++) {
			insertLog(list.get(i));
		}
	}
	public void insertLog(Logs logs)
	{
		String date = logs.getDate();
		String title = logs.getTitleView();
		String context = logs.getTextView();
		String img0 = logs.getUrList()[0];
		String img1 = logs.getUrList()[1];
		String img2 = logs.getUrList()[2];
		String img3 = logs.getUrList()[3];
		try {
			db.execSQL("insert into Logs values(?, ?, ?, ?, ?, ?, ?)"
					, new String []{date
					, title
					, context
					, img0, img1, img2, img3});
			
		} catch (SQLiteConstraintException e) {
			e.printStackTrace();
			try {
				db.execSQL("create table Logs("
						+"date varchar(10) primary key"
						+ ", title varchar(50)"
						+ ", context varchar(400)"
						+ ", img0 varchar(20)"
						+ ", img1 varchar(20)"
						+ ", img2 varchar(20)"
						+ ", img3 varchar(20))");
				db.execSQL("insert into Logs values(?,?,?,?,?,?,?)"
						, new String []
						{date
						, title
						, context
						, img0, img1, img2, img3});
			} catch (Exception e2) {
				System.out.println("日期已存在，内容将被修改");
				updataLog(logs);
			}
			}
		}
	
}
