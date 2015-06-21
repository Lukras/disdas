package de.dis2015.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ProductFamilyID extends DBObject {
	private int productFamilyId;
	private int productCategoryId;
	private String name;

	public int getProductFamilyId() {
		return productFamilyId;
	}

	public void setProductFamilyId(int productFamilyId) {
		this.productFamilyId = productFamilyId;
	}

	public int getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(int productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static List<ProductFamilyID> loadAll() {
		
		List<ProductFamilyID> res = new LinkedList<ProductFamilyID>();
		
		try {
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			// Erzeuge Anfrage
			String selectSQL = "SELECT * FROM DB2INST1.productfamilyid";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);

			// Führe Anfrage aus
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductFamilyID articleId = new ProductFamilyID();
				articleId.setProductCategoryId(rs.getInt("productcategoryid"));
				articleId.setProductFamilyId(rs.getInt("productfamilyid"));
				articleId.setName(rs.getString("name"));
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
		return "ProductFamilyID [productFamilyId=" + productFamilyId
				+ ", productCategoryId=" + productCategoryId + ", name=" + name
				+ "]";
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
