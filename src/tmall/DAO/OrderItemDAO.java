/**
 * tmall数据的orderItem表的结构：
 *  +---------+---------+------+-----+---------+----------------+
	| Field   | Type    | Null | Key | Default | Extra          |
	+---------+---------+------+-----+---------+----------------+
	| id      | int(11) | NO   | PRI | NULL    | auto_increment |
	| ptid    | int(11) | YES  | MUL | NULL    |                |
	| oid     | int(11) | YES  | MUL | NULL    |                |
	| uid     | int(11) | YES  | MUL | NULL    |                |
	| nunmber | int(11) | YES  |     | NULL    |                |
	+---------+---------+------+-----+---------+----------------+
	
	orderItem实体类订单项：
	private int id;
	private int number;  //记录各个商品的数量
	private User user;  //和User多对一关系
	private Product product;	//和Product多对一关系
	private Order order;	//和Order多对一关系
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
import tmall.bean.Order;
import tmall.bean.OrderItem;

/**
 * @author Administrator
 *
 */
public class OrderItemDAO
{
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
	//基本的CRUD操作
	//1  增
	public boolean add(OrderItem orderItem)
	{
		boolean bool = false;
		//连接数据库
		try
		{
			conn = DBConn.getConnection();
			String sql = "insert into orderItem values (null, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, orderItem.getProduct().getId()); 
			pstmt.setInt(2, orderItem.getOrder().getId());   
			pstmt.setInt(3, orderItem.getUser().getId());
			pstmt.setInt(4, orderItem.getNumber());
			
			bool = pstmt.execute();   //执行插入操作,执行结果返回给bool
			rs = pstmt.getGeneratedKeys();   //获取自增id
			if(rs.next())
			{
				//将自动生成的id(自增id)从rs 数据集合中取出传给setId()
				//目的：是实体类OrderItem各对象的id和数据库保持一致
				orderItem.setId(rs.getInt(1));
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
			String sql = "delete from orderitem where id = " + id;
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
	public boolean update(OrderItem orderItem)
	{
		boolean bool = false;
		//连接数据库
		try
		{
			conn = DBConn.getConnection();
			String sql = "update orderitem set ptid=?, oid=?,uid=?, number=? where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, orderItem.getProduct().getId()); 
			pstmt.setInt(2, orderItem.getOrder().getId());   
			pstmt.setInt(3, orderItem.getUser().getId());
			pstmt.setInt(4, orderItem.getNumber());
			
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
	public OrderItem get(int id)
	{
		OrderItem orderItem = null;
		//连接数据库
		try
		{
			conn = DBConn.getConnection();
			String sql = "select * from orderItem where id = " + id;
			stmt = conn.createStatement();  //创建statement类对象，用来执行SQL语句！！
			rs = stmt.executeQuery(sql);  //用rs 来收集查询结果集
			
			if (rs.next())
			{
				orderItem = new OrderItem();
				orderItem.setId(id);
				orderItem.setProduct(new ProductDAO().get(rs.getInt("ptid")));
				orderItem.setOrder(new OrderDAO().get(rs.getInt("oid")));
				orderItem.setUser(new UserDAO().get(rs.getInt("uid")));
				orderItem.setNumber(rs.getInt("number"));

			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		//断开数据库连接
		DBClose.disconnection(stmt, conn);
		return orderItem;   //返回结果
	}
	
	//5  获取总订单项数  (也可做选择性查询：利用switch结合ptid、oid、uid来查询)
	public int getTotal()
	{
		int total = 0;
		try
		{
			conn = DBConn.getConnection();
			String sql = "select count(*) from orderitem";
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
		return total;
	}
	
	//6  查某一产品销量（产品销量就是这种产品对应的订单项OrderItem的number字段的总和）
	public int getSaleCount(int ptid)
	{
		int saleCount = 0;
		//连接数据库
		try
		{
			conn = DBConn.getConnection();
			String sql = "select number from orderitem where ptid=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ptid);
			
			rs = pstmt.executeQuery();
			
			if (rs.next())
			{
				saleCount = rs.getInt(1);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		//断开数据库连接
		DBClose.disconnection(pstmt, conn);
		return saleCount;   //返回结果
	}
	
	
	//7  分页查  （主要用于测试）
	public List<OrderItem> list(int start, int count)
	{
		List<OrderItem> orderItems = null;
		
		try
		{
			conn = DBConn.getConnection();
			String sql = "select * from orderitem order by id desc limit ?,?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, count);
			rs = pstmt.executeQuery();
			
			while (rs.next())
			{
				orderItems = new ArrayList<>();
				OrderItem orderItem = new OrderItem();
				
				orderItem.setId(rs.getInt("id"));
				orderItem.setProduct(new ProductDAO().get(rs.getInt("ptid")));
				orderItem.setOrder(new OrderDAO().get(rs.getInt("oid")));
				orderItem.setUser(new UserDAO().get(rs.getInt("uid")));
				orderItem.setNumber(rs.getInt("number"));
				
				orderItems.add(orderItem);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		//断开数据库连接
		DBClose.disconnection(pstmt, conn);
		return orderItems;
	}
	
	//8  全部查  （测试）
	public List<OrderItem> list()
	{
		return list(0, Short.MAX_VALUE);
	}
	
	//9  分页查寻某一订单下的所有订单项
	public List<OrderItem> listByOrder(int oid, int start, int count)
	{
		List<OrderItem> orderItems = null;
		
		try
		{
			conn = DBConn.getConnection();
			String sql = "select * from orderitem where oid=? order by id desc limit ?,?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, oid);
			pstmt.setInt(2, start);
			pstmt.setInt(3, count);
			rs = pstmt.executeQuery();
			
			while (rs.next())
			{
				orderItems = new ArrayList<>();
				OrderItem orderItem = new OrderItem();
				
				orderItem.setId(rs.getInt("id"));
				orderItem.setProduct(new ProductDAO().get(rs.getInt("ptid")));
				orderItem.setOrder(new OrderDAO().get(oid));
				orderItem.setUser(new UserDAO().get(rs.getInt("uid")));
				orderItem.setNumber(rs.getInt("number"));
				
				orderItems.add(orderItem);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		//断开数据库连接
		DBClose.disconnection(pstmt, conn);
		return orderItems;
	}
	
	//10  全部查寻某一订单下的所有订单项
	public List<OrderItem> listByOrder(int oid)
	{
		return listByOrder(oid, 0, Short.MAX_VALUE);
	}
	
	//11   分页查询某用户未生成订单的订单项
	public List<OrderItem> listByUser(int uid, int start, int count)
	{
		List<OrderItem> orderItems = null;
		
		try
		{
			conn = DBConn.getConnection();
			String sql = "select * from orderitem where uid=? order by id desc limit ?,?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, uid);
			pstmt.setInt(2, start);
			pstmt.setInt(3, count);
			rs = pstmt.executeQuery();
			
			while (rs.next())
			{
				orderItems = new ArrayList<>();
				OrderItem orderItem = new OrderItem();
				
				orderItem.setId(rs.getInt("id"));
				orderItem.setProduct(new ProductDAO().get(rs.getInt("ptid")));
				orderItem.setOrder(new OrderDAO().get(uid));
				orderItem.setUser(new UserDAO().get(rs.getInt("uid")));
				orderItem.setNumber(rs.getInt("number"));
				
				orderItems.add(orderItem);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		//断开数据库连接
		DBClose.disconnection(pstmt, conn);
		return orderItems;
	}
	
	//12  全部查询某用户未生成订单的订单项
	public List<OrderItem> listByUser(int uid)
	{
		return listByUser(uid, 0, Short.MAX_VALUE);
	}
	
	//13   分页查询某商品未生成单的订单项
	public List<OrderItem> listByProduct(int ptid, int start, int count)
	{
		List<OrderItem> orderItems = null;
		
		try
		{
			conn = DBConn.getConnection();
			String sql = "select * from orderitem where uid=? order by id desc limit ?,?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ptid);
			pstmt.setInt(2, start);
			pstmt.setInt(3, count);
			rs = pstmt.executeQuery();
			
			while (rs.next())
			{
				orderItems = new ArrayList<>();
				OrderItem orderItem = new OrderItem();
				
				orderItem.setId(rs.getInt("id"));
				orderItem.setProduct(new ProductDAO().get(rs.getInt("ptid")));
				orderItem.setOrder(new OrderDAO().get(ptid));
				orderItem.setUser(new UserDAO().get(rs.getInt("uid")));
				orderItem.setNumber(rs.getInt("number"));
				
				orderItems.add(orderItem);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		//断开数据库连接
		DBClose.disconnection(pstmt, conn);
		return orderItems;
	}
	
	//14  全部查询某商品生成订单项
	public List<OrderItem> listByProduct(int ptid)
	{
		return listByProduct(ptid, 0, Short.MAX_VALUE);
	}
	
	//15  填充所有订单
	public void fill(List<Order> orders)
	{
		for (Order order : orders)
		{
			fill(order);
		}
	}
	
	//16   填充单个订单
	public void fill(Order order)
	{
		List<OrderItem> orderItems = this.listByOrder(order.getId());
		int totalMoneyEachOrder = 0;
		int totalNumberEachOrder = 0;
		
		for (OrderItem orderItem : orderItems)
		{
			//计算该订单的商品总价
			totalMoneyEachOrder += orderItem.getNumber() * orderItem.getProduct().getPromotePrice();
			totalNumberEachOrder += orderItem.getNumber();  //计算该订单下所有商品总数
		}
		
		//进行填充
		order.setTotalMoney(totalMoneyEachOrder);
		order.setTotalNumber(totalNumberEachOrder);
		order.setOrderitems(orderItems);
	}
}
