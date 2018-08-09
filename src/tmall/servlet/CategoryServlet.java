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


import tmall.Util.Page;
import tmall.bean.Category;
import tmall.Util.ImageUtil;
public class CategoryServlet extends BasebackServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*�̳г��������̳г���������з���*/
	@Override
	public String add(HttpServletRequest request, HttpServletResponse response,
			Page page)
	{
		Map<String,String> params = new HashMap<>();
		
		InputStream is = super.parseUpload(request, params);//���ø����parseUpload����
		String name = params.get("name");  //��ȡ������
		
		System.out.println("��ӵĵ�������Ϊ��" + name);  //�ӿ���̨�����ʾ������
		
		Category category = new Category();
		category.setName(name); //��������
		//Tip��categoryDAO��CategoryDAO�Ķ���categoryDAO������ԴӸ���BaseBackServlet�̳����ģ�
		categoryDAO.add(category); //���   
		request.setAttribute("category", category);
		
		System.out.println("���Կ����Ƿ���������ID:" + category.getId());
		
		//imageFolderָ����category��ͼƬ���ļ���
		File imageFolder = new File(request.getSession().getServletContext().getRealPath
				("/imgs/category"));
		
		System.out.println("����ͼ���ŵ�·���Ƿ���ȷ��" + imageFolder); //����һ�»�ȡ��·���Ƿ���ȷ
		
		//����Ϊcategory.getId()��jpgͼƬ�浽imageFolderָ����ļ�����
		File file = new File(imageFolder, category.getId() + ".jpg"); 
		
		try
		{
			if (is != null && is.available() != 0)
			{
				//try(...){...}catch(..){...} ,try�����С����������д�����Զ��ر�IO����д��
				try (FileOutputStream fos = new FileOutputStream(file)) //����һ���ļ�������������һ������ı���·������
				{
					//����һ��������
					byte[] buffer = new byte[1024*1024]; //��СΪ1M
					int len = 0; //�ж��������е������Ƿ��Ѿ�����ı�ʶ
					
					//whileѭ����������������������ȥ
					while((len = is.read(buffer)) > 0) 	//(len=in.read(buffer))>0�ͱ�ʾin���滹������
					{
						//ʹ��FileOutputStream�������������������д�뵽ָ����Ŀ¼��fileָ��ָ��ĵط�����ȥ
						fos.write(buffer, 0, len);  //0��ʾ��buffer����ĵ�һ��Ԫ�ؿ�ʼд��len��ʾһ��д�ĸ���
					}
					fos.flush(); //flush()��ʾǿ�ƽ��������е����ݷ��ͳ�ȥ,���صȵ���������.
					
	                  //ͨ�����´��룬���ļ�����Ϊjpg��ʽ
                    BufferedImage img = ImageUtil.change2jpg(file);//���ǵ����������com.sun.imageio.plugins.common.ImageUtil;
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
		int cid = Integer.parseInt(request.getParameter("cid"));
		
		System.out.println("Ҫɾ�������ID��" + cid);  //���Ի�ȡ��ֵ�Ƿ���ȷ
		
		categoryDAO.delete(cid);  //����cid����ɾ��
		return "@admin_category_list";   
	}

	@Override
	public String edit(HttpServletRequest request,
			HttpServletResponse response, Page page)
	{
		//���category��id
		int cid = Integer.parseInt(request.getParameter("cid"));
		//����cid�����Ӧ��Category�����
		Category category = categoryDAO.get(cid);
		//��ӵ�session��ȥ
		request.setAttribute("category", category);
		return "admin/editCategory.jsp";
	}

	@Override
	public String update(HttpServletRequest request,
			HttpServletResponse response, Page page)
	{
		Map<String,String> params = new HashMap<>();
		InputStream is = super.parseUpload(request, params);//���ø����parseUpload������ȡ������
		System.out.println(params);
		
	/*	������������Ӧ editCategory.jspҳ��35�к�44�д����name,��ͨ������BasebackServlet���upload����
	 * �����������浽��ͼparams�Ȼ���ڴ�ȡ����
		<input id="categoryName" name="name" value="${category.name}" type="text" 
				class="form-control">
		<input type="hidden" name="cid" value="${category.id}">
		*/
		String name = params.get("name");//��ã�������
		int cid = Integer.parseInt(params.get("cid")); //��ã���id��
		
		//���ͨ����id��ȡ�����Ȼ���޸ķ������ƺ�
		Category category = new Category();
		category.setName(name);
		category.setId(cid);
		categoryDAO.update(category);  //�޸����ݿ�
		
		//imageFolderָ����category��ͼƬ���ļ���
		File imageFolder = new File(request.getSession().getServletContext().getRealPath("imgs/category"));
		//����Ϊcategory.getId()��jpgͼƬ�浽imageFolderָ����ļ�����
		File file = new File(imageFolder,category.getId() + ".jpg");
		file.getParentFile().mkdirs();//��ø����ļ�Ŀ¼
		
		//�����ļ���д����
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
						fos.write(buffer, 0, len);//�����ݴ�buffer����ȥд��file��ָ���ļ���ȥ
					}
					fos.flush();//flush()��ʾǿ�ƽ��������е����ݷ��ͳ�ȥ,���صȵ���������
					
					//ͨ�����´��룬���ļ�����Ϊjpg��ʽ
                    BufferedImage img = ImageUtil.change2jpg(file); 
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
		//ͨ��CategoryDAO�Ķ���categoryDAO����list������ȡCategory�༯��
		List<Category> categories = categoryDAO.list(page.getStart(), page.getCount());
		int total = categoryDAO.getTotal();  //�����ݿ��л���ܵ���������
		page.setTotal(total); //��Page���total��ֵ
		
		//��session��������ݣ�����jspҳ����� 
		request.setAttribute("categories", categories);
		request.setAttribute("page", page); 
		return "admin/listCategory.jsp";  //�������ҳ��ַ
	}
}