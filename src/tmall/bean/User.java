/**
 * 
 */
package tmall.bean;

/**
 * @author Administrator
 *
 */
public class User
{
	private int id;
	private String name;
	private String password;
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public String getAnonymousName()
	{
		if (name.length() <= 1)
		{
			return "*";   //����û�������С�ڵ���1��ֻ����*��
		}
		if (name.length() == 2 )
		{
			return name.substring(0, 1) + "*"; //���ȵ���2���ڶ����ַ�����*��
		}
		char[] chs = name.toCharArray();  //���ַ���תΪ�ַ�����洢��chs��
		for (int i=1; i < chs.length -1; i++)
		{
			chs[i] = '*';
		}
		return new String(chs);  //���ı����ַ�������תΪ�ַ���������
	}
}
