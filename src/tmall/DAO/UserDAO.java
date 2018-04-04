/**
 * tmall数据库 user表结构参考：
 *  +----------+-------------+------+-----+---------+----------------+
	| Field    | Type        | Null | Key | Default | Extra          |
	+----------+-------------+------+-----+---------+----------------+
	| id       | int(11)     | NO   | PRI | NULL    | auto_increment |
	| name     | varchar(50) | YES  |     | NULL    |                |
	| password | varchar(50) | YES  |     | NULL    |                |
	+----------+-------------+------+-----+---------+----------------+
 * 主要是User的CRUD基本操作以及一些非CRUD操作如下：   
 * 1.获取属性总数    public int getTotal()
 * 2.分页展示查询    public List<User> list(int start, int count)
 * 3.查询全部属性    public List<User> list()
 * 4.根据用户名获取用户   public User get(String name)
 * 5.根据用户名判断用户是否存在  public isExist(String name)
 * 6.根据用户名和密码来获取用户从而判断用户名和密码是否匹配   public User get(String name, String password)
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
	//1  增
	public boolean add(User user)
	{
		boolean bool = false;
		//连接数据库
		try
		{
			conn = DBConn.getConnection();
			String sql = "insert into user values (null, ?, ?)";
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getPassword());
			
			bool = pstmt.execute();   //执行插入操作,执行结果返回给bool
			rs = pstmt.getGeneratedKeys();   //获取自增id
			if(rs.next())
			{
				//将自动生成的id(自增id)从rs 数据集合中取出传给setId()
				//目的：是实体类User各对象的id和数据库保持一致
				user.setId(rs.getInt(1));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		//断开数据库连接
		DBClose.disconnection(pstmt, conn);
		return bool;  //返回插入操作执行结果（成功true,失败false）
	}
	
	//2  删（根据id）
	public boolean delete (int id)
	{
		boolean bool = false;
		//连接数据库
		try
		{
			conn = DBConn.getConnection();
			String sql = "delete from user where id = " + id;
			stmt = conn.createStatement(); //创建statement类对象，用来执行SQL语句！！
			bool = stmt.execute(sql);//执行删除操作,执行结果返回给bool
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		//断开数据库连接
		DBClose.disconnection(stmt, conn);
		return bool;    //返回删除操作执行结果（成功true,失败false）
	}
	
	//3  改
	public boolean update(User user)
	{
		boolean bool = false;
		//连接数据库
		try
		{
			conn = DBConn.getConnection();
			String sql = "update user set name=?, password=? where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getPassword());
			pstmt.setInt(3, user.getId());
			
			bool = pstmt.execute(); //执行更新语句，返回更新结果给bool
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		//断开数据库连接
		DBClose.disconnection(pstmt, conn);
		return bool;    //返回修改操作执行结果（成功true,失败false）
	}
	
	//4  查(根据id查)
	public User get(int id)
	{
		User user = new User();
		//连接数据库
		try
		{
			conn = DBConn.getConnection();
			String sql = "select * from user where id = " + id;
			stmt = conn.createStatement();  //创建statement类对象，用来执行SQL语句！！
			rs = stmt.executeQuery(sql);  //用rs 来收集查询结果集
			
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
		//断开数据库连接
		DBClose.disconnection(stmt, conn);
		return user;   //返回结果
	}
	
	//非CRUD操作 涉及List集合
	//5  分页查
	public List<User> list (int start, int count)
	{
		//用users集合来收集查到的user
		List<User> users = new ArrayList<>();
		//连接数据库
		try
		{
			conn = DBConn.getConnection();
			String sql = "select * from user order by id desc limit ?, ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, count);
			rs = pstmt.executeQuery();
			
			
			//遍历暂存在rs中的查询结果
			while (rs.next())
			{
				User user = new User();
				user.setId(rs.getInt(1));
				user.setName(rs.getString(2));
				user.setPassword(rs.getString(3));
				
				users.add(user);  //讲遍历到的user添加到users集合list中去
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		//断开数据库连接
		DBClose.disconnection(pstmt, conn);
		return users;
	}
	
	//6  全部查
	public List<User> list()
	{
		return list(0, Short.MAX_VALUE);  //调用public List<User> list (int start, int count)方法
	}
	
	//7  获取用户总数
	public int getTotal()
	{
		int total = 0;
		//连接数据库
		try
		{
			conn = DBConn.getConnection();
			String sql = "select count()* from user";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if (rs.next())
			{
				total = rs.getInt(1);   //将查询结果赋给total
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		//断开数据库连接
		DBClose.disconnection(stmt, conn);
		return total; //返回结果
	}
	
	//非CRUD操作  （用于登录或注册检测）
	//8  by name
	public User get(String name)
	{
		User user = new User(); //实例化一个User类对象
		//连接数据库
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
		//断开数据库连接
		DBClose.disconnection(stmt, conn);
		return user;
	}
	
	//9  by name and password
	public User get(String name, String password)
	{
		User user = new User();  //实例化一个User类对象
		//连接数据库
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
		//断开数据库连接
		DBClose.disconnection(pstmt, conn);
		return user;
	}
	
	//10  isExist()
	public boolean isExist(String name)
	{
		User user = get(name);
		return user != null;  //如果user为null则不存在（返回false），否在存在（返回true）
	}
}