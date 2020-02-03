package io.forensic.springboot.Other;

public class MapStats {
	private String allele;
	private String province;
	private String count;
	private float x;
	private float y;
	private String region;
	private String color;

	public MapStats() {

	}

	public MapStats(String allele, String province, String count, float x, float y, String region, String color) {
		super();
		this.allele = allele;
		this.province = province;
		this.count = count;
		this.x = x;
		this.y = y;
		this.region = region;
		this.color = color;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getAllele() {
		return allele;
	}

	public void setAllele(String allele) {
		this.allele = allele;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

}
