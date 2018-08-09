package tmall.servlet;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import tmall.DAO.CategoryDAO;
import tmall.DAO.OrderDAO;
import tmall.DAO.OrderItemDAO;
import tmall.DAO.ProductDAO;
import tmall.DAO.ProductImageDAO;
import tmall.DAO.PropertyDAO;
import tmall.DAO.PropertyValueDAO;
import tmall.DAO.ReviewDAO;
import tmall.DAO.UserDAO;
import tmall.Util.Page;

public abstract class BasebackServlet extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//abstract methods
	public abstract String add(HttpServletRequest request, HttpServletResponse response, Page page);
	public abstract String delete(HttpServletRequest request, HttpServletResponse respnse, Page page);
	public abstract String edit(HttpServletRequest request, HttpServletResponse response, Page page);
	public abstract String update(HttpServletRequest request, HttpServletResponse response, Page page);
	public abstract String list(HttpServletRequest request, HttpServletResponse response, Page page);
	
	//create every DAO's object
	protected CategoryDAO categoryDAO = new CategoryDAO();
	protected OrderDAO orderDAO = new OrderDAO();
	protected OrderItemDAO orderItemDAO = new OrderItemDAO();
    protected ProductDAO productDAO = new ProductDAO();
    protected ProductImageDAO productImageDAO = new ProductImageDAO();
    protected PropertyDAO propertyDAO = new PropertyDAO();
    protected PropertyValueDAO propertyValueDAO = new PropertyValueDAO();
    protected ReviewDAO reviewDAO = new ReviewDAO();
    protected UserDAO userDAO = new UserDAO();
    
    //service method
    public void service(HttpServletRequest request, HttpServletResponse response)
    {
    	try
    	{
    		/* get pagination information */
        	int start = 0; //�趨���ĸ����ݿ�ʼ��ʾ���˴��Ǵӵ�һ�����ݿ�ʼ��ʾ��
        	int count = 5; //�趨ÿҳ��ʾ�������������˴���ÿҳ��ʾ5�����ݣ�
        	//get value of start and count in requests' parameter
        	try
        	{
        		start = Integer.parseInt(request.getParameter("page.start"));
        	}catch (Exception e)
        	{
        	}
        	
        	try
        	{
        		count = Integer.parseInt(request.getParameter("page.count"));
        	}catch (Exception e)
        	{
        	}
        	
        	Page page = new Page(start, count);
        	 
        	/* using reflection techniques invoke method*/
        	String method = (String) request.getAttribute("method"); //ע����ʵsession�м�method��ֵ����list��add��delete��update������
        	
        	//CategoryServlet categoryServlet = new CategoryServlet(); //����ȶ
        	
        	//ÿ��Method�����Ӧһ�����������Method����󣬵���invoke�����������������
        	//getMethod�����ĵ�һ�������Ƿ��������˴�method����������list��add��delete��update������
     /*   ����Method m = this.getClass().getDeclaredMethod(������)�е�this
      * Ӧ����ô��⣺BackServletFilter����������֮������ת����CategoryServlet������
        	CategoryServlet�̳���BaseBackServlet��ͬ���ͼ̳���BaseBackServlet��service������
        	��ʱ��service�е�thisָ�����CategoryServletʵ����������BaseBackServletʵ����

     */
        	
        	Method m = this.getClass().getDeclaredMethod(method, javax.servlet.http.HttpServletRequest.class, 
        			javax.servlet.http.HttpServletResponse.class, Page.class);
        	String redirect = m.invoke(this, request, response, page).toString();
        /*	public Object invoke(Object obj,Object... args)
             throws IllegalAccessException,
                    IllegalArgumentException,
                    InvocationTargetException
            Parameters: 
			obj - the object the underlying method is invoked from 
			args - the arguments used for the method call 
			Returns: 
			the result of dispatching the method represented by this object on obj with 
			parameters args 
        */
        	
        	
        	/*���ݷ����ķ���ֵ��������Ӧ�Ŀͻ�����ת�ͷ������ת�����������ַ���*/
        	if (redirect.startsWith("@"))
        	{
        		//���пͻ�����ת 
        		response.sendRedirect(redirect.substring(1));  //substring(int m)  ��mλ�ÿ�ʼһֱ��redirect��ĩβ
        	}else if (redirect.startsWith("%"))
        	{
        		response.getWriter().print("redirect.substring(1)");
        	}else
        	{
        		request.getRequestDispatcher(redirect).forward(request, response);
        	}
    	}catch (Exception e)
    	{
    		e.printStackTrace();
    		throw new RuntimeException(e);
    	}
    	
    }
    
    public InputStream parseUpload(HttpServletRequest request, Map<String, String> params) 
    {
    	InputStream is = null; //��������������
		
		try
		{
			//1������һ��DiskFileItemFactory����
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//2������һ���ļ��ϴ�������
			ServletFileUpload upload = new ServletFileUpload(factory);
			//3�������ļ��ϴ���С����  10m
			factory.setSizeThreshold(1024 * 1024 * 10);
			//4��ʹ��ServletFileUpload�����������ϴ����ݣ�����������ص���һ��List<FileItem>���ϣ�ÿһ��FileItem��Ӧһ��Form����������
			List<?> items = upload.parseRequest(request);
			//5������List��Iterator���������б���
			Iterator<?> iter = items.iterator();
			while(iter.hasNext())
			{
				FileItem item = (FileItem)iter.next();
				//���fileitem�з�װ�Ĳ�����ͨ�����������
				if (!item.isFormField())
				{
					//����ϴ��ļ���������
					is = item.getInputStream();
				}else  //��fileitem�з�װ������ͨ����������
				{
					//�õ��ϴ����ļ�����  getFieldName�������ڷ��ر���ǩname���Ե�ֵ��
					//��<input type="text" name="column" />��value��
					String paramName = item.getFieldName();//��  �����ֶ�
					System.out.println("��ӡ��form���ύ�������ֶ� :" + paramName);
					
					/*
					 * String getString()
				      getString�������ڽ�FileItem�����б����������������һ���ַ������أ������������صĶ�����ʽ��
				      public java.lang.String getString()
				      public java.lang.String getString(java.lang.String encoding)
				             throws java.io.UnsupportedEncodingException
				                    ǰ��ʹ��ȱʡ���ַ������뽫��������ת�����ַ���������ʹ�ò���ָ�����ַ������뽫��������ת�����ַ�����
					        ����ڶ�ȡ��ͨ���ֶ�Ԫ�ص�����ʱ����������������������õڶ���getString������
					        ��Ϊ֮������ȷ���ַ����������ơ�
					*/
					String paramValue = item.getString();  //ֵ  ������������
					System.out.println("��ӡ��form���ύ�ľ������� :" + paramValue);
					
					paramValue = new String(paramValue.getBytes("ISO-8859-1"), "UTF-8");
					System.out.println("��ӡ��form���ύ�ľ������������봦��� :" + paramValue);
					/*
					 *tomcat����Ĭ�ϲ�����iso-8859-1�ı��뷽����ͨ����ΪUTF-8����ȴ��tomcat��iso-8859-1������ֽ��лָ���
				�佫��������ͨ��iso-8859-1������ɶ��������飬�ٽ����ֽ�������UTF-8���롣���ձ�new String���ַ�����
					*/
					params.put(paramName, paramValue);  //����ͼ�У���ֵ�ԣ�
				}
			
			}
		} catch (Exception e)
		
		
		{
			e.printStackTrace();
		}
    	return is;
    }

}