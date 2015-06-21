package de.dis2015.data;

import java.sql.Date;

public class Sale {
	private Date datum;
	private ShopID shop;
	private ArticleID article;
	private int sold;
	private double turnover;
	public Date getDatum() {
		return datum;
	}
	public void setDatum(Date datum) {
		this.datum = datum;
	}
	public ShopID getShop() {
		return shop;
	}
	public void setShop(ShopID shop) {
		this.shop = shop;
	}
	public ArticleID getArticle() {
		return article;
	}
	public void setArticle(ArticleID article) {
		this.article = article;
	}
	public int getSold() {
		return sold;
	}
	public void setSold(int sold) {
		this.sold = sold;
	}
	public double getTurnover() {
		return turnover;
	}
	public void setTurnover(double turnover) {
		this.turnover = turnover;
	}
	public Sale(Date datum, ShopID shop, ArticleID article, int sold,
			double turnover) {
		super();
		this.datum = datum;
		this.shop = shop;
		this.article = article;
		this.sold = sold;
		this.turnover = turnover;
	}
	
	@Override
	public String toString() {
		return "Sale [datum=" + datum + ", shop=" + shop + ", article="
				+ article + ", sold=" + sold + ", turnover=" + turnover + "]";
	}
}
