package com.cidca.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SendSms {

	/**
	 * 发送参数：
	 * userid	企业id	企业ID
	 * account	发送用户帐号	用户帐号，由系统管理员
	 * password	发送帐号密码	用户账号对应的密码
	 * mobile	全部被叫号码	发信发送的目的号码.多个号码之间用半角逗号隔开 
	 * content	发送内容	短信的内容，内容需要UTF-8编码
	 * sendTime	定时发送时间	为空表示立即发送，定时发送格式1900-10-24 09:08:10
	 * action	发送任务命令	设置为固定的:send
	 * extno	扩展子号	请先询问配置的通道是否支持扩展子号，如果不支持，请填空。子号只能为数字，且最多10位数。
	 * @throws Exception 
	 */

	public  static String sendMsg (String mobiles,String content) throws Exception{
		String result="";
		String encode_mobiles = URLEncoder.encode(""+mobiles+"","UTF-8");
		String encode_content = URLEncoder.encode("【合作署】"+content+"","UTF-8");
		if (StringUtil.isNotBlank(encode_mobiles) && StringUtil.isNotBlank(encode_content)) {
//			String urls = "http://182.92.7.106:7892/sms.aspx?action=send&userid=101611"
//					+ "&account=101611&password=75b4bf28a7f747ef&mobile="+encode_mobiles+""
//					+ "&content="+encode_content+"&sendTime=&extno=";
//			result = SMS(urls);
//			System.out.println("sendMsg,mobiles=="+mobiles);
		}
		return result;
	}

	static public String SMS(String url) throws Exception {
		String result = "";
		BufferedReader in = null;
		InputStream inputStream = null;
		try{
			URL U = new URL(url);
			URLConnection connection = U.openConnection();
			connection.connect();
			inputStream=connection.getInputStream();
			in = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			while ((line = in.readLine())!= null) {
				result += line;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if (null!=in) {in.close();}
			if (null!=inputStream) {inputStream.close();}
		}
		return result;
	}

	public static void main(String[] args) {
		try {
			String sendMsg = SendSms.sendMsg("18636514606", "测试短信");
			System.out.println(sendMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
