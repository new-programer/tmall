/**
 * tmall ���ݿ��PropertyValue��ṹ����
 * 	+-------+--------------+------+-----+---------+----------------+
	| Field | Type         | Null | Key | Default | Extra          |
	+-------+--------------+------+-----+---------+----------------+
	| id    | int(11)      | NO   | PRI | NULL    | auto_increment |
	| pid   | int(11)      | YES  | MUL | NULL    |                |
	| ptid  | int(11)      | YES  | MUL | NULL    |                |
	| value | varchar(255) | YES  |     | NULL    |                |
	+-------+--------------+------+-----+---------+----------------+
	PropertyValue��ʵ�������ԣ�
	private int id;
	private String value;   //��¼�������Զ�Ӧ��ֵ
	private Product product;  //��Product���һ��ϵ
	private Property property;  //Property���һ��ϵ
	
 *ע�� ���ĸ�����������ʹ�ã�ʵ��ҵ��û���õ�����
	public List<PropertyValue> list()
	public List<PropertyValue> list(int start, int count) 
	public PropertyValue get(int id) 
	public int getTotal() 
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
import tmall.bean.Property;
import tmall.bean.PropertyValue;

/**
 * @author Administrator
 *
 */
public class PropertyValueDAO
{
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
	//������CRUD����
	//1  ��
	public boolean add(PropertyValue propertyValue)
	{
		boolean bool = false;
		//�������ݿ�
		try
		{
			conn = DBConn.getConnection();
			String sql = "insert into PropertyValue values (null, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, propertyValue.getProperty().getId());  //ĳһ���Ե�id
			pstmt.setInt(2, propertyValue.getProduct().getId());   //ĳһ��Ʒ��id
			pstmt.setString(3, propertyValue.getValue());
			
			bool = pstmt.execute();   //ִ�в������,ִ�н�����ظ�bool
			rs = pstmt.getGeneratedKeys();   //��ȡ����id
			if(rs.next())
			{
				//���Զ����ɵ�id(����id)��rs ���ݼ�����ȡ������setId()
				//Ŀ�ģ���ʵ����PropertyValue�������id�����ݿⱣ��һ��
				propertyValue.setId(rs.getInt(1));
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
			String sql = "delete from propertyvalue where id = " + id;
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
	public boolean update(PropertyValue propertyValue)
	{
		boolean bool = false;
		//�������ݿ�
		try
		{
			conn = DBConn.getConnection();
			String sql = "update PropertyValue set pid=?, ptid=?, value=? where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, propertyValue.getProperty().getId());
			pstmt.setInt(2, propertyValue.getProduct().getId());
			pstmt.setString(3, propertyValue.getValue());
			
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
	public PropertyValue get(int id)
	{
		PropertyValue propertyValue = null;
		//�������ݿ�
		try
		{
			conn = DBConn.getConnection();
			String sql = "select * from propertyvalue where id = " + id;
			stmt = conn.createStatement();  //����statement���������ִ��SQL��䣡��
			rs = stmt.executeQuery(sql);  //��rs ���ռ���ѯ�����
			
			if (rs.next())
			{
				propertyValue = new PropertyValue();
				propertyValue.setId(id);
				propertyValue.setValue(rs.getString("value"));
				
				int pid = rs.getInt("pid");  //��rs�л��pid��ֵ
				//����ͨ��pid������PropertyDAO���get()����������pid��Ӧ��property���һ��ʵ��������
				Property property = new PropertyDAO().get(pid);
				//�ٽ���property����PropertyValue��setProperty()����
				propertyValue.setProperty(property);
			
				int ptid = rs.getInt("ptid");  //��rs�л��ptid��ֵ
				//����ͨ��ptid������ProductDAO���get()����������ptid��Ӧ��product���һ��ʵ��������
				Product product = new ProductDAO().get(ptid);
				//�ٽ���product����PropertyValue��setProduct()����
				propertyValue.setProduct(product);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		//�Ͽ����ݿ�����
		DBClose.disconnection(stmt, conn);
		return propertyValue;   //���ؽ��
	}
	
	//5  ��ȡ������ֵ
	public int getTotal()
	{
		int total = 0;
		try
		{
			conn = DBConn.getConnection();
			String sql = "select count(*) from propertyvalue";
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
	
	//6  ��ȷ�飨����product��id��property��id����ȷ��λ��
	public PropertyValue get(int ptid, int pid)
	{
		PropertyValue propertyValue = null;
		//�������ݿ�
		try
		{
			conn = DBConn.getConnection();
			String sql = "select * from propertyvalue where ptid=? and pid=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ptid);
			pstmt.setInt(2, pid);
			
			rs = pstmt.executeQuery();
			
			if (rs.next())
			{
				propertyValue = new PropertyValue();
				propertyValue.setId(rs.getInt("id"));
				
				//��ProductDAO�����get()������ȡproduct����setProduct()
				propertyValue.setProduct(new ProductDAO().get(ptid));
				
				propertyValue.setProperty(new PropertyDAO().get(pid));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		//�Ͽ����ݿ�����
		DBClose.disconnection(pstmt, conn);
		return propertyValue;   //���ؽ��
	}
	
	
	//7  ��ҳ��  ����Ҫ���ڲ��ԣ�
	public List<PropertyValue> list(int start, int count)
	{
		List<PropertyValue> propertyValues = null;
		
		try
		{
			conn = DBConn.getConnection();
			String sql = "select * from propertyvalue order by id desc limit ?,?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, count);
			rs = pstmt.executeQuery();
			
			while (rs.next())
			{
				propertyValues = new ArrayList<>();
				PropertyValue propertyValue = new PropertyValue();
				
				propertyValue.setId(rs.getInt("id"));
				propertyValue.setValue(rs.getString("value"));
				
				int ptid = rs.getInt("ptid");
				int pid = rs.getInt("pid");
				propertyValue.setProduct(new ProductDAO().get(ptid));
				propertyValue.setProperty(new PropertyDAO().get(pid));
				
				propertyValues.add(propertyValue);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		//�Ͽ����ݿ�����
		DBClose.disconnection(pstmt, conn);
		return propertyValues;
	}
	
	//8  ȫ����
	public List<PropertyValue> list()
	{
		return list(0, Short.MAX_VALUE);
	}
	
	//9 ���Գ�ʼ��  ��ע��thisָ���ʹ�ã�
	public void init(Product product)
	{
		//ʵ����һ��Property���͵ļ����� ������Ʒproduct����������
		List<Property> properties = new PropertyDAO().list(product.getCategory().getId());
		//��forEach����properties������ÿ��Ԫ�ؽ��г�ʼ��
		for (Property property : properties)
		{
			//��ȷ�飨����product��id��property��id����ȷ��λ�� PropertyValueDAO�������� get(int ptid, int pid)
			PropertyValue propertyValue = this.get(product.getId(), property.getId());
			//if���������������ֵ�Ƿ�Ϊ�գ���Ϊ������Ҫ��ʼ����
			if (propertyValue == null)
			{
				propertyValue = new PropertyValue();
				propertyValue.setProduct(product);
				propertyValue.setProperty(property);
				this.add(propertyValue);
			}
		}
	}
	
	//10  ��ĳ��Ʒ�µ�ȫ������ֵ
	public List<PropertyValue> list(int ptid)
	{
		List<PropertyValue> propertyValues = null;
		try
		{
			conn = DBConn.getConnection();
			String sql = "select * from propertyvalue where ptid = " + ptid;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while (rs.next())
			{
				propertyValues = new ArrayList<>();
				PropertyValue propertyValue = new PropertyValue();
				
				propertyValue.setId(rs.getInt("id"));
				propertyValue.setValue(rs.getString("value"));
				
				propertyValue.setProduct(new ProductDAO().get(ptid));
				int pid = rs.getInt("pid");
				propertyValue.setProperty(new PropertyDAO().get(pid));
				
				propertyValues.add(propertyValue);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		//�Ͽ����ݿ�����
		DBClose.disconnection(stmt, conn);
		return propertyValues;
	}
}
