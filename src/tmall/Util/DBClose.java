/**
 * ��Ҫ�������Ͽ����ݿ�����
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
		//	�Ͽ�PreparedStatement��ʽ��Connection
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
	
	//	�Ͽ�Statement��ʽ��Connection
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
