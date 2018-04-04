/**
 * 
 */
package tmall.bean;

import java.util.Date;

/**
 * @author Administrator
 *
 */
public class Review
{
	private int id;
	private String content;   //用存储评价内容
	private Date createDate;  //评价创建日期
	private User user;    		//和User多对一的关系
	private Product product;   //和product多对一的关系
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getContent()
	{
		return content;
	}
	public void setContent(String content)
	{
		this.content = content;
	}
	public Date getCreateDate()
	{
		return createDate;
	}
	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
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
	
}
