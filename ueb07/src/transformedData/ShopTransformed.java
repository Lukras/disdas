package transformedData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import de.dis2015.data.DB2ConnectionManager;

/*
 * CREATE TABLE shop ( shopid INTEGER NOT NULL PRIMARY KEY,
 shopname VARCHAR(255),
 stadt VARCHAR(255),
 region VARCHAR(255),
 land VARCHAR(255));
 * */

public class ShopTransformed {
	int shopId;
	String shopName;
	String Stadt;
	String Region;
	String Land;

	public int getShopId() {
		return shopId;
	}

	public void setShopId(int shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getStadt() {
		return Stadt;
	}

	public void setStadt(String stadt) {
		Stadt = stadt;
	}

	public String getRegion() {
		return Region;
	}

	public void setRegion(String region) {
		Region = region;
	}

	public String getLand() {
		return Land;
	}

	public void setLand(String land) {
		Land = land;
	}

	public ShopTransformed(int shopId, String shopName, String stadt,
			String region, String land) {
		super();
		this.shopId = shopId;
		this.shopName = shopName;
		Stadt = stadt;
		Region = region;
		Land = land;
	}

	@Override
	public String toString() {
		return "shopTransformed [shopId=" + shopId + ", shopName=" + shopName
				+ ", Stadt=" + Stadt + ", Region=" + Region + ", Land=" + Land
				+ "]";
	}

	public static void unloadAll() {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		try {
			// Achtung, hier wird noch ein Parameter mitgegeben,
			// damit spC$ter generierte IDs zurC<ckgeliefert werden!
			String insertSQL = "DELETE FROM shop";

			PreparedStatement pstmt = con.prepareStatement(insertSQL,
					Statement.RETURN_GENERATED_KEYS);

			pstmt.executeUpdate();

			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void save() {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		try {
			// Achtung, hier wird noch ein Parameter mitgegeben,
			// damit spC$ter generierte IDs zurC<ckgeliefert werden!
			String insertSQL = "INSERT INTO shop(shopid, shopname, stadt, region, land) VALUES (?, ?, ?, ?, ?)";

			PreparedStatement pstmt = con.prepareStatement(insertSQL,
					Statement.RETURN_GENERATED_KEYS);

			// Setze Anfrageparameter und fC<hre Anfrage aus
			pstmt.setInt(1, getShopId());
			pstmt.setString(2, getShopName());
			pstmt.setString(3, getStadt());
			pstmt.setString(4, getRegion());
			pstmt.setString(5, getLand());
			pstmt.executeUpdate();

			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
