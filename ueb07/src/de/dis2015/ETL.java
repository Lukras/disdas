package de.dis2015;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import transformedData.ArticleTransformed;
import transformedData.DateTransformed;
import transformedData.SaleTransformed;
import transformedData.ShopTransformed;
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

	private static List<ArticleTransformed> transformedArticles = null;
	private static List<ShopTransformed> transformedShops = null;
	private static List<DateTransformed> transformedDates = null;
	private static List<SaleTransformed> transformedSales = null;

	private static DateTransformed stringToTransformedDate(String strDate) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(strDate));
			long time = cal.getTimeInMillis();
			int day = cal.get(Calendar.DAY_OF_MONTH);
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
			return new DateTransformed(time, day, month, year);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static boolean transformedDateAvailable(long id) {
		for (DateTransformed date : transformedDates) {
			if (date.getId() == id) {
				return true;
			}
		}
		return false;
	}

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

	private static StadtID getStadtIDById(int id) {
		for (StadtID item : stadtIds) {
			if (item.getStadtId() == id) {
				return item;
			}
		}
		return null;
	}

	private static RegionID getRegionIDById(int id) {
		for (RegionID item : regionIds) {
			if (item.getRegionId() == id) {
				return item;
			}
		}
		return null;
	}

	private static LandID getLandIDById(int id) {
		for (LandID item : landIds) {
			if (item.getLandId() == id) {
				return item;
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

	private static ProductGroupID getProductGroupByID(int id) {
		for (ProductGroupID item : productGroupIds) {
			if (item.getProductGroupId() == id) {
				return item;
			}
		}
		return null;
	}

	private static ProductFamilyID getProductFamilyByID(int id) {
		for (ProductFamilyID item : productFamilyIds) {
			if (item.getProductFamilyId() == id) {
				return item;
			}
		}
		return null;
	}

	private static ProductCategoryID getProductCategoryByID(int id) {
		for (ProductCategoryID item : productCategoryIds) {
			if (item.getProductCategoryId() == id) {
				return item;
			}
		}
		return null;
	}

	public static void main(String[] args) {
		/** extract data */
		System.out.println("extract:");
		extract_stores_and_products();
		extract_sales();

		/** transform data */
		System.out.println("transform:");
		transform_shops();
		transform_articles();
		transform_dates();
		transform_sales();

		/** load data */
		System.out.println("load:");
		load_shops();
		load_articles();
		load_dates();
		load_sales();
	}

	public static void extract_stores_and_products() {
		shopIds = ShopID.loadAll();
		stadtIds = StadtID.loadAll();
		regionIds = RegionID.loadAll();
		landIds = LandID.loadAll();
		System.out.println("extracted " + shopIds.size() + " shops");

		articleIds = ArticleID.loadAll();
		productGroupIds = ProductGroupID.loadAll();
		productFamilyIds = ProductFamilyID.loadAll();
		productCategoryIds = ProductCategoryID.loadAll();
		System.out.println("extracted " + articleIds.size() + " articles");
	}

	public static void extract_sales() {
		List<Sale> res = new LinkedList<Sale>();

		String filename = "src/sales.csv";
		BufferedReader br = null;
		String line = "";
		String separator = ";";

		NumberFormat nf = NumberFormat.getInstance();

		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					filename), "ISO-8859-1"));
			br.readLine();
			while ((line = br.readLine()) != null) {
				// System.out.println(line);
				String[] row = line.split(separator);
				if (row.length == 5) {
					// Date date = new Date(sdf.parse(row[0]).getTime());
					// ShopID shopId = getShopIDByName(row[1]);
					// ArticleID articleId = getArticleIDByName(row[2]);
					String date = row[0];
					String shop = row[1];
					String article = row[2];
					int sold = Integer.parseInt(row[3]);
					double turnover = nf.parse(row[4]).doubleValue();
					Sale sale = new Sale(date, shop, article, sold, turnover);
					res.add(sale);
					// System.out.println(row);
				}
			}

			System.out.println("extracted " + res.size() + " sales");
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

	public static void transform_shops() {
		transformedShops = new LinkedList<ShopTransformed>();
		for (ShopID shopId : shopIds) {
			StadtID stadtId = getStadtIDById(shopId.getStadtId());
			RegionID regionId = getRegionIDById(stadtId.getRegionId());
			LandID landId = getLandIDById(regionId.getLandId());
			ShopTransformed shop = new ShopTransformed(shopId.getShopId(),
					shopId.getName(), stadtId.getName(), regionId.getName(),
					landId.getName());
			transformedShops.add(shop);
		}
		System.out.println("transformed " + transformedShops.size() + " shops");
	}

	public static void transform_articles() {
		transformedArticles = new LinkedList<ArticleTransformed>();
		for (ArticleID articleId : articleIds) {
			ProductGroupID prdGrpId = getProductGroupByID(articleId
					.getProductGroupId());
			ProductFamilyID prdFamId = getProductFamilyByID(prdGrpId
					.getProductFamilyId());
			ProductCategoryID prdCatId = getProductCategoryByID(prdFamId
					.getProductCategoryId());
			ArticleTransformed article = new ArticleTransformed(
					articleId.getArticleId(), articleId.getName(),
					prdGrpId.getName(), prdFamId.getName(), prdCatId.getName(),
					articleId.getPreis());
			transformedArticles.add(article);
		}
		System.out.println("transformed " + transformedArticles.size()
				+ " articles");
	}

	public static void transform_dates() {
		transformedDates = new LinkedList<DateTransformed>();
		for (Sale sl : sales) {
			DateTransformed date = stringToTransformedDate(sl.getDatum());
			if (date != null) {
				if (!transformedDateAvailable(date.getId()))
					transformedDates.add(date);
			}
		}
		System.out.println("transformed " + transformedDates.size() + " dates");
	}

	public static void transform_sales() {
		transformedSales = new LinkedList<SaleTransformed>();
		for (Sale sl : sales) {
			
			ArticleID artId = getArticleIDByName(sl.getArticle());
			ShopID shpId = getShopIDByName(sl.getShop());
			if ((artId != null) && (shpId != null)) {
				int articleId = artId.getArticleId();
				long dateId = stringToTransformedDate(sl.getDatum()).getId();
				int shopId = shpId.getShopId();
				int number = sl.getSold();
				double revenue = sl.getTurnover();
				SaleTransformed slTr = new SaleTransformed(articleId, dateId, shopId, number, revenue);
				transformedSales.add(slTr);
			}
		}
		System.out.println("transformed " + transformedSales.size() + " sales");
	}
	
	public static void load_shops() {
		ShopTransformed.unloadAll();
		for (ShopTransformed shopTr : transformedShops) {
			shopTr.save();
		}
		System.out.println("loaded " + transformedShops.size() + " shops");
	}
	
	public static void load_articles() {
		ArticleTransformed.unloadAll();
		for (ArticleTransformed articleTr : transformedArticles) {
			articleTr.save();
		}
		System.out.println("loaded " + transformedArticles.size() + " articles");
	}
	
	public static void load_dates() {
		DateTransformed.unloadAll();
		DateTransformed.saveAll(transformedDates);
		System.out.println("loaded " + transformedDates.size() + " dates");
	}
	
	public static void load_sales() {
		SaleTransformed.unloadAll();
		SaleTransformed.saveAll(transformedSales);
		System.out.println("loaded " + transformedSales.size() + " sales");
	}
}
