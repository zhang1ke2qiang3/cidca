package com.cidca.util;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

public class BigDecimalUtil {

	public final static BigDecimal strToBigDecimal(String str) {
		BigDecimal value=null;
		if (StringUtils.isEmpty(str)) {
			value=new BigDecimal("0.00");
		}else {
			value=new BigDecimal(str);
		}
		return value;
	} 
	
	//不可继承，不可重写，属性不可覆盖
	public final static BigDecimal toZero(BigDecimal value) {
		if (null==value) {
			value=new BigDecimal("0.00");
		}
		return value;
	} 
	
	public final static String toRoundHalfUp(BigDecimal value) {
		String info="";
		if (null!=value) {
			value = value.setScale(2, BigDecimal.ROUND_HALF_UP);
			info=value.toString();
		}
		return info;
	} 
	
	
}
