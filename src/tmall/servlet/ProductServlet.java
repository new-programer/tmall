package tmall.servlet;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.Util.Page;
import tmall.bean.Category;
import tmall.bean.Product;
import tmall.bean.PropertyValue;

public class ProductServlet extends BasebackServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String add(HttpServletRequest request, HttpServletResponse response, Page page)
	{
/*		通过MAP获取参数的方案暂时不用，等把这个功能做出来了，在考虑其可行性
 * Map< String, String> params = new HashMap<>();
		InputStream is = super.parseUpload(request, params);
		
		int cid = Integer.parseInt(params.get("cid"));
		System.out.println("chanpingsushu的分类名称为空？：" + cid);
		Category category = categoryDAO.get(cid); //获得该产品所属的分类	
		
		String productName = params.get("productName"); //获得产品名称
		String productSubTitle = params.get("productSubTitle"); //获得产品小标题
		Float originalPrice = Float.parseFloat(params.get("originalPrice"));  //获得原价
		Float promotePrice = Float.parseFloat(params.get("promotePrice"));  //获得促销价
		int productStock = Integer.parseInt(params.get("productStock")); //获得库存
*/		
		int cid = Integer.parseInt(request.getParameter("cid"));
		System.out.println("chanpingsushu的分类名称为空？：" + cid);
		Category category = categoryDAO.get(cid); //获得该产品所属的分类				
		
		String productName = request.getParameter("productName"); //获得产品名称
		String productSubTitle = request.getParameter("productSubTitle"); //获得产品小标题
		try
		{
			productName = new String(productName.getBytes("ISO-8859-1"),"UTF-8");
			productSubTitle = new String(productSubTitle.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		
		Float originalPrice = Float.parseFloat(request.getParameter("oPrice"));  //获得原价		
		Float promotePrice = Float.parseFloat(request.getParameter("pPrice"));  //获得促销价		
		int productStock = Integer.parseInt(request.getParameter("productStock")); //获得库存		
		Date nowDate = new Date();
		
/*		private int id;
		private String name;			//产品名称
		private String subTitle;           //小标题
		private float originalPrice;    //原始价格
		private float promotePrice;        //促销价格
		private Date createDate;  //产品创建日期
		private int stock;      //库存
		private Category category;   //和category多对一关系
		private int reviewCount;  //评价数量
		private int saleCount;   //销量
		private ProductImage firstProductImage;  //用于显示产品的默认图片
		private List<ProductImage> productSingleImages;  //产品单图集合
		private List<ProductImage> productDetailImages;  //产品详情图集合
*/		
		//下面是在产品实体中设置相应的值，这里有产品描述等其他信息未添加进来，后面会补充的：
		Product product = new Product();
		product.setCategory(category);
		product.setName(productName);
		product.setSubTitle(productSubTitle);
		product.setOriginalPrice(originalPrice);
		product.setPromotePrice(promotePrice);
		product.setStock(productStock);
		product.setCreateDate(nowDate);
		
		productDAO.add(product); //将产品信息添加到数据库
		
		return "@admin_product_list?cid=" + product.getCategory().getId();
	}

	@Override
	public String delete(HttpServletRequest request, HttpServletResponse respnse, Page page)
	{
		int ptid = Integer.parseInt(request.getParameter("ptid"));
		Product product = productDAO.get(ptid);
		productDAO.delete(ptid);
		return "@admin_product_list?cid=" + product.getCategory().getId();
	}

	@Override
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page)
	{
		int ptid = Integer.parseInt(request.getParameter("ptid"));
		Product product = productDAO.get(ptid);
		request.setAttribute("product", product);
		return "admin/editProduct.jsp";
	}
	public String editPropertyValue(HttpServletRequest request, HttpServletResponse response,Page page)
	{
		int ptid = Integer.parseInt(request.getParameter("ptid"));
		Product product = productDAO.get(ptid);
		request.setAttribute("product", product);
		
		List<PropertyValue> propertyValues = propertyValueDAO.list(product.getId());//根据
		propertyValueDAO.init(product); //对产品属性值进行初始化操作
		
//		List<Property> properties = propertyDAO.list(product.getCategory().getId());//根据
//		request.setAttribute("properties",properties);
		
		request.setAttribute("propertyValues",propertyValues);
		return "admin/editProductValue.jsp";
	}
	@Override
	public String update(HttpServletRequest request, HttpServletResponse response, Page page)
	{
		int cid = Integer.parseInt(request.getParameter("cid"));
		Category category = categoryDAO.get(cid);
		
		String productName = request.getParameter("productName"); //获得产品名称
		String productSubTitle = request.getParameter("productSubTitle"); //获得产品小标题
		try
		{
			productName = new String(productName.getBytes("ISO-8859-1"),"UTF-8");
			productSubTitle = new String(productSubTitle.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		
		Float originalPrice = Float.parseFloat(request.getParameter("originalPrice"));  //获得原价		
		Float promotePrice = Float.parseFloat(request.getParameter("promotePrice"));  //获得促销价		
		int productStock = Integer.parseInt(request.getParameter("productStock")); //获得库存		

		//下面是在产品实体中设置相应的值，这里有产品描述等其他信息未添加进来，后面会补充的：
		Product product = new Product();
		product.setCategory(category);
		product.setName(productName);
		product.setSubTitle(productSubTitle);
		product.setOriginalPrice(originalPrice);
		product.setPromotePrice(promotePrice);
		product.setStock(productStock);
		
		productDAO.add(product); //将产品信息添加到数据库
		
		return "@admin_product_list?cid=" + product.getCategory().getId();
	}

	public String updatePropertyValue(HttpServletRequest request, 
			HttpServletResponse response, Page page)
	{
		int pvid = Integer.parseInt(request.getParameter("pvid"));
		String value = request.getParameter("value");
		 try
		{
			value = new String(value.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		 
		PropertyValue propertyValue = propertyValueDAO.get(pvid);
		propertyValue.setValue(value);
		
		propertyValueDAO.update(propertyValue);
		
		return "%success";
	}
	
	@Override
	public String list(HttpServletRequest request, HttpServletResponse response, Page page)
	{
		int cid = Integer.parseInt(request.getParameter("cid"));
		System.out.println("检查是否获得分类ID：" + cid); 
		Category category = categoryDAO.get(cid);
		
		System.out.println("product的list方法中查看page.getCount()的值：" + page.getCount());
		
		List<Product> products = productDAO.list(cid, page.getStart(), page.getCount());
		System.out.println("检查是否获得产品数据：" + products); 
		
/*		Product product1 = products.get(0);
		System.out.println("获得产品内容：" + product1.getSubTitle());
		Product product2 = products.get(1);
		System.out.println("获得产品内容：" + product2.getSubTitle());*/
		
		int total = productDAO.getTotal(cid);
		page.setTotal(total);
		page.setParam("&cid=" + category.getId());
		
		//在session中添加数据，便于listProduct.jsp页面获取并在网页上展示
		request.setAttribute("category", category);
		request.setAttribute("page", page);
		request.setAttribute("products", products);
		return "admin/listProduct.jsp";
	}

}
