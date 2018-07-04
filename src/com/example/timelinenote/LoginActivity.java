package com.example.timelinenote;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wl.android.DateSQLite.DateSQlite;
import com.wl.android.animation.MyAnimation;

public class LoginActivity extends Activity implements OnClickListener{
		
		Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO 自动生成的方法存根
			super.handleMessage(msg);
			if (1 == msg.what) {
				Toast.makeText(LoginActivity.this, "登录成功！",
						Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(LoginActivity.this, IndexActivity.class);
				LoginActivity.this.startActivity(intent);
				LoginActivity.this.finish();
			}else if (2 == msg.what) {
				Toast.makeText(LoginActivity.this, "请重新登录！",
						Toast.LENGTH_SHORT).show();
				miv_user.startAnimation(mAnimation.ButtonClickClose());
					new Timer().schedule(new TimerTask() {
								@Override
								public void run() {
									// TODO 自动生成的方法存根
									handler.sendEmptyMessage(3);
								}
							}, 1000);
			}else if (3 == msg.what) {
				miv_user.startAnimation(mAnimation.setAnimationSet());
			}
		}
	};
		
	DateSQlite mSQL;
	String mstrPassword;
	ImageView miv_user;
	MyAnimation mAnimation;
	TextView mtv_forget;
	protected void onCreate(Bundle savedInstanceState) {
			// TODO 自动生成的方法存根
			super.onCreate(savedInstanceState);
			setContentView(R.layout.login);
			mSQL = new DateSQlite(this);
			
				try {
					Cursor cursor = mSQL.getInformation();
					cursor.moveToFirst();
					mstrPassword = cursor.getString(mSQL.KEY);
					System.out.println(mstrPassword);
					cursor.close();
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("无密码或密码获取错误"); 
					mstrPassword = "0";
				}
				mAnimation = new MyAnimation();
			miv_user = (ImageView) findViewById(R.id.img_user);
			mtv_forget = (TextView) findViewById(R.id.tv_frogivelogin);
			mtv_forget.setOnClickListener(this);
			miv_user.setOnClickListener(this);
			miv_user.setAnimation(mAnimation.setAnimationSet());
		}
	 
	 
	 public AnimationSet setAnimationSetBack()
	    {	
	    	AnimationSet animationSet = new AnimationSet(true);
	    	ScaleAnimation animation;
	    	animation = new ScaleAnimation(1f, 0.1f, 1f, 0.1f
	    			,ScaleAnimation.RELATIVE_TO_SELF, 0.5f
	    			,ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
			animation.setDuration(4000);
			animationSet.addAnimation(animation);
			return animationSet;
	    }
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.img_user:
			
				miv_user.startAnimation(mAnimation.ButtonClickOpen());
				new Timer().schedule(new TimerTask() {
					
					@Override
					public void run() {
						// TODO 自动生成的方法存根
						logIn();
					}
				}, 1500);
				
				break;
			case R.id.tv_frogivelogin:
				Intent intent = new Intent(LoginActivity.this, ResistitActivity.class);
				intent.putExtra("type", "4");
				LoginActivity.this.startActivity(intent);
			default:
				break;
			}
		}
		
		public void logIn() {
			
			new Thread(){
				
				public void run() {
					String strPassword = ((EditText)findViewById(R.id.ed_password)).getText().toString();
					if ((!mstrPassword.equals("0")) && strPassword.equals(mstrPassword)) {
							handler.sendEmptyMessage(1);
					}else if (mstrPassword.equals("0")) {
						handler.sendEmptyMessage(1);
					}else {
						handler.sendEmptyMessage(2);
					}
					
				};
			}.start();
		}
		
}
