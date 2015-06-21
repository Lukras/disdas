package de.dis2015.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class StadtID extends DBObject {
	private int stadtId;
	private int regionId;
	private String name;
	
	public int getStadtId() {
		return stadtId;
	}

	public void setStadtId(int stadtId) {
		this.stadtId = stadtId;
	}

	public int getRegionId() {
		return regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static List<StadtID> loadAll() {
		
		List<StadtID> res = new LinkedList<StadtID>();
		
		try {
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			// Erzeuge Anfrage
			String selectSQL = "SELECT * FROM DB2INST1.stadtid";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);

			// Führe Anfrage aus
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				StadtID shopId = new StadtID();
				shopId.setRegionId(rs.getInt("regionid"));
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
		return "StadtID [stadtId=" + stadtId + ", regionId=" + regionId
				+ ", name=" + name + "]";
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
