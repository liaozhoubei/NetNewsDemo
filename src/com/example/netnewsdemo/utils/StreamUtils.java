package com.example.netnewsdemo.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 转换流工具类，将Inputstream流数据输出为String值
 * @author ASUS-H61M
 *
 */
public class StreamUtils {
	public static String convertStream(InputStream is) {
		String result = "";
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] bt = new byte[1024];
		int len = 0;
		try {
			while ((len = is.read(bt)) != -1) {
				bos.write(bt, 0, len);
				bos.flush();
			}
			result = new String(bos.toByteArray(), "utf-8");
			result = bos.toString();
			bos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
		
	}
}
