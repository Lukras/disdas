package transformedData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import de.dis2015.data.DB2ConnectionManager;

/*
 * CREATE TABLE date ( id INTEGER NOT NULL PRIMARY KEY,
 day INTEGER,
 month INTEGER,
 year INTEGER);
 */

public class DateTransformed {
	long id;
	int day;
	int month;
	int year;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public DateTransformed(long id, int day, int month, int year) {
		super();
		this.id = id;
		this.day = day;
		this.month = month;
		this.year = year;
	}
	@Override
	public String toString() {
		return "DateTransformed [id=" + id + ", day=" + day + ", month="
				+ month + ", year=" + year + "]";
	}
	public static void unloadAll() {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		try {
			// Achtung, hier wird noch ein Parameter mitgegeben,
			// damit spC$ter generierte IDs zurC<ckgeliefert werden!
			String insertSQL = "DELETE FROM date";

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
			String insertSQL = "INSERT INTO date(id, day, month, year) VALUES (?, ?, ?, ?)";

			PreparedStatement pstmt = con.prepareStatement(insertSQL,
					Statement.RETURN_GENERATED_KEYS);

			// Setze Anfrageparameter und fC<hre Anfrage aus
			pstmt.setLong(1, getId());
			pstmt.setInt(2, getDay());
			pstmt.setInt(3, getMonth());
			pstmt.setInt(4, getYear());
			pstmt.executeUpdate();

			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveAll(List<DateTransformed> list) {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		try {
			Statement stmt = con.createStatement();
			con.setAutoCommit(false);

			for (DateTransformed dateTr : list) {
				String insertSQL = "INSERT INTO date"
						+ "(id, day, month, year) "
						+ "VALUES (" + dateTr.getId() + ", "
						+ dateTr.getDay() + ", " + dateTr.getMonth() + ", "
						+ dateTr.getYear() +  ")";
				
				stmt.addBatch(insertSQL);
			}
			
			int[] count = stmt.executeBatch();
			con.commit();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
