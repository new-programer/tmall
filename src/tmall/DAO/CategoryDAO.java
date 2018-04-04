/**
 * ������Ҫ�ǶԱ�category����ɾ�Ĳ飨CRUD  create read update delete��
 * tmall���ݿ�  category��ṹ���£�
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
		
	//1  ��
	public void add(Category category)
	{
		//�������ݿⲢִ�в������
		try
		{
			conn = DBConn.getConnection();
			String sql = "insert into category values(null, ?)"; //���ݿ�������
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, category.getName());
			pstmt.execute();  //ִ�в������
			
			//����idֻ��ͨ��Statement��PreparedStatement��getGeneratedKeys()��������ȡ
			rs = pstmt.getGeneratedKeys();
			if (rs.next())
			{
				int id = rs.getInt(1);  //��rs���ݼ�����ȡ������id����ֵ��id
				category.setId(id);  //��ʵ��category������id��ֵ
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	//2  ɾ������id����ɾ��������
	public void delete(int id)
	{
		//�������ݿⲢִ�в������
		try
		{
			conn = DBConn.getConnection();
			String sql = "delete from category where id = ��";//��idΪ�ж�����
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.execute();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
	//3  ��
	public void update(Category category)
	{
		//�������ݿⲢ
		try
		{
			conn = DBConn.getConnection();
			String sql = "update category set name = ? where id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, category.getName());
			pstmt.setInt(2, category.getId());
			
			pstmt.execute();//ִ�и��²���
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	//4  �飨����id�飩
		public Category get(int id)
		{
			Category category = new Category();  //ʵ����һ��Category����
			//�������ݿⲢ���в�ѯ����
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
					category.setName(rs.getString(2));//����rs���ݼ��л�ȡ��name��ֵ��setName
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			return category;  //���ؽ��
		}
	
		//��CRUD���� �漰List����
	//5   ��ҳ��
		public List<Category> list(int start, int count)
		{
			//����һ��Category �༯��
			List<Category> categories = new ArrayList<>();
			//�������ݿ�
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
					category.setId(rs.getInt(1));   //����rs���ݼ��л�ȡ��id��ֵ��setId
					category.setName(rs.getString(2));  //����rs���ݼ��л�ȡ��name��ֵ��setName
					
					categories.add(category);//������category����category�༯��categories��ȥ
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			
			return categories;  //����category�༯��categories
		}
	
	//6  ȫ����
		public List<Category> list()
		{
			//Ҳ����ͨ������public List<Category> list(int start, int count)����ѯȫ����Ϣ
			return list(0, Short.MAX_VALUE);  //����˼�壺����short�������ֵ
			
/*	����Ĵ����public List<Category> list(int start, int count)����Ĵ����кܴ������ԣ�ֱ��������ķ�ʽ��ȡ�������
 * 		List<Category> categories = new ArrayList<>();
			//�������ݿ�
			try
			{
				conn = DBConn.getConnection();
				String sql = "select * from category";  //��ѯȫ����Ϣ
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();  //����������rs��
				
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
			return categories; //���ز�ѯ�������
*/		
		}
	
	//7  ��ȡ����
		public int getTotal()
		{
			int total = 0;
			//�������ݿⲢִ�в�ѯ����
			try
			{
				conn = DBConn.getConnection();
				String sql = "select count(*) from category";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				if (rs.next())
				{
					total = rs.getInt(1); //����ѯ�������total
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			
			return total;
		}
	
}