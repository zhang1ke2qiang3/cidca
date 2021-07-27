package com.cidca.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import com.cidca.common.Constants;
import com.cidca.entity.TBaseData;


/**
 * String 类型字符串的处理工具，包括部分验证、格式化及编码
 * 
 * @author chen
 */
public class StringUtil {

	public static String checkNULL(String str) throws Exception {
		if (StringUtils.isEmpty(str)) {
			return "";
		}else if("null".equals(str)){
			return "";
		}else{
			return str;
		}
	}

	public static String decodeParms(String str) throws UnsupportedEncodingException {
		if (isBlank(str)) {
			return "";
		}
		return URLDecoder.decode(StringUtil.decodeBase64(str), "UTF-8");
	}

	public static String decodeParms(String str, String enc) throws UnsupportedEncodingException {
		if (isBlank(str)) {
			return "";
		}
		return URLDecoder.decode(StringUtil.decodeBase64(str), enc);
	}

	public static String getRandomHex() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**得到一个大写32位的UUID*/
	public static String getUUIDRandomHexToUpperCase() {
		return (UUID.randomUUID().toString().replaceAll("-", "")).toUpperCase();
	}

	public static boolean isNullJson(String str) {
		boolean isNullJson = false;
		if ((str == null) || ("null".equalsIgnoreCase(str)) || ("[]".equalsIgnoreCase(str)) || ("{}".equalsIgnoreCase(str)) || ("[{}]".equalsIgnoreCase(str))) {
			isNullJson = true;
		}
		return isNullJson;
	}

	public static boolean isNotNullJson(String str) {
		boolean isNotNullJson = true;
		if ((str == null) || ("null".equalsIgnoreCase(str)) || ("[]".equalsIgnoreCase(str)) || ("{}".equalsIgnoreCase(str)) || ("[{}]".equalsIgnoreCase(str))) {
			isNotNullJson = false;
		}
		return isNotNullJson;
	}

	public static boolean isNotNull(Object obj) {
		boolean isNotNull = true;
		if (obj == null) {
			isNotNull = false;
		}
		return isNotNull;
	}

	public static boolean isNull(Object obj) {
		boolean isNull = false;
		if (obj == null) {
			isNull = true;
		}
		return isNull;
	}

	public static boolean isNotNull(String str) {
		boolean isNotNull = true;
		if ((str == null) || ("null".equalsIgnoreCase(str))) {
			isNotNull = false;
		}
		return isNotNull;
	}

	public static boolean isNull(String str) {
		boolean isNull = true;
		if ((str != null) && (!"null".equalsIgnoreCase(str))) {
			isNull = false;
		}
		return isNull;
	}

	public static boolean isNotBlank(String str) {
		boolean isNotBlank = true;
		if ((str == null) || ("".equals(str.trim())) || ("null".equalsIgnoreCase(str))) {
			isNotBlank = false;
		}
		return isNotBlank;
	}

	public static boolean isBlank(String str) {
		boolean isBlank = true;
		if ((str != null) && (!"".equals(str.trim())) && (!"null".equalsIgnoreCase(str))) {
			isBlank = false;
		}
		return isBlank;
	}

	public static boolean isNotBlank(Object[] objs) {
		boolean isNotBlank = true;
		if ((objs == null) || (objs.length <= 0)) {
			isNotBlank = false;
		}
		return isNotBlank;
	}

	public static boolean isBlank(Object[] objs) {
		boolean isBlank = true;
		if ((objs != null) && (objs.length > 0)) {
			isBlank = false;
		}
		return isBlank;
	}

	public static String null2Str(String str) {
		if ((str == null) || ("".equals(str.trim())) || ("null".equalsIgnoreCase(str))) {
			str = "";
		}
		return str;
	}

	public static String randomUUID() {
		return UUID.randomUUID().toString();
	}

	public static String setLength(String instr, int length) {
		int realLen = instr.getBytes().length;
		if (realLen < length) {
			for (int i = realLen; i < length; i++) {
				instr = instr + " ";
			}
		}
		return instr;
	}

	public static String setLength(String instr, int length, char c) {
		int realLen = instr.getBytes().length;
		if (realLen < length) {
			for (int i = realLen; i < length; i++) {
				instr = instr + c;
			}
		}
		return instr;
	}

