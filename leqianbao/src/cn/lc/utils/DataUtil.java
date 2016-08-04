package cn.lc.utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;

public class DataUtil {
	
	/**
	 * 读取数据
	 * @throws IOException 
	 */
	
	public static String readDateFromRequest(InputStream in) throws IOException{
		
		byte[] bytes = new byte[1024*100];//定义一个100k的字节数组
		int len = -1;//每次真实读取的长度
		StringBuffer buf = new StringBuffer();
		while((len=in.read(bytes))!=-1){
			buf.append(new String(bytes,0,len));
		}
		in.close();
		return java.net.URLDecoder.decode(buf.toString(), "utf-8");
	}
	
	
	/**
	 * 添加请求体
	 */
	
	public static String addReqBody(Map<String, String> map){
		 String json=JSON.toJSONString(map); 
         StringBuffer sbf = new StringBuffer();
         sbf.append("{\"REP_BODY\":");
         sbf.append(json);
         sbf.append("}");
         return sbf.toString();
	}
	
	/**
     *加密算法 
     */
    public static String getpass(String p){
        StringBuffer s = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(p.getBytes());
            for(byte b : md.digest()){
                s.append(String.format("%X",b));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String t = s.toString();
        t = t.substring(0,7) + t.substring(4,15) + t.substring(13,23) + t.substring(8,12); 
        return t; 
    }
    
    /**
     * 校验字符串是否是大于0的数字
     * @param string
     * @return
     */
    public static boolean isNum(String string){
        Pattern pattern = Pattern.compile("[1-9]{1}\\d*");
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }
	

}
