package com.currencyfair.domain;

public class AmountSumDTO {
	
	private Double sellSum;
	
	private Double buySum;
	
	private String yearMonth;

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

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	@Override
	public String toString() {
		return "AmountSumDTO [sellSum=" + sellSum + ", buySum=" + buySum
				+ ", yearMonth=" + yearMonth + "]";
	}


}
