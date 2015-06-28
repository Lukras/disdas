package transformedData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import de.dis2015.data.DB2ConnectionManager;

/*
 * CREATE TABLE sale ( articleid INTEGER NOT NULL,
 date BIGINT,
 shopid INTEGER,
 number INTEGER,
 revenue DOUBLE);
 */

public class SaleTransformed {
	int articleId;
	long dateId;
	int shopId;
	int number;
	double revenue;

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public long getDateId() {
		return dateId;
	}

	public void setDateId(long dateId) {
		this.dateId = dateId;
	}

	public int getShopId() {
		return shopId;
	}

	public void setShopId(int shopId) {
		this.shopId = shopId;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public double getRevenue() {
		return revenue;
	}

	public void setRevenue(double revenue) {
		this.revenue = revenue;
	}

	public SaleTransformed(int articleId, long dateId, int shopId, int number,
			double revenue) {
		super();
		this.articleId = articleId;
		this.dateId = dateId;
		this.shopId = shopId;
		this.number = number;
		this.revenue = revenue;
	}

	@Override
	public String toString() {
		return "SaleTransformed [articleId=" + articleId + ", dateId=" + dateId
				+ ", shopId=" + shopId + ", number=" + number + ", revenue="
				+ revenue + "]";
	}

	public static void unloadAll() {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		try {
			// Achtung, hier wird noch ein Parameter mitgegeben,
			// damit spC$ter generierte IDs zurC<ckgeliefert werden!
			String insertSQL = "DELETE FROM sale";

			PreparedStatement pstmt = con.prepareStatement(insertSQL,
					Statement.RETURN_GENERATED_KEYS);

			pstmt.executeUpdate();

			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void saveAll(List<SaleTransformed> list) {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		try {
			String insertSQL = "INSERT INTO sale"
					+ "(articleid, date, shopid, number, revenue) "
					+ "VALUES (?,?,?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(insertSQL);
			con.setAutoCommit(false);

			for (SaleTransformed saleTr : list) {
				pstmt.setInt(1, saleTr.getArticleId());
				pstmt.setLong(2, saleTr.getDateId());
				pstmt.setInt(3, saleTr.getShopId());
				pstmt.setInt(4, saleTr.getNumber());
				pstmt.setDouble(5, saleTr.getRevenue());
				pstmt.addBatch();
			}
			
			int[] count = pstmt.executeBatch();
			con.commit();

			// pstmt.executeUpdate();
			// pstmt.close();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
