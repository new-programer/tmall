/**
 * 这个类的作用是初始化驱动，并且提供一个getConnection用于获取连接。 
 * 在后续的所有DAO中，当需要获取连接的时候，都采用这种方式进行。
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
	static String driver = "com.mysql.jdbc.Driver";  //数据库驱动
	static String ip = "127.0.0.1";  //服务器ip地址
	static int port = 3306;				//服务器端口好
	static String database = "tmall";   //要访问的数据库
	static String encoding = "UTF-8";   //编码方式
	static String name = "root";  //登录数据库的用户名
	static String password = "peter123.";  //登录数据库的密码
	
	static 
	{
		try
		{
			Class.forName(driver);  //加载数据库驱动
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	//数据库连接获取函数（返回数据库连接）
	public static Connection getConnection() throws SQLException
	{
		//数据库连接url
		String url = String.format("jdbc:mysql://%s:%d/%s?characterEncoding=%s", ip, port, database, encoding);
		//获得数据库连接并返回
		return DriverManager.getConnection(url, name, password);
	}
	
	
	//用于测试该工具类
	public static void main(String[] args)
	{
		System.out.println("getConnection()");
	}
}
