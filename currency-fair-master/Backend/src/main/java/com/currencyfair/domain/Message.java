package com.currencyfair.domain;

import java.sql.Date;

/**
 * Class that represents the database Message entity.
 * 
 * 
 *
 */
public class MessageDTO {

	private Long userId;

	private String currencyFrom;
	
	private String currencyTo;
	
	private Double amountSell;
	
	private Double amountBuy;
	
	private Double rate;
	
	private String timePlaced;
	
	private Date timePlacedF;
	
	private String originatingCountry;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCurrencyFrom() {
		return currencyFrom;
	}

	public void setCurrencyFrom(String currencyFrom) {
		this.currencyFrom = currencyFrom;
	}

	public String getCurrencyTo() {
		return currencyTo;
	}

	public void setCurrencyTo(String currencyTo) {
		this.currencyTo = currencyTo;
	}

	public Double getAmountSell() {
		return amountSell;
	}

	public void setAmountSell(Double amountSell) {
		this.amountSell = amountSell;
	}

	public Double getAmountBuy() {
		return amountBuy;
	}

	public void setAmountBuy(Double amountBuy) {
		this.amountBuy = amountBuy;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public String getTimePlaced() {
		return timePlaced;
	}

	public void setTimePlaced(String timePlaced) {
		this.timePlaced = timePlaced;
	}
	
	public Date getTimePlacedF() {
		return timePlacedF;
	}

	public void setTimePlacedF(Date timePlacedF) {
		this.timePlacedF = timePlacedF;
	}

	public String getOriginatingCountry() {
		return originatingCountry;
	}

	public void setOriginatingCountry(String originatingCountry) {
		this.originatingCountry = originatingCountry;
	}

	@Override
	public String toString() {
		return "MessageDTO [userId=" + userId + ", currencyFrom="
				+ currencyFrom + ", currencyTo=" + currencyTo + ", amountSell="
				+ amountSell + ", amountBuy=" + amountBuy + ", rate=" + rate
				+ ", timePlaced=" + timePlaced + ", timePlacedF=" + timePlacedF
				+ ", originatingCountry=" + originatingCountry + "]";
	}		
	
}
