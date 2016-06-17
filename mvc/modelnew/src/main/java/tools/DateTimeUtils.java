package tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期和时间工具类.
 * 本类中日期字符串格式为:				yyyy-MM-dd,
 * 时间字符串格式为:					HH:mm:ss,
 * 日期和时间混合字符串格式:				yyyy-MM-dd HH:mm:ss
 * 没有分隔符的日期和时间混合字符串格式：	yyyyMMddHHmmss
 */
public class DateTimeUtils {
	public static final SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
	public static final SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat sdfDateTimeA = new SimpleDateFormat("yyyyMMddHHmmss");
	public static Calendar calendar= Calendar.getInstance(); 
	
	/**
	 * 获得日期字符串 	yyyy-MM-dd
	 * @return	日期字符串
	 */
	public static String getDate(){
		return sdfDate.format(new Date());
	}
	/**
	 * 获得时间字符串	HH:mm:ss
	 * @return	时间字符串
	 */
	public static String getTime(){
		return sdfTime.format(new Date());
	}	
	/**
	 * 获得日期和时间混合字符串	yyyy-MM-dd HH:mm:ss
	 * @return 日期和时间混合字符串
	 */
	public static String getDateTime(){
		return sdfDateTime.format(new Date()); 
	}
	/**
	 * 获得日期和时间混合字符串	yyyyMMddHHmmss
	 * @return 日期和时间混合字符串
	 */
	public static String getDateTimeA(){
		return sdfDateTimeA.format(new Date()); 
	}
	/**
	 * 返回原日期提前或延后若干天的日期 ,sdf指定了date的格式
	 * @param date	字符串日期
	 * @param days	要加上的天数，可以为负数表示相减
	 * @param sdf	指定的SimpleDateFormat
	 * @return
	 */
	public static String addDays(String date,int days,SimpleDateFormat sdf){		
		try {
			Date oldDate = sdf.parse(date);			
			calendar.setTime(oldDate);
			calendar.add(Calendar.DAY_OF_MONTH, days);
			return sdf.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}			
	}
	/**
	 * 比较两个字符串表示的日期的大小，sdf指定的格式
	 * @param date1	第一个日期
	 * @param date2	第二个日期
	 * @param sdf	指定的SimpleDateFormat
	 * @return		正数大于，0等于，负数小于，null出错
	 */
	public static Integer compare(String date1,String date2,SimpleDateFormat sdf){
		try {
			Date d1 = sdf.parse(date1);
			Date d2 = sdf.parse(date2);
			return d1.compareTo(d2);
		} catch (ParseException e) {
			e.printStackTrace();			
			return null;
		}	
	}
	
	/**
	 * 返回指定月份的最后一天
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getLastDayOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		calendar.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		int lastDay = calendar.get(Calendar.DATE);
		return lastDay;
	}
	
}