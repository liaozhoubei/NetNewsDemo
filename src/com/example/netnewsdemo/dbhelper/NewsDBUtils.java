package com.example.netnewsdemo.dbhelper;

import java.util.ArrayList;

import com.example.netnewsdemo.bean.NewsBean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 数据库工具类，封装对数据库进行增删改查的方法
 * @author ASUS-H61M
 *
 */
public class NewsDBUtils {
	private NewsDBHelper dbHelper;
	
	public NewsDBUtils (Context context) {
		dbHelper = new NewsDBHelper(context);
	}
	
	// 保存新闻到数据库中
	public void saveNews(ArrayList<NewsBean> arrayList) {
		SQLiteDatabase sqLite = dbHelper.getWritableDatabase();
		for(NewsBean newsBean : arrayList) {
			ContentValues value = new ContentValues();
			value.put("_id", newsBean.getId());
			value.put("time", newsBean.getTime());
			value.put("des", newsBean.getDes());
			value.put("title", newsBean.getTitle());
			value.put("news_url", newsBean.getNews_url());
			value.put("icon_url", newsBean.getIcon_url());
			value.put("comment", newsBean.getComment());
			value.put("type", newsBean.getType());
			sqLite.insert("news", null, value);
		}
		sqLite.close();
	}
	
	
	// 删除数据库数据
	public void deleteNews (){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		db.delete("news", null, null);
		db.close();
	}
	
	// 从数据库中获取存储的行为
	public ArrayList<NewsBean> getNews() {
		ArrayList<NewsBean> arrayList = new ArrayList<NewsBean>();
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query("news", null, null, null, null, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				NewsBean newsBean = new NewsBean();
				newsBean.setId(cursor.getInt(0));
				newsBean.setTime(cursor.getString(1));
				newsBean.setDes(cursor.getString(2));
				newsBean.setTitle(cursor.getString(3));
				newsBean.setNews_url(cursor.getString(4));
				newsBean.setIcon_url(cursor.getString(5));
				newsBean.setComment(cursor.getInt(6));
				newsBean.setType(cursor.getInt(7));
				arrayList.add(newsBean);
			}
		}
		
		return arrayList;
	}
}
