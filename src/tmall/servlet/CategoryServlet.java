package tmall.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import tmall.DAO.CategoryDAO;
import tmall.Util.Page;
import tmall.bean.Category;
import tmall.Util.ImageUtil;
public class CategoryServlet extends BasebackServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*继承抽象类必须继承抽象类的所有方法*/
	@Override
	public String add(HttpServletRequest request, HttpServletResponse response,
			Page page)
	{
		Map<String,String> params = new HashMap<>();
		
		InputStream is = super.parseUpload(request, params);//调用父类的parseUpload函数
		String name = params.get("name");  //获取类名称
		
		Category category = new Category();
		category.setName(name); //设置类名
		//Tip：categoryDAO是CategoryDAO的对象（categoryDAO这个属性从父类BaseBackServlet继承来的）
		categoryDAO.add(category); //添加   
		
		//imageFolder指向存放category类图片的文件夹
		File imageFolder = new File(request.getSession().getServletContext().getRealPath
				("imgs/category"));
		//将名为category.getId()的jpg图片存到imageFolder指向的文件夹下
		File file = new File(imageFolder, category.getId() + ".jpg"); 
		
		try
		{
			if (is != null && is.available() != 0)
			{
				//try(...){...}catch(..){...} ,try后面的小括号这样的写法是自动关闭IO流的写法
				try (FileOutputStream fos = new FileOutputStream(file)) //创建一个文件输出流（输出到一个具体的保存路径），
				{
					//创建一个缓冲区
					byte[] buffer = new byte[1024]; //大小为1M
					int len = 0; //判断输入流中的数据是否已经读完的标识
					
					//while循环将输入流读到缓冲区中去
					while((len = is.read(buffer)) > 0) 	//(len=in.read(buffer))>0就表示in里面还有数据
					{
						//使用FileOutputStream输出流将缓冲区的数据写入到指定的目录（file指针指向的地方）中去
						fos.write(buffer, 0, len);  //0表示从buffer数组的第一个元素开始写，len表示一共写的个数
					}
					fos.flush(); //flush()表示强制将缓冲区中的数据发送出去,不必等到缓冲区满.
					
	                  //通过如下代码，把文件保存为jpg格式
                    BufferedImage img = ImageUtil.change2jpg(file);//不是导入这个包：com.sun.imageio.plugins.common.ImageUtil;
                    ImageIO.write(img, "jpg", file);          
				}catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		return "@admin_category_list";
	}

	@Override
	public String delete(HttpServletRequest request,
			HttpServletResponse respnse, Page page)
	{
		int cid = Integer.parseInt(request.getParameter("id"));
		categoryDAO.delete(cid);  //根据cid进行删除
		return "@admin_category_list";   
	}

	@Override
	public String edit(HttpServletRequest request,
			HttpServletResponse response, Page page)
	{
		//获得category的id
		int cid = Integer.parseInt(request.getParameter("id"));
		//根据cid获得相应的Category类对象
		Category category = categoryDAO.get(cid);
		//添加到session中去
		request.setAttribute("category", category);
		return "admin/editCategory.jsp";
	}

	@Override
	public String update(HttpServletRequest request,
			HttpServletResponse response, Page page)
	{
		Map<String,String> params = new HashMap<>();
		InputStream is = super.parseUpload(request, params);//调用父类的parseUpload方法获取输入流
		
		String name = params.get("name");//获得键名（类名）
		int cid = Integer.parseInt(params.get("id")); //获得键值（类id）
		
		//添加一个类对象
		Category category = new Category();
		category.setName(name);
		category.setId(cid);
		categoryDAO.add(category);
		
		//imageFolder指向存放category类图片的文件夹
		File imageFolder = new File(request.getSession().getServletContext().getRealPath("imgs/category"));
		//将名为category.getId()的jpg图片存到imageFolder指向的文件夹下
		File file = new File(imageFolder,category.getId() + ".jpg");
		file.getParentFile().mkdirs();//获得父级文件目录
		
		
		//进行文件读写操作
		try
		{
			if (is != null && is.available() != 0)
			{
				try(FileOutputStream fos = new FileOutputStream(file))
				{
					byte[] buffer = new byte[1024];
					int len = 0;
					while((len = is.read(buffer)) > 0)
					{
						fos.write(buffer, 0, len);//将数据从buffer缓冲去写到file所指的文件中去
					}
					fos.flush();//flush()表示强制将缓冲区中的数据发送出去,不必等到缓冲区满
					
					//通过如下代码，把文件保存为jpg格式
                    BufferedImage img = ImageUtil.change2jpg(file); //此处报错，
                    ImageIO.write(img, "jpg", file);        
				}catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		return "@admin_category_list";
	}

	@Override
	public String list(HttpServletRequest request,
			HttpServletResponse response, Page page)
	{
		//通过CategoryDAO的对象categoryDAO调用list函数获取Category类集合
		List<Category> categories = categoryDAO.list(page.getStart(), page.getCount());
		int total = categoryDAO.getTotal();  //获取总页数
		page.setTotal(total); //给Page类的total赋值
		
		//向session中添加数据
		request.setAttribute("categories", categories);
		request.setAttribute("page", page);
		return "admin/listCategory.jsp";  //返回类分页地址
	}
}