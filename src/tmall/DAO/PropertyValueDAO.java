/**
 * tmall 数据库的PropertyValue表结构如下
 * 	+-------+--------------+------+-----+---------+----------------+
	| Field | Type         | Null | Key | Default | Extra          |
	+-------+--------------+------+-----+---------+----------------+
	| id    | int(11)      | NO   | PRI | NULL    | auto_increment |
	| pid   | int(11)      | YES  | MUL | NULL    |                |
	| ptid  | int(11)      | YES  | MUL | NULL    |                |
	| value | varchar(255) | YES  |     | NULL    |                |
	+-------+--------------+------+-----+---------+----------------+
	PropertyValue的实体类属性：
	private int id;
	private String value;   //记录各种属性对应的值
	private Product product;  //和Product多对一关系
	private Property property;  //Property多对一关系
	
 *注： 这四个方法供测试使用（实际业务并没有用到）。
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
	
	//基本的CRUD操作
	//1  增
	public boolean add(PropertyValue propertyValue)
	{
		boolean bool = false;
		//连接数据库
		try
		{
			conn = DBConn.getConnection();
			String sql = "insert into PropertyValue values (null, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, propertyValue.getProperty().getId());  //某一属性的id
			pstmt.setInt(2, propertyValue.getProduct().getId());   //某一商品的id
			pstmt.setString(3, propertyValue.getValue());
			
			bool = pstmt.execute();   //执行插入操作,执行结果返回给bool
			rs = pstmt.getGeneratedKeys();   //获取自增id
			if(rs.next())
			{
				//将自动生成的id(自增id)从rs 数据集合中取出传给setId()
				//目的：是实体类PropertyValue各对象的id和数据库保持一致
				propertyValue.setId(rs.getInt(1));
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
			String sql = "delete from propertyvalue where id = " + id;
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
	public boolean update(PropertyValue propertyValue)
	{
		boolean bool = false;
		//连接数据库
		try
		{
			conn = DBConn.getConnection();
			String sql = "update PropertyValue set pid=?, ptid=?, value=? where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, propertyValue.getProperty().getId());
			pstmt.setInt(2, propertyValue.getProduct().getId());
			pstmt.setString(3, propertyValue.getValue());
			
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
	public PropertyValue get(int id)
	{
		PropertyValue propertyValue = null;
		//连接数据库
		try
		{
			conn = DBConn.getConnection();
			String sql = "select * from propertyvalue where id = " + id;
			stmt = conn.createStatement();  //创建statement类对象，用来执行SQL语句！！
			rs = stmt.executeQuery(sql);  //用rs 来收集查询结果集
			
			if (rs.next())
			{
				propertyValue = new PropertyValue();
				propertyValue.setId(id);
				propertyValue.setValue(rs.getString("value"));
				
				int pid = rs.getInt("pid");  //从rs中获得pid的值
				//下面通过pid，调用PropertyDAO类的get()方法来返回pid对应的property类的一个实例化对象
				Property property = new PropertyDAO().get(pid);
				//再将该property传给PropertyValue的setProperty()函数
				propertyValue.setProperty(property);
			
				int ptid = rs.getInt("ptid");  //从rs中获得ptid的值
				//下面通过ptid，调用ProductDAO类的get()方法来返回ptid对应的product类的一个实例化对象
				Product product = new ProductDAO().get(ptid);
				//再将该product传给PropertyValue的setProduct()函数
				propertyValue.setProduct(product);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		//断开数据库连接
		DBClose.disconnection(stmt, conn);
		return propertyValue;   //返回结果
	}
	
	//5  获取总属性值
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
		
		//断开数据库连接
		DBClose.disconnection(stmt, conn);
		return total;
	}
	
	//6  精确查（根据product的id和property的id来精确定位）
	public PropertyValue get(int ptid, int pid)
	{
		PropertyValue propertyValue = null;
		//连接数据库
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
				
				//用ProductDAO对象的get()方法获取product传给setProduct()
				propertyValue.setProduct(new ProductDAO().get(ptid));
				
				propertyValue.setProperty(new PropertyDAO().get(pid));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		//断开数据库连接
		DBClose.disconnection(pstmt, conn);
		return propertyValue;   //返回结果
	}
	
	
	//7  分页查  （主要用于测试）
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
		//断开数据库连接
		DBClose.disconnection(pstmt, conn);
		return propertyValues;
	}
	
	//8  全部查
	public List<PropertyValue> list()
	{
		return list(0, Short.MAX_VALUE);
	}
	
	//9 属性初始化  （注意this指针的使用）
	public void init(Product product)
	{
		//实例化一个Property类型的集合来 保存商品product的所有属性
		List<Property> properties = new PropertyDAO().list(product.getCategory().getId());
		//用forEach遍历properties，并将每个元素进行初始化
		for (Property property : properties)
		{
			//精确查（根据product的id和property的id来精确定位） PropertyValueDAO（自身）的 get(int ptid, int pid)
			PropertyValue propertyValue = this.get(product.getId(), property.getId());
			//if语句块用来检测属性值是否为空，若为空则需要初始化，
			if (propertyValue == null)
			{
				propertyValue = new PropertyValue();
				propertyValue.setProduct(product);
				propertyValue.setProperty(property);
				this.add(propertyValue);
			}
		}
	}
	
	//10  查某产品下的全部属性值
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
		
		//断开数据库连接
		DBClose.disconnection(stmt, conn);
		return propertyValues;
	}
}
