/**tmall数据库property表结构参考：
 *  +-------+-------------+------+-----+---------+----------------+
	| Field | Type        | Null | Key | Default | Extra          |
	+-------+-------------+------+-----+---------+----------------+
	| id    | int(11)     | NO   | PRI | NULL    | auto_increment |
	| cid   | int(11)     | YES  | MUL | NULL    |                |
	| name  | varchar(50) | YES  |     | NULL    |                |
	+-------+-------------+------+-----+---------+----------------+
 * 主要是基本的CRUD操作和非CRUD操作（如下）
 * 1.获取某一分类（cid）的属性总数    public int getTotal(int cid)
 * 2.分页展示查询    public List<Property> list(int start, int count)
 * 3.查询全部属性    public List<Property> list()
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
import tmall.bean.Category;
import tmall.bean.Property;

/**
 * @author Administrator
 *
 */
public class PropertyDAO
{
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
	//基本的CRUD操作
	//1  增
	public boolean add(Property property)
	{
		boolean bool = false;
		//连接数据库
		try
		{
			conn = DBConn.getConnection();
			String sql = "insert into property values (null, ?, ?)";
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, property.getCategory().getId());  //获取某一分类的id
			pstmt.setString(2, property.getName());
			bool = pstmt.execute();   //执行插入操作,执行结果返回给bool
			rs = pstmt.getGeneratedKeys();   //获取自增id
			if(rs.next())
			{
				//将自动生成的id(自增id)从rs 数据集合中取出传给setId()
				//目的：是实体类Property各对象的id和数据库保持一致
				property.setId(rs.getInt(1));
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
			String sql = "delete from property where id = " + id;
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
	public boolean update(Property property)
	{
		boolean bool = false;
		//连接数据库
		try
		{
			conn = DBConn.getConnection();
			String sql = "update property set cid=?, name=? where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, property.getCategory().getId());
			pstmt.setString(2, property.getName());
			pstmt.setInt(3, property.getId());
			
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
	public Property get(int id)
	{
		Property property = new Property();
		//连接数据库
		try
		{
			conn = DBConn.getConnection();
			String sql = "select * from property where id = " + id;
			stmt = conn.createStatement();  //创建statement类对象，用来执行SQL语句！！
			rs = stmt.executeQuery(sql);  //用rs 来收集查询结果集
			
			if (rs.next())
			{
				property.setId(id);
				property.setName(rs.getString("name"));
				
				int cid = rs.getInt("cid");  //从rs中获得cid的值
				//下面通过cid，调用CategoryDAO类的get()方法来返回cid对应的Category类的一个实例化对象
				Category category = new CategoryDAO().get(cid);
				//再将该category传给property的setCategory()函数
				property.setCategory(category);
			
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		//断开数据库连接
		DBClose.disconnection(stmt, conn);
		return property;   //返回结果
	}
	
	//非CRUD基本操作
	//5  获取属性总数 （注意是某一分类对应的属性总数，故必须先获取Category 类id :'cid'）
	public int getTotal(int cid)
	{
		int total = 0;
		//连接数据库
		try
		{
			conn = DBConn.getConnection();
			String sql = "select count()* from property where cid = " + cid;
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
	
	//6  分页查
	public List<Property> list (int cid, int start, int count)
	{
		//用properties集合来收集查的property
		List<Property> properties = new ArrayList<>();
		//连接数据库
		try
		{
			conn = DBConn.getConnection();
			String sql = "select * from Property where cid=? order by id desc limit ?, ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cid);
			pstmt.setInt(2, start);
			pstmt.setInt(3, count);
			
			rs = pstmt.executeQuery();
			
			//遍历暂存在rs中的查询结果
			while (rs.next())
			{
				Property property = new Property();
				property.setId(rs.getInt(1));
				property.setName(rs.getString("name"));
				
				//下面通过cid，调用CategoryDAO类的get()方法来返回cid对应的Category类的一个实例化对象
				Category category = new CategoryDAO().get(cid);
				//再将该category传给property的setCategory()函数
				property.setCategory(category);
				
				properties.add(property);  //讲遍历到的property添加到propertys集合list中去
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		//断开数据库连接
		DBClose.disconnection(pstmt, conn);
		return properties;
	}
	
	//7  全部查
	public List<Property> list(int cid)
	{
		return list(cid,0, Short.MAX_VALUE);  //返回cid对应的某分类属性总数
	}
}
