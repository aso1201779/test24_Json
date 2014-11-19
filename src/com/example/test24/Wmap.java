package com.example.test24;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Wmap extends Activity implements View.OnClickListener{

	String username;
	String userID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wmap);
		Intent intent = getIntent();
		username = intent.getStringExtra("username");
		userID = intent.getStringExtra("userID");
	}

	@Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		Button MapEnd = (Button)findViewById(R.id.MapEnd);
		MapEnd.setOnClickListener(this);
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		// TODO 自動生成されたメソッド・スタブ
		switch(v.getId()){
			case R.id.MapEnd:
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
				alertDialogBuilder.setMessage("ウォッチを終了しますか？")
				.setCancelable(false)

				//GPS設定画面起動用ボタンとイベントの定義
				.setPositiveButton("終了",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int id) {
								// TODO 自動生成されたメソッド・スタブ
								endpop();
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
		}
	}
	protected void endpop(){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setMessage("終了しました。")
		.setCancelable(false)

		//GPS設定画面起動用ボタンとイベントの定義
		.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int id) {
						// TODO 自動生成されたメソッド・スタブ
						Intent intent = new Intent(Wmap.this,Home.class);
						intent.putExtra("username", username);
						intent.putExtra("userID", userID);
						startActivity(intent);
					}
		});
		AlertDialog alert = alertDialogBuilder.create();
		//設定画面へ移動するかの問い合わせダイアログを表示
		alert.show();
	}

}
