/**
 * tmall���ݿ�  product��ṹ���£�
 *  +---------------+--------------+------+-----+---------+----------------+
	| Field         | Type         | Null | Key | Default | Extra          |
	+---------------+--------------+------+-----+---------+----------------+
	| id            | int(11)      | NO   | PRI | NULL    | auto_increment |
	| cid           | int(11)      | YES  | MUL | NULL    |                |
	| name          | varchar(255) | YES  |     | NULL    |                |
	| subTitle      | varchar(255) | YES  |     | NULL    |                |
	| originalPrice | float        | YES  |     | NULL    |                |
	| promotePrice  | float        | YES  |     | NULL    |                |
	| stock         | int(11)      | YES  |     | NULL    |                |
	| CreateDate    | datetime     | YES  |     | NULL    |                |
	+---------------+--------------+------+-----+---------+----------------+
	Product ʵ���������
	private int id;
	private String name;			//��Ʒ����
	private String subTitle;           //С����
	private float originalPrice;    //ԭʼ�۸�
	private float promotePrice;        //�����۸�
	private Date createDate;  //��Ʒ��������
	private int stock;      //���
	private Category category;   //��category���һ��ϵ
	private int reviewCount;  //��������
	private int saleCount;   //����
	private ProductImage firstProductImage;  //������ʾ��Ʒ��Ĭ��ͼƬ
	private List<ProductImage> productSingleImages;  //��Ʒ��ͼ����
	private List<ProductImage> productDetailImages;  //��Ʒ����ͼ����
	
	��Ҫ��CRUD���������ͷ�CRUD����
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
import tmall.bean.Category;
import tmall.bean.Product;
import tmall.bean.ProductImage;

/**
 * @author Administrator
 *
 */
