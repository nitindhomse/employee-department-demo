package com.demo.app.employeeDepartmentCRUDOps.dto;
public class SearchCriteria {
	
	
	private Integer max = 10;
	private Integer offset = 0;
	private String sortBy = "";
	private String sortDir = "ASC";
	private String searchKey = "";
	public Integer getMax() {
		return max;
	}
	public void setMax(Integer max) {
		this.max = max;
	}
	
	
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	public String getSortDir() {
		return sortDir;
	}
	public void setSortDir(String sortDir) {
		this.sortDir = sortDir;
	}
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	@Override
	public String toString() {
		return "SearchCriteria [max=" + max + ", offset=" + offset + ", sortBy=" + sortBy + ", sortDir=" + sortDir
				+ ", searchKey=" + searchKey + "]";
	}
	
	
	
	
}
