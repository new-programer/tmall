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
	private String type;     //��ƷͼƬ���ͣ�single��detail��
	private Product product;  //�Ͳ�Ʒ���һ�Ĺ�ϵ
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
