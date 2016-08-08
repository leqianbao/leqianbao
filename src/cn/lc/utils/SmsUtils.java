package cn.lc.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class SmsUtils {
	//智能匹配模版发送接口的http地址
		private static String URI_SEND_SMS = "http://yunpian.com/v1/sms/send.json";
		//private static String URI_SEND_SMS = "https://sms.yunpian.com/v2/sms/single_send.json";;

		//编码格式。发送编码格式统一用UTF-8
		private static String ENCODING = "UTF-8";

	    public static String sendSms(String code, String mobile) {
	    	 try {
				 String text = "【乐钱宝】您的验证码是"+code+",[请勿向任何人提供您收到的短信验证码]验证码有效时间3分钟，请及时使用。";

				Map<String, String> params = new HashMap<String, String>();
				params.put("apikey", "b6d1d8c25b379838f5946ab4da59ade3");
				params.put("text", text);
				params.put("mobile", mobile);
				return post(URI_SEND_SMS, params);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
	    	 return null;
	    }

	    
	    /**
	    * 基于HttpClient 4.3的通用POST方法
	    *
	    * @param url       提交的URL
	    * @param paramsMap 提交<参数，值>Map
	    * @return 提交响应
	    */

	    public static String post(String url, Map<String, String> paramsMap) {
	        CloseableHttpClient client = HttpClients.createDefault();
	        String responseText = "";
	        CloseableHttpResponse response = null;
	        try {
	            HttpPost method = new HttpPost(url);
	            if (paramsMap != null) {
	                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
	                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
	                    NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
	                    paramList.add(pair);
	                }
	                method.setEntity(new UrlEncodedFormEntity(paramList, ENCODING));
	            }
	            response = client.execute(method);
	            HttpEntity entity = response.getEntity();
	            if (entity != null) {
	                responseText = EntityUtils.toString(entity);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                response.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	            return responseText;
	        }

}
