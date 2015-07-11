package de.dis2015.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ArticleID extends DBObject {
	private int articleId;
	private int productGroupId;
	private String name;
	private double preis;
	
	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public int getProductGroupId() {
		return productGroupId;
	}

	public void setProductGroupId(int productGroupId) {
		this.productGroupId = productGroupId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPreis() {
		return preis;
	}

	public void setPreis(double preis) {
		this.preis = preis;
	}

	public static List<ArticleID> loadAll() {
		
		List<ArticleID> res = new LinkedList<ArticleID>();
		
		try {
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			// Erzeuge Anfrage
			String selectSQL = "SELECT * FROM DB2INST1.articleid";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);

			// Führe Anfrage aus
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ArticleID articleId = new ArticleID();
				articleId.setArticleId(rs.getInt("articleId"));
				articleId.setProductGroupId(rs.getInt("productgroupid"));
				articleId.setName(rs.getString("name"));
				articleId.setPreis(rs.getDouble("preis"));
				res.add(articleId);
			}
			
			rs.close();
			pstmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	@Override
	public String toString() {
		return "ArticleID [articleId=" + articleId + ", productGroupId="
				+ productGroupId + ", name=" + name + ", preis=" + preis + "]";
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}
}
