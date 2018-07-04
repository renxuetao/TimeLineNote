package com.example.timelinenote;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.style.TypefaceSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wl.android.DateSQLite.DateSQlite;
import com.wl.android.enpty.GetPictureFormCAMER;
import com.wl.android.enpty.Logs;
import com.wl.android.myeditView.MyEditView;

public class EditActivity extends Activity implements OnClickListener,
		OnTouchListener, OnLongClickListener {

	Logs mLogs;
	MyEditView medt_title;
	MyEditView medt_content;
	Button mbtn_back;
	Button mbtn_right;
	Button mbtn_addPicture;
	String mstrTitle;
	String mstrContent;
	String mstrDate;
	TextView mtv_showDate;
	String[] mstrImgList = { "0", "0", "0", "0" };
	RelativeLayout mRL_imgGroup;
	GetPictureFormCAMER mGetPicture;
	int[] mintImgId = { R.id.imageView0, R.id.imageView1, R.id.imageView2,
			R.id.imageView3 };
	int mintFlat = 0;
	int mintImgFlat = 0;
	final String BTN_SAVE = "save";
	final String BTN_ALTER = "alter";
	final int CAMERA = 120;
	int position;
	Boolean isadd = true;
	Typeface mTypeface_HM;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View parentView = View.inflate(getBaseContext(), R.layout.editview,
				null);
		setContentView(parentView);
		mTypeface_HM = Typeface.createFromAsset(getAssets(), "fonts/Hym.ttf");
		mLogs = new Logs();
		medt_title = (MyEditView) findViewById(R.id.edittitle);
		mtv_showDate = (TextView) findViewById(R.id.showdate);
		medt_content = (MyEditView) findViewById(R.id.editcontent);
		
		medt_title.setTypeface(mTypeface_HM);
		medt_content.setTypeface(mTypeface_HM);
		mtv_showDate.setTypeface(mTypeface_HM);
		mbtn_back = (Button) findViewById(R.id.back);

		mbtn_right = (Button) findViewById(R.id.ok);
		mbtn_right.setTag(BTN_SAVE);
		mbtn_addPicture = (Button) findViewById(R.id.addpicture);

		mRL_imgGroup = (RelativeLayout) findViewById(R.id.imagewindow);
		
		
		medt_content.setOnTouchListener(this);
		mGetPicture = new GetPictureFormCAMER(this);
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy/hhmmss");
		Date date = new Date(System.currentTimeMillis());
		mstrDate = format.format(date);
		mtv_showDate.setText(mstrDate);
		if (null != getIntent().getExtras()) {
			mLogs = (Logs) getIntent().getExtras().get("Logs");
			isadd = getIntent().getExtras().getBoolean("isadd");
			position = getIntent().getExtras().getInt("position");
			if (null != mLogs) {
				mbtn_right.setText("修改");
				mbtn_right.setTag(BTN_ALTER);
				mstrTitle = mLogs.getTitleView();
				mstrContent = mLogs.getTextView();
				mstrImgList = mLogs.getUrList();
				medt_title.setText(mstrTitle);
				medt_content.setText(mstrContent);
				for (int i = 0; i < 4; i++) {
					if (!mstrImgList[i].equals("0")) {
						Bitmap bitmap = mGetPicture
								.takePictureFormName(mstrImgList[i]);
						if (null != bitmap) {
							ImageView imageView = (ImageView) findViewById(mintImgId[i]);
							imageView.setImageBitmap(bitmap);
						}
					}
				}
				medt_title.setEnabled(false);
				medt_content.setEnabled(false);
			}
		}
		mbtn_back.setOnClickListener(this);
		mbtn_right.setOnClickListener(this);
		mbtn_addPicture.setOnClickListener(this);
		parentView.setOnTouchListener(this);
		medt_content.setOnTouchListener(this);
		for (int i = 0; i < mintImgId.length; i++) {
			ImageView imageView = (ImageView) findViewById(mintImgId[i]);
			imageView.setOnClickListener(this);
			imageView.setOnLongClickListener(this);
		}

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (0 == requestCode) {
			return ;
		}
		Bitmap bitmap = mGetPicture.getPictureFromResult(resultCode,
				requestCode, data);
		if (null == bitmap) {
			return;
		}
		String name_R = mGetPicture.getname();
		Toast.makeText(this, name_R, Toast.LENGTH_LONG).show();

		ImageView imageView = (ImageView) findViewById(mintImgId[mintImgFlat]);
		imageView.setImageBitmap(bitmap);
		mstrImgList[mintImgFlat] = name_R;

	}
	private void saveOn() {
		mstrTitle = medt_title.getText().toString();
		mstrContent = medt_content.getText().toString();
		mLogs.setTitleView(mstrTitle);
		mLogs.setTextView(mstrContent);
		mLogs.setDate(mstrDate);
		mLogs.setUrList(mstrImgList);

		if (true == isadd) {
			Intent intent = getIntent();
			intent.putExtra("Logs", mLogs);
			Bundle bundle = new Bundle();
			EditActivity.this.setResult(0, intent);
			
		} else {
			Intent intent = new Intent();
			intent.putExtra("Logs", mLogs);
			intent.putExtra("position", position);
			intent.putExtra("isedit", true);
			EditActivity.this.setResult(0, intent);
		}
		DateSQlite db = new DateSQlite(this);
		db.insertLog(mLogs);
		EditActivity.this.finish();
		overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.ok:
			// 判断内容是否为空
			if (0 == medt_content.length() && 0 == medt_title.length()) {
				Toast.makeText(this, "日记内容还是空的哦！", Toast.LENGTH_SHORT).show();
			} else {
				// 判断当前内容的状态 编辑模式 or 查看模式
				if (mbtn_right.getTag().equals(BTN_ALTER)) {
					mbtn_right.setTag(BTN_SAVE);
					mbtn_right.setText("保存");
					medt_title.setEnabled(true);
					medt_content.setEnabled(true);
				} else {
					saveOn();
				}
			}

			break;
		case R.id.back:
			if (mbtn_right.getTag().equals(BTN_SAVE)) {
				DialogMessegs();
			} else {
				setResult(1);
				EditActivity.this.finish();
				overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
			}
			break;
		case R.id.addpicture:
			mRL_imgGroup.setVisibility(View.VISIBLE);
			mintFlat = 1;
			break;
		case R.id.imageView0:
			popuwindow();
			mintImgFlat = 0;
			break;
		case R.id.imageView1:
			popuwindow();
			mintImgFlat = 1;
			break;
		case R.id.imageView2:
			popuwindow();
			mintImgFlat = 2;
			break;
		case R.id.imageView3:
			popuwindow();
			mintImgFlat = 3;
			break;
		case R.id.editcontent:
			medt_content.requestFocus();
		default:
			break;
		}
	}

	public void popuwindow() {
		AlertDialog.Builder deleteBuilder = new AlertDialog.Builder(this);
		deleteBuilder.setMessage("请选择一个来源");
		final int KEY = position;

		deleteBuilder.setPositiveButton("相机",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						mGetPicture.takePhoto();
					}
				});
		deleteBuilder.setNegativeButton("相册",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						mGetPicture.takeAlbum();
					}
				});
		deleteBuilder.create();
		deleteBuilder.show();
	}  
	public void DialogMessegs() {
		AlertDialog.Builder deleteBuilder = new AlertDialog.Builder(this);
		deleteBuilder.setMessage("确定要放弃编辑？");
		final int KEY = position;

		deleteBuilder.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						setResult(1);
						EditActivity.this.finish();
						overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
					}
				});
		deleteBuilder.setNegativeButton("取消",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});
		deleteBuilder.create();
		deleteBuilder.show();
	}

	public void deleteImage(int mintImgFlatx) {
		AlertDialog.Builder deleteBuilder = new AlertDialog.Builder(this);
		deleteBuilder.setMessage("确定删除图片？");
		final int mintImgFlat = mintImgFlatx;

		deleteBuilder.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						ImageView imageView = (ImageView) findViewById(mintImgId[mintImgFlat]);
						imageView
								.setImageResource(R.drawable.img_add_x);
						mstrImgList[mintImgFlat] = 0 + "";
					}
				});
		deleteBuilder.setNegativeButton("取消",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});
		deleteBuilder.create();
		deleteBuilder.show();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		medt_content.requestFocus();
		if (1 == mintFlat) {
			int height = mRL_imgGroup.getTop();
			int y = (int) event.getY();
			if (event.getAction() == MotionEvent.ACTION_UP) {
				if (y < height) {
					mRL_imgGroup.setVisibility(View.GONE);
					mintFlat = 0;
				}
			}
		}

		return true;
	}

	@Override
	public boolean onLongClick(View v) {

		switch (v.getId()) {
		case R.id.imageView0:
			deleteImage(0);
			break;
		case R.id.imageView1:
			deleteImage(1);
			break;
		case R.id.imageView2:
			deleteImage(2);
			break;
		case R.id.imageView3:
			deleteImage(3);
			break;
		default:
			break;
		}
		return true;
	}

}
