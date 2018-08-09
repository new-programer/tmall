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
		Category category = categoryDAO.get(cid); //ͨ����ID��ȡ����������ȷ�����Ķ����ࣨ��Ʒ��������
		
		String name = request.getParameter("name"); //��ȡ��������
		try
		{
			//�������
			name = new String(name.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		System.out.println("�����������Ƿ�����룺" + name);
		
		//�������Ե�ʵ���ֵ
		Property property = new Property();
		property.setCategory(category);
		property.setName(name);
		//�����ݿ��д洢������
		propertyDAO.add(property);
		
		return "@admin_property_list?cid=" + cid; //���ص��÷��������б�ҳ��
	}

	@Override
	public String delete(HttpServletRequest request, HttpServletResponse respnse, Page page)
	{
		int id = Integer.parseInt(request.getParameter("id"));
	
		Property property = propertyDAO.get(id);//ͨ��property���������Թ�������
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
		int cid = Integer.parseInt(request.getParameter("cid"));//��÷���ID
		Category category = categoryDAO.get(cid);
		
		int id = Integer.parseInt(request.getParameter("id"));//�������ID
		Property property = propertyDAO.get(id);
		
		String name = request.getParameter("name");//��ȡԭ������������
		try
		{
			name = new String(name.getBytes("ISO-8859-1"),"UTF-8");//ת�룬���д���ز�����
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		
		//�޸�propertyʵ���ڲ�����Ԫ�ص�ֵ
		property.setCategory(category);
		property.setId(id);
		property.setName(name);
		
		propertyDAO.update(property);//����
		return "@admin_property_list?cid=" + property.getCategory().getId();//��ת
	}

	@Override
	public String list(HttpServletRequest request, HttpServletResponse response, Page page)
	{
		int cid = Integer.parseInt(request.getParameter("cid"));
		Category category = categoryDAO.get(cid);
		
		//��ѯ�������Բ��浽List����
		List<Property> properties = propertyDAO.list(cid, page.getStart(), page.getCount());
		int total = propertyDAO.getTotal(cid);//ͨ������ID��ȡ�÷�����������Ը���
		System.out.println("page.getCount():" + page.getCount());
		
		page.setTotal(total);
		page.setParam("&cid=" + cid); //�ڷ�ҳʵ�������ò�����ֵ,��������µ����Է�ҳ
		
		//������������Զ��󡢷�ҳ�����ϴ���session��ȥ
		request.setAttribute("category", category);
		request.setAttribute("properties", properties);
		request.setAttribute("page", page);
		return "admin/listProperty.jsp";
	}

}
