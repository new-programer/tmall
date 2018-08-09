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


/**
 * @author Administrator
 *
 */
public class EncodingFilter implements Filter
{

	public void destroy()
	{
		
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		response.setCharacterEncoding("UTF-8");//对提交的数据进行UTF-8编码
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException
	{
		
	}
	
}
