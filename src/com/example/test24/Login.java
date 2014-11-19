package com.example.test24;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class Login extends Activity implements View.OnClickListener{

	SQLiteDatabase db = null;
	MySQLiteOpenHelper helper = null;

	private TextView tv = null;
	private JSONArray rootObjectArray;
	String inputloginID;
	String inputloginpass;

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
			inputloginID = ID.getText().toString();
			inputloginpass = pass.getText().toString();

				if(inputloginID != null && !inputloginID.isEmpty()
					&& inputloginpass != null && !inputloginpass.isEmpty()){

					if(inputloginID.length() > 5 && inputloginpass.length() > 5){

						//Json呼び出し
						exec_post();

						String strSelect = helper.selectMember(db, inputloginID, inputloginpass);
						if(strSelect != null){


						}else{

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

	 private void exec_post() {

		    Log.d("posttest", "postします");

		    HashMap<String,Object> ret = null;

		    // URL
		    URI url = null;
		    try {
		      url = new URI( "http://54.68.202.192/name.php" );
		      Log.d("posttest", "URLはOK");
		    } catch (URISyntaxException e) {
		      e.printStackTrace();
		      //String code =toString(ret.getStatusLine().getStatusCode());
		      //ret = e.toString();
		    }

		    // POSTパラメータ付きでPOSTリクエストを構築
		    HttpPost request = new HttpPost( url );

		    /*
		    List<NameValuePair> post_params\e = new ArrayList<NameValuePair>();
		    post_params.add(new BasicNameValuePair("post_1", "ユーザID"));
		    post_params.add(new BasicNameValuePair("post_2", "パスワード"));
		    */


		    HashMap<String, Object> hashMap = new HashMap<String, Object>();
		    hashMap.put("menberID", inputloginID);
		    hashMap.put("password",inputloginpass );


		    //オブジェクトクラスHashMap　キーワードと値をペアでセット

		    try {
			    request.setHeader("Content-Type", "application/json; charset=utf-8");
			    //
			    Type mapType = new TypeToken<HashMap<String, Object>>() {}.getType();
			    //HashMapをJSONに変換
			    request.setEntity(new StringEntity(new Gson().toJson(hashMap, mapType)));
			    //同上

			    /*
			    // 送信パラメータのエンコードを指定
		        request.setEntity(new UrlEncodedFormEntity(post_params, "UTF-8"));
		        */

		    } catch (UnsupportedEncodingException e1) {
		        e1.printStackTrace();
		    }

		    // POSTリクエストを実行
		    DefaultHttpClient httpClient = new DefaultHttpClient();
		    try {
		      Log.d("posttest", "POST開始");

		      // POSTを実行して、戻ってきたJSONをHashMapの形にして受け取る
		      ret = httpClient.execute(request, new MyResponseHandler());
		      //
		      String SETusername = (String)ret.get("username");
		      String SETuserID = (String)ret.get("userID");


		      //画面の呼び出しと値の送信
		      Intent intent = null;
		      intent = new Intent(Login.this, Home.class);
		      intent.putExtra("username", SETusername);
		      intent.putExtra("userID", SETuserID); //このuserIDは返り値に指定してないので、セットした時の値を持ってきている。
		      startActivity(intent);

		    } catch (IOException e) {
		      Log.d("posttest", "通信に失敗：" + e.toString());
		    } finally {
		      // shutdownすると通信できなくなる
		      httpClient.getConnectionManager().shutdown();
		    }

		    // 受信結果をUIに表示
	}
	 public class MyResponseHandler implements ResponseHandler<HashMap<String,Object>> {

			@Override
			public HashMap<String,Object> handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
				// TODO 自動生成されたメソッド・スタブ
				//		          Log.d(
				//		            "posttest",
				//		            "レスポンスコード：" + response.getStatusLine().getStatusCode()

				HashMap<String,Object> retMap = new HashMap<String,Object>();

	            // 正常に受信できた場合は200
		          switch (response.getStatusLine().getStatusCode()) {
			          case HttpStatus.SC_OK:
			            Log.d("posttest", "レスポンス取得に成功");

			            try {
			            		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			            		response.getEntity().writeTo(outputStream);
			            		String data;
			            		data = outputStream.toString(); // JSONデータ
			            		rootObjectArray = new JSONArray(data);

			            		JSONObject jsonobject = rootObjectArray.getJSONObject(0);

			            		String username = jsonobject.getString("username");


			            		retMap.put("username", username);

			            } catch (Exception e) {
			            	Log.d("Member_entry. Json取得エラー", "Error");
			            }

			            break;

			          case HttpStatus.SC_NOT_FOUND:
			            Log.d("posttest", "データが存在しない");

			            //リターンするので、retMapに値を入れて、戻り先でswich文でアラーとを出した方がいいのか分らん
			            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Login.this);
						alertDialogBuilder.setMessage("IDまたは\npassが違います。")

						 .setPositiveButton("OK", null);
						AlertDialog alert = alertDialogBuilder.create();
						alert.show();
			            break;

			          default:
			            Log.d("posttest", "通信エラー");
			            break;
		          }
		          return retMap;

			}
	 }
//	 public class Member {
//		    String menberID;
//		    String seibetu;
//		    String birthyear;
//		    String pass;
//		    String username;
//		    //@SerializedName("location")
//		    //String basho;
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}



}
