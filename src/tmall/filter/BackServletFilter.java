package tmall.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
 
public class BackServletFilter implements Filter
{

	@Override
	public void destroy()
	{
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		String contextPath = request.getServletContext().getContextPath();//获取“/tmall”，即项目的根路径
		String uri = request.getRequestURI();  //获取“/tmall/admin_catrgory_list”，即获取用户访问的页面地址
		//StringUtils的remove(a,b)函数是从a除去和b相同的数据
		uri = StringUtils.remove(uri, contextPath);  //将uri的“/tmall”去掉，结果保存在uri中
		
		if (uri.startsWith("/admin_"))
		{
			String servletPath = StringUtils.substringBetween(uri, "_", "_") + "Servlet";//不包含“_”
			/*
			 * StringUtils.substringAfter("chinachina","h")); // inachina
			 * StringUtils.substringAfterLast("chinachina", "i"); // na "i"最后出现的位置向后截取
			 */
			String method = StringUtils.substringAfterLast(uri, "_");
			
			request.setAttribute("method", method);  //向session中添加method（要清楚的一点是这个method变量就是list）
			request.getRequestDispatcher("/" + servletPath).forward(request, response);  //进行服务器跳转
			return;
		}
		
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException
	{
		
	}
	
}