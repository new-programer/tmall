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
	private String orderCode;//�������
	private String address;	//�ջ���ַ
	private String post;	//�ʱ�
	private String receiever;	//�ռ���
	private String mobile;	//�ռ��˵绰
	private String userMessage; 	//�û�����
	private Date createDate;		//������������
	private Date payDate;			//������������
	private Date delieveryDate;		//��������
	private Date confirmDate;		//�ջ�����
	private User user;	//��User���һ�Ĺ�ϵ
	private List<OrderItem> orderitems;  //��OrderItemһ�Զ��ϵ
	private float totalMoney;  //��Ʒ�ܽ��
	private int totalNumber;  //��Ʒ������
	private String status;		//����״̬
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
