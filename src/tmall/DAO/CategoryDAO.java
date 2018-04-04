/**
 * 该类主要是对表category的增删改查（CRUD  create read update delete）
 * tmall数据库  category表结构如下：
 *  +-------+-------------+------+-----+---------+----------------+
	| Field | Type        | Null | Key | Default | Extra          |
	+-------+-------------+------+-----+---------+----------------+
	| id    | int(11)     | NO   | PRI | NULL    | auto_increment |
	| name  | varchar(50) | YES  |     | NULL    |                |
	+-------+-------------+------+-----+---------+----------------+
 */
package tmall.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.Util.DBConn;
import tmall.bean.Category;

/**
 * @author Administrator
 *
 */
public class CategoryDAO
{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
	//1  增
	public void add(Category category)
	{
		//连接数据库并执行插入操作
		try
		{
			conn = DBConn.getConnection();
			String sql = "insert into category values(null, ?)"; //数据库插入语句
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, category.getName());
			pstmt.execute();  //执行插入操作
			
			//自增id只能通过Statement或PreparedStatement的getGeneratedKeys()方法来获取
			rs = pstmt.getGeneratedKeys();
			if (rs.next())
			{
				int id = rs.getInt(1);  //从rs数据集合中取出自增id并赋值给id
				category.setId(id);  //在实体category中设置id的值
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	//2  删（根据id进行删除操作）
	public void delete(int id)
	{
		//连接数据库并执行插入操作
		try
		{
			conn = DBConn.getConnection();
			String sql = "delete from category where id = ？";//以id为判断条件
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.execute();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
	//3  改
	public void update(Category category)
	{
		//连接数据库并
		try
		{
			conn = DBConn.getConnection();
			String sql = "update category set name = ? where id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, category.getName());
			pstmt.setInt(2, category.getId());
			
			pstmt.execute();//执行更新操作
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	//4  查（根据id查）
		public Category get(int id)
		{
			Category category = new Category();  //实例化一个Category对象
			//连接数据库并进行查询操作
			try
			{
				conn = DBConn.getConnection();
				String sql = "select * from category where id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, id);
				
				rs = pstmt.executeQuery();
				if (rs.next())
				{
					category.setId(id);
					category.setName(rs.getString(2));//将从rs数据集中获取的name赋值给setName
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			return category;  //返回结果
		}
	
		//非CRUD操作 涉及List集合
	//5   分页查
		public List<Category> list(int start, int count)
		{
			//定义一个Category 类集合
			List<Category> categories = new ArrayList<>();
			//连接数据库
			try
			{
				conn = DBConn.getConnection();
				String sql = "select * from category order by id desc limit ?,?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, start);
				pstmt.setInt(2, count);
				
				rs = pstmt.executeQuery();
				while (rs.next())
				{
					Category category = new Category();
					category.setId(rs.getInt(1));   //将从rs数据集中获取的id赋值给setId
					category.setName(rs.getString(2));  //将从rs数据集中获取的name赋值给setName
					
					categories.add(category);//将对象category加入category类集合categories中去
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			
			return categories;  //返回category类集合categories
		}
	
	//6  全部查
		public List<Category> list()
		{
			//也可以通过调用public List<Category> list(int start, int count)来查询全部信息
			return list(0, Short.MAX_VALUE);  //顾名思义：返回short类型最大值
			
/*	下面的代码和public List<Category> list(int start, int count)里面的代码有很大相似性，直接用上面的方式获取结果即可
 * 		List<Category> categories = new ArrayList<>();
			//连接数据库
			try
			{
				conn = DBConn.getConnection();
				String sql = "select * from category";  //查询全部信息
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();  //将查结果放入rs中
				
				while (rs.next())
				{
					Category category = new Category();
					category.setId(rs.getInt(1));
					category.setName(rs.getString(2));
					categories.add(category);
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			return categories; //返回查询结果集合
*/		
		}
	
	//7  获取总数
		public int getTotal()
		{
			int total = 0;
			//连接数据库并执行查询操作
			try
			{
				conn = DBConn.getConnection();
				String sql = "select count(*) from category";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				if (rs.next())
				{
					total = rs.getInt(1); //将查询结果赋给total
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			
			return total;
		}
	
}