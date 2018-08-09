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
/*		ͨ��MAP��ȡ�����ķ�����ʱ���ã��Ȱ���������������ˣ��ڿ����������
 * Map< String, String> params = new HashMap<>();
		InputStream is = super.parseUpload(request, params);
		
		int cid = Integer.parseInt(params.get("cid"));
		System.out.println("chanpingsushu�ķ�������Ϊ�գ���" + cid);
		Category category = categoryDAO.get(cid); //��øò�Ʒ�����ķ���	
		
		String productName = params.get("productName"); //��ò�Ʒ����
		String productSubTitle = params.get("productSubTitle"); //��ò�ƷС����
		Float originalPrice = Float.parseFloat(params.get("originalPrice"));  //���ԭ��
		Float promotePrice = Float.parseFloat(params.get("promotePrice"));  //��ô�����
		int productStock = Integer.parseInt(params.get("productStock")); //��ÿ��
*/		
		int cid = Integer.parseInt(request.getParameter("cid"));
		System.out.println("chanpingsushu�ķ�������Ϊ�գ���" + cid);
		Category category = categoryDAO.get(cid); //��øò�Ʒ�����ķ���				
		
		String productName = request.getParameter("productName"); //��ò�Ʒ����
		String productSubTitle = request.getParameter("productSubTitle"); //��ò�ƷС����
		try
		{
			productName = new String(productName.getBytes("ISO-8859-1"),"UTF-8");
			productSubTitle = new String(productSubTitle.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		
		Float originalPrice = Float.parseFloat(request.getParameter("oPrice"));  //���ԭ��		
		Float promotePrice = Float.parseFloat(request.getParameter("pPrice"));  //��ô�����		
		int productStock = Integer.parseInt(request.getParameter("productStock")); //��ÿ��		
		Date nowDate = new Date();
		
/*		private int id;
		private String name;			//��Ʒ����
		private String subTitle;           //С����
		private float originalPrice;    //ԭʼ�۸�
		private float promotePrice;        //�����۸�
		private Date createDate;  //��Ʒ��������
		private int stock;      //���
		private Category category;   //��category���һ��ϵ
		private int reviewCount;  //��������
		private int saleCount;   //����
		private ProductImage firstProductImage;  //������ʾ��Ʒ��Ĭ��ͼƬ
		private List<ProductImage> productSingleImages;  //��Ʒ��ͼ����
		private List<ProductImage> productDetailImages;  //��Ʒ����ͼ����
*/		
		//�������ڲ�Ʒʵ����������Ӧ��ֵ�������в�Ʒ������������Ϣδ��ӽ���������Ჹ��ģ�
		Product product = new Product();
		product.setCategory(category);
		product.setName(productName);
		product.setSubTitle(productSubTitle);
		product.setOriginalPrice(originalPrice);
		product.setPromotePrice(promotePrice);
		product.setStock(productStock);
		product.setCreateDate(nowDate);
		
		productDAO.add(product); //����Ʒ��Ϣ��ӵ����ݿ�
		
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
		
		List<PropertyValue> propertyValues = propertyValueDAO.list(product.getId());//����
		propertyValueDAO.init(product); //�Բ�Ʒ����ֵ���г�ʼ������
		
//		List<Property> properties = propertyDAO.list(product.getCategory().getId());//����
//		request.setAttribute("properties",properties);
		
		request.setAttribute("propertyValues",propertyValues);
		return "admin/editProductValue.jsp";
	}
	@Override
	public String update(HttpServletRequest request, HttpServletResponse response, Page page)
	{
		int cid = Integer.parseInt(request.getParameter("cid"));
		Category category = categoryDAO.get(cid);
		
		String productName = request.getParameter("productName"); //��ò�Ʒ����
		String productSubTitle = request.getParameter("productSubTitle"); //��ò�ƷС����
		try
		{
			productName = new String(productName.getBytes("ISO-8859-1"),"UTF-8");
			productSubTitle = new String(productSubTitle.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		
		Float originalPrice = Float.parseFloat(request.getParameter("originalPrice"));  //���ԭ��		
		Float promotePrice = Float.parseFloat(request.getParameter("promotePrice"));  //��ô�����		
		int productStock = Integer.parseInt(request.getParameter("productStock")); //��ÿ��		

		//�������ڲ�Ʒʵ����������Ӧ��ֵ�������в�Ʒ������������Ϣδ��ӽ���������Ჹ��ģ�
		Product product = new Product();
		product.setCategory(category);
		product.setName(productName);
		product.setSubTitle(productSubTitle);
		product.setOriginalPrice(originalPrice);
		product.setPromotePrice(promotePrice);
		product.setStock(productStock);
		
		productDAO.add(product); //����Ʒ��Ϣ��ӵ����ݿ�
		
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
		System.out.println("����Ƿ��÷���ID��" + cid); 
		Category category = categoryDAO.get(cid);
		
		System.out.println("product��list�����в鿴page.getCount()��ֵ��" + page.getCount());
		
		List<Product> products = productDAO.list(cid, page.getStart(), page.getCount());
		System.out.println("����Ƿ��ò�Ʒ���ݣ�" + products); 
		
/*		Product product1 = products.get(0);
		System.out.println("��ò�Ʒ���ݣ�" + product1.getSubTitle());
		Product product2 = products.get(1);
		System.out.println("��ò�Ʒ���ݣ�" + product2.getSubTitle());*/
		
		int total = productDAO.getTotal(cid);
		page.setTotal(total);
		page.setParam("&cid=" + category.getId());
		
		//��session��������ݣ�����listProduct.jspҳ���ȡ������ҳ��չʾ
		request.setAttribute("category", category);
		request.setAttribute("page", page);
		request.setAttribute("products", products);
		return "admin/listProduct.jsp";
	}

}
