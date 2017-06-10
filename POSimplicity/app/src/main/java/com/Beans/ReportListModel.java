package com.Beans;

public class ReportListModel {
	
	private String nameOfField;
	private String valueOfField;	
	
	
	public ReportListModel(String nameOfField, String valueOfField) {
		super();
		this.nameOfField = nameOfField;
		this.valueOfField = valueOfField;
	}
	
	public String getNameOfField() {
		return nameOfField;
	}
	public void setNameOfField(String nameOfField) {
		this.nameOfField = nameOfField;
	}
	public String getValueOfField() {
		return valueOfField;
	}
	public void setValueOfField(String valueOfField) {
		this.valueOfField = valueOfField;
	}

	@Override
	public String toString() {
		return "ReportListModel [nameOfField=" + nameOfField
				+ ", valueOfField=" + valueOfField + "]";
	}
}
