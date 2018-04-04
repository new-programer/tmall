/**
 * tmall���ݿ� user��ṹ�ο���
 *  +----------+-------------+------+-----+---------+----------------+
	| Field    | Type        | Null | Key | Default | Extra          |
	+----------+-------------+------+-----+---------+----------------+
	| id       | int(11)     | NO   | PRI | NULL    | auto_increment |
	| name     | varchar(50) | YES  |     | NULL    |                |
	| password | varchar(50) | YES  |     | NULL    |                |
	+----------+-------------+------+-----+---------+----------------+
 * ��Ҫ��User��CRUD���������Լ�һЩ��CRUD�������£�   
 * 1.��ȡ��������    public int getTotal()
 * 2.��ҳչʾ��ѯ    public List<User> list(int start, int count)
 * 3.��ѯȫ������    public List<User> list()
 * 4.�����û�����ȡ�û�   public User get(String name)
 * 5.�����û����ж��û��Ƿ����  public isExist(String name)
 * 6.�����û�������������ȡ�û��Ӷ��ж��û����������Ƿ�ƥ��   public User get(String name, String password)
 */
package tmall.DAO; 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.Util.DBClose;
import tmall.Util.DBConn;
import tmall.bean.User;

/**
 * @author Administrator
 *
 */
public class UserDAO
{
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	//1  ��
	public boolean add(User user)
	{
		boolean bool = false;
		//�������ݿ�
		try
		{
			conn = DBConn.getConnection();
			String sql = "insert into user values (null, ?, ?)";
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getPassword());
			
			bool = pstmt.execute();   //ִ�в������,ִ�н�����ظ�bool
			rs = pstmt.getGeneratedKeys();   //��ȡ����id
			if(rs.next())
			{
				//���Զ����ɵ�id(����id)��rs ���ݼ�����ȡ������setId()
				//Ŀ�ģ���ʵ����User�������id�����ݿⱣ��һ��
				user.setId(rs.getInt(1));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		//�Ͽ����ݿ�����
		DBClose.disconnection(pstmt, conn);
		return bool;  //���ز������ִ�н�����ɹ�true,ʧ��false��
	}
	
	//2  ɾ������id��
	public boolean delete (int id)
	{
		boolean bool = false;
		//�������ݿ�
		try
		{
			conn = DBConn.getConnection();
			String sql = "delete from user where id = " + id;
			stmt = conn.createStatement(); //����statement���������ִ��SQL��䣡��
			bool = stmt.execute(sql);//ִ��ɾ������,ִ�н�����ظ�bool
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		//�Ͽ����ݿ�����
		DBClose.disconnection(stmt, conn);
		return bool;    //����ɾ������ִ�н�����ɹ�true,ʧ��false��
	}
	
	//3  ��
	public boolean update(User user)
	{
		boolean bool = false;
		//�������ݿ�
		try
		{
			conn = DBConn.getConnection();
			String sql = "update user set name=?, password=? where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getPassword());
			pstmt.setInt(3, user.getId());
			
			bool = pstmt.execute(); //ִ�и�����䣬���ظ��½����bool
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		//�Ͽ����ݿ�����
		DBClose.disconnection(pstmt, conn);
		return bool;    //�����޸Ĳ���ִ�н�����ɹ�true,ʧ��false��
	}
	
	//4  ��(����id��)
	public User get(int id)
	{
		User user = new User();
		//�������ݿ�
		try
		{
			conn = DBConn.getConnection();
			String sql = "select * from user where id = " + id;
			stmt = conn.createStatement();  //����statement���������ִ��SQL��䣡��
			rs = stmt.executeQuery(sql);  //��rs ���ռ���ѯ�����
			
			if (rs.next())
			{
				user.setId(id);
				user.setName(rs.getString(1));
				user.setPassword(rs.getString(2));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		//�Ͽ����ݿ�����
		DBClose.disconnection(stmt, conn);
		return user;   //���ؽ��
	}
	
	//��CRUD���� �漰List����
	//5  ��ҳ��
	public List<User> list (int start, int count)
	{
		//��users�������ռ��鵽��user
		List<User> users = new ArrayList<>();
		//�������ݿ�
		try
		{
			conn = DBConn.getConnection();
			String sql = "select * from user order by id desc limit ?, ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, count);
			rs = pstmt.executeQuery();
			
			
			//�����ݴ���rs�еĲ�ѯ���
			while (rs.next())
			{
				User user = new User();
				user.setId(rs.getInt(1));
				user.setName(rs.getString(2));
				user.setPassword(rs.getString(3));
				
				users.add(user);  //����������user��ӵ�users����list��ȥ
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		//�Ͽ����ݿ�����
		DBClose.disconnection(pstmt, conn);
		return users;
	}
	
	//6  ȫ����
	public List<User> list()
	{
		return list(0, Short.MAX_VALUE);  //����public List<User> list (int start, int count)����
	}
	
	//7  ��ȡ�û�����
	public int getTotal()
	{
		int total = 0;
		//�������ݿ�
		try
		{
			conn = DBConn.getConnection();
			String sql = "select count()* from user";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if (rs.next())
			{
				total = rs.getInt(1);   //����ѯ�������total
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		//�Ͽ����ݿ�����
		DBClose.disconnection(stmt, conn);
		return total; //���ؽ��
	}
	
	//��CRUD����  �����ڵ�¼��ע���⣩
	//8  by name
	public User get(String name)
	{
		User user = new User(); //ʵ����һ��User�����
		//�������ݿ�
		try
		{
			conn = DBConn.getConnection();
			String sql = "select * from user where name = " + name;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if(rs.next())
			{
				user.setId(rs.getInt(1));
				user.setName(rs.getString(2));
				user.setPassword(rs.getString(3));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		//�Ͽ����ݿ�����
		DBClose.disconnection(stmt, conn);
		return user;
	}
	
	//9  by name and password
	public User get(String name, String password)
	{
		User user = new User();  //ʵ����һ��User�����
		//�������ݿ�
		try
		{
			conn = DBConn.getConnection();
			String sql = "select * from user where name = ?,  password = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, password);
			
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				user.setId(rs.getInt(1));
				user.setName(rs.getString(2));
				user.setPassword(rs.getString(3));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		//�Ͽ����ݿ�����
		DBClose.disconnection(pstmt, conn);
		return user;
	}
	
	//10  isExist()
	public boolean isExist(String name)
	{
		User user = get(name);
		return user != null;  //���userΪnull�򲻴��ڣ�����false�������ڴ��ڣ�����true��
	}
}