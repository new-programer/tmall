/**
 * 
 */
package tmall.bean;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 *
 */
public class Product
{
	private int id;
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
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getSubTitle()
	{
		return subTitle;
	}
	public void setSubTitle(String subTitle)
	{
		this.subTitle = subTitle;
	}
	public float getOriginalPrice()
	{
		return originalPrice;
	}
	public void setOriginalPrice(float originalPrice)
	{
		this.originalPrice = originalPrice;
	}
	public float getPromotePrice()
	{
		return promotePrice;
	}
	public void setPromotePrice(float promotePrice)
	{
		this.promotePrice = promotePrice;
	}
	public Date getCreateDate()
	{
		return createDate;
	}
	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}
	public int getStock()
	{
		return stock;
	}
	public void setStock(int stock)
	{
		this.stock = stock;
	}
	public Category getCategory()
	{
		return category;
	}
	public void setCategory(Category category)
	{
		this.category = category;
	}
	public int getReviewCount()
	{
		return reviewCount;
	}
	public void setReviewCount(int reviewCount)
	{
		this.reviewCount = reviewCount;
	}
	public int getSaleCount()
	{
		return saleCount;
	}
	public void setSaleCount(int saleCount)
	{
		this.saleCount = saleCount;
	}
	public ProductImage getFirstProductImage()
	{
		return firstProductImage;
	}
	public void setFirstProductImage(ProductImage firstProductImage)
	{
		this.firstProductImage = firstProductImage;
	}
	public List<ProductImage> getProductSingleImages()
	{
		return productSingleImages;
	}
	public void setProductSingleImages(List<ProductImage> productSingleImages)
	{
		this.productSingleImages = productSingleImages;
	}
	public List<ProductImage> getProductDetailImages()
	{
		return productDetailImages;
	}
	public void setProductDetailImages(List<ProductImage> productDetailImages)
	{
		this.productDetailImages = productDetailImages;
	}
}
