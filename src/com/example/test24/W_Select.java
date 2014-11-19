package com.example.test24;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;

public class W_Select extends Activity implements View.OnClickListener {

	CheckBox ten;
	CheckBox twenty;
	CheckBox thirty;
	CheckBox forty;
	CheckBox fifty;
	CheckBox sixty;
	String sei = null;
	String username;
	String userID;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		setContentView(R.layout.w_select);

		Intent intent = getIntent();
		username = intent.getStringExtra("username");
		userID = intent.getStringExtra("userID");

		// ラジオグループ
				final RadioGroup rgSelect = (RadioGroup)findViewById(R.id.radiogroup_id);
				rgSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					// ラジオボタンが変更されると何番が選択されたか表示する
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						switch (checkedId) {
							case R.id.manbtn:
								sei = "男";
								break;

							case R.id.womanbtn:
								sei = "女";
								break;
						}
					}
				});

	}

	@Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();
		Button next = (Button)findViewById(R.id.next);
		next.setOnClickListener(this);

		ten = (CheckBox)findViewById(R.id.Ten);
		ten.setOnClickListener(this);
		twenty = (CheckBox)findViewById(R.id.Twenty);
		twenty.setOnClickListener(this);
		thirty = (CheckBox)findViewById(R.id.Thirty);
		thirty.setOnClickListener(this);
		forty = (CheckBox)findViewById(R.id.Forty);
		forty.setOnClickListener(this);
		fifty = (CheckBox)findViewById(R.id.Fifty);
		fifty.setOnClickListener(this);
		sixty = (CheckBox)findViewById(R.id.Sixty);
		sixty.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO 自動生成されたメソッド・スタブ
		Intent intent = null;

		switch(v.getId()){
			case R.id.next:
				if(sei != null &&
					(ten.isChecked() ||twenty.isChecked() ||thirty.isChecked() ||
					forty.isChecked() ||fifty.isChecked() ||sixty.isChecked() == true)){


					intent = new Intent(W_Select.this,W_Random.class);
					intent.putExtra("sei", sei);
					intent.putExtra("username", username);
					intent.putExtra("userID", userID);
					startActivity(intent);
				}
			break;

			case R.id.Ten:
				if(ten.isChecked() == true){

				}
			break;

			case R.id.Twenty:
				if(twenty.isChecked() == true){

				}
			break;

			case R.id.Thirty:
				if(thirty.isChecked() == true){

				}
			break;

			case R.id.Forty:
				if(forty.isChecked() == true){

				}
			break;

			case R.id.Fifty:
				if(fifty.isChecked() == true){

				}
			break;

			case R.id.Sixty:
				if(sixty.isChecked() == true){

				}
			break;



		}
	}

}
