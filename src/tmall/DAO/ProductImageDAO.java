/**
 * tmall数据库表结构如下：
 *  +-------+--------------+------+-----+---------+----------------+
	| Field | Type         | Null | Key | Default | Extra          |
	+-------+--------------+------+-----+---------+----------------+
	| id    | int(11)      | NO   | PRI | NULL    | auto_increment |
	| ptid  | int(11)      | YES  | MUL | NULL    |                |
	| type  | varchar(255) | YES  |     | NULL    |                |
	+-------+--------------+------+-----+---------+----------------+
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
import tmall.bean.Product;
import tmall.bean.ProductImage;

/**
 * @author Administrator
 *
 */
public class ProductImageDAO
{
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		//定义两个type常量
		public static final String TYPE_SINGLE = "type_single";//定义单图类型常量
		public static final String TYPE_DETAIL = "type_detail";//定义细节图类型常量
	//基本的CRUD操作
	//1  增
	public boolean add(ProductImage productImage)
	{
		boolean bool = false;
		//连接数据库
		try
		{
			conn = DBConn.getConnection();
			String sql = "insert into ProductImage values (null, ?, ?)";
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, productImage.getProduct().getId());  //获取某一商品的id
			pstmt.setString(2, productImage.getType());
			bool = pstmt.execute();   //执行插入操作,执行结果返回给bool
			rs = pstmt.getGeneratedKeys();   //获取自增id
			if(rs.next())
			{
				//将自动生成的id(自增id)从rs 数据集合中取出传给setId()
				//目的：是实体类ProductImage各对象的id和数据库保持一致
				productImage.setId(rs.getInt(1));
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
			String sql = "delete from productimage where id = " + id;
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
	
	//3  改    //无业务需求  此函数可有可无
	public boolean update(ProductImage ProductImage)
	{
		boolean bool = false;
		//连接数据库
		try
		{
			conn = DBConn.getConnection();
			String sql = "update ProductImage set ptid=?, name=? where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ProductImage.getProduct().getId());
			pstmt.setString(2, ProductImage.getType());
			pstmt.setInt(3, ProductImage.getId());
			
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
	public ProductImage get(int id)
	{
		ProductImage ProductImage = new ProductImage();
		//连接数据库
		try
		{
			conn = DBConn.getConnection();
			String sql = "select * from ProductImage where id = " + id;
			stmt = conn.createStatement();  //创建statement类对象，用来执行SQL语句！！
			rs = stmt.executeQuery(sql);  //用rs 来收集查询结果集
			
			if (rs.next())
			{
				ProductImage.setId(id);
				ProductImage.setType(rs.getString("type"));
				
				int ptid = rs.getInt("ptid");  //从rs中获得ptid的值
				//下面通过ptid，调用ProductDAO类的get()方法来返回ptid对应的Product类的一个实例化对象
				Product product = new ProductDAO().get(ptid);
				//再将该product传给ProductImage的setProduct()函数
				ProductImage.setProduct(product);
			
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		//断开数据库连接
		DBClose.disconnection(stmt, conn);
		return ProductImage;   //返回结果
	}
	
	//5  获取总数
	public int getTotal()
	{
		int total = 0;
		//连接数据库
		try
		{
			conn = DBConn.getConnection();
			String sql = "select count(*) from productimage";  //查询总是的sql语句
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
	
	//6   分页查询
	public List<ProductImage> list(Product product, String type, int start, int count)
	{
		List <ProductImage> productimages = null;
		//连接数据库
		try
		{
			conn = DBConn.getConnection();
			String sql = "select * from productimage where ptid=? and type=? order by id desc limit ?,?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, product.getId());
			pstmt.setString(2, type);
			pstmt.setInt(3, start);
			pstmt.setInt(4, count);
			
			rs = pstmt.executeQuery();
			
			while (rs.next())
			{
				productimages = new ArrayList<>();
				ProductImage productimage = new ProductImage();  //实例化ProductImage
				
				productimage.setId(rs.getInt(1));
				productimage.setProduct(product);
				productimage.setType(type);
				
				productimages.add(productimage);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return productimages;  //返回结果
	}
	
	//7  全查询
	public List<ProductImage> list(Product product, String type)
	{
		return list(product, type, 0, Short.MAX_VALUE);
	}
}
