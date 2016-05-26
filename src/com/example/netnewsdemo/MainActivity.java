package com.example.netnewsdemo;

import java.util.ArrayList;
import java.util.List;

import com.example.netnewsdemo.adapter.NewsAdapter;
import com.example.netnewsdemo.bean.NewsBean;
import com.example.netnewsdemo.dbhelper.NewsDBUtils;
import com.example.netnewsdemo.utils.NewsUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnItemClickListener {
	private static String result = "傳入包含Json數據的網頁URL";
	private Context mContext;
	private TextView textView;
	NewsUtils newsUtils;
	List<NewsBean> listNewsBean;
	ListView listview;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			listNewsBean = (List<NewsBean>) msg.obj;
			NewsAdapter newsAdapter = new NewsAdapter(MainActivity.this, listNewsBean);
			listview.setAdapter(newsAdapter);

		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = MainActivity.this;
		listview = (ListView) findViewById(R.id.list_news);
		newsUtils = new NewsUtils();
		NewsDBUtils newsDatabase = new NewsDBUtils(mContext);

		// 1.先去数据库中获取缓存的新闻数据展示到listview
		ArrayList<NewsBean> allnews_database = NewsUtils.getDBNews(mContext);

		if (allnews_database != null && allnews_database.size() > 0) {
			// 创建一个adapter设置给listview
			NewsAdapter newsAdapter = new NewsAdapter(mContext, allnews_database);
			listview.setAdapter(newsAdapter);
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				listNewsBean = newsUtils.getNetNews(mContext, result);
				Message message = Message.obtain();
				message.obj = listNewsBean;
				mHandler.sendMessage(message);
			}
		}).start();
		
		listview.setOnItemClickListener(this);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		NewsBean news = (NewsBean) parent.getItemAtPosition(position);
		String url = news.getNews_url();
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(url));
		startActivity(intent);

	}


}