	public static String getShortString(String subject, int num) {
		int n = 0;
		int i = 0;
		int j = 0;
		int byteNum = num * 2;

		boolean flag = true;
		if (subject == null) {
			return " ";
		}
		if (subject.endsWith(".")) {
			subject = subject.substring(0, subject.length() - 1);
		}

		for (i = 0; i < subject.length(); i++) {
			if (subject.charAt(i) < '') n++;
			else {
				n += 2;
			}
			if ((n > byteNum) && (flag)) {
				j = i;
				flag = false;
			}
			if (n >= byteNum + 2) {
				break;
			}
		}
		if ((n >= byteNum + 2) && (i != subject.length() - 1)) {
			subject = subject.substring(0, j);
			subject = subject + "…";
		}

		return subject;
	}

	public static String covertCode(String s, String code1, String code2) {
		String result = null;
		try {
			if ((s == null) || (s.trim().equals(""))) result = null;
			else {
				result = new String(s.getBytes(code1), code2);
			}
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		}
		return result;
	}

	public static String replaceDBSpecialChar(String strReplace) {
		String strRet = strReplace;
		if (strRet != null) strRet = strRet.replaceAll("'", "''");
		else {
			strRet = "";
		}
		return strRet;
	}

	public static String encodeBase64(String str) {
		if (isBlank(str)) {
			return "";
		}
		return encodeBase64(str, false);
	}

	public static String encodeBase64(String str, String charset) {
		if (isBlank(str)) {
			return "";
		}
		return encodeBase64(str, charset, false);
	}

