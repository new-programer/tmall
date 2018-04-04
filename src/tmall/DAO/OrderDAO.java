/**
 * tmall 数据库 order_表结构如下：
 *  +-------------+--------------+------+-----+---------+----------------+
	| Field       | Type         | Null | Key | Default | Extra          |
	+-------------+--------------+------+-----+---------+----------------+
	| id          | int(11)      | NO   | PRI | NULL    | auto_increment |
	| uid         | int(11)      | YES  | MUL | NULL    |                |
	| orderCode   | varchar(255) | YES  |     | NULL    |                |
	| address     | varchar(255) | YES  |     | NULL    |                |
	| post        | varchar(50)  | YES  |     | NULL    |                |
	| receiver    | varchar(50)  | YES  |     | NULL    |                |
	| mobile      | varchar(40)  | YES  |     | NULL    |                |
	| userMessage | varchar(255) | YES  |     | NULL    |                |
	| CreateDate  | datetime     | YES  |     | NULL    |                |
	| payDate     | datetime     | YES  |     | NULL    |                |
	| confirmDate | datetime     | YES  |     | NULL    |                |
	| status      | varchar(255) | YES  |     | NULL    |                |
	+-------------+--------------+------+-----+---------+----------------+
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
import tmall.bean.Order;

/**
 * @author Administrator
 *
 */
public class OrderDAO
{
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; 
	
