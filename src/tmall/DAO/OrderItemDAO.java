/**
 * tmall���ݵ�orderItem��Ľṹ��
 *  +---------+---------+------+-----+---------+----------------+
	| Field   | Type    | Null | Key | Default | Extra          |
	+---------+---------+------+-----+---------+----------------+
	| id      | int(11) | NO   | PRI | NULL    | auto_increment |
	| ptid    | int(11) | YES  | MUL | NULL    |                |
	| oid     | int(11) | YES  | MUL | NULL    |                |
	| uid     | int(11) | YES  | MUL | NULL    |                |
	| nunmber | int(11) | YES  |     | NULL    |                |
	+---------+---------+------+-----+---------+----------------+
	
	orderItemʵ���ඩ���
	private int id;
	private int number;  //��¼������Ʒ������
	private User user;  //��User���һ��ϵ
	private Product product;	//��Product���һ��ϵ
	private Order order;	//��Order���һ��ϵ
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
	
	//������CRUD����
	//1  ��
	public boolean add(OrderItem orderItem)
	{
		boolean bool = false;
		//�������ݿ�
		try
		{
			conn = DBConn.getConnection();
			String sql = "insert into orderItem values (null, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, orderItem.getProduct().getId()); 
			pstmt.setInt(2, orderItem.getOrder().getId());   
			pstmt.setInt(3, orderItem.getUser().getId());
			pstmt.setInt(4, orderItem.getNumber());
			
			bool = pstmt.execute();   //ִ�в������,ִ�н�����ظ�bool
			rs = pstmt.getGeneratedKeys();   //��ȡ����id
			if(rs.next())
			{
				//���Զ����ɵ�id(����id)��rs ���ݼ�����ȡ������setId()
				//Ŀ�ģ���ʵ����OrderItem�������id�����ݿⱣ��һ��
				orderItem.setId(rs.getInt(1));
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
			String sql = "delete from orderitem where id = " + id;
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
	public boolean update(OrderItem orderItem)
	{
		boolean bool = false;
		//�������ݿ�
		try
		{
			conn = DBConn.getConnection();
			String sql = "update orderitem set ptid=?, oid=?,uid=?, number=? where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, orderItem.getProduct().getId()); 
			pstmt.setInt(2, orderItem.getOrder().getId());   
			pstmt.setInt(3, orderItem.getUser().getId());
			pstmt.setInt(4, orderItem.getNumber());
			
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
	public OrderItem get(int id)
	{
		OrderItem orderItem = null;
		//�������ݿ�
		try
		{
			conn = DBConn.getConnection();
			String sql = "select * from orderItem where id = " + id;
			stmt = conn.createStatement();  //����statement���������ִ��SQL��䣡��
			rs = stmt.executeQuery(sql);  //��rs ���ռ���ѯ�����
			
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
		//�Ͽ����ݿ�����
		DBClose.disconnection(stmt, conn);
		return orderItem;   //���ؽ��
	}
	
	//5  ��ȡ�ܶ�������  (Ҳ����ѡ���Բ�ѯ������switch���ptid��oid��uid����ѯ)
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
		
		//�Ͽ����ݿ�����
		DBClose.disconnection(stmt, conn);
		return total;
	}
	
	//6  ��ĳһ��Ʒ��������Ʒ�����������ֲ�Ʒ��Ӧ�Ķ�����OrderItem��number�ֶε��ܺͣ�
	public int getSaleCount(int ptid)
	{
		int saleCount = 0;
		//�������ݿ�
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
		
		//�Ͽ����ݿ�����
		DBClose.disconnection(pstmt, conn);
		return saleCount;   //���ؽ��
	}
	
	
	//7  ��ҳ��  ����Ҫ���ڲ��ԣ�
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
		//�Ͽ����ݿ�����
		DBClose.disconnection(pstmt, conn);
		return orderItems;
	}
	
	//8  ȫ����  �����ԣ�
	public List<OrderItem> list()
	{
		return list(0, Short.MAX_VALUE);
	}
	
	//9  ��ҳ��Ѱĳһ�����µ����ж�����
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
		//�Ͽ����ݿ�����
		DBClose.disconnection(pstmt, conn);
		return orderItems;
	}
	
	//10  ȫ����Ѱĳһ�����µ����ж�����
	public List<OrderItem> listByOrder(int oid)
	{
		return listByOrder(oid, 0, Short.MAX_VALUE);
	}
	
	//11   ��ҳ��ѯĳ�û�δ���ɶ����Ķ�����
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
		//�Ͽ����ݿ�����
		DBClose.disconnection(pstmt, conn);
		return orderItems;
	}
	
	//12  ȫ����ѯĳ�û�δ���ɶ����Ķ�����
	public List<OrderItem> listByUser(int uid)
	{
		return listByUser(uid, 0, Short.MAX_VALUE);
	}
	
	//13   ��ҳ��ѯĳ��Ʒδ���ɵ��Ķ�����
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
		//�Ͽ����ݿ�����
		DBClose.disconnection(pstmt, conn);
		return orderItems;
	}
	
	//14  ȫ����ѯĳ��Ʒ���ɶ�����
	public List<OrderItem> listByProduct(int ptid)
	{
		return listByProduct(ptid, 0, Short.MAX_VALUE);
	}
	
	//15  ������ж���
	public void fill(List<Order> orders)
	{
		for (Order order : orders)
		{
			fill(order);
		}
	}
	
	//16   ��䵥������
	public void fill(Order order)
	{
		List<OrderItem> orderItems = this.listByOrder(order.getId());
		int totalMoneyEachOrder = 0;
		int totalNumberEachOrder = 0;
		
		for (OrderItem orderItem : orderItems)
		{
			//����ö�������Ʒ�ܼ�
			totalMoneyEachOrder += orderItem.getNumber() * orderItem.getProduct().getPromotePrice();
			totalNumberEachOrder += orderItem.getNumber();  //����ö�����������Ʒ����
		}
		
		//�������
		order.setTotalMoney(totalMoneyEachOrder);
		order.setTotalNumber(totalNumberEachOrder);
		order.setOrderitems(orderItems);
	}
}
