package com.sysc3110.project;

public class BoardItem {

	private int row;
	private int column;
	private String name;
	
	public BoardItem(int row, int column, String name) {
		this.row = row;
		this.column = column;
		this.name = name;
	}
	
	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public String getName() {
		return name;
	}
	
}
