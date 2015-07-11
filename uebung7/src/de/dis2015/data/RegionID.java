package de.dis2015.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class RegionID extends DBObject {
	private int regionId;
	private int landId;
	private String name;
	
	public int getRegionId() {
		return regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	public int getLandId() {
		return landId;
	}

	public void setLandId(int landId) {
		this.landId = landId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static List<RegionID> loadAll() {
		
		List<RegionID> res = new LinkedList<RegionID>();
		
		try {
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			// Erzeuge Anfrage
			String selectSQL = "SELECT * FROM DB2INST1.regionid";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);

			// Führe Anfrage aus
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				RegionID shopId = new RegionID();
				shopId.setRegionId(rs.getInt("regionid"));
				shopId.setLandId(rs.getInt("landid"));
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
		return "RegionID [regionId=" + regionId + ", landId=" + landId
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
