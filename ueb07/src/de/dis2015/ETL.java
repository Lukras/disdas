package de.dis2015;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import de.dis2015.data.ArticleID;
import de.dis2015.data.LandID;
import de.dis2015.data.ProductCategoryID;
import de.dis2015.data.ProductFamilyID;
import de.dis2015.data.ProductGroupID;
import de.dis2015.data.RegionID;
import de.dis2015.data.Sale;
import de.dis2015.data.ShopID;
import de.dis2015.data.StadtID;

public class ETL {
	
	private static List<ShopID> shopIds = null;
	private static List<StadtID> stadtIds = null;
	private static List<RegionID> regionIds = null;
	private static List<LandID> landIds = null;
	
	private static List<ArticleID> articleIds = null;
	private static List<ProductGroupID> productGroupIds = null;
	private static List<ProductFamilyID> productFamilyIds = null;
	private static List<ProductCategoryID> productCategoryIds = null;
	
	private static List<Sale> sales = null;
	
	private static ShopID getShopIDByName(String name) {
		if (shopIds != null) {
			for (ShopID shopId : shopIds) {
				if (shopId.getName().compareTo(name) == 0) {
					return shopId;
				}
			}
		}
		return null;
	}
	
	private static ArticleID getArticleIDByName(String name) {
		if (articleIds != null) {
			for (ArticleID articleId : articleIds) {
				if (articleId.getName().compareTo(name) == 0) {
					return articleId;
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		load_stores_and_products();
		load_sales();
	}
	
	public static void load_stores_and_products() {
		System.out.println("load stores and products:");
		
		System.out.println(shopIds = ShopID.loadAll());
		System.out.println(stadtIds = StadtID.loadAll());
		System.out.println(regionIds = RegionID.loadAll());
		System.out.println(landIds = LandID.loadAll());
		
		System.out.println(articleIds = ArticleID.loadAll());
		System.out.println(productGroupIds = ProductGroupID.loadAll());
		System.out.println(productFamilyIds = ProductFamilyID.loadAll());
		System.out.println(productCategoryIds = ProductCategoryID.loadAll());
	}
	
	public static void load_sales() {
		System.out.println("load sales:");
		
		List<Sale> res = new LinkedList<Sale>();
		
		String filename = "src/sales.csv";
		BufferedReader br = null;
		String line = "";
		String separator = ";";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		NumberFormat nf = NumberFormat.getInstance();
		
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "ISO-8859-1"));
			br.readLine();
			while ((line = br.readLine()) != null) {
				//System.out.println(line);
				String[] row = line.split(separator);
				if (row.length == 5) {
					Date date = new Date(sdf.parse(row[0]).getTime());
					ShopID shopId = getShopIDByName(row[1]);
					ArticleID articleId = getArticleIDByName(row[2]);
					int sold = Integer.parseInt(row[3]);
					double turnover = nf.parse(row[4]).doubleValue();
					Sale sale = new Sale(date, shopId, articleId, sold, turnover);
					res.add(sale);
					//System.out.println(row);
				}
			}
			
			System.out.println("loaded "+res.size()+" sales");
			sales = res;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
