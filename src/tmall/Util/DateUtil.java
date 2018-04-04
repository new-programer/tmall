/**
 * ������ڹ�������Ҫ������java.util.Date����java.sql.Timestamp ��Ļ���ת����
��Ϊ:
��ʵ�������������͵����ԣ�ʹ�õĶ���java.util.Date�ࡣ
��Ϊ����MySQL�е����ڸ�ʽ�ﱣ��ʱ����Ϣ������ʹ��datetime���͵��ֶΣ�
��jdbcҪ��ȡdatetime�����ֶε���Ϣ����Ҫ����java.sql.Timestamp����ȡ��
����ֻ�ᱣ��������Ϣ������ʧʱ����Ϣ�� 
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
	//��Date��ʱ��תΪTimestamp��ʱ��
	public static Timestamp date_to_timestamp(Date d)
	{
		if (d == null)
			return null;
		return new Timestamp(d.getTime());
	}
	//��Timestamp��ʱ��תΪDate��ʱ��
	/*
	 * java.sql���µ�Date��Time��Timestamp�����඼��java.util.Date�����࣬
	�ӽ�����л�õ����ڲ�����Ҫ�ֶ�ת����java.util.Date��DateUtil�е�t2d������ʵ����ʡ��
	*/
	public static Date timestamp_to_date(Timestamp t)
	{
		if (t == null)
			return null;
		return new Date(t.getTime());
	}
}
