/**
 * 
 */
package tmall.bean;

/**
 * @author Administrator
 *
 */
public class OrderItem
{
	private int id;
	private int number;  //��¼������Ʒ������
	private User user;  //��User���һ��ϵ
	private Product product;	//��Product���һ��ϵ
	private Order order;	//��Order���һ��ϵ
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public int getNumber()
	{
		return number;
	}
	public void setNumber(int number)
	{
		this.number = number;
	}
	public User getUser()
	{
		return user;
	}
	public void setUser(User user)
	{
		this.user = user;
	}
	public Product getProduct()
	{
		return product;
	}
	public void setProduct(Product product)
	{
		this.product = product;
	}
	public Order getOrder()
	{
		return order;
	}
	public void setOrder(Order order)
	{
		this.order = order;
	}
	
}
