/**
 * 
 */
package tmall.bean;

import java.util.List;

/**
 * @author Administrator
 *     总分类  category
 */ 
public class Category
{
	private int id;
	private String name;
	private List<Product> product;    //具体商品集合
	private List<List<Product>> productsByRow;  //横向预览商品具体分类集合

	
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


	public List<Product> getProducts()
	{
		return product;
	}


	public void setProducts(List<Product> product)
	{
		this.product = product;
	}


	public List<List<Product>> getProductsByRow()
	{
		return productsByRow;
	}


	public void setProductsByRow(List<List<Product>> productsByRow)
	{
		this.productsByRow = productsByRow;
	}

//重写toString方法
	public String toString()
	{
		return "Category [name:" + name +"]";
	}
}
