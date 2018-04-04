/**
 * 
 */
package tmall.bean;

/**
 * @author Administrator
 *
 */
public class ProductImage
{
	private int id;
	private String type;     //产品图片类型（single和detail）
	private Product product;  //和产品多对一的关系
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public Product getProduct()
	{
		return product;
	}
	public void setProduct(Product product)
	{
		this.product = product;
	}
}
