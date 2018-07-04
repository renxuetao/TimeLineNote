package com.wl.android.myadapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Path;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.widget.Toast;

public class GetPictureFormCAMER {

	private Activity activity;
	private static final int CAMEA_TAKE = 1;
	private static final int CAMEA_SELECT = 2;
	public String name;
	private static final String PATH = Environment
			.getExternalStorageDirectory() + "/Withepage/image";
	private boolean isBig = false;


	public GetPictureFormCAMER(Activity activity) {
		this.activity = activity;
	}
	public GetPictureFormCAMER()
	{}
	public String getname_R() {

		new DateFormat();
		String name_R = DateFormat.format("yyyyMMMdd_hhmmss",
				Calendar.getInstance(Locale.CHINA))
				+ ".png";
		return name_R;

	}

	public String getname() {
		return name;
	}

	public String getPath() {
		return PATH;
	}

	public void takeAlbum() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		name = getname_R();
		activity.startActivityForResult(intent, CAMEA_SELECT);
	}

	public void takePhoto() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		new DateFormat();
		name = getname_R();

		Uri imageUri = Uri.fromFile(new File(PATH, name));

		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

		activity.startActivityForResult(intent, CAMEA_TAKE);
	}

	public Bitmap getPictureFromResult(int resultCode, int requestCode,
			Intent data) {
		if (resultCode == activity.RESULT_OK) {
			switch (requestCode) {
			case CAMEA_TAKE:
				Bitmap bitmap = BitmapFactory.decodeFile(PATH + "/" + name);
				saveBitmap(name, bitmap);
				Toast.makeText(activity, name, Toast.LENGTH_LONG).show();
				System.out.println(bitmap.getHeight() + "====="
						+ bitmap.getWidth());

				DisplayMetrics dm = new DisplayMetrics();
				activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

				float scale = bitmap.getWidth() / (float) dm.widthPixels;

				Bitmap newBitmap = null;
				if (scale > 1) {
					newBitmap = zoomBitmap(bitmap, bitmap.getWidth() / scale,
							bitmap.getHeight() / scale);
					bitmap.recycle();
					isBig = true;
				}
				if (isBig) {
					return newBitmap;
				} else {
					return bitmap;
				}
			case CAMEA_SELECT:
				ContentResolver resolver = activity.getContentResolver();

				Uri imgUri = data.getData();

				try {
					Bitmap photoBitmap = MediaStore.Images.Media.getBitmap(
							resolver, imgUri);

					saveBitmap(name, photoBitmap);

					DisplayMetrics dm_2 = new DisplayMetrics();
					activity.getWindowManager().getDefaultDisplay()
							.getMetrics(dm_2);

					float scale_2 = photoBitmap.getWidth()
							/ (float) dm_2.widthPixels;

					Bitmap newBitmap_2 = null;
					if (scale_2 > 1) {
						newBitmap_2 = zoomBitmap(photoBitmap,
								photoBitmap.getWidth() / scale_2,
								photoBitmap.getHeight() / scale_2);
						photoBitmap.recycle();
						isBig = true;
					}
					if (isBig) {
						return newBitmap_2;
					} else {
						return photoBitmap;
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				break;
			default:
				break;
			}
		}
		return null;
	}

	public Bitmap zoomBitmap(Bitmap bitmap, float width, float height) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) width / w);
		float scaleheight = ((float) height / h);
		matrix.postScale(scaleWidth, scaleheight);
		Bitmap newbitBitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix,
				true);
		return newbitBitmap;
	}

	public void saveBitmap(String name, Bitmap bm) {
		File ff = new File(PATH);
		if (ff.exists() == false) {
			ff.mkdirs();
		}
		File f = new File(PATH + "/" + name);
		System.out.println("��ʼ������.......");

		try {
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.PNG, 90, out);
			out.flush();
			out.close();
			System.out.print(" ����ɹ� �r(�s_�t)�q");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("����ʧ��");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("����ʧ��");
		}

	}
	public Bitmap takePictureFormName(String names)
	{
		Bitmap bitmap = BitmapFactory.decodeFile(PATH + "/" + names);
		if (null != bitmap) {
			return bitmap;
		}
		return null;
	}
}
