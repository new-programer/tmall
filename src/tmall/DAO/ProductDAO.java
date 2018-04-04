/**
 * tmall数据库  product表结构如下：
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
	Product 实体类参数：
	private int id;
	private String name;			//产品名称
	private String subTitle;           //小标题
	private float originalPrice;    //原始价格
	private float promotePrice;        //促销价格
	private Date createDate;  //产品创建日期
	private int stock;      //库存
	private Category category;   //和category多对一关系
	private int reviewCount;  //评价数量
	private int saleCount;   //销量
	private ProductImage firstProductImage;  //用于显示产品的默认图片
	private List<ProductImage> productSingleImages;  //产品单图集合
	private List<ProductImage> productDetailImages;  //产品详情图集合
	
	主要有CRUD基本操作和非CRUD操作
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
	
	//基本的CRUD操作
	//1  增
	public boolean add(Product product)
	{
		boolean bool = false;
		//连接数据库
		try
		{
			conn = DBConn.getConnection();
			String sql = "insert into Product values (null, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, product.getCategory().getId());  //获取某一分类的id
			pstmt.setString(2, product.getName());
			pstmt.setString(3, product.getSubTitle());
			pstmt.setFloat(4, product.getOriginalPrice());
			pstmt.setFloat(5, product.getPromotePrice());
			pstmt.setInt(6, product.getStock());
			pstmt.setTimestamp(7, DateUtil.date_to_timestamp(product.getCreateDate()));			//日期转换（利用DateUtil工具）

			bool = pstmt.execute();   //执行插入操作,执行结果返回给bool，执行之后会生成自增id
			
			rs = pstmt.getGeneratedKeys();   //获取自增id
			if(rs.next())
			{
				//将自动生成的id(自增id)从rs 数据集合中取出传给setId()
				//目的：是实体类Product各对象的id和数据库保持一致
				product.setId(rs.getInt(1));  
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
			String sql = "delete from product where id = " + id;
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
	public boolean update(Product product)
	{
		boolean bool = false;
		//连接数据库
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
	public Product get(int id)
	{
//		Product product = new Product();    //这种方式默认返回的是：类名 + @ + 地址，若用此方法根据id查不到结果时也会返回一个实际地址而不是返回null了
		Product product = null;          //这样做声明更好一点
		//连接数据库
		try
		{
			conn = DBConn.getConnection();
			String sql = "select * from Product where id = " + id;
			stmt = conn.createStatement();  //创建statement类对象，用来执行SQL语句！！
			rs = stmt.executeQuery(sql);  //用rs 来收集查询结果集
			
			if (rs.next())
			{
				product = new Product();   //在此实例化比前面实例化好一点，理由见上
				product.setId(id);
				product.setName(rs.getString("name"));
				product.setSubTitle(rs.getString("subTitle"));
				product.setOriginalPrice(rs.getFloat("originalPrice"));
				product.setPromotePrice(rs.getFloat("promotePrice"));
				product.setStock(rs.getInt("stock"));
				product.setCreateDate(rs.getDate("CreateDate"));
				
				int cid = rs.getInt("cid");  //从rs中获得cid的值
				//下面通过cid，调用CategoryDAO类的get()方法来返回cid对应的Category类的一个实例化对象
				Category category = new CategoryDAO().get(cid);
				//再将该category传给Product的setCategory()函数
				product.setCategory(category);
			
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		//断开数据库连接
		DBClose.disconnection(stmt, conn);
		return product;   //返回结果
	}
	
	//非CRUD基本操作
	//5  获取属性总数 （注意是某一分类对应的属性总数，故必须先获取Category 类id :'cid'）
	public int getTotal(int cid)
	{
		int total = 0;
		//连接数据库
		try
		{
			conn = DBConn.getConnection();
			String sql = "select count()* from product where cid = " + cid;
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
	
	//6  分页查
	public List<Product> list (int cid, int start, int count)
	{
		//用products集合来收集查的product
		List<Product> products = null;
		//连接数据库
		try
		{
			conn = DBConn.getConnection();
			String sql = "select * from product where cid=? order by id desc limit ?, ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cid);
			pstmt.setInt(2, start);
			pstmt.setInt(3, count);
			
			rs = pstmt.executeQuery();
			
			//遍历暂存在rs中的查询结果
			while (rs.next())
			{
				products = new ArrayList<>();
				Product product = new Product();
				
				product.setId(rs.getInt(1));   //rs中第一个就是product的id，故可用参数1获取
				product.setName(rs.getString("name"));  
				product.setSubTitle(rs.getString("subTitle"));
				product.setOriginalPrice(rs.getFloat("originalPrice"));
				product.setPromotePrice(rs.getFloat("promotePrice"));
				product.setStock(rs.getInt("stock"));
				product.setCreateDate(rs.getDate("CreateDate"));
				
				//下面通过cid，调用CategoryDAO类的get()方法来返回cid对应的Category类的一个实例化对象
				Category category = new CategoryDAO().get(cid);
				//再将该category传给product的setCategory()函数
				product.setCategory(category);
				
				setFirstProductImage(product);  //设置默认图片
				products.add(product);  //讲遍历到的product添加到products集合list中去
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		//断开数据库连接
		DBClose.disconnection(pstmt, conn);
		return products;
	}
	
	//7  全部查
	public List<Product> list(int cid)
	{
		return list(cid, 0, Short.MAX_VALUE);  //返回cid对应的某分类属性总数
	}
	
	//8  给单个类填充商品
	public void fill(Category category)
	{
		//category.getId()获取cid,list()方法根据cid来查询cid对应的某一分类的所有产品， 并将结果赋值给products集合
		List<Product> products = this.list(category.getId());
		category.setProducts(products); //给category对向进行填充（赋值）
	}
	
	//9  给所有分类填充商品
	public void fill(List<Category> categories)
	{
		//用forEach来遍历categories，并对每个进行填充
		for (Category category : categories)
		{
			fill(category);  //调用单个类商品填充函数
		}
	}
	
	//10   各分类横向预览商品填充
	public void fillByRow(List<Category> categories)
	{
		final int PNER = 8; //PNER是ProductNumberEachRow的缩写
		//用forEach遍历categories集合
		for (Category category : categories)
		{
			//获取每个类下的所有商品
			List<Product> products = category.getProducts();  //没个products的元素个数小于等于PNER
			//每PNER个商品（product）用productsByrow集合来收集，即produstsByRow的每个元素对应PNER个商品（product）
			List<List<Product>> productsByRow = new ArrayList<>();
			
			//下面是安PNER个元素增长的方式遍历products
			for (int i=0; i<products.size(); i+=PNER)
			{
				//用size变量来记录  要遍历到的元素的位置（每次遍历PNER个）
				int size = i + PNER;
				
				/*
				 * 判断size是否大于products的长度，如果大于则说明这次遍历不足PNEER个，
				size的值取为products.size()来遍历剩余的几个（小于PNER）即可，否则继续安size的值遍历
				*/
				size = size > products.size() ? products.size() : size;
				
				//用productsOfEachRow来收集每次遍历的结果，目的是便于在前端安PNER个显示
				List<Product> productsOfEachRow = products.subList(i, size); //size - i = PNER
				productsByRow.add(productsOfEachRow);
			}
			//赋值
			category.setProductsByRow(productsByRow);
		}
	}
	
	//11  设置商品第一个图片为默认图片
	public void setFirstProductImage(Product product)
	{
		//用productImages获取指定商品的所有单图
		List<ProductImage> productImages = new ProductImageDAO().list(product, ProductImageDAO.TYPE_SINGLE);
		//若productImages不为空，则设第一个单图为商品默认图片
		if (!productImages.isEmpty())
		{
			product.setFirstProductImage(productImages.get(0));
		}
	}
	
	//12       单个商品的销量和评价数量的设置
	public void setNumberOfSaleAndReview(Product product)
	{
		//从订单项中获得某商品的销量
		int saleCount = new OrderItemDAO().getSaleCount(product.getId());
		//销量设置
		product.setSaleCount(saleCount);
		
		//从ReviewDAO中获取某商品的评价总数
		int reviewCount = new ReviewDAO().getTotal(product.getId());
		//设置商品评价总数
		product.setReviewCount(reviewCount);
	}
	
	//13  所有商品的销量和关键字的设置
	public void setNumberOfSaleAndReview(List<Product> products)
	{
		for (Product product : products)
		{
			setNumberOfSaleAndReview(product);
		}
	}
	
	//14  根据关键字分页查找商品
	public List<Product> list (String keyword, int start, int count)
	{
		//用products集合来收集查的product
		List<Product> products = null;
		//连接数据库
		try
		{
			conn = DBConn.getConnection();
			String sql = "select * from product where name like ? order by id desc limit ?, ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setInt(2, start);
			pstmt.setInt(3, count);
			
			rs = pstmt.executeQuery();
			
			//遍历暂存在rs中的查询结果
			while (rs.next())
			{
				products = new ArrayList<>();
				Product product = new Product();
				
				product.setId(rs.getInt(1));   //rs中第一个就是product的id，故可用参数1获取
				product.setName(rs.getString("name"));  
				product.setSubTitle(rs.getString("subTitle"));
				product.setOriginalPrice(rs.getFloat("originalPrice"));
				product.setPromotePrice(rs.getFloat("promotePrice"));
				product.setStock(rs.getInt("stock"));
				product.setCreateDate(rs.getDate("CreateDate"));
				
				int cid = rs.getInt("cid");
				//下面通过cid，调用CategoryDAO类的get()方法来返回cid对应的Category类的一个实例化对象
				Category category = new CategoryDAO().get(cid);
				//再将该category传给product的setCategory()函数
				product.setCategory(category);
				
				setFirstProductImage(product);  //设置默认图片
				products.add(product);  //讲遍历到的product添加到products集合list中去
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		//断开数据库连接
		DBClose.disconnection(pstmt, conn);
		return products;
	}
}
