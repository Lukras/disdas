package de.dis2011.data;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class PurchaseContract extends Contract {

	private int noOfInstallments;
	private BigDecimal intrestRate;
	private int houseId;
	private int personId;

	public int getNoOfInstallments() {
		return noOfInstallments;
	}

	public void setNoOfInstallments(int noOfInstallments) {
		this.noOfInstallments = noOfInstallments;
	}

	public BigDecimal getIntrestRate() {
		return intrestRate;
	}

	public void setIntrestRate(BigDecimal intrestRate) {
		this.intrestRate = intrestRate;
	}

	public int getHouseId() {
		return houseId;
	}

	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public static PurchaseContract load(int id) {
		try {
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			// Erzeuge Anfrage
			String selectSQL = "SELECT * FROM contract AS co INNER JOIN purchaseContract AS pc ON co.id = pc.id WHERE co.id = ?";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, id);

			// Führe Anfrage aus
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				PurchaseContract ts = new PurchaseContract();
				ts.setId(id);
				ts.setContractNo(rs.getInt("contractNo"));
				ts.setDate(rs.getDate("date"));
				ts.setPlace(rs.getString("place"));

				ts.setNoOfInstallments(rs.getInt("noOfInstallments"));
				ts.setIntrestRate(rs.getBigDecimal("interestRate"));
				ts.setHouseId(rs.getInt("houseId"));
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

	public static List<PurchaseContract> loadAll() {
		try {
			// Erstelle Liste
			List<PurchaseContract> res = new LinkedList<PurchaseContract>();

			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			// Erzeuge Anfrage
			String selectSQL = "SELECT * FROM contract AS co INNER JOIN purchaseContract AS pc ON co.id = pc.id";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);

			// Führe Anfrage aus
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				PurchaseContract ts = new PurchaseContract();
				ts.setId(rs.getInt("id"));
				ts.setContractNo(rs.getInt("contractNo"));
				ts.setDate(rs.getDate("date"));
				ts.setPlace(rs.getString("place"));

				ts.setNoOfInstallments(rs.getInt("noOfInstallments"));
				ts.setIntrestRate(rs.getBigDecimal("intrestRate"));
				ts.setHouseId(rs.getInt("houseId"));
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

				String insertPurchaseContractSQL = "INSERT INTO purchaseContract(id, noOfInstallments, intrestRate, houseId, personId) VALUES (?, ?, ?, ?, ?)";

				PreparedStatement pstmtPurchaseContract = con.prepareStatement(
						insertPurchaseContractSQL,
						Statement.RETURN_GENERATED_KEYS);

				pstmtPurchaseContract.setInt(1, getId());
				pstmtPurchaseContract.setInt(2, getNoOfInstallments());
				pstmtPurchaseContract.setBigDecimal(3, getIntrestRate());
				pstmtPurchaseContract.setInt(4, getHouseId());
				pstmtPurchaseContract.setInt(5, getPersonId());
				pstmtPurchaseContract.executeUpdate();

				ResultSet rsPurchaseContract = pstmtPurchaseContract
						.getGeneratedKeys();
				if (rsPurchaseContract.next()) {
					// setId(rs.getInt(1));
				}

				rsPurchaseContract.close();
				pstmtPurchaseContract.close();
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

				String updatePurchaseContractSQL = "UPDATE purchaseContract SET noOfInstallments = ?, intrestRate = ?, houseId = ?, personId = ? WHERE id = ?";
				PreparedStatement pstmtPurchaseContract = con
						.prepareStatement(updatePurchaseContractSQL);

				// Setze Anfrage Parameter
				pstmtPurchaseContract.setInt(1, getNoOfInstallments());
				pstmtPurchaseContract.setBigDecimal(2, getIntrestRate());
				pstmtPurchaseContract.setInt(3, getHouseId());
				pstmtPurchaseContract.setInt(4, getPersonId());
				pstmtPurchaseContract.setInt(5, getId());
				pstmtPurchaseContract.executeUpdate();

				pstmtPurchaseContract.close();
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
			String deletePurchaseContractSQL = "DELETE FROM purchaseContract WHERE id = ?";
			String deleteContractSQL = "DELETE FROM contract WHERE id = ?";
			PreparedStatement pstmtPurchaseContract = con
					.prepareStatement(deletePurchaseContractSQL);
			PreparedStatement pstmtContract = con
					.prepareStatement(deleteContractSQL);
			pstmtPurchaseContract.setInt(1, getId());
			pstmtContract.setInt(1, getId());

			// Führe Aktualisierung aus
			// con.setAutoCommit(false);
			if (pstmtPurchaseContract.execute()) {
				pstmtPurchaseContract.close();
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
