package com.example.netnewsdemo.utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.netnewsdemo.bean.NewsBean;
import com.example.netnewsdemo.dbhelper.NewsDBUtils;

import android.content.Context;
import android.util.Log;

public class NewsUtils {
	
	// 从网络中获取Json数据，解析Json数据
	public static ArrayList<NewsBean> getNetNews(Context context, String urlString) {
		ArrayList<NewsBean> arraylistNews = new ArrayList<NewsBean>();
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(20 * 1000);
			int responseCode = conn.getResponseCode();
			
			if (responseCode == 200) {
				// 获取请求到的流信息
				InputStream is = conn.getInputStream();
				String result = StreamUtils.convertStream(is);
				JSONObject root_json = new JSONObject(result);
				JSONArray jsonArray  = root_json.getJSONArray("newss");
				for (int i = 0; i < jsonArray .length(); i ++ ){
					JSONObject news_json = jsonArray.getJSONObject(i);
					NewsBean newsBean = new NewsBean();
					newsBean.setId(news_json.getInt("id"));
					newsBean.setTime(news_json.getString("time"));
					newsBean.setDes(news_json.getString("des"));
					newsBean.setTitle(news_json.getString("title"));
					newsBean.setNews_url(news_json.getString("news_url"));
					newsBean.setIcon_url(news_json.getString("icon_url"));
					Log.i("NewsUtils", newsBean.getIcon_url());
					newsBean.setComment(news_json.getInt("comment"));
					newsBean.setType(news_json.getInt("type"));
					arraylistNews.add(newsBean);
					
				}
				
				// 如果获取到网络上的数据，就删除之前获取的新闻数据，保存新的新闻数据
				new NewsDBUtils(context).deleteNews();
				new NewsDBUtils(context).saveNews(arraylistNews);
				
				is.close();
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return arraylistNews;
	}
	
	// 返回数据库缓存到的数据
	public static ArrayList<NewsBean> getDBNews(Context context){
		
		return new NewsDBUtils(context).getNews();
	}

	

}
