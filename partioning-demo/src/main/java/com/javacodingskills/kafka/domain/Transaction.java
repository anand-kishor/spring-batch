package com.javacodingskills.kafka.domain;

import java.math.BigDecimal;
import java.util.Date;
public class Transaction {
	private String account;
	private BigDecimal amount;
	private Date timestamp;
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}

	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	


}
