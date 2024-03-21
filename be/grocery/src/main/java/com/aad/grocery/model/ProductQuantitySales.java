package com.aad.grocery.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="product_qty_sold")
public class ProductQuantitySales {

	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Integer productSalesId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "transaction_id")
	private SalesTransaction salesTransaction;

	
	@NotNull(message = "Product ID cannot be empty")
	private Integer productId;
	
	@NotNull(message = "Quantity cannot be empty")
	private Integer quantity;
	
	@NotNull(message = "Price per unit cannot be empty")
	private Integer pricePerUnit;
	
	private Boolean archived = false;
	
	public ProductQuantitySales () {}

	public Integer getProductSalesId() {
		return productSalesId;
	}

	public void setProductSalesId(Integer productSalesId) {
		this.productSalesId = productSalesId;
	}

	@JsonBackReference
	public SalesTransaction getTransactionId() {
		return salesTransaction;
	}

	public void setTransactionId(SalesTransaction salesTransaction) {
		this.salesTransaction = salesTransaction;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(Integer pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	@JsonIgnore
	public SalesTransaction getSalesTransaction() {
		return salesTransaction;
	}

	public void setSalesTransaction(SalesTransaction salesTransaction) {
		this.salesTransaction = salesTransaction;
	}

	public Boolean getArchived() {
		return archived;
	}

	public void setArchived(Boolean archived) {
		this.archived = archived;
	}

	@Override
	public String toString() {
		return "ProductQuantitySales [productSalesId=" + productSalesId + ", salesTransaction="
				+ ", productId=" + productId + ", quantity=" + quantity + ", pricePerUnit=" + pricePerUnit + "]";
	}

	
	
	
}
