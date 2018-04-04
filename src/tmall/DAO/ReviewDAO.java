/**
 * tmall���ݿ�   ��review��Ľṹ��
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

		//������CRUD����
	//1  ��
	public boolean add(Review review)
	{
		boolean bool = false;
		//�������ݿ�
		try
		{
			conn = DBConn.getConnection();
			String sql = "insert into Review values (null, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, review.getProduct().getId());  //��ȡĳһ��Ʒ��id
			pstmt.setInt(2, review.getUser().getId());    //�û�id
			pstmt.setString(3, review.getContent());
			pstmt.setTimestamp(4, DateUtil.date_to_timestamp(review.getCreateDate())); //����ת��
			
			bool = pstmt.execute();   //ִ�в������,ִ�н�����ظ�bool
			rs = pstmt.getGeneratedKeys();   //��ȡ����id
			if(rs.next())
			{
				//���Զ����ɵ�id(����id)��rs ���ݼ�����ȡ������setId()
				//Ŀ�ģ���ʵ����Review�������id�����ݿⱣ��һ��
				review.setId(rs.getInt(1));
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
			String sql = "delete from review where id = " + id;
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
	public boolean update(Review review)
	{
		boolean bool = false;
		//�������ݿ�
		try
		{
			conn = DBConn.getConnection();
			String sql = "update review set ptid=?, uti=?, content=?, CreateDate=? where id=?";
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, review.getProduct().getId());  //��ȡĳһ��Ʒ��id
			pstmt.setInt(2, review.getUser().getId());    //�û�id
			pstmt.setString(3, review.getContent());
			pstmt.setTimestamp(4, DateUtil.date_to_timestamp(review.getCreateDate())); //����ת��
			pstmt.setInt(5, review.getId());
			
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
	public Review get(int id)
	{
		Review review = null;
		//�������ݿ�
		try
		{
			conn = DBConn.getConnection();
			String sql = "select * from review where id = " + id;
			stmt = conn.createStatement();  //����statement���������ִ��SQL��䣡��
			rs = stmt.executeQuery(sql);  //��rs ���ռ���ѯ�����
			
			if (rs.next())
			{
				review = new Review();
				
				review.setId(id);
				review.setContent(rs.getString("content"));
				review.setCreateDate(rs.getDate("CreateDate"));
				
				int ptid = rs.getInt("ptid");  //��rs�л��ptid��ֵ
				//����ͨ��ptid������ProductDAO���get()����������ptid��Ӧ��Product���һ��ʵ��������
				Product product = new ProductDAO().get(ptid);
				//�ٽ���product����Review�����setProduct()����
				review.setProduct(product);
				
				int uid = rs.getInt("uid");
				User user = new UserDAO().get(uid);
				review.setUser(user);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		//�Ͽ����ݿ�����
		DBClose.disconnection(stmt, conn);
		return review;   //���ؽ��
	}
	
	//5  ��ȡ����
	public int getTotal()
	{
		int total = 0;
		//�������ݿ�
		try
		{
			conn = DBConn.getConnection();
			String sql = "select count(*) from review";  //��ѯ���ǵ�sql���
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
		return total;  //���ؽ��
	}
	
	//6   ��ҳ��ѯ��ĳһ��Ʒ�µ��������ۣ�
	public List<Review> list(Product product, int start, int count)
	{
		List <Review> reviews = null;
		//�������ݿ�
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
				Review review = new Review();  //ʵ����review
				
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
		return reviews;  //���ؽ��
	}
	
	//7  ȫ��ѯ
	public List<Review> list(Product product)
	{
		return list(product, 0, Short.MAX_VALUE);
	}
	
	//8  ��ȡĳһ��Ʒ�������������Ĳ�������Ʒ��id��
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
	
	//9  ׷������   ���ù��ܴ��Ժ����ƣ�
	/*public void exendReview()
	{
		
	}*/
}
