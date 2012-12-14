package org.position.objects;

import java.io.Serializable;

public class position implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 569315305487478908L;

	private  String STOCK;
	private  int QUANTITY;
	private  Double EXPOSURE;
	
	
	public String getCURRENCY() {
		return CURRENCY;
	}

	public void setCURRENCY(String cURRENCY) {
		CURRENCY = cURRENCY;
	}

	private String CURRENCY;
	
	
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
	
	public Double getEXPOSURE() {
		return EXPOSURE;
	}
	
	public void setEXPOSURE(Double eXPOSURE) {
		EXPOSURE = eXPOSURE;
	}
	

}
