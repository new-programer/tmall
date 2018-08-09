package tmall.servlet;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.Util.Page;
import tmall.bean.Category;
import tmall.bean.Property;

public class PropertyServlet extends BasebackServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String add(HttpServletRequest request, HttpServletResponse response, Page page)
	{
		int cid = Integer.parseInt(request.getParameter("cid"));
		Category category = categoryDAO.get(cid); //通过类ID获取分类名称来确定是哪儿分类（产品）的属性
		
		String name = request.getParameter("name"); //获取属性名称
		try
		{
			//逆向操作
			name = new String(name.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		System.out.println("测试属性名是否会乱码：" + name);
		
		//设置属性的实体的值
		Property property = new Property();
		property.setCategory(category);
		property.setName(name);
		//在数据库中存储该属性
		propertyDAO.add(property);
		
		return "@admin_property_list?cid=" + cid; //返回到该分类属性列表页面
	}

	@Override
	public String delete(HttpServletRequest request, HttpServletResponse respnse, Page page)
	{
		int id = Integer.parseInt(request.getParameter("id"));
	
		Property property = propertyDAO.get(id);//通过property对象获得属性归属的类
		propertyDAO.delete(id);
		
		return "@admin_property_list?cid=" + property.getCategory().getId();
	}

	@Override
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page)
	{
		int id = Integer.parseInt(request.getParameter("id"));
		Property property = propertyDAO.get(id);
		request.setAttribute("property", property);
		
		return "admin/editProperty.jsp";
	}

	@Override
	public String update(HttpServletRequest request, HttpServletResponse response, Page page)
	{
		int cid = Integer.parseInt(request.getParameter("cid"));//获得分类ID
		Category category = categoryDAO.get(cid);
		
		int id = Integer.parseInt(request.getParameter("id"));//获得属性ID
		Property property = propertyDAO.get(id);
		
		String name = request.getParameter("name");//获取原来的属性名称
		try
		{
			name = new String(name.getBytes("ISO-8859-1"),"UTF-8");//转码，此行代码必不可少
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		
		//修改property实体内部各个元素的值
		property.setCategory(category);
		property.setId(id);
		property.setName(name);
		
		propertyDAO.update(property);//更新
		return "@admin_property_list?cid=" + property.getCategory().getId();//跳转
	}

	@Override
	public String list(HttpServletRequest request, HttpServletResponse response, Page page)
	{
		int cid = Integer.parseInt(request.getParameter("cid"));
		Category category = categoryDAO.get(cid);
		
		//查询所有属性并存到List表中
		List<Property> properties = propertyDAO.list(cid, page.getStart(), page.getCount());
		int total = propertyDAO.getTotal(cid);//通过分类ID获取该分类的所有属性个数
		System.out.println("page.getCount():" + page.getCount());
		
		page.setTotal(total);
		page.setParam("&cid=" + cid); //在分页实体中设置参数的值,具体分类下的属性分页
		
		//将分类对象、属性对象、分页对象都上传到session中去
		request.setAttribute("category", category);
		request.setAttribute("properties", properties);
		request.setAttribute("page", page);
		return "admin/listProperty.jsp";
	}

}
