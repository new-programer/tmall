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
			return "*";   //如果用户名长度小于等于1则只返回*号
		}
		if (name.length() == 2 )
		{
			return name.substring(0, 1) + "*"; //长度等于2，第二个字符返回*号
		}
		char[] chs = name.toCharArray();  //将字符串转为字符数组存储在chs中
		for (int i=1; i < chs.length -1; i++)
		{
			chs[i] = '*';
		}
		return new String(chs);  //将改变后的字符数组再转为字符串并返回
	}
}
