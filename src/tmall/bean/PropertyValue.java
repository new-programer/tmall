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
	private String value;   //��¼�������Զ�Ӧ��ֵ
	private Product product;  //��Product���һ��ϵ
	private Property property;  //Property���һ��ϵ
	
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
