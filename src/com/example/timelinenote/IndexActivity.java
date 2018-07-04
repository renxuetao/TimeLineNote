package com.example.timelinenote;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;

import com.wl.android.DateSQLite.DateSQlite;
import com.wl.android.enpty.Logs;
import com.wl.android.myadapter.Myadapter;


public class IndexActivity extends Activity implements OnClickListener,OnItemClickListener,OnItemLongClickListener{

	private ListView mlistview;
	ArrayList<Logs> mlistLogs = new ArrayList<Logs>();
	final int HEADER_CODE = 1;
	DateSQlite mSQL;
	android.widget.PopupMenu mpopupMenu = null;
	Button mbtnMenu;
	Typeface mTypeface_HM;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        
        mTypeface_HM = Typeface.createFromAsset(getAssets(), "fonts/Hym.ttf");
        mlistview = (ListView) findViewById(R.id.listview);
        View ListViewHeader = View.inflate(this, R.layout.listview_header, null);
        mlistview.addHeaderView(ListViewHeader);
        Button addbButton = (Button) findViewById(R.id.btn_add);
        mbtnMenu = (Button) findViewById(R.id.btn_menu);
        mSQL = new DateSQlite(this);


		initDatas();
        addbButton.setOnClickListener(this);
        mlistview.setOnItemClickListener(this);
        mlistview.setOnItemLongClickListener(this);
        mbtnMenu.setOnClickListener(this);
        try {
			mlistLogs = mSQL.getLogs();
			initDatas();
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    
    private void initDatas() {
		// TODO 自动生成的方法存根
        Myadapter myadapter = new Myadapter(mlistLogs, this);
        mlistview.setAdapter(myadapter);
	}

	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO 自动生成的方法存根
    	super.onActivityResult(requestCode, resultCode, data);
    	
    		if (0 == resultCode) {
    			Bundle bundle = data.getExtras();
    			Logs dateLogs = (Logs) (bundle.get("Logs"));
    			Boolean isedit = data.getExtras().getBoolean("isedit");
    			int position = data.getExtras().getInt("position");
    			if (isedit) {
    				mlistLogs.remove(position - HEADER_CODE);
    				mlistLogs.add(position - HEADER_CODE,dateLogs);
    			}else {
    				//listview 增加  header 之后产生的1个位子的位移
    				mlistLogs.add(0,dateLogs);
    			}
			}
			initDatas();
		
    }
			@Override
			protected void onPause() {
				// TODO 自动生成的方法存根
				super.onPause();
				mSQL.insertLogs(mlistLogs);
			}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO 自动生成的方法存根
		if (0 != position) {
			
			Intent intent = new Intent(IndexActivity.this, EditActivity.class);
			intent.putExtra("isadd", false);
			intent.putExtra("position", position);
			intent.putExtra("Logs", mlistLogs.get(position - HEADER_CODE));
			startActivityForResult(intent, 0);
		}
			
		
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_add:
			Intent intent = new Intent();
	    	intent.setClass(IndexActivity.this, EditActivity.class);
	    	IndexActivity.this.startActivityForResult(intent, 0);
	    	overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
			break;
		case R.id.btn_menu:
			PopupMenu(mbtnMenu);
		default:
			break;
		}
		
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {

		AlertDialog.Builder deleteBuilder = new AlertDialog.Builder(this);
		deleteBuilder.setMessage("你确定删除这篇日记吗？");
		final int KEY = position - HEADER_CODE;
		
		deleteBuilder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mSQL.deleteLog(mlistLogs.get(KEY));
				mlistLogs.remove(KEY);
				initDatas();
			}
		});
		deleteBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO 自动生成的方法存根
				
			}
		});
		deleteBuilder.create();
		deleteBuilder.show();
		return true;
	}
   
	public void PopupMenu(View button)
	{
		
		mpopupMenu = new PopupMenu(this, button);
		getMenuInflater().inflate(R.menu.menu, mpopupMenu.getMenu());
		mpopupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent intent = new Intent(IndexActivity.this, ResistitActivity.class);
				switch (item.getItemId()) {
				case R.id.menu_im_setPassword:
					intent.putExtra("type", "1");
					IndexActivity.this.startActivity(intent);
					break;
				case R.id.menu_im_alterPassword:
					intent.putExtra("type", "2");
					IndexActivity.this.startActivity(intent);
					break;
				case R.id.menu_im_deletePassword:
					intent.putExtra("type", "3");
					IndexActivity.this.startActivity(intent);
					break;
				default:
					break;
				}
				
				return false;
			}
		});
		mpopupMenu.show();
		
	}
	
}
