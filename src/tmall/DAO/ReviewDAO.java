/**
 * tmall数据库   的review表的结构：
 *  +------------+---------------+------+-----+---------+----------------+
	| Field      | Type          | Null | Key | Default | Extra          |
	+------------+---------------+------+-----+---------+----------------+
	| id         | int(11)       | NO   | PRI | NULL    | auto_increment |
	| ptid       | int(11)       | YES  | MUL | NULL    |                |
	| uid        | int(11)       | YES  | MUL | NULL    |                |
	| content    | varchar(4000) | YES  |     | NULL    |                |
	| CreateDate | datetime      | YES  |     | NULL    |                |
	+------------+---------------+------+-----+---------+----------------+
	
	
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
import tmall.Util.DateUtil;
import tmall.bean.Product;
import tmall.bean.Review;
import tmall.bean.User;

/**
 * @author Administrator
 *
 */
public class ReviewDAO
{
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		//基本的CRUD操作
	//1  增
	public boolean add(Review review)
	{
		boolean bool = false;
		//连接数据库
		try
		{
			conn = DBConn.getConnection();
			String sql = "insert into Review values (null, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, review.getProduct().getId());  //获取某一商品的id
			pstmt.setInt(2, review.getUser().getId());    //用户id
			pstmt.setString(3, review.getContent());
			pstmt.setTimestamp(4, DateUtil.date_to_timestamp(review.getCreateDate())); //日期转换
			
			bool = pstmt.execute();   //执行插入操作,执行结果返回给bool
			rs = pstmt.getGeneratedKeys();   //获取自增id
			if(rs.next())
			{
				//将自动生成的id(自增id)从rs 数据集合中取出传给setId()
				//目的：是实体类Review各对象的id和数据库保持一致
				review.setId(rs.getInt(1));
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
			String sql = "delete from review where id = " + id;
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
	public boolean update(Review review)
	{
		boolean bool = false;
		//连接数据库
		try
		{
			conn = DBConn.getConnection();
			String sql = "update review set ptid=?, uti=?, content=?, CreateDate=? where id=?";
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, review.getProduct().getId());  //获取某一商品的id
			pstmt.setInt(2, review.getUser().getId());    //用户id
			pstmt.setString(3, review.getContent());
			pstmt.setTimestamp(4, DateUtil.date_to_timestamp(review.getCreateDate())); //日期转换
			pstmt.setInt(5, review.getId());
			
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
	public Review get(int id)
	{
		Review review = null;
		//连接数据库
		try
		{
			conn = DBConn.getConnection();
			String sql = "select * from review where id = " + id;
			stmt = conn.createStatement();  //创建statement类对象，用来执行SQL语句！！
			rs = stmt.executeQuery(sql);  //用rs 来收集查询结果集
			
			if (rs.next())
			{
				review = new Review();
				
				review.setId(id);
				review.setContent(rs.getString("content"));
				review.setCreateDate(rs.getDate("CreateDate"));
				
				int ptid = rs.getInt("ptid");  //从rs中获得ptid的值
				//下面通过ptid，调用ProductDAO类的get()方法来返回ptid对应的Product类的一个实例化对象
				Product product = new ProductDAO().get(ptid);
				//再将该product传给Review对象的setProduct()函数
				review.setProduct(product);
				
				int uid = rs.getInt("uid");
				User user = new UserDAO().get(uid);
				review.setUser(user);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		//断开数据库连接
		DBClose.disconnection(stmt, conn);
		return review;   //返回结果
	}
	
	//5  获取总数
	public int getTotal()
	{
		int total = 0;
		//连接数据库
		try
		{
			conn = DBConn.getConnection();
			String sql = "select count(*) from review";  //查询总是的sql语句
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if (rs.next())
			{
				total = rs.getInt(1);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		//断开数据库连接
		DBClose.disconnection(stmt, conn);
		return total;  //返回结果
	}
	
	//6   分页查询（某一商品下的所有评价）
	public List<Review> list(Product product, int start, int count)
	{
		List <Review> reviews = null;
		//连接数据库
		try
		{
			conn = DBConn.getConnection();
			String sql = "select * from review where ptid=? order by ptid desc limit ?,?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, product.getId());
			pstmt.setInt(2, start);
			pstmt.setInt(3, count);
			
			rs = pstmt.executeQuery();
			
			while (rs.next())
			{
				reviews = new ArrayList<>();
				Review review = new Review();  //实例化review
				
				review.setId(rs.getInt("id"));
				review.setProduct(product);
				review.setUser(new UserDAO().get(rs.getInt("uid")));
				review.setContent(rs.getString("content"));
				review.setCreateDate(rs.getDate("CreateDate"));
				
				reviews.add(review);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		DBClose.disconnection(pstmt, conn);
		return reviews;  //返回结果
	}
	
	//7  全查询
	public List<Review> list(Product product)
	{
		return list(product, 0, Short.MAX_VALUE);
	}
	
	//8  获取某一商品评价总数（传的参数是商品的id）
	public int getTotal(int ptid)
	{
		int ptotal = 0;
		try
		{
			conn = DBConn.getConnection();
			String sql = "select count(*) from review where = " + ptid;
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if (rs.next())
			{
				ptotal = rs.getInt(1);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		DBClose.disconnection(stmt, conn);
		return ptotal;
	}
	
	//9  追加评价   （该功能待以后完善）
	/*public void exendReview()
	{
		
	}*/
}
