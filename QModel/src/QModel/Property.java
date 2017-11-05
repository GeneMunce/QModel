/*
* Copyright (c) 2017, Gene Munce
* All rights reserved.
*/
package QModel;

public class Property {
	static int currentPropertyId = 1000;
	private int propertyId;
	private int propertyValue;
	private String propertyName;

	public Property(int propertyValue, String propertyName) {
		super();
		this.propertyId = currentPropertyId++;
		this.propertyValue = propertyValue;
		this.propertyName = propertyName;
	}

	public int getPropertyId() {
		return propertyId;
	}
	/*
	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}
	*/
	public int getPropertyValue() {
		return propertyValue;
	}
	public void setPropertyValue(int propertyValue) {
		this.propertyValue = propertyValue;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
}
