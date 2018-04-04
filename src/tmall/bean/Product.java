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
