package com.currencyfair.domain;

public class RateAvgDTO {
	
	private Double rateAvg;
	
	private String yearMonth;

	public Double getRateAvg() {
		return rateAvg;
	}

	public void setRateAvg(Double rateAvg) {
		this.rateAvg = rateAvg;
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	@Override
	public String toString() {
		return "RateAvgDTO [rateAvg=" + rateAvg + ", yearMonth=" + yearMonth
				+ "]";
	}

}
