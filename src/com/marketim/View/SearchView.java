package com.marketim.View;

public class SearchView {
	
	private static  String search;

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		SearchView.search = search;
	}
	
	public String search() {
		
		return "searchProduct.xhtml?faces-redirect=true";
	}
	
	public static void searchReset() {
		search = null;
	}

}
