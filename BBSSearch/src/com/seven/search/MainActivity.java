package com.seven.search;

import java.util.List;

import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private EditText url;
	private EditText page;
	private EditText tail;
	private EditText key;
	private Button btn;
	private ProgressDialog dia;
	private EditText et_max;
	private EditText et_result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		init();
		btn.setOnClickListener(this);
	}

	private void init() {
		url = (EditText) findViewById(R.id.editText1);
		page = (EditText) findViewById(R.id.page_url);
		key = (EditText) findViewById(R.id.keyword);
		tail = (EditText) findViewById(R.id.tail);
		btn = (Button) findViewById(R.id.btn_ok);
		et_max =(EditText) findViewById(R.id.max);
		et_result = (EditText) findViewById(R.id.et_result);

		// 定义进度条
		dia = new ProgressDialog(this);
		dia.setTitle("Information");
		dia.setMessage("Downloading....");
		dia.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		// 设置点击进度条外部，不响应；
		dia.setCancelable(false);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "on click", Toast.LENGTH_LONG).show();
		new MyTask().execute();
	}

	public class MyTask extends AsyncTask<String, Integer ,List<Integer>> {

		// 任务执行之前的准备工作。
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dia.show();
		}

		// 主要完成耗时操作
		@Override
		protected List<Integer> doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			WebSearch search = new WebSearch(url.getText().toString(), page.getText().toString(), tail.getText().toString(), key.getText().toString());
			int maxInt = Integer.parseInt(et_max.getText().toString());
			int value;
			for(int i=1;i<=maxInt;i++) {
				search.start(i);
				
				value = ((i*100/maxInt));
				//调用update函数，更新进度  
				publishProgress(value);
			}
			return search.getList();
		}

		// 更新进度条
		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			dia.setProgress(values[0]);
		}

		// 主要完成耗时操作
		@Override
		protected void onPostExecute(List<Integer> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dia.dismiss();
			
			StringBuilder builder = new StringBuilder("find "+result.size()+"\n");
			for(Integer i:result) {
				builder.append(i+" ");
			}
			et_result.setText(builder.toString());
		}
	}
}
