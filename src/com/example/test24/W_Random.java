package com.example.test24;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class W_Random extends Activity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		setContentView(R.layout.w_random);
	}

	@Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();
		Button skip = (Button)findViewById(R.id.skipbtn);
		skip.setOnClickListener(this);
		Button back = (Button)findViewById(R.id.backbtn);
		back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO 自動生成されたメソッド・スタブ
		Intent intent = null;

		switch(v.getId()){
		case R.id.skipbtn:
			intent = new Intent(W_Random.this,W_Random.class);
			startActivity(intent);
		break;

		case R.id.backbtn:
			intent = new Intent(W_Random.this,W_Select.class);
			startActivity(intent);
		break;
		}
	}

}
