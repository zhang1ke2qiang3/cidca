package com.cidca.util;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class MultifunUtils {

	public static String judgeStr(String value){
		if (StringUtils.isNotEmpty(value)) {
			return value;
		}
		return null;
	}

	public static Integer judgeInteger(Integer value){
		if (null!=value) {
			return value;
		}
		return null;
	}

	public static Date judgeDate(Date value){
		if (null!=value) {
			return value;
		}
		return null;
	}
	
	public static BigDecimal judgeBigDecimal(BigDecimal value){
		if (null!=value && value.compareTo(BigDecimal.ZERO)!=0) {
			return value;
		}
		return null;
	}

	public static String getStrChange(String oldStr,String newStr,String strName){
		if (StringUtils.isNotEmpty(oldStr) && StringUtils.isNotEmpty(newStr) ) {
			return oldStr+"变更为空"+newStr;
		}else if (StringUtils.isEmpty(oldStr) && StringUtils.isNotEmpty(newStr) ){
			return strName+"由空变更为"+newStr;
		}else{
			return strName+"变更为空";
		}
	}
	
	
}
