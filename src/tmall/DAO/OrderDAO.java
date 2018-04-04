/**
 * tmall ���ݿ� order_��ṹ���£�
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
	
	    public static final String waitPay = "waitPay";   //������
	    public static final String waitDelivery = "waitDelivery";   //������
	    public static final String waitConfirm = "waitConfirm";      //���ջ�
	    public static final String waitReview = "waitReview";		//������
	    public static final String finish = "finish";		//����ɶ���
	    public static final String delete = "delete";		//ɾ����ǣ��������������ݿ�ɾ������ֻ�����ز���ʾ��
	//������CRUD����
	//1  ��
	public boolean add(Order order)
	{
		boolean bool = false;
		//�������ݿ�
		try
		{
			conn = DBConn.getConnection();
			String sql = "insert into order values (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, order.getUser().getId());  //ĳһ�û���id
			pstmt.setString(2, order.getOrderCode());
			pstmt.setString(3, order.getAddress());
			pstmt.setString(4, order.getPost());
			pstmt.setString(5, order.getReceiever());  
			pstmt.setString(6, order.getMobile());
			pstmt.setString(7, order.getUserMessage());//�û�����
			pstmt.setTimestamp(8, DateUtil.date_to_timestamp(order.getCreateDate()));			//����ת��������DateUtil���ߣ�
			pstmt.setTimestamp(9, DateUtil.date_to_timestamp(order.getPayDate())); //��������ʱ��
			pstmt.setTimestamp(10, DateUtil.date_to_timestamp(order.getConfirmDate()));  //ȷ���ջ�ʱ��
			pstmt.setString(11, order.getStatus());   //����״̬
			bool = pstmt.execute();   //ִ�в������,ִ�н�����ظ� bool��ִ��֮�����������id
			
			rs = pstmt.getGeneratedKeys();   //��ȡ����id
			if(rs.next())
			{
				//���Զ����ɵ�id(����id)��rs ���ݼ�����ȡ������setId()
				//Ŀ�ģ���ʵ����order�������id�����ݿⱣ��һ��
				order.setId(rs.getInt(1));  
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
			String sql = "delete from order where id = " + id;
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
	public boolean update(Order order)
	{
		boolean bool = false;
		//�������ݿ�
		try
		{
			conn = DBConn.getConnection();
			String sql = "update order set uid=?, OrderCode=?, address=?, post=?, receiever=?, "
					+ "mobile=?, useMessage=?, CreateDate=?, payDate=?, confirmDate=?, status=? where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, order.getUser().getId());  //ĳһ�û���id
			pstmt.setString(2, order.getOrderCode());
			pstmt.setString(3, order.getAddress());
			pstmt.setString(4, order.getPost());
			pstmt.setString(5, order.getReceiever());  
			pstmt.setString(6, order.getMobile());
			pstmt.setString(7, order.getUserMessage());//�û�����
			pstmt.setTimestamp(8, DateUtil.date_to_timestamp(order.getCreateDate()));			//����ת��������DateUtil���ߣ�
			pstmt.setTimestamp(9, DateUtil.date_to_timestamp(order.getPayDate())); //��������ʱ��
			pstmt.setTimestamp(10, DateUtil.date_to_timestamp(order.getConfirmDate()));  //ȷ���ջ�ʱ��
			pstmt.setString(11, order.getStatus());   //����״̬
			pstmt.setInt(12, order.getId());
			
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
	public Order get(int id)
	{
	//	Order order = new Order();    //���ַ�ʽĬ�Ϸ��ص��ǣ����� + @ + ��ַ�����ô˷�������id�鲻�����ʱҲ�᷵��һ��ʵ�ʵ�ַ�����Ƿ���null��
		Order order = null;          //��������������һ��
		//�������ݿ�
		try
		{
			conn = DBConn.getConnection();
			String sql = "select * from order where id = " + id;
			stmt = conn.createStatement();  //����statement���������ִ��SQL��䣡��
			rs = stmt.executeQuery(sql);  //��rs ���ռ���ѯ�����
			
			if (rs.next())
			{
				order = new Order();   //�ڴ�ʵ������ǰ��ʵ������һ�㣬���ɼ���
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
		//�Ͽ����ݿ�����
		DBClose.disconnection(stmt, conn);
		return order;   //���ؽ��
	}
	
	//��CRUD��������
	//5  ��ȡ�������� ��ע����ĳһ�û���Ӧ�Ķ����������ʱ����Ȼ�ȡUser ��id :'uid'���������Լ��鿴
	public int getTotal(int uid)
	{
		int total = 0;
		//�������ݿ�
		try
		{
			conn = DBConn.getConnection();
			String sql = "select count()* from order where cid = " + uid;
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
	
	//6  ��ȡ��������   ������̨�鿴��
	public int getTotal()
	{
		int total = 0;
		//�������ݿ�
		try
		{
			conn = DBConn.getConnection();
			String sql = "select count()* from order";
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
	
	//7  ��ҳ��  �����ԣ�
	public List<Order> list (int start, int count)
	{
		//��orders�������ռ����order
		List<Order> orders = null;
		//�������ݿ�
		try
		{
			conn = DBConn.getConnection();
			String sql = "select * from order_ ? order by id desc limit ?, ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, count);
			
			rs = pstmt.executeQuery();
			
			//�����ݴ���rs�еĲ�ѯ���
			while (rs.next())
			{
				orders = new ArrayList<>();//�ڴ�ʵ������ǰ��ʵ������һ�㣬���ɼ���
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
				
				orders.add(order);  //����������order��ӵ�orders����list��ȥ
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		//�Ͽ����ݿ�����
		DBClose.disconnection(pstmt, conn);
		return orders;
	}
	  
	//7  ȫ����  �����ԣ�
	public List<Order> list()
	{
		return list(0, Short.MAX_VALUE);  //����cid��Ӧ��ĳ���ඩ������
	}
	
	//8  ��ҳ�飨�����û�id�Ͷ���״̬status��
	public List<Order> list(int uid, String excludedStatus, int start, int count)
	{
		//��orders�������ռ����order
		List<Order> orders = null;
		//�������ݿ�
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
			
			//�����ݴ���rs�еĲ�ѯ���
			while (rs.next())
			{
				orders = new ArrayList<>();//�ڴ�ʵ������ǰ��ʵ������һ�㣬���ɼ���
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
				
				orders.add(order);  //����������order��ӵ�orders����list��ȥ
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		//�Ͽ����ݿ�����
		DBClose.disconnection(pstmt, conn);
		return orders;
	}
	
	//9  ȫ���飨uid��status��
	public List<Order> list(int uid, String excludedStatus)
	{
		return list(uid, excludedStatus, 0, Short.MAX_VALUE);
	}
}
