package com.example.test24;

import android.app.ActionBar;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Tab extends Activity{

	SQLiteDatabase db = null;
	MySQLiteOpenHelper helper = null;

	static final String[] TAGS ={"tab1","tab2","tab3"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab);
		final ActionBar bar = getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		String label1 = "マップ";
		String label2 = "投稿";
		String label3 = "END";
		bar.addTab(bar.newTab()
				.setText(label1)
				.setTabListener(new MyTabListener(TabContentFragment.newInstance(R.layout.dmap))));
		bar.addTab(bar.newTab()
				.setText(label2)
				.setTabListener(new MyTabListener(TabContentFragment.newInstance(R.layout.d_entry))));
		bar.addTab(bar.newTab()
				.setText(label3)
				.setTabListener(new MyTabListener(TabContentFragment.newInstance(R.layout.home))));
	}


	public class MyTabListener extends Activity implements TabListener, View.OnClickListener{
		private TabContentFragment mFragment;



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
		public MyTabListener(TabContentFragment fragment){
			mFragment = fragment;
		}
		@Override
		public void onTabSelected(android.app.ActionBar.Tab tab, FragmentTransaction ft) {
			// TODO 自動生成されたメソッド・スタブ
			if(!mFragment.isAdded()){
				ft.add(R.id.fragment_content,mFragment,TAGS[tab.getPosition()]);
			}else{
				ft.show(mFragment);
			}
			// タブにリスナーを追加
			this.setListener();
		}
		@Override
		public void onTabUnselected(android.app.ActionBar.Tab tab, FragmentTransaction ft) {
			// TODO 自動生成されたメソッド・スタブ
			ft.hide(mFragment);
		}
		@Override
		public void onTabReselected(android.app.ActionBar.Tab tab, FragmentTransaction ft) {
			// TODO 自動生成されたメソッド・スタブ

		}


	    public void setListener() {
	    	// ActivityからFragmentのインスタンスを取得する
	    	TabContentFragment tf1 = (TabContentFragment) getFragmentManager().findFragmentByTag(TAGS[0]);
	    	if (tf1!= null) {
//	    		Log.d("ActionBarTab",tv1.getText().toString());
//	    		Log.d("ActionBarTab",edt1.getText().toString());
	    	}
	    	TabContentFragment tf2 = (TabContentFragment) getFragmentManager().findFragmentByTag(TAGS[1]);
	    	if (tf2!= null) {
	    		Button Dentry =(Button)findViewById(R.id.Send);
	    		Dentry.setOnClickListener(this);
	    		
	    		


	    		//	    		Log.d("ActionBarTab",tv2.getText().toString());
//	    		Log.d("ActionBarTab",tv3.getText().toString());
	    	}
	    	TabContentFragment tf3 = (TabContentFragment) getFragmentManager().findFragmentByTag(TAGS[2]);
	    	if (tf3!= null) {
//	    		TextView tv4 = (TextView) findViewById(R.id.textView4);
//	    		EditText edt2 = (EditText) findViewById(R.id.editText2);
//	    		TextView tv5 = (TextView) findViewById(R.id.textView5);
//	    		EditText edt3 = (EditText) findViewById(R.id.editText3);
//	    		Log.d("ActionBarTab",tv4.getText().toString());
//	    		Log.d("ActionBarTab",edt2.getText().toString());
//	    		Log.d("ActionBarTab",tv5.getText().toString());
//	    		Log.d("ActionBarTab",edt3.getText().toString());
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
						&& inputLocation != null && !inputLocation.isEmpty()){

						helper.insertSpot(db, inputTitle, inputLocation, inputComment);

						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MyTabListener.this);
						alertDialogBuilder.setMessage("登録しました。")


						.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int id) {
								// TODO 自動生成されたメソッド・スタブ
								Intent intent = new Intent(Tab.this,Tab.class);
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
//
//			case R.id.Reset:
//				EditText Rtitle = (EditText)findViewById(R.id.Title);
//				Rtitle.setText("");
//				EditText Rlocation = (EditText)findViewById(R.id.Location);
//				Rlocation.setText("");
//				EditText Rcomment = (EditText)findViewById(R.id.Comment);
//				Rcomment.setText("");
//				break;
			}
		}

	}

	public static class TabContentFragment extends Fragment{
		private int mLayoutId;
		public static TabContentFragment newInstance(int layoutId){
			TabContentFragment tf = new TabContentFragment();

			Bundle args = new Bundle();
			args.putInt("layoutId", layoutId);
			tf.setArguments(args);

			return tf;
		}
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			// TODO 自動生成されたメソッド・スタブ
			mLayoutId = getArguments().getInt("layoutId");
			View fragView = inflater.inflate(mLayoutId,container,false);

			return fragView;
		}


	}


	public void doPositiviClick(){
		appEnd();
	}
	void appEnd(){
		super.finish();
	}

}