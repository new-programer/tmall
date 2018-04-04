/**
 * tmall���ݿ��ṹ���£�
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
		
		//��������type����
		public static final String TYPE_SINGLE = "type_single";//���嵥ͼ���ͳ���
		public static final String TYPE_DETAIL = "type_detail";//����ϸ��ͼ���ͳ���
	//������CRUD����
	//1  ��
	public boolean add(ProductImage productImage)
	{
		boolean bool = false;
		//�������ݿ�
		try
		{
			conn = DBConn.getConnection();
			String sql = "insert into ProductImage values (null, ?, ?)";
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, productImage.getProduct().getId());  //��ȡĳһ��Ʒ��id
			pstmt.setString(2, productImage.getType());
			bool = pstmt.execute();   //ִ�в������,ִ�н�����ظ�bool
			rs = pstmt.getGeneratedKeys();   //��ȡ����id
			if(rs.next())
			{
				//���Զ����ɵ�id(����id)��rs ���ݼ�����ȡ������setId()
				//Ŀ�ģ���ʵ����ProductImage�������id�����ݿⱣ��һ��
				productImage.setId(rs.getInt(1));
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
			String sql = "delete from productimage where id = " + id;
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
	
	//3  ��    //��ҵ������  �˺������п���
	public boolean update(ProductImage ProductImage)
	{
		boolean bool = false;
		//�������ݿ�
		try
		{
			conn = DBConn.getConnection();
			String sql = "update ProductImage set ptid=?, name=? where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ProductImage.getProduct().getId());
			pstmt.setString(2, ProductImage.getType());
			pstmt.setInt(3, ProductImage.getId());
			
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
	public ProductImage get(int id)
	{
		ProductImage ProductImage = new ProductImage();
		//�������ݿ�
		try
		{
			conn = DBConn.getConnection();
			String sql = "select * from ProductImage where id = " + id;
			stmt = conn.createStatement();  //����statement���������ִ��SQL��䣡��
			rs = stmt.executeQuery(sql);  //��rs ���ռ���ѯ�����
			
			if (rs.next())
			{
				ProductImage.setId(id);
				ProductImage.setType(rs.getString("type"));
				
				int ptid = rs.getInt("ptid");  //��rs�л��ptid��ֵ
				//����ͨ��ptid������ProductDAO���get()����������ptid��Ӧ��Product���һ��ʵ��������
				Product product = new ProductDAO().get(ptid);
				//�ٽ���product����ProductImage��setProduct()����
				ProductImage.setProduct(product);
			
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		//�Ͽ����ݿ�����
		DBClose.disconnection(stmt, conn);
		return ProductImage;   //���ؽ��
	}
	
	//5  ��ȡ����
	public int getTotal()
	{
		int total = 0;
		//�������ݿ�
		try
		{
			conn = DBConn.getConnection();
			String sql = "select count(*) from productimage";  //��ѯ���ǵ�sql���
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
	
	//6   ��ҳ��ѯ
	public List<ProductImage> list(Product product, String type, int start, int count)
	{
		List <ProductImage> productimages = null;
		//�������ݿ�
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
				ProductImage productimage = new ProductImage();  //ʵ����ProductImage
				
				productimage.setId(rs.getInt(1));
				productimage.setProduct(product);
				productimage.setType(type);
				
				productimages.add(productimage);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return productimages;  //���ؽ��
	}
	
	//7  ȫ��ѯ
	public List<ProductImage> list(Product product, String type)
	{
		return list(product, type, 0, Short.MAX_VALUE);
	}
}
