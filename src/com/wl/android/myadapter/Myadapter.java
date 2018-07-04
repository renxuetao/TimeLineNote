package com.wl.android.myadapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.timelinenote.R;
import com.wl.android.enpty.GetPictureFormCAMER;
import com.wl.android.enpty.Logs;




public class Myadapter extends BaseAdapter{

	private List<Logs> list;
	private Context context;
	ViewHolder viewHoder;
	private GetPictureFormCAMER getpicture = new GetPictureFormCAMER();
	Typeface mTypeface_HM ;
	public Myadapter(List<Logs> list, Context context) {
		this.list = list;
		this.context = context;
		mTypeface_HM = Typeface.createFromAsset(context.getAssets(), "fonts/Hym.ttf");
	}
	public int getCount() {
		// TODO 自动生成的方法存根
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO 自动生成的方法存根
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = new ViewHolder();
		if (convertView == null) {
			convertView = View.inflate(context,
					R.layout.timeline_index, null);
			viewHolder.titleView = (TextView) convertView.findViewById(R.id.title);
			viewHolder.textView = (TextView) convertView.findViewById(R.id.text);
			viewHolder.dateView = (TextView) convertView.findViewById(R.id.date);
			viewHolder.textView.setTypeface(mTypeface_HM);
			viewHolder.dateView.setTypeface(mTypeface_HM);
			viewHolder.titleView.setTypeface(mTypeface_HM);
			viewHolder.img[0] = (ImageView) convertView.findViewById(R.id.imagetlv0);
			viewHolder.img[1] = (ImageView) convertView.findViewById(R.id.imagetlv1);
			viewHolder.img[2] = (ImageView) convertView.findViewById(R.id.imagetlv2);
			viewHolder.img[3] = (ImageView) convertView.findViewById(R.id.imagetlv3);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		viewHolder.titleView.setText(list.get(position).getTitleView());
		viewHolder.textView.setText(list.get(position).getTextView());
		viewHolder.dateView.setText(list.get(position).getDate());
		String names[] = list.get(position).getUrList();
		for (int i = 0; i < 4; i++) {
			if (!names[i].equals("0")) {
				Bitmap bitmap = getpicture.takePictureFormName(names[i]);
				if (null != bitmap) {
					viewHolder.img[i].setImageBitmap(bitmap);
					viewHolder.img[i].setVisibility(View.VISIBLE);
				}
			}else {
				viewHolder.img[i].setVisibility(View.GONE);
			}
			
			
		}
		return convertView;	
}
}
class ViewHolder{
	public TextView titleView;
	public TextView textView;
	public TextView dateView;
	public ImageView[] img = new ImageView[4];
	
	
}
