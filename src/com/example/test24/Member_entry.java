package com.example.test24;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.test24.R.id;

public class Member_entry extends Activity implements View.OnClickListener{

	SQLiteDatabase db = null;
	MySQLiteOpenHelper helper = null;
	String ddd = null;
	String eee = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		setContentView(R.layout.member_entry);


		// ラジオグループ
		final RadioGroup rgSelect = (RadioGroup)findViewById(R.id.radioGroup1);
		rgSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			// ラジオボタンが変更されると何番が選択されたか表示する
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
					case R.id.man:
						eee = "男";
						break;

					case R.id.woman:
						eee = "女";
						break;
				}
			}
		});


		Time time = new Time("Asia/Tokyo");
		time.setToNow();
		int date = time.year;

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// アイテムを追加します

		int i;
		for(i=0;i < 100;i++){
			adapter.add(Integer.toString(date));
			date = date - 1 ;
		}
		Spinner spinner = (Spinner) findViewById(id.seinen);
		// アダプターを設定します
		spinner.setAdapter(adapter);
		// スピナーのアイテムが選択された時に呼び出されるコールバックリスナーを登録します
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
				Spinner spinner = (Spinner) parent;
				// 選択されたアイテムを取得します
				String item = (String) spinner.getSelectedItem();
				ddd = item;
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();
		Button entry =(Button)findViewById(R.id.btnentry);
		entry.setOnClickListener(this);


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
		case R.id.btnentry:
			EditText aaa = (EditText)findViewById(R.id.userID);
			EditText bbb = (EditText)findViewById(R.id.password);
			EditText ccc = (EditText)findViewById(R.id.user_name);

			String inputID = aaa.getText().toString();
			String inputpass = bbb.getText().toString();
			String inputname = ccc.getText().toString();
			String inputyear = ddd;
			String inputseibetu = eee;



				if(inputID != null && !inputID.isEmpty()
				   && inputpass != null && !inputpass.isEmpty()
				   && inputname != null && !inputname.isEmpty()){


					if(inputID.length() > 5 && inputpass.length() > 5){

						helper.insertMember(db, inputID, inputpass, inputname, inputyear, inputseibetu);

						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
						alertDialogBuilder.setMessage("登録しました。")


						.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int id) {
								// TODO 自動生成されたメソッド・スタブ
								Intent intent = new Intent(Member_entry.this,Login.class);
								startActivity(intent);
							}
						});
						AlertDialog alert = alertDialogBuilder.create();
						alert.show();

					}else{

						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
						alertDialogBuilder.setMessage("6文字以上入力してください。")

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
			aaa.setText("");
			bbb.setText("");
			ccc.setText("");
			break;
		}
	}

}