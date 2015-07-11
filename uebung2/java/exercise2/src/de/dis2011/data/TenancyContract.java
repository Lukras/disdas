package de.dis2011.data;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class TenancyContract extends Contract {

	private Date startDate;
	private int duration;
	private BigDecimal additionalCosts;
	private int apartmentId;
	private int personId;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public BigDecimal getAdditionalCosts() {
		return additionalCosts;
	}

	public void setAdditionalCosts(BigDecimal additionalCosts) {
		this.additionalCosts = additionalCosts;
	}

	public int getApartmentId() {
		return apartmentId;
	}

	public void setApartmentId(int apartmentId) {
		this.apartmentId = apartmentId;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public static TenancyContract load(int id) {
		try {
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			// Erzeuge Anfrage
			String selectSQL = "SELECT * FROM contract AS co INNER JOIN tenancyContract AS tc ON co.id = tc.id WHERE co.id = ?";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, id);

			// Führe Anfrage aus
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				TenancyContract ts = new TenancyContract();
				ts.setId(id);
				ts.setContractNo(rs.getInt("contractNo"));
				ts.setDate(rs.getDate("date"));
				ts.setPlace(rs.getString("place"));

				ts.setStartDate(rs.getDate("startDate"));
				ts.setDuration(rs.getInt("duration"));
				ts.setAdditionalCosts(rs.getBigDecimal("additionalCosts"));
				ts.setApartmentId(rs.getInt("apartmentId"));
				ts.setPersonId(rs.getInt("personId"));

				rs.close();
				pstmt.close();
				return ts;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<TenancyContract> loadAll() {
		try {
			// Erstelle Liste
			List<TenancyContract> res = new LinkedList<TenancyContract>();

			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			// Erzeuge Anfrage
			String selectSQL = "SELECT * FROM contract AS co INNER JOIN tenancyContract AS tc ON co.id = tc.id";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);

			// Führe Anfrage aus
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				TenancyContract ts = new TenancyContract();
				ts.setId(rs.getInt("id"));
				ts.setContractNo(rs.getInt("contractNo"));
				ts.setDate(rs.getDate("date"));
				ts.setPlace(rs.getString("place"));

				ts.setStartDate(rs.getDate("startDate"));
				ts.setDuration(rs.getInt("duration"));
				ts.setAdditionalCosts(rs.getBigDecimal("additionalCosts"));
				ts.setApartmentId(rs.getInt("apartmentId"));
				ts.setPersonId(rs.getInt("personId"));
				res.add(ts);
			}

			rs.close();
			pstmt.close();
			return res;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void save() {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		try {
			// FC<ge neues Element hinzu, wenn das Objekt noch keine ID hat.
			if (getId() == -1) {
				// Achtung, hier wird noch ein Parameter mitgegeben,
				// damit spC$ter generierte IDs zurC<ckgeliefert werden!
				String insertSQL = "INSERT INTO contract(contractNo, date, place) VALUES (?, ?, ?)";

				PreparedStatement pstmt = con.prepareStatement(insertSQL,
						Statement.RETURN_GENERATED_KEYS);

				// Setze Anfrageparameter und fC<hre Anfrage aus
				pstmt.setInt(1, getContractNo());
				pstmt.setDate(2, getDate());
				pstmt.setString(3, getPlace());
				pstmt.executeUpdate();

				// Hole die Id des engefC<gten Datensatzes
				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					setId(rs.getInt(1));
				}
				rs.close();

				String insertTenancyContractSQL = "INSERT INTO tenancyContract(id, startDate, duration, additionalCosts, apartmentId, personId) VALUES (?, ?, ?, ?, ?, ?)";

				PreparedStatement pstmtTenancyContract = con.prepareStatement(
						insertTenancyContractSQL,
						Statement.RETURN_GENERATED_KEYS);

				pstmtTenancyContract.setInt(1, getId());
				pstmtTenancyContract.setDate(2, getStartDate());
				pstmtTenancyContract.setInt(3, getDuration());
				pstmtTenancyContract.setDouble(4, getAdditionalCosts()
						.doubleValue());
				pstmtTenancyContract.setInt(5, getApartmentId());
				pstmtTenancyContract.setInt(6, getPersonId());
				pstmtTenancyContract.executeUpdate();

				ResultSet rsTenancyContract = pstmtTenancyContract
						.getGeneratedKeys();
				if (rsTenancyContract.next()) {
					// setId(rs.getInt(1));
				}

				rsTenancyContract.close();
				pstmtTenancyContract.close();
			} else {
				// Falls schon eine ID vorhanden ist, mache ein Update...
				String updateSQL = "UPDATE contract SET contractNo = ?, date = ?, place = ? WHERE id = ?";
				PreparedStatement pstmt = con.prepareStatement(updateSQL);

				// Setze Anfrage Parameter
				pstmt.setInt(1, getContractNo());
				pstmt.setDate(2, getDate());
				pstmt.setString(3, getPlace());
				pstmt.setInt(4, getId());
				pstmt.executeUpdate();

				pstmt.close();

				String updateTenancyContractSQL = "UPDATE tenancyContract SET startDate = ?, duration = ?, additionalCosts = ?, apartmentId = ?, personId = ? WHERE id = ?";
				PreparedStatement pstmtTenancyContract = con
						.prepareStatement(updateTenancyContractSQL);

				// Setze Anfrage Parameter
				pstmtTenancyContract.setDate(1, getStartDate());
				pstmtTenancyContract.setInt(2, getDuration());
				pstmtTenancyContract.setBigDecimal(3, getAdditionalCosts());
				pstmtTenancyContract.setInt(4, getApartmentId());
				pstmtTenancyContract.setInt(5, getPersonId());
				pstmtTenancyContract.setInt(6, getId());
				pstmtTenancyContract.executeUpdate();

				pstmtTenancyContract.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete() {
		try {
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			// Erzeuge Anfrage
			String deleteTenancyContractSQL = "DELETE FROM tenancyContract WHERE id = ?";
			String deleteContractSQL = "DELETE FROM contract WHERE id = ?";
			PreparedStatement pstmtTenancyContract = con
					.prepareStatement(deleteTenancyContractSQL);
			PreparedStatement pstmtContract = con
					.prepareStatement(deleteContractSQL);
			pstmtTenancyContract.setInt(1, getId());
			pstmtContract.setInt(1, getId());

			// Führe Aktualisierung aus
			// con.setAutoCommit(false);
			if (pstmtTenancyContract.execute()) {
				pstmtTenancyContract.close();
				// con.commit();
				// con.setAutoCommit(true);
			}
			if (pstmtContract.execute()) {
				pstmtContract.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
