package org.position.objects;

import java.io.Serializable;

/**
 * @author sgrotz
 * Sample trade object. Each thread will use the trade object to fire it against Terracotta. 
 * Make sure it is serializable :)
 */
public class trade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3819340811046918531L;

	private String ID;
	private String BUYSELL;
	private String STOCK;
	private int QUANTITY;
	private Double PRICE;
	private String CCY;
	
	
	public String getCCY() {
		return CCY;
	}
	public void setCCY(String cCY) {
		CCY = cCY;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getBUYSELL() {
		return BUYSELL;
	}
	public void setBUYSELL(String bUYSELL) {
		BUYSELL = bUYSELL;
	}
	public String getSTOCK() {
		return STOCK;
	}
	public void setSTOCK(String sTOCK) {
		STOCK = sTOCK;
	}
	public int getQUANTITY() {
		return QUANTITY;
	}
	public void setQUANTITY(int qUANTITY) {
		QUANTITY = qUANTITY;
	}
	public Double getPRICE() {
		return PRICE;
	}
	public void setPRICE(Double pRICE) {
		PRICE = pRICE;
	}
	
	

}
