package com.example.test24;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Login extends Activity implements View.OnClickListener{

	SQLiteDatabase db = null;
	MySQLiteOpenHelper helper = null;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
	}

	@Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();
		Button login =(Button)findViewById(R.id.login);
		login.setOnClickListener(this);
		Button notMember =(Button)findViewById(R.id.notMember);
		notMember.setOnClickListener(this);
		Button member_entry =(Button)findViewById(R.id.member_entry);
		member_entry.setOnClickListener(this);


		if(db == null){
			helper = new MySQLiteOpenHelper(getApplicationContext());
		}
		try{
			db = helper.getWritableDatabase();
		}catch(SQLiteException e){
			Log.e("ERROR" , e.toString());
		}
	}


	@Override
	public void onClick(View v) {
		Intent intent = null;
		// TODO 自動生成されたメソッド・スタブ
		switch(v.getId()) {
		case R.id.login:
			EditText ID = (EditText)findViewById(R.id.userID);
			EditText pass = (EditText)findViewById(R.id.password);
			String inputloginID = ID.getText().toString();
			String inputloginpass = pass.getText().toString();

				if(inputloginID != null && !inputloginID.isEmpty()
					&& inputloginpass != null && !inputloginpass.isEmpty()){

					if(inputloginID.length() > 5 && inputloginpass.length() > 5){


						String strSelect = helper.selectMember(db, inputloginID, inputloginpass);
						if(strSelect != null){
							intent = new Intent(Login.this, Home.class);
							intent.putExtra("username", strSelect);
							startActivity(intent);

						}else{
							AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
							alertDialogBuilder.setMessage("IDまたは\npassが違います。")

							 .setPositiveButton("OK", null);
							AlertDialog alert = alertDialogBuilder.create();
							alert.show();
						}
					}else{
						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
						alertDialogBuilder.setMessage("文字数が足りません")

						 .setPositiveButton("OK", null);
						AlertDialog alert = alertDialogBuilder.create();
						alert.show();
					}
				}else{
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
					alertDialogBuilder.setMessage("正しく入力されていません、\n確認してください。")

					 .setPositiveButton("OK", null);
					AlertDialog alert = alertDialogBuilder.create();
					alert.show();
				}
			ID.setText("");
			pass.setText("");
			break;
		case R.id.member_entry:
			intent = new Intent(Login.this, Member_entry.class);
			startActivity(intent);
			break;
		case R.id.notMember:
			intent = new Intent(Login.this, Home.class);
			startActivity(intent);
			break;

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}



}
