package cn.lc.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StringUtils {
	
	
	public static String  getCode() {
		return ((int)((Math.random()*9+1)*1000))+"";
	}

	
	public static String getstance(){
		
		SimpleDateFormat gs = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return (int)((Math.random()*9+1)*10)+gs.format(new Date())+(int)((Math.random()*9+1)*1000);
	}
	

    public static boolean IsEmpty(String str){
        
        boolean flag = false;
        if(null==str||"".equals(str)){
            flag = true;
        }
        return flag;
    }
    
    public static String getDateString(Date date){
        SimpleDateFormat gs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return gs.format(date);
        
    }
    
    
    public static String[] getArrayStringFromArrayList(List<Object> paramList ){
        String[] strs = new String[paramList.size()];
        
        for(int i=0;i<paramList.size();i++){
            strs[i]=(String) paramList.get(i);
        }
        return strs;
    }
    /**
     * 格式化时间戳
     * @param date YYYYMMDDHHMMSS
     * @return YYYY-MM-DD HH:MM:SS
     */
	public static String formatDateString(String date){
		String formatDate = "";
		for(int i=0;i<date.length();i++) {
			String str = date.substring(i,i+1);
			switch (i) {
			case 4:
			case 6:
			    formatDate = formatDate + "-" + str;
				break;
			case 8:
				formatDate = formatDate + " " + str;
				break;
			case 10:
			case 12:
				formatDate = formatDate + ":" + str;
				break;
			default:
				formatDate = formatDate + str;
				break;
			}
		}
		return formatDate;
	}
}
