package tmall.Util;

public class Page
{
	int start;  //  the first information
	int count; //  the biggest number of page
	int total;  // all informations 
	
	String param;  //	?
	
	//add a constructor
	public Page(int start, int count)
	{
		this.start = start;
		this.count = count;
	}

	public int getStart()
	{
		return start;
	}

	public void setStart(int start)
	{
		this.start = start;
	}

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}

	public int getTotal()
	{
		return total;
	}

	public void setTotal(int total)
	{
		this.total = total;
	}

	public String getParam()
	{
		return param;
	}

	public void setParam(String param)
	{
		this.param = param;
	}
	
	//get the total page
	public int getTotalPage()
	{
		int totalPage;
		//informations in database start with zero
		if (total % count == 0)
		{
			totalPage = total / count;
		}else 
		{
			totalPage = total / count + 1;
		}
		/*这个if语句若没有，adminPage.jsp中24行代码的结果会出问题"
		 <c:forEach begin="0" end="${page.totalPage-1}" varStatus="status">"
		 即page.totalPage-1值是 -1
		 */
		if (totalPage == 0)
			totalPage = 1;
		
		return totalPage;
	}
	
	//get the last information
	public int getLast()
	{
		int last;
		//important tip:informations in database start with zero
		if (total % count == 0)
		{
			last = total - count;
		}else 
		{
			last = total - total % count;
		}
		
		last = last < 0 ? 0 : last;  //if last less than 0,last must equal zero
		return last;
	}
	
	//check if there is previous page
	public boolean isHasPrevious()
	{
		if (start == 0)
			return false;
		else
			return true;
	}
	
	//check if there is last page
	public boolean isHasNext()
	{
		if (start == getLast())
			return false;
		else
			return true;
	}
}
