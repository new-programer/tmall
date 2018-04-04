/**
 * 这个日期工具类主要是用于java.util.Date类与java.sql.Timestamp 类的互相转换。
因为:
在实体类中日期类型的属性，使用的都是java.util.Date类。
而为了在MySQL中的日期格式里保存时间信息，必须使用datetime类型的字段，
而jdbc要获取datetime类型字段的信息，需要采用java.sql.Timestamp来获取，
否则只会保留日期信息，而丢失时间信息。 
 */
package tmall.Util;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Administrator
 *
 */
public class DateUtil
{
	//将Date类时间转为Timestamp类时间
	public static Timestamp date_to_timestamp(Date d)
	{
		if (d == null)
			return null;
		return new Timestamp(d.getTime());
	}
	//将Timestamp类时间转为Date类时间
	/*
	 * java.sql包下的Date、Time、Timestamp三个类都是java.util.Date的子类，
	从结果集中获得的日期并不需要手动转换成java.util.Date，DateUtil中的t2d方法其实可以省略
	*/
	public static Date timestamp_to_date(Timestamp t)
	{
		if (t == null)
			return null;
		return new Date(t.getTime());
	}
}
