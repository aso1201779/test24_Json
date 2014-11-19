package com.example.test24;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.OnScanCompletedListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;

public class Dmap extends Activity implements LocationListener ,View.OnClickListener{

	private WebView mWebView;
	private LocationManager mLocationManager;
	String username;
	private Bitmap bm;
	private Uri bitmapUri;
	static final int REQUEST_CODE_CAMERA = 1; /* カメラを判定するコード */


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//ロケーションマネージャを取得
		mLocationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
		setContentView(R.layout.dmap);
		mWebView = (WebView)findViewById(R.id.webview);
		mWebView.setWebViewClient(new WebViewClient(){

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO 自動生成されたメソッド・スタブ
				return super.shouldOverrideUrlLoading(view, url);
			}

		});
		Intent intent = getIntent();
		username = intent.getStringExtra("username");
		ImageView imageView1 =(ImageView)findViewById(R.id.imageView1);
		imageView1.setOnClickListener(this);
	}

	@Override
	protected void onResume() {

		Button MapEndBtn = (Button)findViewById(R.id.MapEndBtn);
		MapEndBtn.setOnClickListener(this);


		// TODO 自動生成されたメソッド・スタブ
		if(mLocationManager != null){
			mLocationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER,
					//LocationManager.NETWORK_PROVIDER,
					5000,
					0,
					this);
		}
		chkGpsService();
		mWebView.setWebChromeClient(new WebChromeClient(){
		@Override
		public void onGeolocationPermissionsShowPrompt(String origin, Callback callback) {
			// TODO 自動生成されたメソッド・スタブ
			super.onGeolocationPermissionsShowPrompt(origin, callback);
			//常に位置情報を取得する
			callback.invoke(origin, true, false);
		}
	    });

		findViews();//viewの読み込み
			if(netWorkCheck(this.getApplicationContext()) ){
				WebSettings settings = mWebView.getSettings();
				settings.setJavaScriptEnabled(true);
				//Geolocationを有効化
				settings.setGeolocationEnabled(true);
				mWebView.loadUrl("file:///android_asset/html/test.html");//サイトの読み込み
			}else{
				// 確認ダイアログの生成
		        AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
		        alertDlg.setTitle("エラー");
		        alertDlg.setMessage("メッセージ");
		        alertDlg.setPositiveButton(
		            "OK",
		            new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog, int which) {
		                    // OK ボタンクリック処理
		                }
		            });
		        //表示
		        alertDlg.create().show();
			}
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public void findViews(){
		mWebView = (WebView)findViewById(R.id.webview);
	}
	//ネットワーク接続確認
	public static boolean netWorkCheck(Context context){
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if(info != null){
			return info.isConnected();
		}else{
			return false;
		}
	}

	//GPSが有効かチェック
	//無効になっていれば、設定画面の表示確認ダイアログ
	private void chkGpsService(){
		//GPSセンサーが利用可能か？
		if(!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
			alertDialogBuilder.setMessage("GPSが有効になっていません。\n有効化しますか？")
			.setCancelable(false)

			//GPS設定画面起動用ボタンとイベントの定義
			.setPositiveButton("GPS設定起動",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int id) {
							// TODO 自動生成されたメソッド・スタブ
							Intent callGPSSettingIntent = new Intent(
									android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
							startActivity(callGPSSettingIntent);
						}
			});
			//キャンセルボタン処理
			alertDialogBuilder.setNegativeButton("キャンセル",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							// TODO 自動生成されたメソッド・スタブ
							dialog.cancel();
						}
					});
			AlertDialog alert = alertDialogBuilder.create();
			//設定画面へ移動するかの問い合わせダイアログを表示
			alert.show();
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void onClick(View v) {
		// TODO 自動生成されたメソッド・スタブ
		switch(v.getId()){
			case R.id.MapEndBtn:
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
				alertDialogBuilder.setMessage("ドライブを終了しますか？")
				.setCancelable(false)

				//GPS設定画面起動用ボタンとイベントの定義
				.setPositiveButton("終了",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int id) {
								// TODO 自動生成されたメソッド・スタブ

								// 取ったキャプチャの幅と高さを元に
		                        // 新しいBitmapを生成する。
		                        Bitmap  bitmap = Bitmap.createBitmap(
		                                        mWebView.getWidth(),
		                                        mWebView.getHeight(),
		                                        Bitmap.Config.ARGB_8888);
								final Canvas c =new Canvas(bitmap);
								mWebView.draw(c);
								Log.d("キャプチャ","成功");

								// 新しいフォルダへのパス
						        String folderPath = Environment.getExternalStorageDirectory()
						                + "/NewFolder/";
						        File folder = new File(folderPath);
						        Log.d("フォルダパス","インポート");
						        if (!folder.exists()) {
						            folder.mkdirs();
						        }

						        // NewFolderに保存する画像のパス
						        File file = new File(folder,System.currentTimeMillis() + ".jpg");
						        Log.d("画像パス","インポート");
						        if (file.exists()) {
						            file.delete();
						        }

						        try {
						        	Log.d("保存","成功");
						            FileOutputStream out = new FileOutputStream(file);
						            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
						            out.flush();
						            out.close();
						        } catch (Exception e) {
						            e.printStackTrace();
						        }

						        try {
						            // これをしないと、新規フォルダは端末をシャットダウンするまで更新されない
						            showFolder(file);
						        } catch (Exception e) {
						            e.printStackTrace();
						        }

								Intent intent = new Intent(Dmap.this,D_entry.class);
								intent.putExtra("username", username);
								startActivity(intent);
							}
				});
				//キャンセルボタン処理
				alertDialogBuilder.setNegativeButton("キャンセル",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								// TODO 自動生成されたメソッド・スタブ
								dialog.cancel();
							}
						});
				AlertDialog alert = alertDialogBuilder.create();
				//設定画面へ移動するかの問い合わせダイアログを表示
				alert.show();
				break;
			case R.id.cameraBtn:

				// アップロードボタンが押された時
				String[] str_items = {"カメラで撮影", "ギャラリーの選択", "キャンセル"};
				new AlertDialog.Builder(this)
				.setTitle("写真をアップロード")
				.setItems(str_items, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO 自動生成されたメソッド・スタブ
						switch(which){
							case 0:
								wakeupCamera(); // カメラ起動
								break;
							default:
								// キャンセルを選んだ場合
								break;
									}
						}
				}).show();

				break;
		}
	}
	protected void wakeupCamera(){
		File mediaStorageDir = new File(
			Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_PICTURES
			), "PictureSaveDir"
		);
		if (! mediaStorageDir.exists() & ! mediaStorageDir.mkdir()){
			return;
		}
		String timeStamp = new SimpleDateFormat("yyyMMddHHmmss").format(new Date());
		File mediaFile;
		mediaFile = new File(mediaStorageDir.getPath() + File.separator + timeStamp + ".JPG");
		Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		bitmapUri = Uri.fromFile(mediaFile);
		i.putExtra(MediaStore.EXTRA_OUTPUT, bitmapUri); // 画像をmediaUriに書き込み
		startActivityForResult(i, REQUEST_CODE_CAMERA);
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		if (resultCode == RESULT_OK){
			if (bm != null)
				bm.recycle(); // 直前のBitmapが読み込まれていたら開放する

			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 4; // 元の1/4サイズでbitmap取得

			switch(requestCode){
				case 1: // カメラの場合
					bm = BitmapFactory.decodeFile(bitmapUri.getPath(), options);
					// 撮影した画像をギャラリーのインデックスに追加されるようにスキャンする。
					// これをやらないと、アプリ起動中に撮った写真が反映されない
					String[] paths = {bitmapUri.getPath()};
					String[] mimeTypes = {"image/*"};
					MediaScannerConnection.scanFile(getApplicationContext(), paths, mimeTypes, new OnScanCompletedListener(){
						@Override
						public void onScanCompleted(String path, Uri uri){
						}
					});
					break;
			}

		}
	}

	// ContentProviderに新しいイメージファイルが作られたことを通知する
    private void showFolder(File path) throws Exception {
        try {
            ContentValues values = new ContentValues();
            ContentResolver contentResolver = getApplicationContext()
                    .getContentResolver();
            values.put(Images.Media.MIME_TYPE, "image/jpeg");
            values.put(Images.Media.DATE_MODIFIED,
                    System.currentTimeMillis() / 1000);
            values.put(Images.Media.SIZE, path.length());
            values.put(Images.Media.TITLE, path.getName());
            values.put(Images.Media.DATA, path.getPath());
            contentResolver.insert(Media.EXTERNAL_CONTENT_URI, values);
        } catch (Exception e) {
            throw e;
        }
    }

}