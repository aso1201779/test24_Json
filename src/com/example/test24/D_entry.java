package com.example.test24;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class D_entry extends Activity implements View.OnClickListener{

	SQLiteDatabase db = null;
	MySQLiteOpenHelper helper = null;
	double inputReview = 0;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		setContentView(R.layout.d_entry);
	}

	@Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();
		Button Dentry =(Button)findViewById(R.id.Send);
		Dentry.setOnClickListener(this);
		Button DReset =(Button)findViewById(R.id.Reset);
		DReset.setOnClickListener(this);

		if(db == null){
			 helper = new MySQLiteOpenHelper(getApplicationContext());
			}try{
				db = helper.getWritableDatabase();
			}catch(SQLiteException e){
				return;
			}
	}

	@Override
	public void onClick(View v) {
		// TODO 自動生成されたメソッド・スタブ
		switch(v.getId()) {
		case R.id.Send:
			EditText title = (EditText)findViewById(R.id.Title);
			EditText location = (EditText)findViewById(R.id.Location);
			EditText comment = (EditText)findViewById(R.id.Comment);

			String inputTitle = title.getText().toString();
			String inputLocation = location.getText().toString();
			String inputComment = comment.getText().toString();

				if(inputTitle != null && !inputTitle.isEmpty()
					&& inputLocation != null && !inputLocation.isEmpty()
					&& inputComment != null && !inputComment.isEmpty()){

					helper.insertSpot(db, inputTitle, inputLocation, inputComment);

					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
					alertDialogBuilder.setMessage("登録しました。")


					.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int id) {
							// TODO 自動生成されたメソッド・スタブ
							Intent intent = new Intent(D_entry.this,Tab.class);
							startActivity(intent);
						}
					});
					AlertDialog alert = alertDialogBuilder.create();
					//設定画面へ移動するかの問い合わせダイアログを表示
					alert.show();
				}
			title.setText("");
			location.setText("");
			comment.setText("");
			break;

		case R.id.Reset:
			EditText Rtitle = (EditText)findViewById(R.id.Title);
			Rtitle.setText("");
			EditText Rlocation = (EditText)findViewById(R.id.Location);
			Rlocation.setText("");
			EditText Rcomment = (EditText)findViewById(R.id.Comment);
			Rcomment.setText("");
			break;
		}
	}

}
