package com.aad.grocery.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name="sales")
public class SalesTransaction {
	
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name="transaction_id")
    private Integer transactionId;
	
	@NotNull(message = "Date is required")
	private Date dateOfTransaction;
	
	@OneToMany( mappedBy = "salesTransaction", cascade = CascadeType.ALL)
	private Set<ProductQuantitySales> productToQtySold = new HashSet<ProductQuantitySales>();;
	
	@NotNull(message = "Amount tendered is required")
	@PositiveOrZero(message = "Amount tendered cannot be negative")
	private Double amountTendered;

	private Double discount = 0.0;
	
	private Boolean archived = false;

	public SalesTransaction() {}
	
	@Override
	public String toString() {
		return "SalesTransactions [transactionId=" + transactionId + ", dateOfTransaction=" + dateOfTransaction
				+ ", productToQtySold=" + productToQtySold + ", amountTendered=" + amountTendered + ", discount="
				+ discount + "]";
	}


	public Integer getTransactionId() {
		return transactionId;
	}


	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}


	public Date getDateOfTransaction() {
		return dateOfTransaction;
	}


	public void setDateOfTransaction(Date dateOfTransaction) {
		this.dateOfTransaction = dateOfTransaction;
	}

	@JsonManagedReference
	public Set<ProductQuantitySales> getProductToQtySold() {
		return productToQtySold;
	}

	public void setProductToQtySold(Set<ProductQuantitySales> productToQtySold) {
		this.productToQtySold = productToQtySold;
	}

	public void setAmountTendered(Double amountTendered) {
		this.amountTendered = amountTendered;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public double getAmountTendered() {
		return amountTendered;
	}


	public void setAmountTendered(double amountTendered) {
		this.amountTendered = amountTendered;
	}


	public double getDiscount() {
		return discount;
	}


	public void setDiscount(double discount) {
		this.discount = discount;
	}
	
	public Boolean getArchived() {
		return archived;
	}

	public void setArchived(Boolean archived) {
		this.archived = archived;
	}



	

}