public class ProductDAO
{
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
	//������CRUD����
	//1  ��
	public boolean add(Product product)
	{
		boolean bool = false;
		//�������ݿ�
		try
		{
			conn = DBConn.getConnection();
			String sql = "insert into Product values (null, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, product.getCategory().getId());  //��ȡĳһ�����id
			pstmt.setString(2, product.getName());
			pstmt.setString(3, product.getSubTitle());
			pstmt.setFloat(4, product.getOriginalPrice());
			pstmt.setFloat(5, product.getPromotePrice());
			pstmt.setInt(6, product.getStock());
			pstmt.setTimestamp(7, DateUtil.date_to_timestamp(product.getCreateDate()));			//����ת��������DateUtil���ߣ�

			bool = pstmt.execute();   //ִ�в������,ִ�н�����ظ�bool��ִ��֮�����������id
			
			rs = pstmt.getGeneratedKeys();   //��ȡ����id
			if(rs.next())
			{
				//���Զ����ɵ�id(����id)��rs ���ݼ�����ȡ������setId()
				//Ŀ�ģ���ʵ����Product�������id�����ݿⱣ��һ��
				product.setId(rs.getInt(1));  
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
			String sql = "delete from product where id = " + id;
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
	public boolean update(Product product)
	{
		boolean bool = false;
		//�������ݿ�
		try
		{
			conn = DBConn.getConnection();
			String sql = "update product set cid=?, name=?, subTitle=?, originalPrice=?, promotePrice=?, stock=?, CreateDate=? where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, product.getCategory().getId());
			pstmt.setString(2, product.getName());
			pstmt.setString(3, product.getSubTitle());
			pstmt.setFloat(4, product.getOriginalPrice());
			pstmt.setFloat(5, product.getPromotePrice());
			pstmt.setInt(6, product.getStock());
			pstmt.setTimestamp(7, DateUtil.date_to_timestamp(product.getCreateDate()));
			pstmt.setInt(8, product.getId());
			
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
	public Product get(int id)
	{
//		Product product = new Product();    //���ַ�ʽĬ�Ϸ��ص��ǣ����� + @ + ��ַ�����ô˷�������id�鲻�����ʱҲ�᷵��һ��ʵ�ʵ�ַ�����Ƿ���null��
		Product product = null;          //��������������һ��
		//�������ݿ�
		try
		{
			conn = DBConn.getConnection();
			String sql = "select * from Product where id = " + id;
			stmt = conn.createStatement();  //����statement���������ִ��SQL��䣡��
			rs = stmt.executeQuery(sql);  //��rs ���ռ���ѯ�����
			
			if (rs.next())
			{
				product = new Product();   //�ڴ�ʵ������ǰ��ʵ������һ�㣬���ɼ���
				product.setId(id);
				product.setName(rs.getString("name"));
				product.setSubTitle(rs.getString("subTitle"));
				product.setOriginalPrice(rs.getFloat("originalPrice"));
				product.setPromotePrice(rs.getFloat("promotePrice"));
				product.setStock(rs.getInt("stock"));
				product.setCreateDate(rs.getDate("CreateDate"));
				
				int cid = rs.getInt("cid");  //��rs�л��cid��ֵ
				//����ͨ��cid������CategoryDAO���get()����������cid��Ӧ��Category���һ��ʵ��������
				Category category = new CategoryDAO().get(cid);
				//�ٽ���category����Product��setCategory()����
				product.setCategory(category);
			
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		//�Ͽ����ݿ�����
		DBClose.disconnection(stmt, conn);
		return product;   //���ؽ��
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
			String sql = "select count()* from product where cid = " + cid;
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
	public List<Product> list (int cid, int start, int count)
	{
		//��products�������ռ����product
		List<Product> products = null;
		//�������ݿ�
		try
		{
			conn = DBConn.getConnection();
			String sql = "select * from product where cid=? order by id desc limit ?, ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cid);
			pstmt.setInt(2, start);
			pstmt.setInt(3, count);
			
			rs = pstmt.executeQuery();
			
			//�����ݴ���rs�еĲ�ѯ���
			while (rs.next())
			{
				products = new ArrayList<>();
				Product product = new Product();
				
				product.setId(rs.getInt(1));   //rs�е�һ������product��id���ʿ��ò���1��ȡ
				product.setName(rs.getString("name"));  
				product.setSubTitle(rs.getString("subTitle"));
				product.setOriginalPrice(rs.getFloat("originalPrice"));
				product.setPromotePrice(rs.getFloat("promotePrice"));
				product.setStock(rs.getInt("stock"));
				product.setCreateDate(rs.getDate("CreateDate"));
				
				//����ͨ��cid������CategoryDAO���get()����������cid��Ӧ��Category���һ��ʵ��������
				Category category = new CategoryDAO().get(cid);
				//�ٽ���category����product��setCategory()����
				product.setCategory(category);
				
				setFirstProductImage(product);  //����Ĭ��ͼƬ
				products.add(product);  //����������product��ӵ�products����list��ȥ
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		//�Ͽ����ݿ�����
		DBClose.disconnection(pstmt, conn);
		return products;
	}
	
	//7  ȫ����
	public List<Product> list(int cid)
	{
		return list(cid, 0, Short.MAX_VALUE);  //����cid��Ӧ��ĳ������������
	}
	
	//8  �������������Ʒ
	public void fill(Category category)
	{
		//category.getId()��ȡcid,list()��������cid����ѯcid��Ӧ��ĳһ��������в�Ʒ�� ���������ֵ��products����
		List<Product> products = this.list(category.getId());
		category.setProducts(products); //��category���������䣨��ֵ��
	}
	
	//9  �����з��������Ʒ
	public void fill(List<Category> categories)
	{
		//��forEach������categories������ÿ���������
		for (Category category : categories)
		{
			fill(category);  //���õ�������Ʒ��亯��
		}
	}
	
	//10   ���������Ԥ����Ʒ���
	public void fillByRow(List<Category> categories)
	{
		final int PNER = 8; //PNER��ProductNumberEachRow����д
		//��forEach����categories����
		for (Category category : categories)
		{
			//��ȡÿ�����µ�������Ʒ
			List<Product> products = category.getProducts();  //û��products��Ԫ�ظ���С�ڵ���PNER
			//ÿPNER����Ʒ��product����productsByrow�������ռ�����produstsByRow��ÿ��Ԫ�ض�ӦPNER����Ʒ��product��
			List<List<Product>> productsByRow = new ArrayList<>();
			
			//�����ǰ�PNER��Ԫ�������ķ�ʽ����products
			for (int i=0; i<products.size(); i+=PNER)
			{
				//��size��������¼  Ҫ��������Ԫ�ص�λ�ã�ÿ�α���PNER����
				int size = i + PNER;
				
				/*
				 * �ж�size�Ƿ����products�ĳ��ȣ����������˵����α�������PNEER����
				size��ֵȡΪproducts.size()������ʣ��ļ�����С��PNER�����ɣ����������size��ֵ����
				*/
				size = size > products.size() ? products.size() : size;
				
				//��productsOfEachRow���ռ�ÿ�α����Ľ����Ŀ���Ǳ�����ǰ�˰�PNER����ʾ
				List<Product> productsOfEachRow = products.subList(i, size); //size - i = PNER
				productsByRow.add(productsOfEachRow);
			}
			//��ֵ
			category.setProductsByRow(productsByRow);
		}
	}
	
	//11  ������Ʒ��һ��ͼƬΪĬ��ͼƬ
	public void setFirstProductImage(Product product)
	{
		//��productImages��ȡָ����Ʒ�����е�ͼ
		List<ProductImage> productImages = new ProductImageDAO().list(product, ProductImageDAO.TYPE_SINGLE);
		//��productImages��Ϊ�գ������һ����ͼΪ��ƷĬ��ͼƬ
		if (!productImages.isEmpty())
		{
			product.setFirstProductImage(productImages.get(0));
		}
	}
	
	//12       ������Ʒ����������������������
	public void setNumberOfSaleAndReview(Product product)
	{
		//�Ӷ������л��ĳ��Ʒ������
		int saleCount = new OrderItemDAO().getSaleCount(product.getId());
		//��������
		product.setSaleCount(saleCount);
		
		//��ReviewDAO�л�ȡĳ��Ʒ����������
		int reviewCount = new ReviewDAO().getTotal(product.getId());
		//������Ʒ��������
		product.setReviewCount(reviewCount);
	}
	
	//13  ������Ʒ�������͹ؼ��ֵ�����
	public void setNumberOfSaleAndReview(List<Product> products)
	{
		for (Product product : products)
		{
			setNumberOfSaleAndReview(product);
		}
	}
	
	//14  ���ݹؼ��ַ�ҳ������Ʒ
	public List<Product> list (String keyword, int start, int count)
	{
		//��products�������ռ����product
		List<Product> products = null;
		//�������ݿ�
		try
		{
			conn = DBConn.getConnection();
			String sql = "select * from product where name like ? order by id desc limit ?, ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setInt(2, start);
			pstmt.setInt(3, count);
			
			rs = pstmt.executeQuery();
			
			//�����ݴ���rs�еĲ�ѯ���
			while (rs.next())
			{
				products = new ArrayList<>();
				Product product = new Product();
				
				product.setId(rs.getInt(1));   //rs�е�һ������product��id���ʿ��ò���1��ȡ
				product.setName(rs.getString("name"));  
				product.setSubTitle(rs.getString("subTitle"));
				product.setOriginalPrice(rs.getFloat("originalPrice"));
				product.setPromotePrice(rs.getFloat("promotePrice"));
				product.setStock(rs.getInt("stock"));
				product.setCreateDate(rs.getDate("CreateDate"));
				
				int cid = rs.getInt("cid");
				//����ͨ��cid������CategoryDAO���get()����������cid��Ӧ��Category���һ��ʵ��������
				Category category = new CategoryDAO().get(cid);
				//�ٽ���category����product��setCategory()����
				product.setCategory(category);
				
				setFirstProductImage(product);  //����Ĭ��ͼƬ
				products.add(product);  //����������product��ӵ�products����list��ȥ
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		//�Ͽ����ݿ�����
		DBClose.disconnection(pstmt, conn);
		return products;
	}
}
