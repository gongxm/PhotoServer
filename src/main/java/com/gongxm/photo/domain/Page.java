package com.gongxm.photo.domain;

/**
 * @author 作者 : gongxm
 * @version 创建时间：2019年12月30日 上午12:00:05
 * @description 描述 :
 * 
 */
public class Page {
	private int previousPage;
	private int nextPage;
	private int startIndex;
	private int endIndex;
	private int currentPage;
	private int totalPage;

	private String baseUrl;

	private int pageSize = 24;

	public Page() {
	}

	public Page(String baseUrl, int totalNum, int currentPage, int pageSize) {
		this.baseUrl = baseUrl;
		this.totalPage = totalNum / pageSize;
		if (totalNum % pageSize != 0) {
			totalPage += 1;
		}

		this.currentPage = currentPage;

		this.previousPage = currentPage - 1;

		if (currentPage == totalPage || totalPage == 0) {
			this.nextPage = 0;
		} else {
			this.nextPage = currentPage + 1;
		}

		this.startIndex = currentPage - 5;
		if (currentPage <= 5 || this.totalPage<10) {
			this.startIndex = 1;
		}

		this.endIndex = startIndex + 9;
		if (endIndex > totalPage) {
			endIndex = totalPage;
		}
	}

	public Page(String baseUrl, int totalNum, int currentPage) {
		this(baseUrl, totalNum, currentPage, 24);
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPreviousPage() {
		return previousPage;
	}

	public void setPreviousPage(int previousPage) {
		this.previousPage = previousPage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public String toString() {
		return "Page [previousPage=" + previousPage + ", nextPage=" + nextPage + ", startIndex=" + startIndex
				+ ", endIndex=" + endIndex + ", currentPage=" + currentPage + ", totalPage=" + totalPage + ", pageSize="
				+ pageSize + "]";
	}

}
