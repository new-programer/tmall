/**
 * 主要是用来断开数据库连接
 */
package tmall.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Administrator
 *
 */
public class DBClose
{
		//	断开PreparedStatement方式的Connection
	public static void disconnection (PreparedStatement pstmt, Connection conn)
	{
		
		try
		{
			if (pstmt != null)
			{
				pstmt.close();
			}
			
			if (conn != null)
			{
				conn.close();
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	//	断开Statement方式的Connection
	public static void disconnection (Statement stmt, Connection conn)
	{
		
		try
		{
			if (stmt != null)
			{
				stmt.close();
			}
			
			if (conn != null)
			{
				conn.close();
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
