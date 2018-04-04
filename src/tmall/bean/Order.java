/**
 * 
 */
package tmall.bean;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 *
 */
public class Order
{
	private int id;
	private String orderCode;//订单编号
	private String address;	//收货地址
	private String post;	//邮编
	private String receiever;	//收件人
	private String mobile;	//收件人电话
	private String userMessage; 	//用户留言
	private Date createDate;		//订单创建日期
	private Date payDate;			//订单付款日期
	private Date delieveryDate;		//发货日期
	private Date confirmDate;		//收获日期
	private User user;	//和User多对一的关系
	private List<OrderItem> orderitems;  //和OrderItem一对多关系
	private float totalMoney;  //商品总金额
	private int totalNumber;  //商品总数量
	private String status;		//订单状态
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getOrderCode()
	{
		return orderCode;
	}
	public void setOrderCode(String orderCode)
	{
		this.orderCode = orderCode;
	}
	public String getAddress()
	{
		return address;
	}
	public void setAddress(String address)
	{
		this.address = address;
	}
	public String getPost()
	{
		return post;
	}
	public void setPost(String post)
	{
		this.post = post;
	}
	public String getReceiever()
	{
		return receiever;
	}
	public void setReceiever(String receiever)
	{
		this.receiever = receiever;
	}
	public String getMobile()
	{
		return mobile;
	}
	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}
	public String getUserMessage()
	{
		return userMessage;
	}
	public void setUserMessage(String userMessage)
	{
		this.userMessage = userMessage;
	}
	public Date getCreateDate()
	{
		return createDate;
	}
	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}
	public Date getPayDate()
	{
		return payDate;
	}
	public void setPayDate(Date payDate)
	{
		this.payDate = payDate;
	}
	public Date getDelieveryDate()
	{
		return delieveryDate;
	}
	public void setDelieveryDate(Date delieveryDate)
	{
		this.delieveryDate = delieveryDate;
	}
	public Date getConfirmDate()
	{
		return confirmDate;
	}
	public void setConfirmDate(Date confirmDate)
	{
		this.confirmDate = confirmDate;
	}
	public User getUser()
	{
		return user;
	}
	public void setUser(User user)
	{
		this.user = user;
	}
	public List<OrderItem> getOrderitems()
	{
		return orderitems;
	}
	public void setOrderitems(List<OrderItem> orderitems)
	{
		this.orderitems = orderitems;
	}
	public float getTotalMoney()
	{
		return totalMoney;
	}
	public void setTotalMoney(float totalMoney)
	{
		this.totalMoney = totalMoney;
	}
	public int getTotalNumber()
	{
		return totalNumber;
	}
	public void setTotalNumber(int totalNumber)
	{
		this.totalNumber = totalNumber;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
}