	public static String encodeBase64(String str, boolean isChunked) {
		if (isBlank(str)) {
			return "";
		}
		byte[] encodedArray = (byte[]) null;
		try {
			encodedArray = Base64.encodeBase64(str.getBytes("UTF-8"), isChunked);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String result = "";
		try {
			result = new String(encodedArray);
		} catch (NullPointerException e) {
			result = "";
		}
		return result;
	}

	public static String encodeBase64(String str, String charset, boolean isChunked) {
		if (isBlank(str)) {
			return "";
		}
		byte[] encodedArray = (byte[]) null;
		try {
			encodedArray = Base64.encodeBase64(str.getBytes(charset), isChunked);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String result = "";
		try {
			result = new String(encodedArray);
		} catch (NullPointerException e) {
			result = "";
		}
		return result;
	}

	public static String decodeBase64(String base64Data) {
		if (isBlank(base64Data)) {
			return "";
		}
		return decodeBase64(base64Data, "UTF-8");
	}

	public static String decodeBase64(String base64Data, String charset) {
		if (isBlank(base64Data)) {
			return "";
		}
		return decodeBase64(base64Data.getBytes(), charset);
	}

	public static String decodeBase64(byte[] base64Data) {
		if (base64Data == null || base64Data.length <= 0) {
			return "";
		}
		return decodeBase64(base64Data, "UTF-8");
	}

	public static String decodeBase64(byte[] base64Data, String charset) {
		if (base64Data == null || base64Data.length <= 0) {
			return "";
		}
		byte[] toBeDecoded = (byte[]) null;
		toBeDecoded = Base64.decodeBase64(base64Data);
		String result = "";
		try {
			result = new String(toBeDecoded, charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String md5(String data) {
		if (isBlank(data)) {
			return "";
		}
		return md5(data, "UTF-8");
	}

	public static String md5(String data, String charset) {
		if (isBlank(data)) {
			return "";
		}
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(data.getBytes(charset));
			byte[] s = m.digest();
			StringBuffer result = new StringBuffer();
			for (int i = 0; i < s.length; i++) {
				result.append(Integer.toHexString(0xFF & s[i] | 0xFFFFFF00).substring(6));
			}
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String escape(String src) {
		if (isBlank(src)) {
			return "";
		}
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (int i = 0; i < src.length(); i++) {
			char j = src.charAt(i);
			if ((Character.isDigit(j)) || (Character.isLowerCase(j)) || (Character.isUpperCase(j))) {
				tmp.append(j);
			} else if (j < 'Ā') {
				tmp.append("%");
				if (j < '\020') tmp.append(Constants.ZERO);
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

	public static String unescape(String src) {
		if (isBlank(src)) {
			return "";
		}
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0;
		int pos = 0;

		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					char ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					char ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else if (pos == -1) {
				tmp.append(src.substring(lastPos));
				lastPos = src.length();
			} else {
				tmp.append(src.substring(lastPos, pos));
				lastPos = pos;
			}
		}

		return tmp.toString();
	}

	public static boolean isDate(String str_input, String rDateFormat) {
		if (!isNull(str_input)) {
			SimpleDateFormat formatter = new SimpleDateFormat(rDateFormat);
			formatter.setLenient(false);
			try {
				String s = formatter.format(formatter.parse(str_input));
				return str_input.equals(s);
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	public static boolean isDate(String str_input) {
		boolean isOk = false;
		if (isNull(str_input)) {
			return isOk;
		}
		if (!isOk) {
			isOk = isDate(str_input, "yyyyMMdd");
		}
		if (!isOk) {
			isOk = isDate(str_input, "yyyy/MM/dd");
		}
		if (!isOk) {
			isOk = isDate(str_input, "yyyy-MM-dd");
		}
		if (!isOk) {
			isOk = isDate(str_input, "yyyy年MM月dd日");
		}
		if (!isOk) {
			isOk = isDate(str_input, "yyyyMMddHHmm");
		}
		if (!isOk) {
			isOk = isDate(str_input, "yyyy/MM/dd HH:mm");
		}
		if (!isOk) {
			isOk = isDate(str_input, "yyyy-MM-dd HH:mm");
		}
		if (!isOk) {
			isOk = isDate(str_input, "yyyy年MM月dd日 HH时mm分");
		}
		if (!isOk) {
			isOk = isDate(str_input, "yyyyMMddHHmmss");
		}
		if (!isOk) {
			isOk = isDate(str_input, "yyyy/MM/dd HH:mm:ss");
		}
		if (!isOk) {
			isOk = isDate(str_input, "yyyy-MM-dd HH:mm:ss");
		}
		if (!isOk) {
			isOk = isDate(str_input, "yyyy年MM月dd日 HH时mm分ss秒");
		}
		return isOk;
	}

	private static boolean match(String regex, String str_input) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str_input);
		return matcher.matches();
	}

	public static boolean isEmail(String str_input) {
		String regex = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		return match(regex, str_input);
	}

	public static boolean isPhone(String str_input) {
		String regex = "^(([0-9]{11})|^(([0-9]{7,8})|([0-9]{4}|[0-9]{3})-([0-9]{7,8})|([0-9]{4}|[0-9]{3})-([0-9]{7,8})-([0-9]{4}|[0-9]{3}|[0-9]{2}|[0-9]{1})|([0-9]{7,8})-([0-9]{4}|[0-9]{3}|[0-9]{2}|[0-9]{1}))$)";
		return match(regex, str_input);
	}

	public static boolean isMobilePhone(String str_input) {
		String regex = "^1[3,8,5][0-9]{9}";
		return match(regex, str_input);
	}

	public static boolean isNumeric(String str_input) {
		if (isNull(str_input)) {
			return false;
		}

		return str_input.matches("\\d*");
	}

	public static boolean isURL(String str_input) {
		String regex = "^(http|ftp|file)://.*";
		return match(regex, str_input);
	}

	public static boolean isIDCard(String str_input) {
		if (str_input.length() == 15) {
			str_input = uptoeighteen(str_input);
		}

		if (str_input.length() != 18) {
			return false;
		}

		String verify = str_input.substring(17, 18);
		verify = verify.toUpperCase();

		if ((verify.equals(getVerify(str_input))) && (verifyIDCardYear(str_input))) {
			return verifyIDCardArea(str_input);
		}

		return false;
	}

	private static String uptoeighteen(String fifteencardid) {
		String eightcardid = fifteencardid.substring(0, 6);
		eightcardid = eightcardid + "19";
		eightcardid = eightcardid + fifteencardid.substring(6, 15);
		eightcardid = eightcardid + getVerify(eightcardid);
		return eightcardid;
	}

	private static String getVerify(String eightcardid) {
		int[] wi = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 };
		int[] vi = { 1, 0, 88, 9, 8, 7, 6, 5, 4, 3, 2 };
		int[] ai = new int[18];

		int remaining = 0;
		if (eightcardid.length() == 18) {
			eightcardid = eightcardid.substring(0, 17);
		}
		if (eightcardid.length() == 17) {
			int sum = 0;
			for (int i = 0; i < 17; i++) {
				String k = eightcardid.substring(i, i + 1);
				ai[i] = Integer.parseInt(k);
			}

			for (int i = 0; i < 17; i++) {
				sum += wi[i] * ai[i];
			}
			remaining = sum % 11;
		}
		return remaining == 2 ? "X" : String.valueOf(vi[remaining]);
	}

	private static boolean verifyIDCardYear(String eightcardid) {
		String cardYear = eightcardid.substring(6, 14);
		return isDate(cardYear, "yyyyMMdd");
	}

	private static boolean verifyIDCardArea(String eightcardid) {
		String[] aCity = { Constants.ZERO, Constants.ZERO, Constants.ZERO, Constants.ZERO, Constants.ZERO, Constants.ZERO, Constants.ZERO, Constants.ZERO, Constants.ZERO, Constants.ZERO, Constants.ZERO, "北京", "天津", "河北", "山西", "内蒙古", Constants.ZERO, Constants.ZERO, Constants.ZERO, Constants.ZERO, Constants.ZERO, "辽宁", "吉林", "黑龙江", Constants.ZERO, Constants.ZERO, Constants.ZERO, Constants.ZERO, Constants.ZERO, Constants.ZERO, Constants.ZERO, "上海",
				"江苏", "浙江", "安微", "福建", "江西", "山东", Constants.ZERO, Constants.ZERO, Constants.ZERO, "河南", "湖北", "湖南", "广东", "广西", "海南", Constants.ZERO, Constants.ZERO, Constants.ZERO, "重庆", "四川", "贵州", "云南", "西藏", Constants.ZERO, Constants.ZERO, Constants.ZERO, Constants.ZERO, Constants.ZERO, Constants.ZERO, "陕西", "甘肃", "青海",
				"宁夏", "***", Constants.ZERO, Constants.ZERO, Constants.ZERO, Constants.ZERO, Constants.ZERO, "台湾", Constants.ZERO, Constants.ZERO, Constants.ZERO, Constants.ZERO, Constants.ZERO, Constants.ZERO, Constants.ZERO, Constants.ZERO, Constants.ZERO, "香港", "澳门", Constants.ZERO, Constants.ZERO, Constants.ZERO, Constants.ZERO, Constants.ZERO, Constants.ZERO, Constants.ZERO, Constants.ZERO, "国外" };

		return aCity[Integer.parseInt(eightcardid.substring(0, 2))] != null;
	}

	public static boolean isIP(String str_input) {
		try {
			String number = str_input.substring(0, str_input.indexOf('.'));
			if (Integer.parseInt(number) > 255) return false;
			str_input = str_input.substring(str_input.indexOf('.') + 1);
			number = str_input.substring(0, str_input.indexOf('.'));
			if (Integer.parseInt(number) > 255) return false;
			str_input = str_input.substring(str_input.indexOf('.') + 1);
			number = str_input.substring(0, str_input.indexOf('.'));
			if (Integer.parseInt(number) > 255) return false;
			number = str_input.substring(str_input.indexOf('.') + 1);

			return Integer.parseInt(number) <= 255;
		} catch (Exception e) {
		}

		return false;
	}

	// 首字母转小写
	public static String toLowerCaseFirstChar(String s) {
		if (Character.isLowerCase(s.charAt(0))) return s;
		else return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
	}

	// 首字母转大写
	public static String toUpperCaseFirstChar(String s) {
		if (Character.isUpperCase(s.charAt(0))) return s;
		else return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
	}

	public static String getStrIsNotNull(String str, String defaultStr) {
		String retStr = "";
		if ((str != null) && (!"".equals(str.trim())) && (!"null".equalsIgnoreCase(str))) {
			retStr = str;
		} else {
			retStr = defaultStr;
		}
		return retStr;
	}

	public static String escapeSQLLike(String string) {
		String str = string.replace("_", "/_");
		str = string.replace("%",    "/%");
		str = string.replace("?", "_");
		str = string.replace("*", "%");
		return str;
	}

	/** 
	 * 使用java正则表达式去掉多余的.与0 
	 */  
	public static String subZeroAndDot(String s){  
		if(s.indexOf(".") > 0){  
			s = s.replaceAll("0+?$", "");//去掉多余的0  
			s = s.replaceAll("[.]$", "");//如最后一位是.则去掉  
		}  
		return s;  
	} 

	public static int generateNumber8() {
		StringBuilder str=new StringBuilder();//定义变长字符串
		Random random=new Random();
		//随机生成数字，并添加到字符串
		for(int i=0;i<8;i++){
			str.append(random.nextInt(9)+1);
		}
		//将字符串转换为数字并输出
		int num=Integer.parseInt(str.toString());
		return num;
	}

	/**
	 * 使用逗号将集合拼接成字符串
	 * [即不会第一位有逗号，也防止最后一位拼接逗号！]
	 * @param dataList
	 * @return
	 * @author zonglei
	 */
	public static String dataToString(List<String> dataList) {
		if (dataList!=null && dataList.size()>0) {
			StringBuilder sb = new StringBuilder();
			for (String string : dataList) {
				if (sb.length() > 0) {//该步即不会第一位有逗号，也防止最后一位拼接逗号！
					sb.append(",");
				}
				sb.append(string);
			}
			return sb.toString();
		}else{
			return "";
		}
	}
	/**
	 * 使用逗号将集合拼接成字符串
	 * [即不会第一位有逗号，也防止最后一位拼接逗号！]
	 * @param dataList
	 * @return
	 * @author zonglei
	 */
	public static String dataToString1(String[] dataArray) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < dataArray.length; i++) {
			if (sb.length() > 0) {//该步即不会第一位有逗号，也防止最后一位拼接逗号！
				sb.append(",");
			}
			sb.append(dataArray[i]);
		}
		return sb.toString();
	}

	/**
	 * 字符串比对工具：比对两个字符串是否相同
	 * @author fjw
	 */
	public static boolean isChangeStr(String str1,String str2) {
		boolean flag=false;
		if (StringUtils.isNotEmpty(str1)&&StringUtils.isNotEmpty(str2)){
			if(!str1.equals(str2)){
				flag=true;
			}
		}
		if (StringUtils.isEmpty(str1)&&StringUtils.isNotEmpty(str2)){
			flag=true;
		}
		if (StringUtils.isNotEmpty(str1)&&StringUtils.isEmpty(str2)){
			flag=true;
		}
		return flag;
	}

	/**
	 * 字符串比对工具：比对两个字符串是否相同
	 * @author fjw
	 */
	public static boolean isChangeStr2(String str1,String str2) {
		boolean flag=false;
		if(StringUtils.isEmpty(str1) || Constants.ZERO.equals(str1)){
			str1="空";
		}
		if(StringUtils.isEmpty(str2) || Constants.ZERO.equals(str2)){
			str2="空";
		}
		if(Constants.ONE.equals(str1)){
			str1="是";
		}
		if(Constants.ONE.equals(str2)){
			str2="是";
		}
		if (!str1.equals(str2)){
			flag=true;
		} 
		return flag;
	}


	/**
	 * 对象比对工具：比对两个对象是否相同
	 * @author fjw
	 */
	public static boolean isChangeBaseData(TBaseData obj1,TBaseData obj2) {
		boolean flag=false;
		if(null!=obj1&&null!=obj2){
			if(obj1.getCode()!=obj2.getCode()){ 
				flag=true;
			}
		}
		if (null==obj1&&null!=obj2) {
			flag=true;
		}
		if (null!=obj1&&null==obj2) {
			flag=true;
		}
		return flag;
	}

	/**
	 * 对象比对工具：比对两个对象是否相同
	 * @author fjw
	 */
	public static boolean isChangeBaseData(String str1,TBaseData obj2) {
		boolean flag=false;
		if(StringUtils.isNotEmpty(str1)&&null!=obj2){
			if(!str1.equals(Integer.toString(obj2.getCode()))){ 
				flag=true;
			}
		}
		if(StringUtils.isEmpty(str1)&&null!=obj2) {
			flag=true;
		}
		if (StringUtils.isNotEmpty(str1)&&null==obj2) {
			flag=true;
		}
		return flag;
	}


	/**
	 * 日期比对工具：比对两个日期是否相同
	 * @author fjw
	 */
	public static boolean isChangeDate(Date date1,Date date2) {
		boolean flag=false;
		if(null!=date1&&null!=date2){
			if(date1.compareTo(date2)!=0){
				flag=true;
			}
		}
		if (null==date1&&null!=date2) {
			flag=true;
		}
		if (null!=date1&&null==date2) {
			flag=true;
		}
		return flag;
	}

	/**
	 * 日期比对工具：比对两个日期是否相同
	 * @author fjw
	 */
	public static boolean isChangeDate(String str1,Date date2) {
		boolean flag=false;
		if(StringUtils.isNotEmpty(str1)&&null!=date2){
			if(!str1.equals(DateTools.dateToStr(date2))){
				flag=true;
			}
		}
		if (StringUtils.isEmpty(str1)&&null!=date2) {
			flag=true;
		}
		if (StringUtils.isNotEmpty(str1)&&null==date2) {
			flag=true;
		}
		return flag;
	}

	/**
	 * 字符串比对工具：并获取变更信息
	 * @author fjw
	 */
	public static String getChangeStr(String str1,String str2,String name) {
		String msg="";
		if (StringUtils.isNotEmpty(str1)&&StringUtils.isNotEmpty(str2)){
			if(!str1.equals(str2)){
				msg=name+"：由 "+str1+" <font color='blue'>变更为</font> "+str2;
			}
		}

		if (StringUtils.isEmpty(str1)&&StringUtils.isNotEmpty(str2)){
			msg=name+"：由 空 <font color='blue'>变更为</font> "+str2;
		}
		if (StringUtils.isNotEmpty(str1)&&StringUtils.isEmpty(str2)){
			msg=name+"：由 "+str1+" <font color='blue'>变更为</font> 空";
		}
		return msg;
	}
	/**
	 * 字符串比对工具：并获取变更信息
	 * @author fjw
	 */
	public static String getChangeStr2(String str1,String str2,String name) {
		String msg="";

		if(StringUtils.isEmpty(str1) || Constants.ZERO.equals(str1)){
			str1="空";
		}
		if(StringUtils.isEmpty(str2) || Constants.ZERO.equals(str2)){
			str2="空";
		}
		if(Constants.ONE.equals(str1)){
			str1="是";
		}
		if(Constants.ONE.equals(str2)){
			str2="是";
		}
		if (!str1.equals(str2)){
			msg=name+"：由"+str1+" <font color='blue'>变更为</font> "+str2;
		} 
		return msg;
	}
	
	/**
	 * 头像比较
	 */
	public static String getChangeHeadPhoto(String str1,String str2,String name,String basePath) {
		String msg="";
		if (StringUtils.isNotEmpty(str2) && !str2.equals(str1)){
			msg=name+"：变更为："+"<a href='"+basePath+Constants.HEADPHOTO_PATH+str2+"' target='_blank'>"+str2+"</a>";
		}
		return msg;
	}

	/**
	 * 对象比对工具：并获取变更信息
	 * @author fjw
	 */
	public static String getChangeBaseData(TBaseData obj1,TBaseData obj2,String name) {
		String msg="";
		if(null!=obj1&&null!=obj2){
			if(obj1.getCode()!=obj2.getCode()){ 
				msg=name+"：由 "+obj1.getName()+" <font color='blue'>变更为</font> "+obj2.getName();
			}
		}
		if (null==obj1&&null!=obj2) {
			msg=name+"：由 空 <font color='blue'>变更为</font> "+obj2.getName();
		}
		if (null!=obj1&&null==obj2) {
			msg=name+"：由 "+obj1.getName()+" <font color='blue'>变更为</font> 空";
		}
		return msg;
	}

	/**
	 * 对象比对工具：并获取变更信息
	 * @author fjw
	 */
	public static String getChangeBaseData(TBaseData obj1,String str2,String name) {
		String msg="";
		if(null!=obj1&&StringUtils.isNotEmpty(str2)){
			if(!str2.equals(obj1.getName())){ 
				msg=name+"：由 "+obj1.getName()+" <font color='blue'>变更为</font> "+str2;
			}
		}
		if (null!=obj1&&StringUtils.isEmpty(str2)) {
			msg=name+"：由 "+obj1.getName()+" <font color='blue'>变更为</font> 空";
		}
		if (null==obj1&&StringUtils.isNotEmpty(str2)) {
			msg=name+"：由 空 <font color='blue'>变更为</font> "+str2;
		}
		return msg;
	}


	/**
	 * 日期比对工具：并获取变更信息
	 * @author fjw
	 */
	public static String getChangeDate(Date date1,Date date2,String name) {
		String msg="";
		if(null!=date1&&null!=date2){
			if(date1.compareTo(date2)!=0){
				msg=name+"：由 "+DateTools.dateToStr(date1)+" <font color='blue'>变更为</font> "+DateTools.dateToStr(date2);
			}
		}
		if (null==date1&&null!=date2) {
			msg=name+"：由 空 <font color='blue'>变更为</font> "+DateTools.dateToStr(date2);
		}
		if (null!=date1&&null==date2) {
			msg=name+"：由 "+DateTools.dateToStr(date1)+" <font color='blue'>变更为</font> 空";
		}
		return msg;
	}

	/**
	 * 日期比对工具：并获取变更信息
	 * @author fjw
	 */
	public static String getChangeDate(Date date1,String str2,String name) {
		String msg="";
		if(null!=date1&&StringUtils.isNotEmpty(str2)){
			if(!str2.equals(DateTools.dateToStr(date1))){
				msg=name+"：由 "+DateTools.dateToStr(date1)+" <font color='blue'>变更为</font> "+str2;
			}
		}
		if (null==date1&&StringUtils.isNotEmpty(str2)) {
			msg=name+"：由 空 <font color='blue'>变更为</font> "+str2;
		}
		if (null!=date1&&StringUtils.isEmpty(str2)) {
			msg=name+"：由 "+DateTools.dateToStr(date1)+" <font color='blue'>变更为</font> 空";
		}
		return msg;
	}


	/**
	 * 字符串比对工具：并获取新增信息
	 * @author fjw
	 */
	public static String getChangeNewStr(String str1,String name) {
		String msg="";
		if (StringUtils.isNotEmpty(str1)){
			msg="<font color='green'>新增 "+name+"：</font> "+str1+";";
		}
		return msg;
	}

	/**
	 * 对象比对工具：并获取新增信息
	 * @author fjw
	 */
	public static String getChangeNewBaseData(TBaseData obj1,String name) {
		String msg="";

		if (null!=obj1) {
			if (StringUtils.isNotEmpty(obj1.getName())){
				msg="<font color='green'>新增 "+name+"：</font> "+obj1.getName()+";";
			}
		}
		return msg;
	}

	/**
	 * 字符串比对工具：并获取删除信息
	 * @author fjw
	 */
	public static String getChangeDelStr(String str1,String name) {
		String msg="";
		if (StringUtils.isNotEmpty(str1)){
			msg="<font color='red'>删除 "+name+"：</font> "+str1+";";
		}
		return msg;
	}

	/**
	 * 对象比对工具：并获取删除信息
	 * @author fjw
	 */
	public static String getChangeDelBaseData(TBaseData obj1,String name) {
		String msg="";

		if (null!=obj1) {
			if (StringUtils.isNotEmpty(obj1.getName())){
				msg="<font color='red'>删除 "+name+"：</font> "+obj1.getName()+";";
			}
		}
		return msg;
	}
	/**
	 *20180702,字符串比较。针对信息变更时数据为空导致的bug改造
	 */
	public static boolean getCompStr(String str1,String str2) {
		boolean msg=false;
		if (StringUtils.isEmpty(str1)) {
			str1="";
		}
		if (StringUtils.isEmpty(str2)) {
			str2="";
		}
		if (!str1.equals(str2)) {
			msg=true;
		}
		return msg;
	}

	/**
	 * 自动计算协议年限
	 * @author fjw
	 */
	public static int getXyYear(Date date) {
		int xysum = 4;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int nl = Calendar.getInstance().get(Calendar.YEAR)- cal.get(Calendar.YEAR);
		if (nl >= 65) {
			xysum = 1;
		} else if (nl > 61) {
			xysum = 65 - nl;
		}
		return xysum;
	}

	/**
	 * 字符串空检查，并提示
	 * @author fjw
	 */
	public static String getStrNullTip(String str,String tip) {
		String msg="";
		if (StringUtils.isEmpty(str)){
			msg="基本信息-"+tip+" 不能为空；<br>";
		}
		return msg;
	}	
	/**
	 * 对象空检查，并提示
	 * @author fjw
	 */
	public static String getObjNullTip(TBaseData obj,String tip) {
		String msg="";
		if (obj==null){
			msg="基本信息-"+tip+" 不能为空；<br>";
		}
		return msg;
	}	
	/**
	 * 日期空检查，并提示
	 * @author fjw
	 */
	public static String getDateNullTip(Date date,String tip) {
		String msg="";
		if (date==null){
			msg="基本信息-"+tip+" 不能为空；<br>";
		}
		return msg;
	}

	/**
	 * 数字对象空返回0
	 * @author fjw
	 */
	public static int getNumberZero(Integer obj) {
		int number=0;
		if (obj!=null){
			number=obj;
		}
		return number;
	}

	/**
	 * 20181226
	 * 防止把空串插入数据库：
	 * 1、有内容的，如空格够返回；
	 * 2、没内容的，返回null;
	 */
	public static String strImportData(String str) {
		if(StringUtil.isNotBlank(str)){
			return str.trim();
		}else{
			return null;
		}
	}

	/**
	 * 根据属性，获取get方法--反射机制
	 * @param ob 对象
	 * @param name 属性名
	 * @return
	 * @throws Exception
	 */
	public static Object getGetMethod(Object ob, String name)throws Exception{
		Method[] m = ob.getClass().getMethods();
		for(int i = 0;i < m.length;i++){
			if(("get"+name).toLowerCase().equals(m[i].getName().toLowerCase())){
				return m[i].invoke(ob);
			}
		}
		return null;
	}

	/**
	 *  根据属性，拿到set方法，并把值set到对象中	 --反射机制
	 * @param obj 对象	 
	 * @param clazz 对象的class	 
	 * @param fileName 需要设置值得属性	 
	 * @param typeClass 	 
	 * @param value	 
	 */
	public static void setValue(Object obj,Class<?> clazz,String filedName,Class<?> typeClass,Object value){
		String methodName = "set" + filedName.substring(0,1).toUpperCase()+filedName.substring(1);
		try{
			Method method =  clazz.getDeclaredMethod(methodName, new Class[]{typeClass});
			method.invoke(obj, new Object[]{getClassTypeValue(typeClass, value)});
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	/**
	 * 通过class类型获取获取对应类型的值
	 * @param typeClass class类型
	 * @param value 值
	 * @return Object
	 */
	private static Object getClassTypeValue(Class<?> typeClass, Object value){
		if(typeClass == int.class  || value instanceof Integer){            
			if(null == value){                
				return 0;            
			}           
			return value;        
		}else if(typeClass == short.class){            
			if(null == value){            
				return 0;     
			}           
			return value;   
		}else if(typeClass == byte.class){        
			if(null == value){            
				return 0;     
			}           
			return value;      
		}else if(typeClass == double.class){        
			if(null == value){   
				return 0;         
			}          
			return value;    
		}else if(typeClass == long.class){ 
			if(null == value){               
				return 0;    
			}           
			return value; 
		}else if(typeClass == String.class){       
			if(null == value){           
				return "";  
			}          
			return value;      
		}else if(typeClass == boolean.class){     
			if(null == value){             
				return true;         
			}       
			return value;  
		}else if(typeClass == BigDecimal.class){       
			if(null == value){     
				return new BigDecimal(0);        
			}       
			return new BigDecimal(value+""); 
		}else {     
			return typeClass.cast(value);       
		}
	}

	/**获取2位数月份，用于标准化记录数据，比如01，02，09**/
	public static String getMonth2(String value){
		String month="";
		if(value.length()==1){
			month=Constants.ZERO+value;
		}else {
			month=value;
		}
		return month;
	}
	
	/**字符串数组去重 字符串去重**/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String removeDuplicateData(String test) {
		String newStr="";
		if (StringUtils.isNotEmpty(test)) {
			String[] test1 = test.split(",");
			List list = Arrays.asList(test1);
			Set set = new HashSet(list);
			String [] rid=(String [])set.toArray(new String[0]);
			for (int i = 0; i < rid.length; i++) {
				newStr+=rid[i]+",";
			}
			if (StringUtils.isNotEmpty(newStr)) {
				newStr=newStr.substring(0, newStr.length()-1);
			}
		}
		return newStr;
	}

	/**
	 * 把返回页面的提示消息，抽取业务公共方法20210608
	 * 有时间把所有controller的都改一下，代码冗余太多了 
	 * @param flag 返回标识：成功或者失败
	 * @param msg 返回的消息：10字内
	 * @return
	 */
//	public static String ConvertToStringWithJSON(boolean flag,String msg) {
//		Map<String, Object> returnMap=new HashMap<String, Object>();
//		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
//		returnMap.put("success", flag);
//		returnMap.put("msg", msg);
//		return gson.toJson(returnMap);
//	}
	
//	public static String ConvertToStringWithJSON(String flag,String message) {
//		Map<String, Object> returnMap=new HashMap<String, Object>();
//		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
//		returnMap.put("success", flag);
//		returnMap.put("message", message);
//		return gson.toJson(returnMap);
//	}
	
//	@SuppressWarnings({ "rawtypes" })
//	public static String ConvertToStringWithJSON(int total,List list) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("total", total);
//		map.put("rows", list);
//		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
//		return gson.toJson(map);
//	}
	
	public static Map<String, Object> returnMapToView(String resultcode,String value) {
		Map<String, Object> returnMap=new HashMap<String, Object>();
		returnMap.put("resultcode",resultcode);
		returnMap.put("value", value);
		return returnMap;
	}
}
