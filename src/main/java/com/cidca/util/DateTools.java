package com.cidca.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.cidca.common.Constants;

/**日期工具类**/
public class DateTools {

	private static final int[] dayArray = new int[] { 31, 28, 31, 30, 31, 30,31, 31, 30, 31, 30, 31 };
	private static SimpleDateFormat sdf = new SimpleDateFormat();

	/**
	 * 获取日历类
	 * @return Calendar
	 */
	public static synchronized Calendar getCalendar() {
		return GregorianCalendar.getInstance();
	}

	/**
	 * 得到格式为yyyy-MM-dd HH:mm:ss,SSS的时间字符串
	 * @return String
	 */
	public static synchronized String getDateMilliFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateMilliFormat(cal);
	}

	/**
	 * 得到格式为yyyy-MM-dd HH:mm:ss,SSS的时间字符串
	 * @param cal Calendar
	 * @return String
	 */
	public static synchronized String getDateMilliFormat(java.util.Calendar cal) {
		String pattern = "yyyy-MM-dd HH:mm:ss,SSS";
		return getDateFormat(cal, pattern);
	}

	/**
	 * 将格式为yyyy-MM-dd HH:mm:ss,SSS的时间字符串转化为Date类型
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateMilliFormat(java.util.Date date) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return getDateFormat(date, pattern);
	}

	/**
	 * 输出时间格式为yyyy-MM-dd HH:mm:ss的字符串
	 * @param strDate
	 * @return java.util.Calendar
	 */
	public static synchronized Calendar parseCalendarMilliFormat(String strDate) {
		String pattern = "yyyy-MM-dd HH:mm:ss,SSS";
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * @Description: 组装返回日期
	 * @param strDate
	 * @return Date
	 * @Author: 刘国亮 Date: 2007-4-15
	 */
	public static Date parseDateChinaFormat(String strDate) {
		if (strDate == null) {
			return null;
		}
		String pattern = "yyyy-MM-dd";
		if (StringUtils.isEmpty(strDate))
			return new Date();
		else {
			int yearIndex = strDate.indexOf("年");
			int monthIndex = strDate.indexOf("月");
			int dayIndex = strDate.indexOf("日");
			int hourIndex = strDate.indexOf("时");
			int minIndex = strDate.indexOf("分");
			int sencdIndex = strDate.indexOf("秒");
			String splitYear = strDate.substring(0, 4);
			;
			String splitMonth = strDate.substring(yearIndex + 1, yearIndex + 3);
			String splitDay = strDate.substring(monthIndex + 1, monthIndex + 3);
			String strFormatDat = splitYear + "-" + splitMonth + "-" + splitDay;
			if (hourIndex > 0) {
				String hourceDay = strDate
						.substring(dayIndex + 1, dayIndex + 3);
				strFormatDat += " " + hourceDay;
				pattern += " HH";
			}
			if (minIndex > 0) {
				String minDay = strDate.substring(hourIndex + 1, hourIndex + 3);
				strFormatDat += ":" + minDay;
				pattern += ":mm";
			}
			if (sencdIndex > 0) {
				String sencdDay = strDate.substring(minIndex + 1, minIndex + 3);
				strFormatDat += ":" + sencdDay;
				pattern += ":ss";
			}
			return parseDateFormat(strFormatDat, pattern);
		}
	}

	/**
	 * @param strDate
	 * @return java.util.Date
	 */
	public static synchronized Date parseDateMilliFormat(String strDate) {
		String pattern = "yyyy-MM-dd HH:mm:ss,SSS";
		return parseDateFormat(strDate, pattern);
	}

	public static synchronized Date parseDatetoStringMilliFormat() {
		String strDate = getDateMilliFormat();
		String pattern = "yyyy-MM-dd HH:mm:ss,SSS";
		return parseDateFormat(strDate, pattern);
	}

	public static synchronized Date parseDatetoStringSecondFormat() {
		String strDate = getDateMilliFormat();
		String pattern = "yyyy-MM-dd HH:mm";
		return parseDateFormat(strDate, pattern);
	}

	public static synchronized Date parseDatetoSecondFormat() {
		String strDate = getDateMilliFormat();
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return parseDateFormat(strDate, pattern);
	}

	/**
	 * @return String
	 */
	public static synchronized String getDateSecondFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateSecondFormat(cal);
	}

	/**
	 * 显示中文日期
	 * 
	 * @Description: yyyy年MM月dd日
	 * @return
	 */
	public static synchronized String getDateChinaFormat() {
		Date dateTest = new Date();
		// 用来显示某一个时间的中文格式
		SimpleDateFormat df = new SimpleDateFormat("yyyy年M月d日 hh时m分");
		String timevalue = df.format(dateTest);
		return timevalue;

	}

	public static synchronized Date getToday(String format) {
		Date dateTest = new Date();
		SimpleDateFormat df = new SimpleDateFormat(format);
		try {
			return df.parse(df.format(dateTest));
		} catch (ParseException e) {
			e.printStackTrace();
			return dateTest;
		}
	}


	/**
	 * 显示中文日期
	 * 
	 * @Description: yyyy年MM月dd日
	 * @return
	 */
	public static synchronized String getDateChinaFormat(java.util.Date date) {
		if (date == null) {
			return "";
		}
		// 用来显示某一个时间的中文格式
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		String timevalue = df.format(date);
		return timevalue;
	}

	/**
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateSecondFormat(java.util.Calendar cal) {
		if (cal == null) {
			return null;
		}
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return getDateFormat(cal, pattern);
	}

	public static synchronized String getToday() {
		Date date = new Date();
		String pattern = "yyyy-MM-dd";
		return getDateFormat(date, pattern);
	}

	public static synchronized String getMonth() {
		Date date = new Date();
		String pattern = "yyyyMM";
		return getDateFormat(date, pattern);
	}

	/**
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateSecondFormat(java.util.Date date) {
		if (date == null) {
			return null;
		}
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return getDateFormat(date, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Calendar
	 */
	public static synchronized Calendar parseCalendarSecondFormat(String strDate) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Date
	 */
	public static synchronized Date parseDateSecondFormat(String strDate) {
		if (StringUtils.isEmpty(strDate)) {
			return null;
		}
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return parseDateFormat(strDate, pattern);
	}

	/**
	 * @return String
	 */
	public static synchronized String getDateMinuteFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateMinuteFormat(cal);
	}

	/**
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateMinuteFormat(java.util.Calendar cal) {
		String pattern = "yyyy-MM-dd HH:mm";
		return getDateFormat(cal, pattern);
	}

	/**
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateMinuteFormat(java.util.Date date) {
		String pattern = "yyyy-MM-dd HH:mm";
		return getDateFormat(date, pattern);
	}

	/**
	 * @param date
	 * @return String
	 */
	public static synchronized Date getDateStrMinuteFormat(java.util.Date date) {
		String pattern = "yyyy-MM-dd HH:mm";
		String strDate = getDateFormat(date, pattern);
		return parseDateFormat(strDate, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Calendar
	 */
	public static synchronized Calendar parseCalendarMinuteFormat(String strDate) {
		String pattern = "yyyy-MM-dd HH:mm";
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Date
	 */
	public static synchronized Date parseDateMinuteFormat(String strDate) {
		String pattern = "yyyy-MM-dd HH:mm";
		return parseDateFormat(strDate, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Date
	 */
	public static synchronized Date parseDate(String strDate) {
		String pattern = "yyyy-MM-dd";
		return parseDateFormat(strDate, pattern);
	}

	/**
	 * 日期转化字为符串
	 * date-str
	 */
	public static synchronized String getDateDayFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateDayFormat(cal);
	}

	public static synchronized String getInThePastWeekFormat() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, - 7);
		return getDateDayFormat(cal);
	}
	/**
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateDayFormat(java.util.Calendar cal) {
		String pattern = "yyyy-MM-dd";
		return getDateFormat(cal, pattern);
	}

	/**
	 * 个数化字符
	 * 
	 * @param strDate
	 * @return
	 */
	public static synchronized String getDateFormatStr(String strDate) {
		String pattern = "yyyy-MM-dd";
		Calendar ca = parseCalendarFormat(strDate, pattern);
		return getDateDayFormat(ca);
	}

	/**
	 * 个数化字符
	 * 
	 * @param strDate
	 * @return
	 */
	public static synchronized String getDateFormatString(String strDate) {
		try {
			Date ca;
			if (strDate != null && strDate.length() == 10) {
				ca = parseDateDayFormat(strDate);
			} else {
				ca = parseDateSecondFormat(strDate);
			}
			return getDateFormat(ca);
		} catch (Exception e) {
			return strDate;
		}
	}

	public static synchronized String getDateDayFormat(java.util.Date date) {
		if (null!=date) {
			String pattern = "yyyy-MM-dd";
			return getDateFormat(date, pattern);
		}
		return null;

	}

	public static synchronized String getDateDayFormatCN(java.util.Date date) {
		if (date == null) {
			return "年&nbsp;&nbsp;月&nbsp;&nbsp;日";
		}
		String pattern = "yyyy年MM月dd";
		return getDateFormat(date, pattern) + "日";
	}

	public static synchronized String getDateDayFormatFullCN(java.util.Date date) {
		if (date == null) {
			return "";
		}
		String pattern = "yyyy年MM月dd";
		return getDateFormat(date, pattern) + "日";
	}

	public static synchronized String getDateDayMinuteFormatCN(
			java.util.Date date) {
		if (date == null) {
			return "";
		}
		String strDate = getDateSecondFormat(date);
		String year = strDate.substring(0, 4);
		String month = strDate.substring(5, 7);
		String day = strDate.substring(8, 10);
		String hour = strDate.substring(11, 13);
		String minute = strDate.substring(14, 16);
		String second = strDate.substring(17, 19);

		StringBuffer sb = new StringBuffer();
		sb.append(year).append("年").append(month).append("月").append(day)
		.append("日").append(hour).append("时").append(minute)
		.append("分").append(second).append("秒");
		return sb.toString();
	}

	public static synchronized String getDateDayMinuteSecondFormatCN(
			java.util.Date date) {
		if (date == null) {
			return "";
		}
		String strDate = getDateSecondFormat(date);
		String year = strDate.substring(0, 4);
		String month = strDate.substring(5, 7);
		String day = strDate.substring(8, 10);
		String hour = strDate.substring(11, 13);
		String minute = strDate.substring(14, 16);

		StringBuffer sb = new StringBuffer();
		sb.append(year).append("年").append(month).append("月").append(day)
		.append("日").append(hour).append("时").append(minute)
		.append("分");
		return sb.toString();
	}

	public static synchronized Date getDateMinuteFormFormatCN(String datestr) {
		if (datestr == null) {
			return null;
		}
		String pattern = "yyyy年MM月dd日hh时mm分";
		Date result = parseDateFormat(datestr, pattern);
		return result;
	}

	/**
	 * @param strDate
	 * @return java.util.Calendar
	 */
	public static synchronized Calendar parseCalendarDayFormat(String strDate) {
		String pattern = "yyyy-MM-dd";
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Date
	 */
	public static synchronized Date parseDateDayFormat(String strDate) {
		String pattern = "yyyy-MM-dd";
		return parseDateFormat(strDate, pattern);
	}

	/**
	 * @return String
	 */
	public static synchronized String getDateFileFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateFileFormat(cal);
	}

	/**
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateFileFormat(java.util.Calendar cal) {
		String pattern = "yyyy-MM-dd_HH-mm-ss";
		return getDateFormat(cal, pattern);
	}

	/**
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateFileFormat(java.util.Date date) {
		String pattern = "yyyy-MM-dd_HH-mm-ss";
		return getDateFormat(date, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Calendar
	 */
	public static synchronized Calendar parseCalendarFileFormat(String strDate) {
		String pattern = "yyyy-MM-dd_HH-mm-ss";
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Date
	 */
	public static synchronized Date parseDateFileFormat(String strDate) {
		String pattern = "yyyy-MM-dd_HH-mm-ss";
		return parseDateFormat(strDate, pattern);
	}

	/**
	 * @return String
	 */
	public static synchronized String getDateW3CFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateW3CFormat(cal);
	}

	/**
	 * 返回指定格式时间
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static synchronized String getDateW3CFormat(java.util.Date date,
			String pattern) {
		if (date == null) {
			Calendar cal = Calendar.getInstance();
			return getDateFormat(cal, pattern);
		} else {
			return getDateFormat(date, pattern);
		}
	}

	/**
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateW3CFormat(java.util.Calendar cal) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return getDateFormat(cal, pattern);
	}

	/**
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateW3CFormat(java.util.Date date) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return getDateFormat(date, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Calendar
	 */
	public static synchronized Calendar parseCalendarW3CFormat(String strDate) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Date
	 */
	public static synchronized Date parseDateW3CFormat(String strDate) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return parseDateFormat(strDate, pattern);
	}

	/**
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateFormat(java.util.Calendar cal) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return getDateFormat(cal, pattern);
	}

	/**
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateFormat(java.util.Date date) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return getDateFormat(date, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Calendar
	 */
	public static synchronized Calendar parseCalendarFormat(String strDate) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Date
	 */
	public static synchronized Date parseDateFormat(String strDate) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return parseDateFormat(strDate, pattern);
	}

	/**
	 * @param cal
	 * @param pattern
	 * @return String
	 */
	public static synchronized String getDateFormat(java.util.Calendar cal,
			String pattern) {
		return getDateFormat(cal.getTime(), pattern);
	}

	/**
	 * @param date
	 * @param pattern
	 * @return String
	 */
	public static synchronized String getDateFormat(java.util.Date date,
			String pattern) {
		synchronized (sdf) {
			String str = null;
			sdf.applyPattern(pattern);
			str = sdf.format(date);
			return str;
		}
	}

	/**
	 * @param strDate
	 * @param pattern
	 * @return java.util.Calendar
	 */
	public static synchronized Calendar parseCalendarFormat(String strDate,
			String pattern) {
		synchronized (sdf) {
			Calendar cal = null;
			sdf.applyPattern(pattern);
			try {
				sdf.parse(strDate);
				cal = sdf.getCalendar();
			} catch (Exception e) {
			}
			return cal;
		}
	}

	/**
	 * @param strDate
	 * @param pattern
	 * @return java.util.Calendar
	 */
	public static synchronized String parseStringFormat(String strDate,String pattern) {
		if (strDate == null || strDate.trim().length() < 1) {
			return "";
		}
		synchronized (sdf) {
			Calendar cal = null;
			sdf.applyPattern(pattern);
			try {
				sdf.parse(strDate);
				cal = sdf.getCalendar();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(cal == null) {
				return "";
			}else {
				return getDateW3CFormat(cal);
			}
		}
	}

	/**
	 * @param strDate
	 * @param pattern
	 * @return java.util.Date
	 */
	public static synchronized Date parseDateFormat(String strDate,String pattern) {
		synchronized (sdf) {
			Date date = null;
			sdf.applyPattern(pattern);
			try {
				date = sdf.parse(strDate);
			} catch (Exception e) {
			}
			return date;
		}
	}

	public static synchronized int getLastDayOfMonth(int month) {
		if (month < 1 || month > 12) {
			return -1;
		}
		int retn = 0;
		if (month == 2) {
			if (isLeapYear()) {
				retn = 29;
			} else {
				retn = dayArray[month - 1];
			}
		} else {
			retn = dayArray[month - 1];
		}
		return retn;
	}

	public static synchronized int getLastDayOfMonth(int year, int month) {
		if (month < 1 || month > 12) {
			return -1;
		}
		int retn = 0;
		if (month == 2) {
			if (isLeapYear(year)) {
				retn = 29;
			} else {
				retn = dayArray[month - 1];
			}
		} else {
			retn = dayArray[month - 1];
		}
		return retn;
	}

	public static synchronized boolean isLeapYear() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		return isLeapYear(year);
	}

	public static synchronized boolean isLeapYear(int year) {
		/**
		 * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
		 * 3.能被4整除同时能被100整除则不是闰年
		 */
		if ((year % 400) == 0)
			return true;
		else if ((year % 4) == 0) {
			if ((year % 100) == 0)
				return false;
			else
				return true;
		} else
			return false;
	}

	/**
	 * 判断指定日期的年份是否是闰年
	 * 
	 * @param date
	 *            指定日期。
	 * @return 是否闰年
	 */
	public static synchronized boolean isLeapYear(java.util.Date date) {
		/**
		 * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
		 * 3.能被4整除同时能被100整除则不是闰年
		 */
		// int year = date.getYear();
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		int year = gc.get(Calendar.YEAR);
		return isLeapYear(year);
	}

	public static synchronized boolean isLeapYear(java.util.Calendar gc) {
		/**
		 * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
		 * 3.能被4整除同时能被100整除则不是闰年
		 */
		int year = gc.get(Calendar.YEAR);
		return isLeapYear(year);
	}

	/**
	 * 得到指定日期的前一个工作日
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的前一个工作日
	 */
	public static synchronized java.util.Date getPreviousWeekDay(
			java.util.Date date) {
		{
			/**
			 * 详细设计： 1.如果date是星期日，则减3天 2.如果date是星期六，则减2天 3.否则减1天
			 */
			GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
			gc.setTime(date);
			return getPreviousWeekDay(gc);
			// switch ( gc.get( Calendar.DAY_OF_WEEK ) )
			// {
			// case ( Calendar.MONDAY ):
			// gc.add( Calendar.DATE, -3 );
			// break;
			// case ( Calendar.SUNDAY ):
			// gc.add( Calendar.DATE, -2 );
			// break;
			// default:
			// gc.add( Calendar.DATE, -1 );
			// break;
			// }
			// return gc.getTime();
		}
	}

	public static synchronized java.util.Date getPreviousWeekDay(
			java.util.Calendar gc) {
		{
			/**
			 * 详细设计： 1.如果date是星期日，则减3天 2.如果date是星期六，则减2天 3.否则减1天
			 */
			switch (gc.get(Calendar.DAY_OF_WEEK)) {
			case (Calendar.MONDAY    ):
				gc.add(Calendar.DATE, -3);
			break;
			case (Calendar.SUNDAY    ):
				gc.add(Calendar.DATE, -2);
			break;
			default:
				gc.add(Calendar.DATE, -1);
				break;
			}
			return gc.getTime();
		}
	}

	/**
	 * 得到指定日期的后一个工作日
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的后一个工作日
	 */
	public static synchronized java.util.Date getNextWeekDay(java.util.Date date) {
		/**
		 * 详细设计： 1.如果date是星期五，则加3天 2.如果date是星期六，则加2天 3.否则加1天
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		switch (gc.get(Calendar.DAY_OF_WEEK)) {
		case (Calendar.FRIDAY    ):
			gc.add(Calendar.DATE, 3);
		break;
		case (Calendar.SATURDAY    ):
			gc.add(Calendar.DATE, 2);
		break;
		default:
			gc.add(Calendar.DATE, 1);
			break;
		}
		return gc.getTime();
	}

	public static synchronized java.util.Calendar getNextWeekDay(
			java.util.Calendar gc) {
		/**
		 * 详细设计： 1.如果date是星期五，则加3天 2.如果date是星期六，则加2天 3.否则加1天
		 */
		switch (gc.get(Calendar.DAY_OF_WEEK)) {
		case (Calendar.FRIDAY    ):
			gc.add(Calendar.DATE, 3);
		break;
		case (Calendar.SATURDAY    ):
			gc.add(Calendar.DATE, 2);
		break;
		default:
			gc.add(Calendar.DATE, 1);
			break;
		}
		return gc;
	}

	/**
	 * 取得指定日期的下一个月的最后一天
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的下一个月的最后一天
	 */
	public static synchronized java.util.Date getLastDayOfNextMonth(
			java.util.Date date) {
		/**
		 * 详细设计： 1.调用getNextMonth设置当前时间 2.以1为基础，调用getLastDayOfMonth
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.setTime(DateTools.getNextMonth(gc.getTime()));
		gc.setTime(DateTools.getLastDayOfMonth(gc.getTime()));
		return gc.getTime();
	}

	/**
	 * 取得指定日期的下一个星期的最后一天
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的下一个星期的最后一天
	 */
	public static synchronized java.util.Date getLastDayOfNextWeek(
			java.util.Date date) {
		/**
		 * 详细设计： 1.调用getNextWeek设置当前时间 2.以1为基础，调用getLastDayOfWeek
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.setTime(DateTools.getNextWeek(gc.getTime()));
		gc.setTime(DateTools.getLastDayOfWeek(gc.getTime()));
		return gc.getTime();
	}

	/**
	 * 取得指定日期的下一个月的第一天
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的下一个月的第一天
	 */
	public static synchronized java.util.Date getFirstDayOfNextMonth(
			java.util.Date date) {
		/**
		 * 详细设计： 1.调用getNextMonth设置当前时间 2.以1为基础，调用getFirstDayOfMonth
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.setTime(DateTools.getNextMonth(gc.getTime()));
		gc.setTime(DateTools.getFirstDayOfMonth(gc.getTime()));
		return gc.getTime();
	}

	public static synchronized java.util.Calendar getFirstDayOfNextMonth(
			java.util.Calendar gc) {
		/**
		 * 详细设计： 1.调用getNextMonth设置当前时间 2.以1为基础，调用getFirstDayOfMonth
		 */
		gc.setTime(DateTools.getNextMonth(gc.getTime()));
		gc.setTime(DateTools.getFirstDayOfMonth(gc.getTime()));
		return gc;
	}

	/**
	 * 取得指定日期的下一个星期的第一天
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的下一个星期的第一天
	 */
	public static synchronized java.util.Date getFirstDayOfNextWeek(
			java.util.Date date) {
		/**
		 * 详细设计： 1.调用getNextWeek设置当前时间 2.以1为基础，调用getFirstDayOfWeek
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.setTime(DateTools.getNextWeek(gc.getTime()));
		gc.setTime(DateTools.getFirstDayOfWeek(gc.getTime()));
		return gc.getTime();
	}

	public static synchronized java.util.Calendar getFirstDayOfNextWeek(
			java.util.Calendar gc) {
		/**
		 * 详细设计： 1.调用getNextWeek设置当前时间 2.以1为基础，调用getFirstDayOfWeek
		 */
		gc.setTime(DateTools.getNextWeek(gc.getTime()));
		gc.setTime(DateTools.getFirstDayOfWeek(gc.getTime()));
		return gc;
	}

	/**
	 * 取得指定日期的下一个月
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的下一个月
	 */
	public static synchronized java.util.Date getNextMonth(java.util.Date date) {
		/**
		 * 详细设计： 1.指定日期的月份加1
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.MONTH, 1);
		return gc.getTime();
	}

	/**
	 * 取得指定日期的前一个月
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的前一个月
	 */
	public static synchronized java.lang.String getPreMonth(String date) {
		/**
		 * 详细设计： 1.指定日期的月份减1
		 */
		Calendar gc = parseCalendarDayFormat(date);
		gc.add(Calendar.MONTH, -1);
		return getDateDayFormat(gc);
	}

	public static synchronized java.util.Calendar getNextMonth(
			java.util.Calendar gc) {
		/**
		 * 详细设计： 1.指定日期的月份加1
		 */
		gc.add(Calendar.MONTH, 1);
		return gc;
	}

	/**
	 * 取得指定日期的下一天
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的下一天
	 */
	public static synchronized java.util.Date getNextDay(java.util.Date date) {
		/**
		 * 详细设计： 1.指定日期加1天
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DATE, 1);
		return gc.getTime();
	}

	/**
	 * 返回指定的日期加上N天
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static synchronized String getNDay(String date, int day) {
		/**
		 * 详细设计： 1.指定日期加N天
		 */

		Calendar gc = parseCalendarDayFormat(date);
		gc.add(Calendar.DATE, day);

		return getDateDayFormat(gc);
	}

	public static synchronized java.util.Calendar getNextDay(
			java.util.Calendar gc) {
		/**
		 * 详细设计： 1.指定日期加1天
		 */
		gc.add(Calendar.DATE, 1);
		return gc;
	}

	/**
	 * 取得指定日期的下一个星期
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的下一个星期
	 */
	public static synchronized java.util.Date getNextWeek(java.util.Date date) {
		/**
		 * 详细设计： 1.指定日期加7天
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DATE, 7);
		return gc.getTime();
	}

	public static synchronized java.util.Calendar getNextWeek(
			java.util.Calendar gc) {
		/**
		 * 详细设计： 1.指定日期加7天
		 */
		gc.add(Calendar.DATE, 7);
		return gc;
	}

	/**
	 * 取得指定日期的所处星期的最后一天
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的所处星期的最后一天
	 */
	public static synchronized java.util.Date getLastDayOfWeek(
			java.util.Date date) {
		/**
		 * 详细设计： 1.如果date是星期日，则加6天 2.如果date是星期一，则加5天 3.如果date是星期二，则加4天
		 * 4.如果date是星期三，则加3天 5.如果date是星期四，则加2天 6.如果date是星期五，则加1天
		 * 7.如果date是星期六，则加0天
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		switch (gc.get(Calendar.DAY_OF_WEEK)) {
		case (Calendar.SUNDAY  ):
			gc.add(Calendar.DATE, 6);
		break;
		case (Calendar.MONDAY  ):
			gc.add(Calendar.DATE, 5);
		break;
		case (Calendar.TUESDAY  ):
			gc.add(Calendar.DATE, 4);
		break;
		case (Calendar.WEDNESDAY  ):
			gc.add(Calendar.DATE, 3);
		break;
		case (Calendar.THURSDAY  ):
			gc.add(Calendar.DATE, 2);
		break;
		case (Calendar.FRIDAY  ):
			gc.add(Calendar.DATE, 1);
		break;
		case (Calendar.SATURDAY  ):
			gc.add(Calendar.DATE, 0);
		break;
		}
		return gc.getTime();
	}

	/**
	 * 取得指定日期的所处星期的第一天
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的所处星期的第一天
	 */
	public static synchronized java.util.Date getFirstDayOfWeek(
			java.util.Date date) {
		/**
		 * 详细设计： 1.如果date是星期日，则减0天 2.如果date是星期一，则减1天 3.如果date是星期二，则减2天
		 * 4.如果date是星期三，则减3天 5.如果date是星期四，则减4天 6.如果date是星期五，则减5天
		 * 7.如果date是星期六，则减6天
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		switch (gc.get(Calendar.DAY_OF_WEEK)) {
		case (Calendar.SUNDAY  ):
			gc.add(Calendar.DATE, 0);
		break;
		case (Calendar.MONDAY  ):
			gc.add(Calendar.DATE, -1);
		break;
		case (Calendar.TUESDAY  ):
			gc.add(Calendar.DATE, -2);
		break;
		case (Calendar.WEDNESDAY  ):
			gc.add(Calendar.DATE, -3);
		break;
		case (Calendar.THURSDAY  ):
			gc.add(Calendar.DATE, -4);
		break;
		case (Calendar.FRIDAY  ):
			gc.add(Calendar.DATE, -5);
		break;
		case (Calendar.SATURDAY  ):
			gc.add(Calendar.DATE, -6);
		break;
		}
		return gc.getTime();
	}

	public static synchronized java.util.Calendar getFirstDayOfWeek(
			java.util.Calendar gc) {
		/**
		 * 详细设计： 1.如果date是星期日，则减0天 2.如果date是星期一，则减1天 3.如果date是星期二，则减2天
		 * 4.如果date是星期三，则减3天 5.如果date是星期四，则减4天 6.如果date是星期五，则减5天
		 * 7.如果date是星期六，则减6天
		 */
		switch (gc.get(Calendar.DAY_OF_WEEK)) {
		case (Calendar.SUNDAY  ):
			gc.add(Calendar.DATE, 0);
		break;
		case (Calendar.MONDAY  ):
			gc.add(Calendar.DATE, -1);
		break;
		case (Calendar.TUESDAY  ):
			gc.add(Calendar.DATE, -2);
		break;
		case (Calendar.WEDNESDAY  ):
			gc.add(Calendar.DATE, -3);
		break;
		case (Calendar.THURSDAY  ):
			gc.add(Calendar.DATE, -4);
		break;
		case (Calendar.FRIDAY  ):
			gc.add(Calendar.DATE, -5);
		break;
		case (Calendar.SATURDAY  ):
			gc.add(Calendar.DATE, -6);
		break;
		}
		return gc;
	}

	/**
	 * 取得指定日期的所处月份的最后一天
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的所处月份的最后一天
	 */
	public static synchronized java.util.Date getLastDayOfMonth(
			java.util.Date date) {
		/**
		 * 详细设计： 1.如果date在1月，则为31日 2.如果date在2月，则为28日 3.如果date在3月，则为31日
		 * 4.如果date在4月，则为30日 5.如果date在5月，则为31日 6.如果date在6月，则为30日
		 * 7.如果date在7月，则为31日 8.如果date在8月，则为31日 9.如果date在9月，则为30日
		 * 10.如果date在10月，则为31日 11.如果date在11月，则为30日 12.如果date在12月，则为31日
		 * 1.如果date在闰年的2月，则为29日
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		switch (gc.get(Calendar.MONTH)) {
		case 0:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 1:
			gc.set(Calendar.DAY_OF_MONTH, 28);
			break;
		case 2:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 3:
			gc.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 4:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 5:
			gc.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 6:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 7:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 8:
			gc.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 9:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 10:
			gc.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 11:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		}
		// 检查闰年
		if ((gc.get(Calendar.MONTH) == Calendar.FEBRUARY)
				&& (isLeapYear(gc.get(Calendar.YEAR)))) {
			gc.set(Calendar.DAY_OF_MONTH, 29);
		}
		return gc.getTime();
	}

	public static synchronized java.util.Calendar getLastDayOfMonth(
			java.util.Calendar gc) {
		/**
		 * 详细设计： 1.如果date在1月，则为31日 2.如果date在2月，则为28日 3.如果date在3月，则为31日
		 * 4.如果date在4月，则为30日 5.如果date在5月，则为31日 6.如果date在6月，则为30日
		 * 7.如果date在7月，则为31日 8.如果date在8月，则为31日 9.如果date在9月，则为30日
		 * 10.如果date在10月，则为31日 11.如果date在11月，则为30日 12.如果date在12月，则为31日
		 * 1.如果date在闰年的2月，则为29日
		 */
		switch (gc.get(Calendar.MONTH)) {
		case 0:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 1:
			gc.set(Calendar.DAY_OF_MONTH, 28);
			break;
		case 2:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 3:
			gc.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 4:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 5:
			gc.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 6:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 7:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 8:
			gc.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 9:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 10:
			gc.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 11:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		}
		// 检查闰年
		if ((gc.get(Calendar.MONTH) == Calendar.FEBRUARY)
				&& (isLeapYear(gc.get(Calendar.YEAR)))) {
			gc.set(Calendar.DAY_OF_MONTH, 29);
		}
		return gc;
	}

	/**
	 * 取得指定日期的所处月份的第一天
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的所处月份的第一天
	 */
	public static synchronized java.util.Date getFirstDayOfMonth(
			java.util.Date date) {
		/**
		 * 详细设计： 1.设置为1号
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.set(Calendar.DAY_OF_MONTH, 1);
		return gc.getTime();
	}

	public static synchronized java.util.Calendar getFirstDayOfMonth(
			java.util.Calendar gc) {
		/**
		 * 详细设计： 1.设置为1号
		 */
		gc.set(Calendar.DAY_OF_MONTH, 1);
		return gc;
	}

	/**
	 * 将日期对象转换成为指定ORA日期、时间格式的字符串形式。如果日期对象为空，返回 一个空字符串对象，而不是一个空对象。
	 * 
	 * @param theDate
	 *            将要转换为字符串的日期对象。
	 * @param hasTime
	 *            如果返回的字符串带时间则为true
	 * @return 转换的结果
	 */
	public static synchronized String toOraString(Date theDate, boolean hasTime) {
		/**
		 * 详细设计： 1.如果有时间，则设置格式为getOraDateTimeFormat()的返回值
		 * 2.否则设置格式为getOraDateFormat()的返回值 3.调用toString(Date theDate, DateFormat
		 * theDateFormat)
		 */
		DateFormat theFormat;
		if (hasTime) {
			theFormat = getOraDateTimeFormat();
		} else {
			theFormat = getOraDateFormat();
		}
		return toString(theDate, theFormat);
	}

	/**
	 * 将日期对象转换成为指定日期、时间格式的字符串形式。如果日期对象为空，返回 一个空字符串对象，而不是一个空对象。
	 * 
	 * @param theDate
	 *            将要转换为字符串的日期对象。
	 * @param hasTime
	 *            如果返回的字符串带时间则为true
	 * @return 转换的结果
	 */
	public static synchronized String toString(Date theDate, boolean hasTime) {
		/**
		 * 详细设计： 1.如果有时间，则设置格式为getDateTimeFormat的返回值 2.否则设置格式为getDateFormat的返回值
		 * 3.调用toString(Date theDate, DateFormat theDateFormat)
		 */
		DateFormat theFormat;
		if (hasTime) {
			theFormat = getDateTimeFormat();
		} else {
			theFormat = getDateFormat();
		}
		return toString(theDate, theFormat);
	}

	/**
	 * 标准日期格式
	 */
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

	/**
	 * 标准时间格式
	 */
	private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm");

	/**
	 * ORA标准日期格式
	 */
	private static final SimpleDateFormat ORA_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

	/**
	 * ORA标准时间格式
	 */
	private static final SimpleDateFormat ORA_DATE_TIME_FORMAT = new SimpleDateFormat("yyyyMMddHHmm");


	/**
	 * 根据格式时间格式，返回指定类型时间
	 */
	public static String getDate2StringByPatt(Date date, String patt) {
		if (patt == null || patt.trim().length() < 1)
			patt = "T";
		String pattern = "yyyyMMddHHmmss";
		String patternDate = getDateW3CFormat(date, pattern);
		String returnDate = patternDate.substring(0, 8) + patt
				+ patternDate.substring(8);
		return returnDate;

	}

	/**
	 * 创建一个标准日期格式的克隆
	 * 
	 * @return 标准日期格式的克隆
	 */
	public static synchronized DateFormat getDateFormat() {
		/**
		 * 详细设计： 1.返回DATE_FORMAT
		 */
		SimpleDateFormat theDateFormat = (SimpleDateFormat) DATE_FORMAT.clone();
		theDateFormat.setLenient(false);
		return theDateFormat;
	}

	/**
	 * 创建一个标准时间格式的克隆
	 */
	public static synchronized DateFormat getDateTimeFormat() {
		/**
		 * 详细设计： 1.返回DATE_TIME_FORMAT
		 */
		SimpleDateFormat theDateTimeFormat = (SimpleDateFormat) DATE_TIME_FORMAT
				.clone();
		theDateTimeFormat.setLenient(false);
		return theDateTimeFormat;
	}

	/**
	 * 创建一个标准ORA日期格式的克隆
	 */
	public static synchronized DateFormat getOraDateFormat() {
		/**
		 * 详细设计： 1.返回ORA_DATE_FORMAT
		 */
		SimpleDateFormat theDateFormat = (SimpleDateFormat) ORA_DATE_FORMAT
				.clone();
		theDateFormat.setLenient(false);
		return theDateFormat;
	}

	/**
	 * 创建一个标准ORA时间格式的克隆
	 */
	public static synchronized DateFormat getOraDateTimeFormat() {
		/**
		 * 详细设计： 1.返回ORA_DATE_TIME_FORMAT
		 */
		SimpleDateFormat theDateTimeFormat = (SimpleDateFormat) ORA_DATE_TIME_FORMAT
				.clone();
		theDateTimeFormat.setLenient(false);
		return theDateTimeFormat;
	}

	/**
	 * 将一个日期对象转换成为指定日期、时间格式的字符串。 如果日期对象为空，返回一个空字符串，而不是一个空对象。
	 * 
	 * @param theDate
	 *            要转换的日期对象
	 * @param theDateFormat
	 *            返回的日期字符串的格式
	 * @return 转换结果
	 */
	public static synchronized String getDateMinuteStrCN(Date theDate) {
		/**
		 * 详细设计： 1.theDate为空，则返回"" 2.否则使用theDateFormat格式化
		 */
		if (theDate == null)
			return "";
		String pattern = "yyyy年MM月dd日HH时mi分";
		String patternDate = getDateW3CFormat(theDate, pattern);

		return patternDate;
	}

	/**
	 * 将一个日期对象转换成为指定日期、时间格式的字符串。 如果日期对象为空，返回一个空字符串，而不是一个空对象。
	 * 
	 * @param theDate
	 *            要转换的日期对象
	 * @param theDateFormat
	 *            返回的日期字符串的格式
	 * @return 转换结果
	 */
	public static synchronized String toString(Date theDate,
			DateFormat theDateFormat) {
		/**
		 * 详细设计： 1.theDate为空，则返回"" 2.否则使用theDateFormat格式化
		 */
		if (theDate == null) {
			return "";
		}else {
			return theDateFormat.format(theDate);
		}
	}

	/**
	 * 得到当前年份
	 * 
	 * @return int
	 */
	public static synchronized int getCurrentYear() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.YEAR);
	}

	/**
	 * 得到当前月
	 * 
	 * @return int
	 */
	public static synchronized int getCurrentMonth() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MONTH)+1;
	}

	/**
	 * 得到年份后两位
	 * 
	 * @Description:
	 * @return int
	 * @Author: ykj Date: 2008-7-4上午10:15:17 History: 1. Date:
	 *          2008-7-4上午10:15:17 Author:ykj Modification:
	 * 
	 */
	public static synchronized String getCurrentYearLast2() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		String yearstr = String.valueOf(year);

		return yearstr.substring(yearstr.length() - 2, yearstr.length());
	}

	/**
	 * 判断时间date1是否在时间date2之前 时间格式 2005-4-21 16:16:34
	 */
	public static boolean isDateBefore(String date1, String date2) {
		try {
			DateFormat df = DateFormat.getDateTimeInstance();
			return df.parse(date1).before(df.parse(date2));
		} catch (ParseException e) {
			System.out.print("[SYS] " + e.getMessage());
			return false;
		}
	}

	/**
	 * 判断当前时间是否在时间date2之前 时间格式 2005-4-21 16:16:34
	 */
	public static boolean isDateBefore(String date2) {
		try {
			Date date1 = new Date();
			DateFormat df = DateFormat.getDateTimeInstance();
			return date1.before(df.parse(date2));
		} catch (ParseException e) {
			System.out.print("[SYS] " + e.getMessage());
			return false;
		}
	}

	/**
	 * 判断时间date1是否在时间date2之前 时间格式 2005-4-21 16:16:34
	 */
	public static boolean isDateBefore(Date date1, Date date2) {
		return date1.before(date2);
	}

	/**
	 * 判断当前时间是否在时间date2之前 时间格式 2005-4-21 16:16:34
	 */
	public static boolean isDateBefore(Date date2) {
		Date date1 = new Date();
		return date1.before(date2);
	}

	/**
	 * 判断当前时间是否在时间date2之后 时间格式 2005-4-21 16:16:34
	 */
	public static boolean isDateAfter(Date date2) {
		Date date1 = new Date();
		return date1.after(date2);
	}

	/**
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateTimeStrFormat() {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		String pattern = "yyyyMMddHHmmss";
		return getDateFormat(cal, pattern);
	}

	/**
	 * @param 将
	 *            20080101格式 与 2008-01-01格式转换
	 * @return String
	 */
	public static synchronized String getFormatDate(String date) {
		StringBuffer ret = new StringBuffer();

		if(StringUtils.isNotEmpty(date) && date.length()==8){
			ret.append(date.substring(0, 4)).append("-").append(
					date.substring(4, 6)).append("-").append(
							date.substring(6, 8));
		}

		return ret.toString();
	}


	/**
	 * 显示中文日期
	 * 
	 * @Description: yyyy年MM月dd日
	 * @return
	 */
	public static synchronized String getTimeChinaFormat(String time) {
		if(StringUtils.isEmpty(time)){
			return "";
		}
		Date dateTest = parseDateSecondFormat(time);
		SimpleDateFormat df = new SimpleDateFormat("yyyy年M月d日 h时m分");
		String timevalue = df.format(dateTest);
		return timevalue;
	}


	/**
	 * @return String
	 */
	public static synchronized String getDateStrFormat(String para) {
		if(StringUtils.isEmpty(para)){
			return "";
		}
		return para.substring(0,para.lastIndexOf(":"));
	}


	/**
	 * @return String
	 */
	public static synchronized String getYYYYMMDDHHMM(String para) {
		if(StringUtils.isEmpty(para)){
			return "";
		}
		String ret="";
		ret=para.split(" ")[0]+" ";
		ret+=para.split(" ")[1].split(":")[0]+":";
		ret+=para.split(" ")[1].split(":")[1];

		return ret;
	}




	/**
	 * 显示日期
	 * 
	 * @Description: 
	 * @return
	 */
	public static synchronized String getTimeChinaFormat1(String time) {
		if(StringUtils.isEmpty(time)){
			return "";
		}
		Date dateTest = parseDateSecondFormat(time);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String timevalue = df.format(dateTest);
		return timevalue;
	}


	/**
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateYYYYMMDD(String time) {
		if(StringUtils.isEmpty(time)){
			return "";
		}
		String[] temp=time.split(" ");
		return temp[0];
	}
	public static String getDateByString(String s)
	{
		//参数s是日期格式如yyyy 
		String time;
		SimpleDateFormat formater = new SimpleDateFormat(s);
		time = formater.format(new Date());
		return time;

	}
	public static synchronized String getTimeChinaFormat5(String time) {
		if(StringUtils.isEmpty(time)){
			return "";
		}
		Date dateTest = parseDateSecondFormat(time);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timevalue = df.format(dateTest);
		return timevalue;
	}
	public static synchronized String getTimeChinaFormat3(String time) {
		if(StringUtils.isEmpty(time)){
			return "";
		}
		Date dateTest = parseDateSecondFormat(time);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String timevalue = df.format(dateTest);
		return timevalue;
	}
	public static synchronized String getTimeChinaFormat4(String time) {
		if(StringUtils.isEmpty(time)){
			return "";
		}
		Date dateTest = parseDateSecondFormat(time);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String timevalue = df.format(dateTest);
		return timevalue;
	}
	public static synchronized String getDateTimeStr(java.util.Date date) {
		String pattern = "yyyyMMddHHmmss";
		return getDateFormat(date, pattern);
	}


	/**
	 * 功能：字符转日期
	 * */
	public static synchronized Date getStr2Date(String para) throws Exception {
		Date date = null;
		if(StringUtils.isNotEmpty(para)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			date = sdf.parse(para);
		}

		return date;
	}

	/**
	 * 功能：字符转日期
	 * */
	public static synchronized Date getStr2Date2(String para){
		Date date = null;
		if(StringUtils.isNotEmpty(para)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				date = sdf.parse(para);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return date;
	}

	/**
	 * 日期转换成字符串
	 * @param date
	 * @return str
	 */
	public static String dateToStr(Date date) {
		String str="";
		if(date!=null){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			str = format.format(date);
		}
		return str;
	}

	/**
	 * 只获取当前年20170918
	 */
	public static String getYearOnly() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		Date date = new Date();
		return format.format(date);
	} 

	/**
	 * 只获取年
	 */
	public static String getYearOnly(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		return format.format(date);
	}

	/**
	 * 只获取月
	 */
	public static String getMonthOnly(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("MM");
		return format.format(date);
	}

	/**
	 * 只获取日
	 */
	public static String getDayOnly(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("dd");
		return format.format(date);
	}

	/**
	 * 只获取年
	 */
	public static int getYear(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		return Integer.parseInt(format.format(date));
	}
	/**
	 * 只获取月
	 */
	public static int getMonth(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("MM");
		return Integer.parseInt(format.format(date));
	}
	/**
	 * 只获取日
	 */
	public static int getDay(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("dd");
		return Integer.parseInt(format.format(date));
	}

	/**
	 * java计算两个日期之间的年数 yearDateDiff  20180306
	 */
	public static int getYearsOfDifDate(String startDate,String endDate){  
		Calendar calBegin = Calendar.getInstance(); //获取日历实例  
		Calendar calEnd = Calendar.getInstance();  
		calBegin.setTime(stringTodate(startDate,"yyyy"));//字符串按照指定格式转化为日期  
		calEnd.setTime(stringTodate(endDate,"yyyy"));  
		return calEnd.get(Calendar.YEAR) - calBegin.get(Calendar.YEAR);  
	}

	/**
	 * java计算两个日期之间的年数 yearDateDiff  20180306
	 */
	public static int getYearsOfDifDate(Date startDate,Date endDate){  
		Calendar calBegin = Calendar.getInstance(); //获取日历实例  
		Calendar calEnd = Calendar.getInstance();  
		calBegin.setTime(startDate);//字符串按照指定格式转化为日期  
		calEnd.setTime(endDate);  
		return calEnd.get(Calendar.YEAR) - calBegin.get(Calendar.YEAR);  
	}

	/**
	 * java计算两个日期之间的年数 yearDayDiff  20180306
	 */
	public static int getDaysOfDifDate(Date startDate,Date endDate){  
		Calendar calBegin = Calendar.getInstance(); //获取日历实例  
		Calendar calEnd = Calendar.getInstance();  
		calBegin.setTime(startDate);//字符串按照指定格式转化为日期  
		calEnd.setTime(endDate);  
		return calEnd.get(Calendar.DAY_OF_MONTH) - calBegin.get(Calendar.DAY_OF_MONTH);  
	}

	/**
	 * java计算两个日期之间相差天数-20210520
	 */
	public static int getDays(String str1,String str2) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date1=null; 
		Date date2=null;
		try {
			date1 = format.parse(str1);
			date2 = format.parse(str2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int days=0;
		if(date1 != null && date2 !=null) {
			days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24)+1);
		}
		return days;
	}

	/**
	 * java计算两个日期之间相差天数-20210520
	 */
	public static int getDays(Date date1,Date date2) {
		int days=0;
		if(date1 != null && date2 !=null) {
			days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24)+1);
		}
		return days;
	}


	public static Date stringTodate(String dateStr, String formatStr) {  
		// 如果时间为空则默认当前时间  
		Date date = null;  
		SimpleDateFormat format = new SimpleDateFormat(formatStr);  
		if (dateStr != null && !dateStr.equals("")) {    
			String time = "";  
			try {  
				Date dateTwo = format.parse(dateStr);  
				time = format.format(dateTwo);  
				date = format.parse(time);  
			} catch (Exception e) {  
				e.printStackTrace();  
			}  

		} else {  
			String timeTwo = format.format(new Date());  
			try {  
				date = format.parse(timeTwo);  
			} catch (Exception e) {  
				e.printStackTrace();  
			}  
		}  
		return date;  
	}

	/**
	 * 由出生日期获得年龄 
	 * @param birthday 出生日期
	 * @return
	 */
	public static int getAge(String strDate) throws Exception {
		Date birthday=DateTools.getStr2Date(strDate);
		return DateTools.getAge(birthday);
	}

	/**
	 * 由出生日期获得年龄 
	 * @param birthday 出生日期
	 * @return
	 */
	public static int getAge(Date birthday) {
		int nl=0;
		if(birthday!=null){
			Calendar date1 = Calendar.getInstance();
			date1.setTime(birthday);
			int year1=date1.get(Calendar.YEAR);
			Calendar date2 = Calendar.getInstance();
			date2.setTime(new Date());
			int year2=date2.get(Calendar.YEAR);
			nl=year2-year1;
		}
		return nl;
	}

	/**
	 * 计算相差时间
	 * @param paraDate
	 * @return
	 */
	public static int dateDiff(String paraDate){
		long min = 0L;
		// 按照传入的格式生成一个simpledateformate对象
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		long diff;
		int Differ=0;
		Date curDate = new Date(System.currentTimeMillis());//获取当前时间
		String str = sd.format(curDate);
		try {
			// 获得两个时间的毫秒时间差异
			diff = sd.parse(paraDate).getTime() - sd.parse(str).getTime();
			min = diff % nd % nh / nm;// 计算差多少分钟
			Differ= new Long(min).intValue(); 
			return Differ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Differ;
	}

	public static final int daysBetween(Date early, Date late) { 
		java.util.Calendar calst = java.util.Calendar.getInstance();   
		java.util.Calendar caled = java.util.Calendar.getInstance();   
		calst.setTime(early);   
		caled.setTime(late);   
		//设置时间为0时   
		calst.set(java.util.Calendar.HOUR_OF_DAY, 0);  
		calst.set(java.util.Calendar.MINUTE, 0);   
		calst.set(java.util.Calendar.SECOND, 0);   
		caled.set(java.util.Calendar.HOUR_OF_DAY, 0);   
		caled.set(java.util.Calendar.MINUTE, 0);   
		caled.set(java.util.Calendar.SECOND, 0);   
		//得到两个日期相差的天数   
		int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst   
				.getTime().getTime() / 1000)) / 3600 / 24;   
		return days;   
	} 


	/**
	 * 计算日期是否是一年前
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static final boolean getDifference(Date date){
		int day = 365;
		Date date2 = new Date();
		//是闰年
		if (DateTools.isLeapYear(date2)) {
			if (DateTools.getMonth(date2)>2) {
				day = 366;
			}
		}
		//是闰年
		if (DateTools.isLeapYear(date)) {
			if (DateTools.getMonth(date)<=2) {
				day = 366;
			}
		}
		Date tempDate = getDateBefore(date2, day);
		if (date.getTime() < tempDate.getTime()) {
			System.out.println(date + "在" + tempDate + "前面");
			return true;
		} else if (date.getTime() > date2.getTime()) {
			System.out.println(date + "在" + tempDate + "后面");
			return false;
		} else {
			System.out.println("是同一天的同一时间");
			return false;
		}

	}
	/**
	 * 得到几天前的时间
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateBefore(Date d,int day){
		Calendar now =Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE,now.get(Calendar.DATE)-day);
		return now.getTime();
	}

	/**
	 * 得到几天后的时间
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateAfter(Date d,int day){
		Calendar now =Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE,now.get(Calendar.DATE)+day);
		return now.getTime();

	}

	/**
	 * 计算日期是否是一个月前
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static final boolean getMonDifference(Date date){
		Date date2 = new Date();

		Date tempDate;
		try {
			tempDate = getBeforeOne(date2);
			if (date.getTime() < tempDate.getTime()) {
				return true;
			} else if (date.getTime() > date2.getTime()) {
				return false;
			} else {
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}





	}

	//得到前一个的日期
	public static Date getBeforeOne(Date date) throws ParseException {
		String dateStr = "";
		String monStr = "";
		String dayStr = "";
		int yer = DateTools.getYear(date);
		int day = DateTools.getDay(date);
		int mon = DateTools.getMonth(date);
		if (mon<2) {
			yer = yer-1;
			if (day > getDaysOfMonth(date)) {
				day = getDaysOfMonth(date);
			}
			monStr = "12";
			if (day<10) {
				dayStr = Constants.ZERO+day;
			}else{
				dayStr = ""+day;
			}
			dateStr = yer + "-" +monStr + "-" + dayStr;
		}else{
			mon = mon-1;
			if (day > getDaysOfMonth(date)) {
				day = getDaysOfMonth(date);
			}
			if (mon<10) {
				monStr = Constants.ZERO+mon;
			}else{
				monStr = ""+mon;
			}
			if (day<10) {
				dayStr = Constants.ZERO+day;
			}else{
				dayStr = ""+day;
			}
			dateStr = yer + "-" +monStr + "-" + dayStr;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		return sdf.parse(dateStr);
	}  

	/**
	 * 得到当前日期的当前月份的天数
	 * @param date
	 * @return
	 */
	public static int getDaysOfMonth(Date date) {  
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(date);  
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);  
	}  

	/**
	 * 默认日期格式
	 */
	public static String DEFAULT_FORMAT = "yyyy-MM-dd";

	/**
	 * 格式化日期
	 */
	public static String formatDate(Date date){
		SimpleDateFormat f = new SimpleDateFormat(DEFAULT_FORMAT);
		String sDate = f.format(date);
		return sDate;
	}

	/**
	 * 获取当年的第一天
	 */
	public static Date getCurrYearFirst(){
		Calendar currCal=Calendar.getInstance();  
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearFirst(currentYear);
	}

	/**
	 * 获取当年的最后一天
	 */
	public static Date getCurrYearLast(){
		Calendar currCal=Calendar.getInstance();  
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearLast(currentYear);
	}

	/**
	 * 获取某年第一天日期
	 */
	public static Date getYearFirst(int year){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return currYearFirst;
	}

	/**
	 * 获取某年最后一天日期
	 */
	public static Date getYearLast(int year){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();

		return currYearLast;
	}

	public static Boolean verificateByIdcard(Integer idType,String idcard) {
		boolean flag=true;
		if (null!=idType && StringUtils.isNotEmpty(idcard)) {
			switch (idType) {
			case 184:
				boolean b = IdcardVerify.isValidIdcard(idcard);
				if (!b) {flag=false;}
				break;
			default:
				String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
				Pattern p = Pattern.compile(regEx);
				Matcher m = p.matcher(idcard);
				boolean find = m.find();
				if (idcard.length()<6 || idcard.length()>11 || find) {
					flag=false;
				}
				break;
			}
		}
		return flag;
	}

	// 计算两个时间差，返回为分钟。
	public static long CalTime(Date d1, Date d2) {
		long minutes = 0L;
		long diff = d1.getTime() - d2.getTime();
		minutes = diff / (1000 * 60);
		return minutes;
	}

	//两个日期之间的月份之差 月份 两个日期间的月份数 月份差 
	public static int getMonthDiff(Date d1, Date d2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);
		int year1 = c1.get(Calendar.YEAR);
		int year2 = c2.get(Calendar.YEAR);
		int month1 = c1.get(Calendar.MONTH);
		int month2 = c2.get(Calendar.MONTH);
		int day1 = c1.get(Calendar.DAY_OF_MONTH);
		int day2 = c2.get(Calendar.DAY_OF_MONTH);
		// 获取年的差值 
		int yearInterval = year1 - year2;
		// 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
		if (month1 < month2 || month1 == month2 && day1 < day2) {
			yearInterval--;
		}
		// 获取月数差值
		int monthInterval = (month1 + 12) - month2;
		if (day1 < day2) {
			monthInterval--;
		}
		monthInterval %= 12;
		int monthsDiff = Math.abs(yearInterval * 12 + monthInterval);
		return monthsDiff;
	}


	/**
	 * 
	 * @param date1 被比较的时间
	 * @param date2 现在的时间
	 * @return
	 */
	public static long ShowTimeInterval(Date date1, Date date2) throws Exception{
		long lDate1 = date1.getTime();
		long lDate2 = date2.getTime();
		long diff = (lDate1 < lDate2) ? (lDate2 - lDate1) : (lDate1 - lDate2);
		long day = diff / (24 * 60 * 60 * 1000);
		long hour = diff / (60 * 60 * 1000) - day * 24;
		long min = diff / (60 * 1000) - day * 24 * 60 - hour * 60;
		long sec = diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60;
		return sec;
	}

	/**得到当前时间*/
	public static synchronized Date getLockTime(){
		Calendar nowTime = Calendar.getInstance();//得到当前时间
		nowTime.add(Calendar.MINUTE, 5);
		return nowTime.getTime();
	}

	/**得到当前时间*/
	public static synchronized Date getCurrentTime(){
		Calendar nowTime = Calendar.getInstance();
		return nowTime.getTime();
	}

	/**String转long*/
	public static long pareLong(String time){
		SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		long s=0;
		try {
			s=sim.parse(time).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}








}