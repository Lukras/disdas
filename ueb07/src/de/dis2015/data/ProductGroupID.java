package de.dis2015.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ProductGroupID extends DBObject {
	private int productGroupId;
	private int productFamilyId;
	private String name;

	public int getProductGroupId() {
		return productGroupId;
	}

	public void setProductGroupId(int productGroupId) {
		this.productGroupId = productGroupId;
	}

	public int getProductFamilyId() {
		return productFamilyId;
	}

	public void setProductFamilyId(int productFamilyId) {
		this.productFamilyId = productFamilyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static List<ProductGroupID> loadAll() {
		
		List<ProductGroupID> res = new LinkedList<ProductGroupID>();
		
		try {
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			// Erzeuge Anfrage
			String selectSQL = "SELECT * FROM DB2INST1.productgroupid";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);

			// Führe Anfrage aus
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductGroupID articleId = new ProductGroupID();
				articleId.setProductGroupId(rs.getInt("productgroupid"));
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
		return "ProductGroupID [productGroupId=" + productGroupId
				+ ", productFamilyId=" + productFamilyId + ", name=" + name
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
