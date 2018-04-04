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
	private int number;  //记录各个商品的数量
	private User user;  //和User多对一关系
	private Product product;	//和Product多对一关系
	private Order order;	//和Order多对一关系
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
