package de.dis2015.data;

import java.sql.Date;

public class Sale {
	private String datum;
	private String shop;
	private String article;
	private int sold;
	private double turnover;
	
	public String getDatum() {
		return datum;
	}
	public void setDatum(String datum) {
		this.datum = datum;
	}
	public String getShop() {
		return shop;
	}
	public void setShop(String shop) {
		this.shop = shop;
	}
	public String getArticle() {
		return article;
	}
	public void setArticle(String article) {
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
	public Sale(String datum, String shop, String article, int sold,
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
