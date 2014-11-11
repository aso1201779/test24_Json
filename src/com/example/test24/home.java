package com.example.test24;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class home extends Activity implements View.OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		Intent intent = getIntent();
		String username = intent.getStringExtra("username");
		TextView tv = (TextView)findViewById(R.id.myname);
		if(username != null){
			tv.setText("ようこそ" + username + "さん");
		}else{
			tv.setText("ようこそゲストさん");
		}
	}

	@Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();
		ImageButton drive =(ImageButton)findViewById(R.id.drive);
		drive.setOnClickListener(this);
		ImageButton watch =(ImageButton)findViewById(R.id.imageButton2);
		watch.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		// TODO 自動生成されたメソッド・スタブ
		switch(v.getId()) {
			case R.id.drive:
				intent = new Intent(home.this, Tab.class);
				startActivity(intent);
				break;
			case R.id.imageButton2:
				intent = new Intent(home.this,D_entry.class);
				startActivity(intent);

		}

	}
}
