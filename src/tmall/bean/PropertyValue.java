 /**
 * 
 */
package tmall.bean;

/**
 * @author Administrator
 *
 */
public class PropertyValue
{
	private int id;
	private String value;   //记录各种属性对应的值
	private Product product;  //和Product多对一关系
	private Property property;  //Property多对一关系
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getValue()
	{
		return value;
	}
	public void setValue(String value)
	{
		this.value = value;
	}
	public Product getProduct()
	{
		return product;
	}
	public void setProduct(Product product)
	{
		this.product = product;
	}
	public Property getProperty()
	{
		return property;
	}
	public void setProperty(Property property)
	{
		this.property = property;
	}
}
