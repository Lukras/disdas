package de.dis2011.data;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * CREATE TABLE house ( id INTEGER UNIQUE NOT NULL, floors INTEGER, price
 * DECIMAL(12,2), garden CHARACTER(1), CONSTRAINT fkId FOREIGN KEY(id)
 * REFERENCES estate(id) );
 */
public class House extends Estate {

	private int floors;
	private BigDecimal price;
	private boolean garden;

	public int getFloors() {
		return floors;
	}

	public void setFloors(int floors) {
		this.floors = floors;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public boolean isGarden() {
		return garden;
	}

	public void setGarden(boolean garden) {
		this.garden = garden;
	}

	public static House load(int id) {
		try {
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			// Erzeuge Anfrage
			String selectSQL = "SELECT * FROM house AS ap INNER JOIN estate AS es ON ap.id = es.id WHERE ap.id = ?";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, id);

			// Führe Anfrage aus
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				House ts = new House();
				ts.setId(id);
				ts.setCity(rs.getString("city"));
				ts.setPostalCode(rs.getString("postalCode"));
				ts.setStreet(rs.getString("street"));
				ts.setStreetNumber(rs.getInt("streetNumber"));
				ts.setSquareArea(rs.getString("squareArea"));
				ts.setEstateAgentId(rs.getInt("estateAgentId"));
				ts.setFloors(rs.getInt("floors"));
				ts.setPrice(BigDecimal.valueOf(rs.getDouble("price")));
				ts.setGarden(rs.getString("garden").equals("X"));

				rs.close();
				pstmt.close();
				return ts;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<Estate> loadAll() {
		try {
			// Erstelle Liste
			List<Estate> res = new LinkedList<Estate>();

			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			// Erzeuge Anfrage
			String selectSQL = "SELECT * FROM house AS ap INNER JOIN estate AS es ON ap.id = es.id";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);

			// Führe Anfrage aus
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				House ts = new House();
				ts.setId(rs.getInt("id"));
				ts.setCity(rs.getString("city"));
				ts.setPostalCode(rs.getString("postalCode"));
				ts.setStreet(rs.getString("street"));
				ts.setStreetNumber(rs.getInt("streetNumber"));
				ts.setSquareArea(rs.getString("squareArea"));
				ts.setEstateAgentId(rs.getInt("estateAgentId"));
				ts.setFloors(rs.getInt("floors"));
				ts.setPrice(BigDecimal.valueOf(rs.getDouble("price")));
				ts.setGarden(rs.getString("garden").equals("X"));

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

	public void save() {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		try {
			// FC<ge neues Element hinzu, wenn das Objekt noch keine ID hat.
			if (getId() == -1) {
				// Achtung, hier wird noch ein Parameter mitgegeben,
				// damit spC$ter generierte IDs zurC<ckgeliefert werden!
				String insertSQL = "INSERT INTO estate(city, postalCode, street, streetNumber, squareArea, estateAgentId) VALUES (?, ?, ?, ?, ?, ?)";

				PreparedStatement pstmt = con.prepareStatement(insertSQL,
						Statement.RETURN_GENERATED_KEYS);

				// Setze Anfrageparameter und fC<hre Anfrage aus
				pstmt.setString(1, getCity());
				pstmt.setString(2, getPostalCode());
				pstmt.setString(3, getStreet());
				pstmt.setInt(4, getStreetNumber());
				pstmt.setString(5, getSquareArea());
				pstmt.setInt(6, getEstateAgentId());
				pstmt.executeUpdate();

				// Hole die Id des engefC<gten Datensatzes
				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					setId(rs.getInt(1));
				}
				rs.close();

				String insertHouseSQL = "INSERT INTO house(id, floors, price, garden) VALUES (?, ?, ?, ?)";

				PreparedStatement pstmtHouse = con.prepareStatement(
						insertHouseSQL, Statement.RETURN_GENERATED_KEYS);

				pstmtHouse.setInt(1, getId());
				pstmtHouse.setInt(2, getFloors());
				pstmtHouse.setDouble(3, getPrice().doubleValue());
				String garden;
				if (isGarden()) {
					garden = "X";
				} else {
					garden = " ";
				}
				pstmtHouse.setString(4, garden);
				pstmtHouse.executeUpdate();

				ResultSet rsHouse = pstmtHouse.getGeneratedKeys();
				if (rsHouse.next()) {
					// setId(rs.getInt(1));
				}

				rsHouse.close();
				pstmtHouse.close();
			} else {
				// Falls schon eine ID vorhanden ist, mache ein Update...
				String updateSQL = "UPDATE estate SET city = ?, postalCode = ?, street = ?, streetNumber = ?, squareArea = ?, estateAgentId = ? WHERE id = ?";
				PreparedStatement pstmt = con.prepareStatement(updateSQL);

				// Setze Anfrage Parameter
				pstmt.setString(1, getCity());
				pstmt.setString(2, getPostalCode());
				pstmt.setString(3, getStreet());
				pstmt.setInt(4, getStreetNumber());
				pstmt.setString(5, getSquareArea());
				pstmt.setInt(6, getEstateAgentId());
				pstmt.setInt(7, getId());
				pstmt.executeUpdate();

				pstmt.close();

				String updateHouseSQL = "UPDATE house SET floors = ?, price = ?, garden = ? WHERE id = ?";
				PreparedStatement pstmtHouse = con
						.prepareStatement(updateHouseSQL);

				// Setze Anfrage Parameter
				pstmtHouse.setInt(1, getFloors());
				pstmtHouse.setDouble(2, getPrice().doubleValue());
				String garden;
				if (isGarden()) {
					garden = "X";
				} else {
					garden = " ";
				}
				pstmtHouse.setString(3, garden);
				pstmtHouse.setInt(4, getId());
				pstmtHouse.executeUpdate();

				pstmtHouse.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete() {
		try {
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			// Erzeuge Anfrage
			String deleteHouseSQL = "DELETE FROM house WHERE id = ?";
			String deleteEstateSQL = "DELETE FROM estate WHERE id = ?";
			PreparedStatement pstmtHouse = con.prepareStatement(deleteHouseSQL);
			PreparedStatement pstmtEstate = con
					.prepareStatement(deleteEstateSQL);
			pstmtHouse.setInt(1, getId());
			pstmtEstate.setInt(1, getId());

			// Führe Aktualisierung aus
			// con.setAutoCommit(false);
			if (pstmtHouse.execute()) {
				pstmtHouse.close();
				// con.commit();
				// con.setAutoCommit(true);
			}
			if (pstmtEstate.execute()) {
				pstmtEstate.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
