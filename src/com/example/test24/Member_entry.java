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
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.test24.R.id;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Member_entry extends Activity implements View.OnClickListener{

	SQLiteDatabase db = null;
	MySQLiteOpenHelper helper = null;
	String ddd = null;
	String eee = null;
	private Button btnWeb = null;
	private TextView tv = null;
	private JSONArray rootObjectArray;

	EditText aaa;
	EditText bbb;
	EditText ccc;
//
//	private InputFilter[] filters = { new MyFilter() };
//
//    class MyFilter implements InputFilter {
//        public CharSequence filter(CharSequence source, int start, int end,
//                                   Spanned dest, int dstart, int dend) {
//
//            if( source.toString().matches("^[a-zA-Z0-9]+$") ){
//                return source;
//            }else{
//                return "";
//            }
//        }
//    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		setContentView(R.layout.member_entry);
//		EditText userID = (EditText)findViewById(R.id.userID);
//        userID.setFilters(filters);

//		InputFilter inputFilter = new InputFilter() {
//		    @Override
//		    public CharSequence filter(CharSequence source, int start, int end,
//		            Spanned dest, int dstart, int dend) {
//		    	if(source.toString().matches("[a-zA-Z0-9]")){
//		            return source;
//		        } else {
//		            return "";
//		        }
//		    }
//		};
//		// フィルターの配列を作成
//					InputFilter[] filters = new InputFilter[] { inputFilter };
//					// フィルターの配列をセット
//					aaa.setFilters(filters);
//					bbb.setFilters(filters);

		//queue
        // RequestQueue queue = Volley.newRequestQueue(this);

        /*
        //url
        String url = "http://54.63.202.192/test.php";

        //request生成
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        //JSONの処理
                        try{

                            //１階層目は普通に
                            //String status = jsonObject.getString("status");
                            //出力
                           // Log.v("tama", "status=" + status);

                            //2階層目（配列）
                            JSONArray menber = jsonObject.getJSONArray("menber");
                            //ループ
                            for(int i=0;i<members.length();i++){

                                //JSONObjectとして１つ１つ取得
                                JSONObject menberID = menberID.getJSONObject(i);
                                    //明日を取得（ListViewとかに追加）
                                String menberID = member.getString("menberID");
                                String seibetu = member.getString("seibetu");
                                String birthyear = menberID.String("birthyear");
                                //出力
                                Log.v("seibetu" + seibetu);
                                Log.v("birthyear=" + birthyear);
                            }

                        }catch(Exception e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){

                    }
                }
        );

		*/


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
			aaa = (EditText)findViewById(R.id.userID);
			bbb = (EditText)findViewById(R.id.password);
			ccc = (EditText)findViewById(R.id.user_name);

			String inputID = aaa.getText().toString();
			String inputpass = bbb.getText().toString();
			String inputname = ccc.getText().toString();
			String inputyear = ddd;
			String inputseibetu = eee;



				if(inputID != null && !inputID.isEmpty()
				   && inputpass != null && !inputpass.isEmpty()
				   && inputname != null && !inputname.isEmpty()){


					if(inputID.length() > 5 && inputpass.length() > 5){

//						exec_post();
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
//	 private void exec_post() {
//
//		    Log.d("posttest", "postします");
//
//		    HashMap<String,Object> ret = null;
//
//		    // URL
//		    URI url = null;
//		    try {
//		      url = new URI( "http://54.68.202.192/test.php" );
//		      Log.d("posttest", "URLはOK");
//		    } catch (URISyntaxException e) {
//		      e.printStackTrace();
//		      //String code =toString(ret.getStatusLine().getStatusCode());
//		      //ret = e.toString();
//		    }
//
//		    // POSTパラメータ付きでPOSTリクエストを構築
//		    HttpPost request = new HttpPost( url );
//
//		    /*
//		    List<NameValuePair> post_params\e = new ArrayList<NameValuePair>();
//		    post_params.add(new BasicNameValuePair("post_1", "ユーザID"));
//		    post_params.add(new BasicNameValuePair("post_2", "パスワード"));
//		    */
//
//
//		    HashMap<String, Object> hashMap = new HashMap<String, Object>();
//		    hashMap.put("userID", "1201769");
//		    //オブジェクトクラスHashMap　キーワードと値をペアでセット
//
//		    try {
//			    request.setHeader("Content-Type", "application/json; charset=utf-8");
//			    //
//			    Type mapType = new TypeToken<HashMap<String, Object>>() {}.getType();
//			    //HashMapをJSONに変換
//			    request.setEntity(new StringEntity(new Gson().toJson(hashMap, mapType)));
//			    //同上
//
//			    /*
//			    // 送信パラメータのエンコードを指定
//		        request.setEntity(new UrlEncodedFormEntity(post_params, "UTF-8"));
//		        */
//
//		    } catch (UnsupportedEncodingException e1) {
//		        e1.printStackTrace();
//		    }
//
//		    // POSTリクエストを実行
//		    DefaultHttpClient httpClient = new DefaultHttpClient();
//		    try {
//		      Log.d("posttest", "POST開始");
//
//		      // POSTを実行して、戻ってきたJSONをHashMapの形にして受け取る
//		      ret = httpClient.execute(request, new MyResponseHandler());
//		      //
//		      String menberID = (String)ret.get("menberID");
//		      tv.setText( menberID );
//
//		    } catch (IOException e) {
//		      Log.d("posttest", "通信に失敗：" + e.toString());
//		    } finally {
//		      // shutdownすると通信できなくなる
//		      httpClient.getConnectionManager().shutdown();
//		    }
//
//		    // 受信結果をUIに表示
//	}
//	 public class MyResponseHandler implements ResponseHandler<HashMap<String,Object>> {
//
//			@Override
//			public HashMap<String,Object> handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
//				// TODO 自動生成されたメソッド・スタブ
//				//		          Log.d(
//				//		            "posttest",
//				//		            "レスポンスコード：" + response.getStatusLine().getStatusCode()
//
//				HashMap<String,Object> retMap = new HashMap<String,Object>();
//
//	            // 正常に受信できた場合は200
//		          switch (response.getStatusLine().getStatusCode()) {
//			          case HttpStatus.SC_OK:
//			            Log.d("posttest", "レスポンス取得に成功");
//
//			            try {
//			            		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//			            		response.getEntity().writeTo(outputStream);
//			            		String data;
//			            		data = outputStream.toString(); // JSONデータ
//			            		rootObjectArray = new JSONArray(data);
//
//			            		JSONObject jsonobject = rootObjectArray.getJSONObject(0);
//
//			            		String menberID = jsonobject.getString("menberID");
//
//			            		retMap.put("menberID", menberID);
//
//			            } catch (Exception e) {
//			            	Log.d("Member_entry. Json取得エラー", "Error");
//			            }
//
//			            break;
//
//			          case HttpStatus.SC_NOT_FOUND:
//			            Log.d("posttest", "データが存在しない");
//			            break;
//
//			          default:
//			            Log.d("posttest", "通信エラー");
//			            break;
//		          }
//		          return retMap;
//
//			}
//	 }
////	 public class Member {
////		    String menberID;
////		    String seibetu;
////		    String birthyear;
////		    String pass;
////		    String username;
////		    //@SerializedName("location")
////		    //String basho;
////	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
}