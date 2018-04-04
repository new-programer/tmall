/**tmall���ݿ�property��ṹ�ο���
 *  +-------+-------------+------+-----+---------+----------------+
	| Field | Type        | Null | Key | Default | Extra          |
	+-------+-------------+------+-----+---------+----------------+
	| id    | int(11)     | NO   | PRI | NULL    | auto_increment |
	| cid   | int(11)     | YES  | MUL | NULL    |                |
	| name  | varchar(50) | YES  |     | NULL    |                |
	+-------+-------------+------+-----+---------+----------------+
 * ��Ҫ�ǻ�����CRUD�����ͷ�CRUD���������£�
 * 1.��ȡĳһ���ࣨcid������������    public int getTotal(int cid)
 * 2.��ҳչʾ��ѯ    public List<Property> list(int start, int count)
 * 3.��ѯȫ������    public List<Property> list()
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
	
	//������CRUD����
	//1  ��
	public boolean add(Property property)
	{
		boolean bool = false;
		//�������ݿ�
		try
		{
			conn = DBConn.getConnection();
			String sql = "insert into property values (null, ?, ?)";
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, property.getCategory().getId());  //��ȡĳһ�����id
			pstmt.setString(2, property.getName());
			bool = pstmt.execute();   //ִ�в������,ִ�н�����ظ�bool
			rs = pstmt.getGeneratedKeys();   //��ȡ����id
			if(rs.next())
			{
				//���Զ����ɵ�id(����id)��rs ���ݼ�����ȡ������setId()
				//Ŀ�ģ���ʵ����Property�������id�����ݿⱣ��һ��
				property.setId(rs.getInt(1));
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
			String sql = "delete from property where id = " + id;
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
	public boolean update(Property property)
	{
		boolean bool = false;
		//�������ݿ�
		try
		{
			conn = DBConn.getConnection();
			String sql = "update property set cid=?, name=? where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, property.getCategory().getId());
			pstmt.setString(2, property.getName());
			pstmt.setInt(3, property.getId());
			
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
	public Property get(int id)
	{
		Property property = new Property();
		//�������ݿ�
		try
		{
			conn = DBConn.getConnection();
			String sql = "select * from property where id = " + id;
			stmt = conn.createStatement();  //����statement���������ִ��SQL��䣡��
			rs = stmt.executeQuery(sql);  //��rs ���ռ���ѯ�����
			
			if (rs.next())
			{
				property.setId(id);
				property.setName(rs.getString("name"));
				
				int cid = rs.getInt("cid");  //��rs�л��cid��ֵ
				//����ͨ��cid������CategoryDAO���get()����������cid��Ӧ��Category���һ��ʵ��������
				Category category = new CategoryDAO().get(cid);
				//�ٽ���category����property��setCategory()����
				property.setCategory(category);
			
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		//�Ͽ����ݿ�����
		DBClose.disconnection(stmt, conn);
		return property;   //���ؽ��
	}
	
	//��CRUD��������
	//5  ��ȡ�������� ��ע����ĳһ�����Ӧ�������������ʱ����Ȼ�ȡCategory ��id :'cid'��
	public int getTotal(int cid)
	{
		int total = 0;
		//�������ݿ�
		try
		{
			conn = DBConn.getConnection();
			String sql = "select count()* from property where cid = " + cid;
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
	
	//6  ��ҳ��
	public List<Property> list (int cid, int start, int count)
	{
		//��properties�������ռ����property
		List<Property> properties = new ArrayList<>();
		//�������ݿ�
		try
		{
			conn = DBConn.getConnection();
			String sql = "select * from Property where cid=? order by id desc limit ?, ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cid);
			pstmt.setInt(2, start);
			pstmt.setInt(3, count);
			
			rs = pstmt.executeQuery();
			
			//�����ݴ���rs�еĲ�ѯ���
			while (rs.next())
			{
				Property property = new Property();
				property.setId(rs.getInt(1));
				property.setName(rs.getString("name"));
				
				//����ͨ��cid������CategoryDAO���get()����������cid��Ӧ��Category���һ��ʵ��������
				Category category = new CategoryDAO().get(cid);
				//�ٽ���category����property��setCategory()����
				property.setCategory(category);
				
				properties.add(property);  //����������property��ӵ�propertys����list��ȥ
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		//�Ͽ����ݿ�����
		DBClose.disconnection(pstmt, conn);
		return properties;
	}
	
	//7  ȫ����
	public List<Property> list(int cid)
	{
		return list(cid,0, Short.MAX_VALUE);  //����cid��Ӧ��ĳ������������
	}
}
