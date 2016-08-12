package cn.lc.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	
	public static Timestamp getCurrentTimestamp(){
		Date date = new Date();
		 String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		 return Timestamp.valueOf(nowTime);
	}
	
}
