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
	private String content;   //�ô洢��������
	private Date createDate;  //���۴�������
	private User user;    		//��User���һ�Ĺ�ϵ
	private Product product;   //��product���һ�Ĺ�ϵ
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
