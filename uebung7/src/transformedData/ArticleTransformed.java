package transformedData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import de.dis2015.data.DB2ConnectionManager;

/*
 * CREATE TABLE article ( articleid INTEGER NOT NULL PRIMARY KEY,
 articlename VARCHAR(255),
 productGroup VARCHAR(255),
 productFamily VARCHAR(255),
 productCategory VARCHAR(255),
 price DOUBLE);
 * */

public class ArticleTransformed {
	int articleID;
	String articleName;
	String productGroup;
	String productFamily;
	String productCategory;
	double price;

	public int getArticleID() {
		return articleID;
	}

	public void setArticleID(int articleID) {
		this.articleID = articleID;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public String getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(String productGroup) {
		this.productGroup = productGroup;
	}

	public String getProductFamily() {
		return productFamily;
	}

	public void setProductFamily(String productFamily) {
		this.productFamily = productFamily;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public ArticleTransformed(int articleID, String articleName,
			String productGroup, String productFamily, String productCategory,
			double price) {
		super();
		this.articleID = articleID;
		this.articleName = articleName;
		this.productGroup = productGroup;
		this.productFamily = productFamily;
		this.productCategory = productCategory;
		this.price = price;
	}

	@Override
	public String toString() {
		return "ArticleTransformed [articleID=" + articleID + ", articleName="
				+ articleName + ", productGroup=" + productGroup
				+ ", productFamily=" + productFamily + ", productCategory="
				+ productCategory + ", price=" + price + "]";
	}

	public static void unloadAll() {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		try {
			// Achtung, hier wird noch ein Parameter mitgegeben,
			// damit spC$ter generierte IDs zurC<ckgeliefert werden!
			String insertSQL = "DELETE FROM article";

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
			String insertSQL = "INSERT INTO article(articleid, articlename, productGroup, productFamily, productCategory, price) VALUES (?, ?, ?, ?, ?, ?)";

			PreparedStatement pstmt = con.prepareStatement(insertSQL,
					Statement.RETURN_GENERATED_KEYS);

			// Setze Anfrageparameter und fC<hre Anfrage aus
			pstmt.setInt(1, getArticleID());
			pstmt.setString(2, getArticleName());
			pstmt.setString(3, getProductGroup());
			pstmt.setString(4, getProductFamily());
			pstmt.setString(5, getProductCategory());
			pstmt.setDouble(6, getPrice());
			pstmt.executeUpdate();

			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
