package com.Beans;

public class CategoryModel implements Comparable<CategoryModel>{

	private  String deptId;
	private  String depStatus;
	private  String deptName;

	public CategoryModel(String deptId, String depStatus, String deptName) {
		super();
		this.deptId = deptId;
		this.depStatus = depStatus;
		this.deptName = deptName;
	}

	public CategoryModel() {}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDepStatus() {
		return depStatus;
	}

	public void setDepStatus(String depStatus) {
		this.depStatus = depStatus;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Override
	public String toString() {
		return "CategoryModel [deptId=" + deptId + ", depStatus=" + depStatus
				+ ", deptName=" + deptName + "]";
	}

	@Override
	public int compareTo(CategoryModel another) {
		return Integer.parseInt(deptId) - Integer.parseInt(another.getDeptId());
	}
}