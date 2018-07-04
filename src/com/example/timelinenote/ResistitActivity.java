package com.example.timelinenote;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wl.android.DateSQLite.DateSQlite;

public class ResistitActivity extends Activity implements OnClickListener {
	DateSQlite db;
	EditText resistPassword;
	EditText resisqustion;
	EditText resistanswer;
	LinearLayout ly_ensure_mPassword;
	LinearLayout ly_set_mPassword;
	LinearLayout ly_answer_mQuestion;
	private String mPassword;
	private String mQuestion;
	private String mKey;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resist);
		ly_ensure_mPassword = (LinearLayout) findViewById(R.id.ly_ensure_password);
		ly_answer_mQuestion = (LinearLayout) findViewById(R.id.ly_answerquestion);
		ly_set_mPassword = (LinearLayout) findViewById(R.id.ly_set_password);
		resistPassword = (EditText) findViewById(R.id.resist_password);
		resisqustion = (EditText) findViewById(R.id.resist_question);
		resistanswer = (EditText) findViewById(R.id.resist_answer);
		
		findViewById(R.id.btn_resist_cancle).setOnClickListener(this);
		db = new DateSQlite(this);
		Intent intent = getIntent();
		String typeString = intent.getStringExtra("type");
		try {
			Cursor cursor = db.getInformation();
			cursor.moveToFirst();
			  mPassword = cursor.getString(db.PASSWORD);
			  mQuestion = cursor.getString(db.QUESTION);
			  mKey = cursor.getString(db.KEY);
		} catch (Exception e) {
			// TODO: handle exception
			mPassword = "0";
			mQuestion = "0";
			mKey = "0";
		}
		if (typeString.equals("1")) {
			if (mPassword.equals("0")) {
				setPassword();
			}else {
				Toast.makeText(ResistitActivity.this, "密码已经设置了哦！", Toast.LENGTH_LONG).show();
			}
		} else if (typeString.equals("2")) {
			alterPassword();
		} else if (typeString.equals("3")) {
			deletePassword();
		}else {
			FogivePassword();
		}
		
	}

	private void deletePassword() {
		// TODO 自动生成的方法存根
		ly_ensure_mPassword.setVisibility(View.VISIBLE);
		TextView tv_answer = (TextView) findViewById(R.id.tv_forgive);
		
		tv_answer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				FogivePassword();
				ly_ensure_mPassword.setVisibility(View.GONE);
				return;
			}
		});
		
		Button btn_ensure = (Button) findViewById(R.id.btn_ensure);
		btn_ensure.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				EditText edt_ensur = (EditText) findViewById(R.id.edt_ensure_password);
				final String ed_mPassword = edt_ensur.getText().toString();
				if (ed_mPassword.equals(mPassword)) {
					db.deleteUser();
					Toast.makeText(ResistitActivity.this, "密码删除成功", Toast.LENGTH_LONG).show();
					findViewById(R.id.ly_ensure_password).setVisibility(View.GONE);
				}else {
					Toast.makeText(ResistitActivity.this, "密码错误", Toast.LENGTH_LONG).show();
				}
			}
		});
		
	}

	private void alterPassword() {
		// TODO 自动生成的方法存根
		ly_ensure_mPassword.setVisibility(View.VISIBLE);
		TextView tv_answer = (TextView) findViewById(R.id.tv_forgive);
		
		tv_answer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				FogivePassword();
				ly_ensure_mPassword.setVisibility(View.GONE);
				return;
			}
		});
		
		Button btn_ensure = (Button) findViewById(R.id.btn_ensure);
		btn_ensure.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				EditText edt_ensur = (EditText) findViewById(R.id.edt_ensure_password);
				String ed_mPassword = edt_ensur.getText().toString();
				if (ed_mPassword.equals(mPassword)) {
					setPassword();
					ly_ensure_mPassword.setVisibility(View.GONE);
				}else {
					Toast.makeText(ResistitActivity.this, "密码错误", Toast.LENGTH_LONG).show();
				}
			}
		});
		
	}

	private void setPassword() {
		ly_set_mPassword.setVisibility(View.VISIBLE);
			Button btn_ensure = (Button) findViewById(R.id.btn_ensure);
			btn_ensure.setText("确定");
			btn_ensure.setOnClickListener( new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO 自动生成的方法存根
					risistmPassword();
					Toast.makeText(ResistitActivity.this, "密码设置成功", Toast.LENGTH_LONG).show();
					ResistitActivity.this.finish();
				}
			});
	}
	private void FogivePassword()
	{
		ly_answer_mQuestion.setVisibility(View.VISIBLE);
	
		TextView tv_qusetion = (TextView) findViewById(R.id.tv_answer_question);
		tv_qusetion.setText(mQuestion);
		
		Button btn_ensure = (Button) findViewById(R.id.btn_ensure);
		btn_ensure.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				EditText ed_answer = (EditText) findViewById(R.id.ed_answer_question);
				String answersString = ed_answer.getText().toString();
				if (answersString.equals(mKey)) {
					Toast.makeText(ResistitActivity.this, "恭喜回答正确", Toast.LENGTH_LONG).show();
					ly_answer_mQuestion.setVisibility(View.GONE);
					setPassword();
				}else {
					Toast.makeText(ResistitActivity.this, "回答错误呢！", Toast.LENGTH_LONG).show();
				}
				
			}
		});
		
	}
	private Boolean getInf() {
		try {
			Cursor cursor = db.getInformation();
			cursor.moveToFirst();
			mPassword = cursor.getString(db.KEY);
			mQuestion = cursor.getString(db.QUESTION);
			mKey = cursor.getString(db.KEY);
			cursor.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("无密码或密码获取错误");
			return false;
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.btn_resist_cancle:
			this.finish();
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO 自动生成的方法存根
		super.onActivityResult(requestCode, resultCode, data);
	}

	private boolean risistmPassword() {
		// TODO 自动生成的方法存根
		String mPassword = resistPassword.getText().toString();
		String mQuestion = resisqustion.getText().toString();
		String mKey = resistanswer.getText().toString();
		db.insertInformation(mPassword, mQuestion, mKey);
		return true;
	}

}
