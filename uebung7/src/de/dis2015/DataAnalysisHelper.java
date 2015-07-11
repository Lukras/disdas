package de.dis2015;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.dis2015.data.DB2ConnectionManager;


public class DataAnalysisHelper {
	
	public static final String[] DATE_COL = { "Year", "Month", "Day"};
	public static final String[] SHOP_COL = { "Land", "Region", "Stadt", "Shopname"};
	public static final String[] ARTICLE_COL = { "ProductCategory", "ProductFamily", "ProductGroup", "ArticleName"};
	
	private static final String[] DATE_DIM = { "d.year", "d.month", "d.day" };
	private static final String[] SHOP_DIM = { "sh.land", "sh.region", "sh.stadt", "sh.shopname"};
	private static final String[] ARTICLE_DIM = { "a.productcategory", "a.productfamily", "a.productgroup", "a.articlename"};
	
	public static List<Map<String,String>> getArticleData(int dateDim, int articleDim, int shopDim) {
		
		String strDate = "";
		String strShop = "";
		String strArticle = "";
		for (int i = 0; i <= dateDim; i++) {
			if (i > 0) {
				strDate = strDate + ", ";
			}
			strDate = strDate + DATE_DIM[i];
		}
		for (int i = 0; i <= shopDim; i++) {
			if (i > 0) {
				strShop = strShop + ", ";
			}
			strShop = strShop + SHOP_DIM[i];
		}
		for (int i = 0; i <= articleDim; i++) {
			if (i > 0) {
				strArticle = strArticle + ", ";
			}
			strArticle = strArticle + ARTICLE_DIM[i];
		}
		
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();
		String sqlSelect = "SELECT "+strDate+", "+strShop+", "+strArticle+", sum(sa.number) as \"Number\" "
				+ "FROM article a, sale sa, date d, shop sh "
				+ "WHERE sa.articleid = a.articleid "
				+ "and sa.date = d.id "
				+ "and sa.shopid = sh.shopid "
				+ "GROUP BY CUBE("+strDate+", "+strShop+", "+strArticle+")";
		//System.out.println(sqlSelect);
		try {
			PreparedStatement pstmt = con.prepareStatement(sqlSelect);
			
			// Führe Anfrage aus
			ResultSet rs = pstmt.executeQuery();
			
			List<Map<String,String>> res = new LinkedList<Map<String,String>>();
			
			while (rs.next()) {
				Map<String,String> row = new HashMap<String,String>();
				for (int i = 0; i <= dateDim; i++) {
					row.put(DATE_COL[i], rs.getString(DATE_COL[i]));
				}
				for (int i = 0; i <= shopDim; i++) {
					row.put(SHOP_COL[i], rs.getString(SHOP_COL[i]));
				}
				for (int i = 0; i <= articleDim; i++) {
					row.put(ARTICLE_COL[i], rs.getString(ARTICLE_COL[i]));
				}
				row.put("Number", rs.getString("NUMBER"));
				res.add(row);
				//System.out.println(row);
			}
			
			rs.close();
			pstmt.close();
			
			return res;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String[] getColumnsForDimensions(int dateDim, int articleDim, int shopDim) {
		List<String> columns = new LinkedList<String>();
		for (int i = 0; i <= dateDim; i++) {
			columns.add(DATE_COL[i]);
		}
		for (int i = 0; i <= shopDim; i++) {
			columns.add(SHOP_COL[i]);
		}
		for (int i = 0; i <= articleDim; i++) {
			columns.add(ARTICLE_COL[i]);
		}
		columns.add("Number");
		String[] res = new String[columns.size()];
		for (int i = 0; i < columns.size(); i++) {
			res[i] = columns.get(i);
		}
		Arrays.toString(res);
		return res;
	}
	
	public static void main(String[] args) {
		getArticleData(1,1,1);
	}
}
