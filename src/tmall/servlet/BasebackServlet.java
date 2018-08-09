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
        	int start = 0; //设定从哪个数据开始显示（此处是从第一条数据开始显示）
        	int count = 5; //设定每页显示的数据条数（此处是每页显示5条数据）
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
        	String method = (String) request.getAttribute("method"); //注：其实session中键method的值就是list、add、delete、update。。。
        	
        	//CategoryServlet categoryServlet = new CategoryServlet(); //待商榷
        	
        	//每个Method对象对应一个方法，获得Method对象后，调用invoke方法来调用这个方法
        	//getMethod方法的第一个参数是方法名，此处method变量可以是list、add、delete、update。。。
     /*   关于Method m = this.getClass().getDeclaredMethod(。。。)中的this
      * 应该这么理解：BackServletFilter过滤器拦截之后，请求转发到CategoryServlet来处理，
        	CategoryServlet继承自BaseBackServlet，同样就继承了BaseBackServlet的service方法，
        	这时候service中的this指向的是CategoryServlet实例，而不是BaseBackServlet实例。

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
        	
        	
        	/*根据方法的返回值，进行相应的客户端跳转和服务端跳转，或仅仅输出字符串*/
        	if (redirect.startsWith("@"))
        	{
        		//进行客户端跳转 
        		response.sendRedirect(redirect.substring(1));  //substring(int m)  从m位置开始一直到redirect的末尾
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
    	InputStream is = null; //定义输入流对象
		
		try
		{
			//1、创建一个DiskFileItemFactory工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//2、创建一个文件上传解析器
			ServletFileUpload upload = new ServletFileUpload(factory);
			//3、设置文件上传大小限制  10m
			factory.setSizeThreshold(1024 * 1024 * 10);
			//4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
			List<?> items = upload.parseRequest(request);
			//5、利用List的Iterator迭代器进行遍历
			Iterator<?> iter = items.iterator();
			while(iter.hasNext())
			{
				FileItem item = (FileItem)iter.next();
				//如果fileitem中封装的不是普通输入项的数据
				if (!item.isFormField())
				{
					//获得上传文件的输入流
					is = item.getInputStream();
				}else  //即fileitem中封装的是普通输入项数据
				{
					//得到上传的文件名称  getFieldName方法用于返回表单标签name属性的值。
					//如<input type="text" name="column" />的value。
					String paramName = item.getFieldName();//键  类名字段
					System.out.println("打印出form表提交的类名字段 :" + paramName);
					
					/*
					 * String getString()
				      getString方法用于将FileItem对象中保存的数据流内容以一个字符串返回，它有两个重载的定义形式：
				      public java.lang.String getString()
				      public java.lang.String getString(java.lang.String encoding)
				             throws java.io.UnsupportedEncodingException
				                    前者使用缺省的字符集编码将主体内容转换成字符串，后者使用参数指定的字符集编码将主体内容转换成字符串。
					        如果在读取普通表单字段元素的内容时出现了中文乱码现象，请调用第二个getString方法，
					        并为之传递正确的字符集编码名称。
					*/
					String paramValue = item.getString();  //值  “具体类名”
					System.out.println("打印出form表提交的具体类名 :" + paramValue);
					
					paramValue = new String(paramValue.getBytes("ISO-8859-1"), "UTF-8");
					System.out.println("打印出form表提交的具体类名（乱码处理后） :" + paramValue);
					/*
					 *tomcat容器默认采用了iso-8859-1的编码方法，通过本为UTF-8编码却被tomcat用iso-8859-1解码的字进行恢复，
				其将解码后的字通过iso-8859-1反解码成二进制数组，再将该字节数组用UTF-8解码。最终被new String成字符串。
					*/
					params.put(paramName, paramValue);  //存入图中（键值对）
				}
			
			}
		} catch (Exception e)
		
		
		{
			e.printStackTrace();
		}
    	return is;
    }

}