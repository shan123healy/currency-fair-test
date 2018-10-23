package com.currencyfair.domain;

public class CountrySumDTO {
	
	private Double sellSum;
	
	private Double buySum;
	
	private String originatingCountry;

	public Double getSellSum() {
		return sellSum;
	}

	public void setSellSum(Double sellSum) {
		this.sellSum = sellSum;
	}

	public Double getBuySum() {
		return buySum;
	}

	public void setBuySum(Double buySum) {
		this.buySum = buySum;
	}

	public String getOriginatingCountry() {
		return originatingCountry;
	}

	public void setOriginatingCountry(String originatingCountry) {
		this.originatingCountry = originatingCountry;
	}

	@Override
	public String toString() {
		return "CountrySumDTO [sellSum=" + sellSum + ", buySum=" + buySum
				+ ", originatingCountry=" + originatingCountry + "]";
	}

	
}
