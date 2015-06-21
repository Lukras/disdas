package de.dis2015.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ShopID extends DBObject {
	private int shopId;
	private int stadtId;
	private String name;
	
	public int getShopId() {
		return shopId;
	}
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}
	public int getStadtId() {
		return stadtId;
	}
	public void setStadtId(int stadtId) {
		this.stadtId = stadtId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public static List<ShopID> loadAll() {
		
		List<ShopID> res = new LinkedList<ShopID>();
		
		try {
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			// Erzeuge Anfrage
			String selectSQL = "SELECT * FROM DB2INST1.shopid";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);

			// Führe Anfrage aus
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ShopID shopId = new ShopID();
				shopId.setShopId(rs.getInt("shopid"));
				shopId.setStadtId(rs.getInt("stadtid"));
				shopId.setName(rs.getString("name"));
				res.add(shopId);
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
		return "ShopID [shopId=" + shopId + ", stadtId=" + stadtId + ", name="
				+ name + "]";
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
