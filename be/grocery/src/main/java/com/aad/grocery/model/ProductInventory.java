package com.aad.grocery.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name="inventory")
public class ProductInventory {
	
	
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name="productId")
    private Integer productId;
	
	@NotEmpty(message = "Product name is required")
	private String productName;
	
	@NotNull(message = "Product price is required")
	@PositiveOrZero(message = "Price cannot be negative")
	private double productPrice;
	
	@NotNull(message = "Product quantity is required")
	@PositiveOrZero(message = "Quantity cannot be negative")
	private int availableQuantity;
	
	private Date expirationDate;
	
	private String unit;
	
	private String remarks;
	
	private boolean archived; // We do not delete products
	
	
	public ProductInventory(){}
	

	public ProductInventory(String productName, double productPrice, int availableQuantity, Date expirationDate,
			String unit, String remarks) {
		super();
		this.productName = productName;
		this.productPrice = productPrice;
		this.availableQuantity = availableQuantity;
		this.expirationDate = expirationDate;
		this.unit = unit;
		this.remarks = remarks;
		this.archived = false;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public int getAvailableQuantity() {
		return availableQuantity;
	}

	public void setAvailableQuantity(int availableQuantity) {
		this.availableQuantity = availableQuantity;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public boolean isArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}

	@Override
	public String toString() {
		return "ProductInventory [productId=" + productId + ", productName=" + productName + ", productPrice="
				+ productPrice + ", availableQuantity=" + availableQuantity + ", expirationDate=" + expirationDate
				+ ", unit=" + unit + ", remarks=" + remarks + ", archived=" + archived + "]";
	}
	
	
	
	
	
	

}
