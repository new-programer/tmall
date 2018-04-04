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
		
		String contextPath = request.getServletContext().getContextPath();//��ȡ��/tmall��������Ŀ�ĸ�·��
		String uri = request.getRequestURI();  //��ȡ��/tmall/admin_catrgory_list��������ȡ�û����ʵ�ҳ���ַ
		//StringUtils��remove(a,b)�����Ǵ�a��ȥ��b��ͬ������
		uri = StringUtils.remove(uri, contextPath);  //��uri�ġ�/tmall��ȥ�������������uri��
		
		if (uri.startsWith("/admin_"))
		{
			String servletPath = StringUtils.substringBetween(uri, "_", "_") + "Servlet";//��������_��
			/*
			 * StringUtils.substringAfter("chinachina","h")); // inachina
			 * StringUtils.substringAfterLast("chinachina", "i"); // na "i"�����ֵ�λ������ȡ
			 */
			String method = StringUtils.substringAfterLast(uri, "_");
			
			request.setAttribute("method", method);  //��session�����method��Ҫ�����һ�������method��������list��
			request.getRequestDispatcher("/" + servletPath).forward(request, response);  //���з�������ת
			return;
		}
		
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException
	{
		
	}
	
}