	    public static final String waitPay = "waitPay";   //待付款
	    public static final String waitDelivery = "waitDelivery";   //待发货
	    public static final String waitConfirm = "waitConfirm";      //待收货
	    public static final String waitReview = "waitReview";		//待评价
	    public static final String finish = "finish";		//已完成订单
	    public static final String delete = "delete";		//删除标记（不会真正从数据库删除，，只是隐藏不显示）
	//基本的CRUD操作
	//1  增
	public boolean add(Order order)
	{
		boolean bool = false;
		//连接数据库
		try
		{
			conn = DBConn.getConnection();
			String sql = "insert into order values (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, order.getUser().getId());  //某一用户的id
			pstmt.setString(2, order.getOrderCode());
			pstmt.setString(3, order.getAddress());
			pstmt.setString(4, order.getPost());
			pstmt.setString(5, order.getReceiever());  
			pstmt.setString(6, order.getMobile());
			pstmt.setString(7, order.getUserMessage());//用户留言
			pstmt.setTimestamp(8, DateUtil.date_to_timestamp(order.getCreateDate()));			//日期转换（利用DateUtil工具）
			pstmt.setTimestamp(9, DateUtil.date_to_timestamp(order.getPayDate())); //订单付款时间
			pstmt.setTimestamp(10, DateUtil.date_to_timestamp(order.getConfirmDate()));  //确认收货时间
			pstmt.setString(11, order.getStatus());   //订单状态
			bool = pstmt.execute();   //执行插入操作,执行结果返回给 bool，执行之后会生成自增id
			
			rs = pstmt.getGeneratedKeys();   //获取自增id
			if(rs.next())
			{
				//将自动生成的id(自增id)从rs 数据集合中取出传给setId()
				//目的：是实体类order各对象的id和数据库保持一致
				order.setId(rs.getInt(1));  
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
			String sql = "delete from order where id = " + id;
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
	public boolean update(Order order)
	{
		boolean bool = false;
		//连接数据库
		try
		{
			conn = DBConn.getConnection();
			String sql = "update order set uid=?, OrderCode=?, address=?, post=?, receiever=?, "
					+ "mobile=?, useMessage=?, CreateDate=?, payDate=?, confirmDate=?, status=? where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, order.getUser().getId());  //某一用户的id
			pstmt.setString(2, order.getOrderCode());
			pstmt.setString(3, order.getAddress());
			pstmt.setString(4, order.getPost());
			pstmt.setString(5, order.getReceiever());  
			pstmt.setString(6, order.getMobile());
			pstmt.setString(7, order.getUserMessage());//用户留言
			pstmt.setTimestamp(8, DateUtil.date_to_timestamp(order.getCreateDate()));			//日期转换（利用DateUtil工具）
			pstmt.setTimestamp(9, DateUtil.date_to_timestamp(order.getPayDate())); //订单付款时间
			pstmt.setTimestamp(10, DateUtil.date_to_timestamp(order.getConfirmDate()));  //确认收货时间
			pstmt.setString(11, order.getStatus());   //订单状态
			pstmt.setInt(12, order.getId());
			
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
	public Order get(int id)
	{
	//	Order order = new Order();    //这种方式默认返回的是：类名 + @ + 地址，若用此方法根据id查不到结果时也会返回一个实际地址而不是返回null了
		Order order = null;          //这样做声明更好一点
		//连接数据库
		try
		{
			conn = DBConn.getConnection();
			String sql = "select * from order where id = " + id;
			stmt = conn.createStatement();  //创建statement类对象，用来执行SQL语句！！
			rs = stmt.executeQuery(sql);  //用rs 来收集查询结果集
			
			if (rs.next())
			{
				order = new Order();   //在此实例化比前面实例化好一点，理由见上
				order.setId(id);
				order.setUser(new UserDAO().get(rs.getInt("uid")));
				order.setOrderCode(rs.getString("orderCode"));
				order.setAddress(rs.getString("address"));
				order.setPost(rs.getString("post"));
				order.setReceiever(rs.getString("receiever"));
				order.setMobile(rs.getString("mobile"));
				order.setUserMessage(rs.getString("userMessage"));
				order.setCreateDate(rs.getDate("CreateDate"));
				order.setPayDate(rs.getDate("payDate"));
				order.setConfirmDate(rs.getDate("confirmDate"));
				order.setStatus(rs.getString("status"));
			
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		//断开数据库连接
		DBClose.disconnection(stmt, conn);
		return order;   //返回结果
	}
	
	//非CRUD基本操作
	//5  获取订单总数 （注意是某一用户对应的订单总数，故必须先获取User 类id :'uid'），供用自己查看
	public int getTotal(int uid)
	{
		int total = 0;
		//连接数据库
		try
		{
			conn = DBConn.getConnection();
			String sql = "select count()* from order where cid = " + uid;
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
	
	//6  获取订单总数   （供后台查看）
	public int getTotal()
	{
		int total = 0;
		//连接数据库
		try
		{
			conn = DBConn.getConnection();
			String sql = "select count()* from order";
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
	
	//7  分页查  （测试）
	public List<Order> list (int start, int count)
	{
		//用orders集合来收集查的order
		List<Order> orders = null;
		//连接数据库
		try
		{
			conn = DBConn.getConnection();
			String sql = "select * from order_ ? order by id desc limit ?, ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, count);
			
			rs = pstmt.executeQuery();
			
			//遍历暂存在rs中的查询结果
			while (rs.next())
			{
				orders = new ArrayList<>();//在此实例化比前面实例化好一点，理由见上
				Order order = new Order();
				
				order.setId(rs.getInt("id"));
				order.setUser(new UserDAO().get(rs.getInt("uid")));
				order.setOrderCode(rs.getString("orderCode"));
				order.setAddress(rs.getString("address"));
				order.setPost(rs.getString("post"));
				order.setReceiever(rs.getString("receiever"));
				order.setMobile(rs.getString("mobile"));
				order.setUserMessage(rs.getString("userMessage"));
				order.setCreateDate(rs.getDate("CreateDate"));
				order.setPayDate(rs.getDate("payDate"));
				order.setConfirmDate(rs.getDate("confirmDate"));
				order.setStatus(rs.getString("status"));
				
				orders.add(order);  //讲遍历到的order添加到orders集合list中去
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		//断开数据库连接
		DBClose.disconnection(pstmt, conn);
		return orders;
	}
	  
	//7  全部查  （测试）
	public List<Order> list()
	{
		return list(0, Short.MAX_VALUE);  //返回cid对应的某分类订单总数
	}
	
	//8  分页查（根据用户id和订单状态status）
	public List<Order> list(int uid, String excludedStatus, int start, int count)
	{
		//用orders集合来收集查的order
		List<Order> orders = null;
		//连接数据库
		try
		{
			conn = DBConn.getConnection();
			String sql = "select * from order_ ? where uid=? and status!=? order by id desc limit ?, ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, uid);
			pstmt.setString(2, excludedStatus);
			pstmt.setInt(3, start);
			pstmt.setInt(4, count);
			
			rs = pstmt.executeQuery();
			
			//遍历暂存在rs中的查询结果
			while (rs.next())
			{
				orders = new ArrayList<>();//在此实例化比前面实例化好一点，理由见上
				Order order = new Order();
				
				order.setId(rs.getInt("id"));
				order.setUser(new UserDAO().get(rs.getInt("uid")));
				order.setOrderCode(rs.getString("orderCode"));
				order.setAddress(rs.getString("address"));
				order.setPost(rs.getString("post"));
				order.setReceiever(rs.getString("receiever"));
				order.setMobile(rs.getString("mobile"));
				order.setUserMessage(rs.getString("userMessage"));
				order.setCreateDate(rs.getDate("CreateDate"));
				order.setPayDate(rs.getDate("payDate"));
				order.setConfirmDate(rs.getDate("confirmDate"));
				order.setStatus(rs.getString("status"));
				
				orders.add(order);  //讲遍历到的order添加到orders集合list中去
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		//断开数据库连接
		DBClose.disconnection(pstmt, conn);
		return orders;
	}
	
	//9  全部查（uid和status）
	public List<Order> list(int uid, String excludedStatus)
	{
		return list(uid, excludedStatus, 0, Short.MAX_VALUE);
	}
}
