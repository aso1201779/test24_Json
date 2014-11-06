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
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		// TODO 自動生成されたメソッド・スタブ
		switch(v.getId()) {
			case R.id.drive:
				intent = new Intent(home.this, Dmap.class);
				startActivity(intent);
				break;

		}

	}
}
