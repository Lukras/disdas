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
 * CREATE TABLE apartment ( id INTEGER UNIQUE NOT NULL, floor INTEGER, rent
 * DECIMAL(12,2), rooms INTEGER, balcony CHARACTER(1), builtInKitchen
 * CHARACTER(1), CONSTRAINT fkId FOREIGN KEY(id) REFERENCES estate(id) );
 *
 */
public class Apartment extends Estate {
	private int floor;
	private BigDecimal rent;
	private int rooms;
	private boolean balcony;
	private boolean builtInKitchen;

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public BigDecimal getRent() {
		return rent;
	}

	public void setRent(BigDecimal rent) {
		this.rent = rent;
	}

	public int getRooms() {
		return rooms;
	}

	public void setRooms(int rooms) {
		this.rooms = rooms;
	}

	public boolean isBalcony() {
		return balcony;
	}

	public void setBalcony(boolean balcony) {
		this.balcony = balcony;
	}

	public boolean isBuiltInKitchen() {
		return builtInKitchen;
	}

	public void setBuiltInKitchen(boolean builtInKitchen) {
		this.builtInKitchen = builtInKitchen;
	}

	public static Apartment load(int id) {
		try {
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			// Erzeuge Anfrage
			String selectSQL = "SELECT * FROM apartment AS ap INNER JOIN estate AS es ON ap.id = es.id WHERE ap.id = ?";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, id);

			// Führe Anfrage aus
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Apartment ts = new Apartment();
				ts.setId(id);
				ts.setCity(rs.getString("city"));
				ts.setPostalCode(rs.getString("postalCode"));
				ts.setStreet(rs.getString("street"));
				ts.setStreetNumber(rs.getInt("streetNumber"));
				ts.setSquareArea(rs.getString("squareArea"));
				ts.setEstateAgentId(rs.getInt("estateAgentId"));
				ts.setFloor(rs.getInt("floor"));
				ts.setRent(BigDecimal.valueOf(rs.getDouble("rent")));
				ts.setRooms(rs.getInt("rooms"));
				ts.setBalcony(rs.getString("balcony").equals("X"));
				ts.setBuiltInKitchen(rs.getString("builtInKitchen").equals("X"));

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
			String selectSQL = "SELECT * FROM apartment AS ap INNER JOIN estate AS es ON ap.id = es.id";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);

			// Führe Anfrage aus
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Apartment ts = new Apartment();
				ts.setId(rs.getInt("id"));
				ts.setCity(rs.getString("city"));
				ts.setPostalCode(rs.getString("postalCode"));
				ts.setStreet(rs.getString("street"));
				ts.setStreetNumber(rs.getInt("streetNumber"));
				ts.setSquareArea(rs.getString("squareArea"));
				ts.setEstateAgentId(rs.getInt("estateAgentId"));
				ts.setFloor(rs.getInt("floor"));
				ts.setRent(BigDecimal.valueOf(rs.getDouble("rent")));
				ts.setRooms(rs.getInt("rooms"));
				ts.setBalcony(rs.getString("balcony").equals("X"));
				ts.setBuiltInKitchen(rs.getString("builtInKitchen").equals("X"));

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

				String insertApartmentSQL = "INSERT INTO apartment(id, floor, rent, rooms, balcony, builtInKitchen) VALUES (?, ?, ?, ?, ?, ?)";

				PreparedStatement pstmtApartment = con.prepareStatement(
						insertApartmentSQL, Statement.RETURN_GENERATED_KEYS);

				pstmtApartment.setInt(1, getId());
				pstmtApartment.setInt(2, getFloor());
				pstmtApartment.setDouble(3, getRent().doubleValue());
				pstmtApartment.setInt(4, getRooms());
				String balcony, builtInKitchen;
				if (isBalcony()) {
					balcony = "X";
				} else {
					balcony = " ";
				}

				if (isBuiltInKitchen()) {
					builtInKitchen = "X";
				} else {
					builtInKitchen = " ";
				}
				pstmtApartment.setString(5, balcony);
				pstmtApartment.setString(6, builtInKitchen);
				pstmtApartment.executeUpdate();

				ResultSet rsApartment = pstmtApartment.getGeneratedKeys();
				if (rsApartment.next()) {
					// setId(rs.getInt(1));
				}

				rsApartment.close();
				pstmtApartment.close();
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

				String updateApartmentSQL = "UPDATE apartment SET floor = ?, rent = ?, rooms = ?, balcony = ?, builtInKitchen = ? WHERE id = ?";
				PreparedStatement pstmtApartment = con
						.prepareStatement(updateApartmentSQL);

				// Setze Anfrage Parameter
				pstmtApartment.setInt(1, getFloor());
				pstmtApartment.setDouble(2, getRent().doubleValue());
				pstmtApartment.setInt(3, getRooms());
				String balcony, builtInKitchen;
				if (isBalcony()) {
					balcony = "X";
				} else {
					balcony = " ";
				}

				if (isBuiltInKitchen()) {
					builtInKitchen = "X";
				} else {
					builtInKitchen = " ";
				}
				pstmtApartment.setString(4, balcony);
				pstmtApartment.setString(5, builtInKitchen);
				pstmtApartment.setInt(6, getId());
				pstmtApartment.executeUpdate();

				pstmtApartment.close();
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
			String deleteApartmentSQL = "DELETE FROM apartment WHERE id = ?";
			String deleteEstateSQL = "DELETE FROM estate WHERE id = ?";
			PreparedStatement pstmtApartment = con.prepareStatement(deleteApartmentSQL);
			PreparedStatement pstmtEstate = con.prepareStatement(deleteEstateSQL);
			pstmtApartment.setInt(1, getId());
			pstmtEstate.setInt(1, getId());

			// Führe Aktualisierung aus
			//con.setAutoCommit(false);
			if (pstmtApartment.execute()) {
				pstmtApartment.close();
					//con.commit();
					//con.setAutoCommit(true);
			}
			if (pstmtEstate.execute()) {
				pstmtEstate.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
