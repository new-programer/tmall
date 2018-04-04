/**
 * �����������ǳ�ʼ�������������ṩһ��getConnection���ڻ�ȡ���ӡ� 
 * �ں���������DAO�У�����Ҫ��ȡ���ӵ�ʱ�򣬶��������ַ�ʽ���С�
 */
package tmall.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Administrator
 *
 */
public class DBConn
{
	static String driver = "com.mysql.jdbc.Driver";  //���ݿ�����
	static String ip = "127.0.0.1";  //������ip��ַ
	static int port = 3306;				//�������˿ں�
	static String database = "tmall";   //Ҫ���ʵ����ݿ�
	static String encoding = "UTF-8";   //���뷽ʽ
	static String name = "root";  //��¼���ݿ���û���
	static String password = "peter123.";  //��¼���ݿ������
	
	static 
	{
		try
		{
			Class.forName(driver);  //�������ݿ�����
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	//���ݿ����ӻ�ȡ�������������ݿ����ӣ�
	public static Connection getConnection() throws SQLException
	{
		//���ݿ�����url
		String url = String.format("jdbc:mysql://%s:%d/%s?characterEncoding=%s", ip, port, database, encoding);
		//������ݿ����Ӳ�����
		return DriverManager.getConnection(url, name, password);
	}
	
	
	//���ڲ��Ըù�����
	public static void main(String[] args)
	{
		System.out.println("getConnection()");
	}
}
